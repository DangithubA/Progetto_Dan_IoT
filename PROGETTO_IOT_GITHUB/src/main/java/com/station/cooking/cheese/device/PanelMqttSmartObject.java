package com.station.cooking.cheese.device;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.gson.Gson;
import com.station.cooking.cheese.message.EventMessage;
import com.station.cooking.cheese.message.TelemetryMessage;
import com.station.cooking.cheese.model.PanelDescriptor;
import com.station.cooking.cheese.model.RecipeDescriptor;
import com.station.cooking.cheese.process.MqttSmartObjectConfiguration;
import com.station.cooking.cheese.resource.SmartObjectResource;
import com.station.cooking.cheese.resource.MotorMixerResource;
import com.station.cooking.cheese.resource.ValveResource;
import com.station.cooking.cheese.resource.TemperatureSensorResource;

import com.station.cooking.cheese.utils.SenMLPack;
import com.station.cooking.cheese.utils.SenMLRecord;
import jdk.internal.org.objectweb.asm.TypeReference;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;
import java.io.Serializable;

/**
 * @author: Daniele Barbieri Powered by Marco Picone
 * @date: 30/01/2021
 * @project: Progetto_Dan_IoT
 */

public class PanelMqttSmartObject implements IMqttSmartObjectDevice{ // implementazione forza a dover implementare i metodi dell'interfaccia

    private static final Logger logger = LoggerFactory.getLogger(PanelMqttSmartObject.class);

    private final ObjectMapper objectMapper;

    private RecipeDescriptor recipe;   // DAN TOLTO STATIC

    private Semaphore semaphore;

    private MqttSmartObjectConfiguration smartObjectConfiguration;

    private IMqttClient mqttClient;

    private String deviceId;

    private HashMap<String, SmartObjectResource<?>> resourceMap;

    private static final String HEARTBEAT_EVENT_TYPE = "HEARTBEAT_EVENT";

    private static final String INFO_HEARTBEAT_EVENT_TYPE = "INFO_HEARTBEAT_EVENT";

    private Timer eventTimer;

    private Timer telemetryTimer;

    private String baseTopic;

    private int messageCount;

    public HashMap<String, SmartObjectResource<?>> getResourceMap() {
        return resourceMap;
    }

    public PanelMqttSmartObject(){
        this.objectMapper = new ObjectMapper();  // crea un oggetto di jakson per la serializzazione deserializzazione
        this.messageCount = 0;
    }

    public String getBaseTopic() {
        return baseTopic;
    }

    @Override
    public void init(MqttSmartObjectConfiguration smartObjectConfiguration,
                     IMqttClient mqttClient,
                     String deviceId,
                     String baseTopic) {
                     //String baseTopic,
                     //Map<String, SmartObjectResource<?>> resourceMap) {

        this.smartObjectConfiguration = smartObjectConfiguration;
        this.mqttClient = mqttClient;
        this.deviceId = deviceId;
        //this.resourceMap = resourceMap;
        this.resourceMap = new HashMap<String, SmartObjectResource<?>>(); // NEW DAN
        this.baseTopic = baseTopic;

        this.semaphore = new Semaphore(0);

        // INSERITO PER CARICARE LA RICETTA ERA IN MqttSmartObjectProcess
        //ObjectMapper om = new ObjectMapper();

        try {

            //recipe = om.readValue(new File("src/main/java/com.station.cooking.cheese.data/recipe.json"), RecipeDescriptor.class);

            HashMap panelDescriptors = new HashMap<>();
            TypeFactory factory;
            MapType type;

            factory = TypeFactory.defaultInstance();
            type    = factory.constructMapType(HashMap.class, String.class, PanelDescriptor.class);

            panelDescriptors = objectMapper.readValue(new File("data/panelsDatabase.json"), type);

            PanelDescriptor panelDesc = (PanelDescriptor) panelDescriptors.get(deviceId);

            recipe = panelDesc.getRecipe();

            //recipe = objectMapper.readValue(new File("data/recipe.json"), RecipeDescriptor.class);
            // Stampa il set di partenza della sonda prelevato dalla ricetta ricevuta
            System.out.println("Set di partenza della sonda prelevato dalla ricetta ricevuta");
            System.out.println(recipe.getTemperatures().get(0));

        } catch (IOException e) {
            e.printStackTrace();
        }

        //new HashMap<String, SmartObjectResource<?>>(){
        //    {
        //        put("temperature", new TemperatureSensorResource(recipe));
        //        put("Mixer", new MotorMixerResource());
        //        put("SteamValve", new ValveResource());

        //    }
        this.resourceMap.put("Temperature", new TemperatureSensorResource(recipe));
        this.resourceMap.put("Mixer", new MotorMixerResource());
        this.resourceMap.put("SteamValve", new ValveResource());

        logger.info("Smart Object correctly initialized ! Resource Number: {}", this.resourceMap.keySet().size());
    }

