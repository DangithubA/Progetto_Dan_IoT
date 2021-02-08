package com.station.cooking.cheese.device;

import com.station.cooking.cheese.process.MqttSmartObjectConfiguration;
import org.eclipse.paho.client.mqttv3.IMqttClient;

/**
 * @author: Daniele Barbieri
 * @date: 30/01/2021
 * @project: Progetto_Dan_IoT
 */

public interface IMqttSmartObjectDevice {

    public void init(MqttSmartObjectConfiguration smartObjectConfiguration,
                     IMqttClient mqttClient, // gli viene passato mqttclient
                     String deviceId,
                     String baseTopic);
                     //Map<String, SmartObjectResource<?>> resourceMap); // gli viene passata una lista di risorse è inserita la classe
                                                                        // base per poter inserire qualsiasi tipo di risorsa
    public void start();

    public void stop();
}

