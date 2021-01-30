package process;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import device.DemoMqttSmartObject;
import exception.MqttSmartObjectConfigurationException;
import resource.SmartObjectResource;
import resource.TemperatureSensorResource;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

/**
 * Author: Marco Picone, Ph.D. (picone.m@gmail.com)
 * Date: 24/04/2020
 * Project: MQTT BOT Smart Object (mqtt-bot-smartobject)
 */
public class MqttSmartObjectProcess {

    private static final Logger logger = LoggerFactory.getLogger(MqttSmartObjectProcess.class);

    private static final String TAG = "[MQTT-SMARTOBJECT]";

    private static final String MQTT_SMARTOBJECT_CONFIGURATION_FILE = "mqtt_smart_object_conf.yaml";

    private static MqttSmartObjectConfiguration mqttSmartObjectConfiguration;

    public static void main(String[] args) {

        try {

            logger.info("MQTT SmartObject Started !");

            readConfigurationFile();
            startSmartObject();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void startSmartObject(){

        try{

            //Generate a random device ID
            String deviceId = UUID.randomUUID().toString();

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

            logger.info("MQTT Client Connected ! Client Id: {}", deviceId);

            DemoMqttSmartObject demoMqttSmartObject = new DemoMqttSmartObject();
            demoMqttSmartObject.init(mqttSmartObjectConfiguration,
                    mqttClient,
                    deviceId,
                    String.format("iot/device/%s", deviceId),
                    new HashMap<String, SmartObjectResource<?>>(){
                        {
                            put("temperature", new TemperatureSensorResource());

                        }
                    });

            demoMqttSmartObject.start();

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private static void readConfigurationFile() throws MqttSmartObjectConfigurationException {

        try{

            //ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            //File file = new File(classLoader.getResource(WLDT_CONFIGURATION_FILE).getFile());
            File file = new File(MQTT_SMARTOBJECT_CONFIGURATION_FILE);

            ObjectMapper om = new ObjectMapper(new YAMLFactory());

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
