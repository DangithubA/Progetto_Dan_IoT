package device;

import process.MqttSmartObjectConfiguration;
import resource.SmartObjectResource;
import org.eclipse.paho.client.mqttv3.IMqttClient;

import java.util.Map;

/**
 * @author: Daniele Barbieri Powered by Marco Picone
 * @date: 30/01/2021
 * @project: Progetto_Dan_IoT
 */

public interface IMqttSmartObjectDevice {

    public void init(MqttSmartObjectConfiguration smartObjectConfiguration,
                     IMqttClient mqttClient, // gli viene passato mqttclient
                     String deviceId,
                     String baseTopic,
                     Map<String, SmartObjectResource<?>> resourceMap); // gli viene passata una lista di risorse è inserita la classe
                                                                        // base per poter inserire qualsiasi tipo di risorsa
    public void start();

    public void stop();
}

