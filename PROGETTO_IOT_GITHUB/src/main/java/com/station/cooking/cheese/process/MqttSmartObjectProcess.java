package com.station.cooking.cheese.process;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.station.cooking.cheese.api_rest.inventory.InventoryDataManager;
import com.station.cooking.cheese.api_rest.inventory.InventoryDataManagerTest;
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

    // private static RecipeDescriptor recipe;

    //private static InventoryDataManagerTest inventoryDataManagerTest = null;

    private static MqttSmartObjectProcess mqttSmartObjectProcess = null;

    private static final Logger logger = LoggerFactory.getLogger(MqttSmartObjectProcess.class);

    private static final String TAG = "[MQTT-SMARTOBJECT]";

    private static final String MQTT_SMARTOBJECT_CONFIGURATION_FILE = "mqtt_smart_object_conf.yaml";

    private static MqttSmartObjectConfiguration mqttSmartObjectConfiguration;

    public static HashMap<String, PanelMqttSmartObject> panelsList = new HashMap<>(); //AGGIUNTO DA TOBI

    private MqttSmartObjectProcess(){}

    public static void main(String[] args) {
/**
 // INSERITO PER CARICARE LA RICETTA POI SPOSTATO SU PanelMqttSmartObject
 ObjectMapper om = new ObjectMapper();

 try {

 recipe = om.readValue(new File("data/recipe.json"), RecipeDescriptor.class);
 // Stampa il set di partenza della sonda prelevato dalla ricetta ricevuta
 System.out.println("Set di partenza della sonda prelevato dalla ricetta ricevuta");
 System.out.println(recipe.getTemperatures().get(0));

 } catch (IOException e) {
 e.printStackTrace();
 }
 */

        try {

            //inventoryDataManagerTest = InventoryDataManagerTest.getInstance();


            logger.info("MQTT SmartObject Started !");

            readConfigurationFile(); // IL MAIN E' SCOMPOSTO IN DUE METODI
            startSmartObject();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // CREATE THE PANEL CONTROLL P01
    private static void startSmartObject() {


        try {

            //Generate a random com.station.cooking.cheese.device ID
            //String deviceId01 = UUID.randomUUID().toString(); DAN MODIFICATO PER METTERE ID FISSO
            String deviceId01 = "P01";
            //Create Mqtt Client with a Memory Persistence
            MqttClientPersistence persistence01 = new MemoryPersistence();
            IMqttClient mqttClient01 = new MqttClient(String.format("tcp://%s:%d",
                    mqttSmartObjectConfiguration.getMqttBrokerAddress(),
                    mqttSmartObjectConfiguration.getMqttBrokerPort()),
                    deviceId01,
                    persistence01);

            MqttConnectOptions options = new MqttConnectOptions();
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);
            options.setConnectionTimeout(10);
            mqttClient01.connect(options);

            logger.info("P01 MQTT Client Connected ! Panel Client Id: {}", deviceId01);

            PanelMqttSmartObject panelMqttSmartObject01 = new PanelMqttSmartObject();
            //System.out.println("Stampa elenco aggiornato HashMap della lista pannelli attivi 1");
            //System.out.println(panelsList);
            panelMqttSmartObject01.init(mqttSmartObjectConfiguration,
                    mqttClient01,
                    deviceId01,
                    String.format("iot/com.station.cooking.cheese.device/%s", deviceId01));//,  SPOSTATO SU PanelMqttSmartObject
            //new HashMap<String, SmartObjectResource<?>>(){
            //    {
            //        put("temperature", new TemperatureSensorResource(recipe));
            //        put("Mixer", new MotorMixerResource());
            //        put("SteamValve", new ValveResource());

            //    }
            //}
            //);
            //Thread.sleep(4000);
            panelsList.put(deviceId01, panelMqttSmartObject01);

            //inventoryDataManagerTest.panelsList.put(deviceId01, panelMqttSmartObject01);
            //logger.info("{}", inventoryDataManagerTest.panelsList);


            Thread newThread01 = new Thread(() -> {
                try {
                    panelMqttSmartObject01.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });
            newThread01.start();

            System.out.println("Stampa elenco aggiornato HashMap della lista pannelli attivi dopo la creazione e attivazione di P01");
            System.out.println(panelsList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    //}

        //final int howmanypaneltocreate = 1;
        //if(howmanypaneltocreate==2) {

        try {

        //Generate a random com.station.cooking.cheese.device ID
        //String deviceId01 = UUID.randomUUID().toString(); DAN MODIFICATO PER METTERE ID FISSO
        String deviceId02 = "P02";
        //Create Mqtt Client with a Memory Persistence
        MqttClientPersistence persistence02 = new MemoryPersistence();
        IMqttClient mqttClient02 = new MqttClient(String.format("tcp://%s:%d",
                mqttSmartObjectConfiguration.getMqttBrokerAddress(),
                mqttSmartObjectConfiguration.getMqttBrokerPort()),
                deviceId02,
                persistence02);

        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        options.setConnectionTimeout(10);
        mqttClient02.connect(options);

        logger.info("P01 MQTT Client Connected ! Panel Client Id: {}", deviceId02);

        PanelMqttSmartObject panelMqttSmartObject02 = new PanelMqttSmartObject();
        //System.out.println("Stampa elenco aggiornato HashMap della lista pannelli attivi 1");
        //System.out.println(panelsList);
        panelMqttSmartObject02.init(mqttSmartObjectConfiguration,
                mqttClient02,
                deviceId02,
                String.format("iot/com.station.cooking.cheese.device/%s", deviceId02));//,  SPOSTATO SU PanelMqttSmartObject
        //new HashMap<String, SmartObjectResource<?>>(){
        //    {
        //        put("temperature", new TemperatureSensorResource(recipe));
        //        put("Mixer", new MotorMixerResource());
        //        put("SteamValve", new ValveResource());

        //    }
        //}
        //);
        //Thread.sleep(4000);
        panelsList.put(deviceId02, panelMqttSmartObject02);
        //inventoryDataManagerTest.panelsList.put(deviceId02, panelMqttSmartObject02);

        logger.info("panelsList: {}", panelsList);

        Thread newThread02 = new Thread(() -> {
            try {
                panelMqttSmartObject02.start();
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
        newThread02.start();

        System.out.println("Stampa elenco aggiornato HashMap della lista pannelli attivi dopo la creazione e attivazione di P02");
        System.out.println(panelsList);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }    //} else {

        //System.out.println("creazione pannelli completata");



//****************************************************************************************



//*****************************************************************************************

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

    public static MqttSmartObjectProcess getMqttSmartObjectProcess(){
        if(mqttSmartObjectProcess == null) mqttSmartObjectProcess = new MqttSmartObjectProcess();
        return mqttSmartObjectProcess;
    }

    public HashMap<String, PanelMqttSmartObject> getPanels(){
        return panelsList;
    }

}