    private class TelemetryTask extends TimerTask {

        @Override
        public void run() {
            try {

                logger.info("Executing Telemetry Task ...");

                //Update value for each available com.station.cooking.cheese.resource
                //Java Streams - Useful reference link: https://www.baeldung.com/java-maps-streams
                //Esempio
                //resourceMap.entrySet().stream().forEach(entry ->{
                //    String key = entry.getKey();
                //    SmartObjectResource objectResource = entry.getValue();
                //});

                // si puo abbreviare
                //resourceMap.entrySet().forEach(entry ->{
                //
                //});

                resourceMap.entrySet()  // lista delle chiavi della mappa           // DAN JAVA STREAM FLUSSI OTTIMIZZATI PER NAVIGARE LE COLLECTION
                        .forEach(mapResourceEntry -> {
                            try {
                                if(mapResourceEntry != null){

                                    //Refresh Smart Object value
                                    SmartObjectResource smartObjectResource = mapResourceEntry.getValue(); //Queste due righe sotto formula abbreviata
                                    //smartObjectResource.refreshValue(0.5);
                                    //smartObjectResource.refreshValue();
                                    //mapResourceEntry.getValue().refreshValue(0.5); // SOSPESO DA DAN IMPLEMENTARE REFRESH HO SCRITTO (INCREASE)
                                    publishTelemetryData(String.format("%s/%s", baseTopic, mapResourceEntry.getKey()), //METODO PUBLISH RIGA181
                                                            smartObjectResource);
                                            //new TelemetryMessage(System.currentTimeMillis(), mapResourceEntry.getValue().getType(), mapResourceEntry.getValue()));
                                }
                            } catch (MqttException | JsonProcessingException e) {
                                logger.error("Error Publishing Message ! Error: {}", e.getLocalizedMessage());
                            }
                        });

            } catch (Exception e) {
                e.printStackTrace();
                cancel();
                semaphore.release();
            }
        }
    }

    private class EventTask extends TimerTask {
        @Override
        public void run() {
            try {

                logger.info("Event Timer Task ! ....");

                //Generating a Random Event Message
                EventMessage eventMessage = new EventMessage();
                eventMessage.setType(HEARTBEAT_EVENT_TYPE);
                eventMessage.setTimestamp(System.currentTimeMillis());
                eventMessage.setMetadata(new HashMap<String, Object>(){
                    {
                        put("panel_id", deviceId);
                        put("software_version:", "0.0.1");
                        put("status", "OK");
                        put("resource_number", resourceMap.entrySet().size());
                        put("connection_type", "GOOD");
                        put("total_message_count", messageCount);
                    }
                });

                publishEventData(String.format("%s/info", baseTopic), eventMessage);

            } catch (Exception e) {
                e.printStackTrace();
                cancel();
                semaphore.release();
            }
        }
    }

