package com.station.cooking.cheese.process;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.station.cooking.cheese.device.PanelMqttSmartObject;
import com.station.cooking.cheese.exception.MqttSmartObjectConfigurationException;
import com.station.cooking.cheese.model.RecipeDescriptor;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author: Daniele Barbieri Powered by Marco Picone
 * @date: 30/01/2021
 * @project: Progetto_Dan_IoT
 */

//Questo è il main per il comportamento dello smartobject che lancia gli smart object ;


public class MqttSmartObjectProcess {

    private static RecipeDescriptor recipe;

    private static final Logger logger = LoggerFactory.getLogger(MqttSmartObjectProcess.class);

    private static final String TAG = "[MQTT-SMARTOBJECT]";

    private static final String MQTT_SMARTOBJECT_CONFIGURATION_FILE = "mqtt_smart_object_conf.yaml";

    private static MqttSmartObjectConfiguration mqttSmartObjectConfiguration;

    public static HashMap<String, PanelMqttSmartObject> panelsList = new HashMap<>(); //AGGIUNTO DA TOBI

    public static void main(String[] args) {

        // INSERITO PER CARICARE LA RICETTA
        ObjectMapper om = new ObjectMapper();

        try {

            recipe = om.readValue(new File("data/recipe.json"), RecipeDescriptor.class);
            // Stampa il set di partenza della sonda prelevato dalla ricetta ricevuta
            System.out.println("Set di partenza della sonda prelevato dalla ricetta ricevuta");
            System.out.println(recipe.getTemperatures().get(0));

        } catch (IOException e) {
            e.printStackTrace();
        }


        try {

            logger.info("MQTT SmartObject Started !");

            readConfigurationFile(); // IL MAIN E' SCOMPOSTO IN DUE METODI
            startSmartObject();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void startSmartObject(){

        try{

            //Generate a random com.station.cooking.cheese.device ID
            //String deviceId = UUID.randomUUID().toString(); DAN MODIFICATO PER METTERE ID FISSO
            String deviceId = "P01";
            //Create Mqtt Client with a Memory Persistence
            MqttClientPersistence persistence = new MemoryPersistence();
            IMqttClient mqttClient = new MqttClient(String.format("tcp://%s:%d",
                    mqttSmartObjectConfiguration.getMqttBrokerAddress(),
                    mqttSmartObjectConfiguration.getMqttBrokerPort()),
                    deviceId,
                    persistence);

            MqttConnectOptions options = new MqttConnectOptions();
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);
            options.setConnectionTimeout(10);
            mqttClient.connect(options);

            logger.info("AAAAAAAA MQTT Client Connected ! Client Id: {}", deviceId);

            PanelMqttSmartObject panelMqttSmartObject = new PanelMqttSmartObject();
            System.out.println("Stampa elenco aggiornato HashMap della lista pannelli attivi 1");
            System.out.println(panelsList);
            panelMqttSmartObject.init(mqttSmartObjectConfiguration,
                    mqttClient,
                    deviceId,
                    String.format("iot/com.station.cooking.cheese.device/%s", deviceId));//,
                    //new HashMap<String, SmartObjectResource<?>>(){
                    //    {
                    //        put("temperature", new TemperatureSensorResource(recipe));
                    //        put("Mixer", new MotorMixerResource());
                    //        put("SteamValve", new ValveResource());

                    //    }
                    //}
                    //);
            //Thread.sleep(4000);
            panelsList.put(deviceId, panelMqttSmartObject);


            Thread newThread = new Thread(() -> {
                try{
                    panelMqttSmartObject.start();
                }catch (Exception e){
                    e.printStackTrace();
                }

            });
            newThread.start();

            System.out.println("Stampa elenco aggiornato HashMap della lista pannelli attivi 2");
            System.out.println(panelsList);
            System.out.println("Stampa elenco aggiornato HashMap della lista pannelli attivi 3");
            //System.out.println(panelsList);

        }catch (Exception e){
            e.printStackTrace();
        }

        //panelsList.put(deviceId, panelMqttSmartObject);

    }
    // CON LA LIBRERIA JAKSON SI POSSONO LEGGERE DEI FILE YAML
    private static void readConfigurationFile() throws MqttSmartObjectConfigurationException {

        try{

            //ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            //File file = new File(classLoader.getResource(WLDT_CONFIGURATION_FILE).getFile());
            File file = new File(MQTT_SMARTOBJECT_CONFIGURATION_FILE);

            ObjectMapper om = new ObjectMapper(new YAMLFactory()); // INDICA DI DESERIALIZZARE UN OGGETTO DI TIPO YAML

            mqttSmartObjectConfiguration = om.readValue(file, MqttSmartObjectConfiguration.class);

            logger.info("{} MQTT Configuration Loaded ! Conf: {}", TAG, mqttSmartObjectConfiguration);

        }catch (Exception e){
            e.printStackTrace();
            String errorMessage = String.format("ERROR LOADING CONFIGURATION FILE ! Error: %s", e.getLocalizedMessage());
            logger.error("{} {}", TAG, errorMessage);
            throw new MqttSmartObjectConfigurationException(errorMessage);
        }
    }

}
