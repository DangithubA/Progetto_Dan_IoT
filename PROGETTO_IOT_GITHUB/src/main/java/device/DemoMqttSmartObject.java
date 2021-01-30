package device;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import message.EventMessage;
import message.TelemetryMessage;
import process.MqttSmartObjectConfiguration;
import resource.SmartObjectResource;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;

/**
 * @author: Daniele Barbieri Powered by Marco Picone
 * @date: 30/01/2021
 * @project: Progetto_Dan_IoT
 */

public class DemoMqttSmartObject implements IMqttSmartObjectDevice {

    private static final Logger logger = LoggerFactory.getLogger(DemoMqttSmartObject.class);

    private final ObjectMapper objectMapper;

    private Semaphore semaphore;

    private MqttSmartObjectConfiguration smartObjectConfiguration;

    private IMqttClient mqttClient;

    private String deviceId;

    private Map<String, SmartObjectResource<?>> resourceMap;

    private static final String HEARTBEAT_EVENT_TYPE = "HEARTBEAT_EVENT";

    private String baseTopic;

    private int messageCount;

    public DemoMqttSmartObject(){
        this.objectMapper = new ObjectMapper();
        this.messageCount = 0;
    }

    @Override
    public void init(MqttSmartObjectConfiguration smartObjectConfiguration,
                     IMqttClient mqttClient,
                     String deviceId,
                     String baseTopic,
                     Map<String, SmartObjectResource<?>> resourceMap) {

        this.smartObjectConfiguration = smartObjectConfiguration;
        this.mqttClient = mqttClient;
        this.deviceId = deviceId;
        this.resourceMap = resourceMap;
        this.baseTopic = baseTopic;

        this.semaphore = new Semaphore(0);

        logger.info("Smart Object correctly initialized ! Resource Number: {}", this.resourceMap.keySet().size());
    }

    private class TelemetryTask extends TimerTask {

        @Override
        public void run() {
            try {

                logger.info("Executing Telemetry Task ...");

                //Update value for each available resource
                //Java Streams - Useful reference link: https://www.baeldung.com/java-maps-streams
                resourceMap.entrySet()
                        .forEach(mapResourceEntry -> {
                            try {
                                if(mapResourceEntry != null){

                                    //Refresh Smart Object value
                                    mapResourceEntry.getValue().refreshValue();

                                    publishTelemetryData(String.format("%s/%s", baseTopic, mapResourceEntry.getKey()),
                                            new TelemetryMessage(System.currentTimeMillis(), mapResourceEntry.getValue().getType(), mapResourceEntry.getValue()));
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
                        put("status", "OK");
                        put("resource_number", resourceMap.entrySet().size());
                        put("connection_type", "GOOD");
                        put("total_message_count", messageCount);
                    }
                });

                publishEventData(String.format("%s/event", baseTopic), eventMessage);

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

                Timer eventTimer = new Timer();
                eventTimer.schedule(new EventTask(), 0, this.smartObjectConfiguration.getEventUpdateTimeMs());

                Timer telemetryTimer = new Timer();
                telemetryTimer.schedule(new TelemetryTask(), 2000, this.smartObjectConfiguration.getTelemetryUpdateTimeMs());

                semaphore.acquire(2);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        //TODO Implement a proper stop ... :)
    }

    private void publishTelemetryData(String topic, TelemetryMessage telemetryMessage) throws MqttException, JsonProcessingException {

        logger.info("Publishing to Topic: {} Smart Object: {}", topic, telemetryMessage);

        if (mqttClient.isConnected() && telemetryMessage != null && topic != null) {

            String msgString = this.objectMapper.writeValueAsString(telemetryMessage);

            MqttMessage msg = new MqttMessage(msgString.getBytes());
            //Set message QoS
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
            //Set message QoS
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

}