    @Override
    public void start(){

        try {

            if(this.smartObjectConfiguration != null &&
                    this.mqttClient != null &&
                    this.deviceId != null &&
                    this.resourceMap != null &&
                    this.baseTopic != null){

                logger.info("Waiting {} ms before starting ...", this.smartObjectConfiguration.getStartUpDelayMs()) ;
                Thread.sleep(this.smartObjectConfiguration.getStartUpDelayMs());

                eventTimer = new Timer();
                eventTimer.schedule(new EventTask(), 0, this.smartObjectConfiguration.getEventUpdateTimeMs()); //getEventUpdateTimeMs è il perido specificato della configurazione

                telemetryTimer = new Timer();
                telemetryTimer.schedule(new TelemetryTask(), 2000, this.smartObjectConfiguration.getTelemetryUpdateTimeMs());

                semaphore.acquire(2); // la classe deve rimanere attiva sino a che i due timer non hanno finito
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {

        eventTimer.cancel();
        telemetryTimer.cancel();

    }

    //private void publishTelemetryData(String topic, TelemetryMessage telemetryMessage) throws MqttException, JsonProcessingException { // prende topic dove pubblicare


    public void publishAlarmEvent(String topic, String eventString) throws MqttException, JsonProcessingException{
        logger.info("Publishing EVENT-ALARM to Topic: {}", topic);     // per configurare i messaggi vedi telemetryMessagge

        //if (mqttClient.isConnected() && telemetryMessage != null && topic != null) {
        if (mqttClient.isConnected() && topic != null) {

            //String msgString = this.objectMapper.writeValueAsString(telemetryMessage);

            ObjectMapper mapper = new ObjectMapper();

            try {

                SenMLPack senMLPack = new SenMLPack();

                SenMLRecord senMLRecord = new SenMLRecord();
                senMLRecord.setVs(eventString);
                senMLRecord.setT(System.currentTimeMillis());
                senMLPack.add(senMLRecord);

                MqttMessage msg = new MqttMessage(Optional.of(mapper.writeValueAsString(senMLPack)).toString().getBytes());

                msg.setQos(this.smartObjectConfiguration.getMqttOutgoingClientQoS());

                mqttClient.publish(topic, msg);

                this.messageCount++;

                logger.info("Data Correctly Published to topic: {} Message: {}", topic, msg);


            } catch (Exception e) {
                logger.info("Data Not Published to topic: SERVER ERROR");
            }
        }
        else{
            logger.error("Error: Topic or Msg = Null or MQTT Client is not Connected !");
        }

    }
                                                                                                // oggetto telemetrymessagge non di mqtt ma definito e strutturato per
    private void publishTelemetryData(String topic, SmartObjectResource smartObjectResource) throws MqttException, JsonProcessingException {
        //logger.info("Publishing to Topic: {} Smart Object: {}", topic, telemetryMessage);
        logger.info("Publishing to Topic: {} Smart Object: {}", topic, smartObjectResource.getJsonSenmlResponse().toString());     // per configurare i messaggi vedi telemetryMessagge

        //if (mqttClient.isConnected() && telemetryMessage != null && topic != null) {
        if (mqttClient.isConnected() && smartObjectResource.getJsonSenmlResponse().toString() != null && topic != null) {

            //String msgString = this.objectMapper.writeValueAsString(telemetryMessage);

            /*String messaggio = smartObjectResource.getJsonSenmlResponse().toString();

            Gson gson = new Gson();

            messaggio = gson.toJson(messaggio);
            messaggio.*/

            MqttMessage msg = new MqttMessage(smartObjectResource.getJsonSenmlResponse().toString().getBytes());
            //Set com.station.cooking.cheese.message QoS
            msg.setQos(this.smartObjectConfiguration.getMqttOutgoingClientQoS());

            mqttClient.publish(topic,msg);

            this.messageCount++;

            logger.info("Data Correctly Published to topic: {}", topic);
        }
        else{
            logger.error("Error: Topic or Msg = Null or MQTT Client is not Connected !");
        }

    }

    private void publishEventData(String topic, EventMessage eventMessage) throws MqttException, JsonProcessingException {

        logger.info("Publishing to Topic: {} Smart Object: {}", topic, eventMessage);

        if (mqttClient.isConnected() && eventMessage != null && topic != null) {

            String msgString = this.objectMapper.writeValueAsString(eventMessage);

            MqttMessage msg = new MqttMessage(msgString.getBytes());
            //Set com.station.cooking.cheese.message QoS
            msg.setQos(this.smartObjectConfiguration.getMqttOutgoingClientQoS());
            msg.setRetained(true);

            mqttClient.publish(topic,msg);

            logger.info("Data Correctly Published to topic: {}", topic);
        }
        else{
            logger.error("Error: Topic or Msg = Null or MQTT Client is not Connected !");
        }

    }

    public void setMqttClient(IMqttClient mqttClient) {
        this.mqttClient = mqttClient;
    }

    public IMqttClient getMqttClient() {
        return mqttClient;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public RecipeDescriptor getRecipe(){return recipe;}

}