package device;

import process.MqttSmartObjectConfiguration;
import resource.SmartObjectResource;
import org.eclipse.paho.client.mqttv3.IMqttClient;

import java.util.Map;

/**
 * Author: Marco Picone, Ph.D. (picone.m@gmail.com)
 * Date: 24/04/2020
 * Project: MQTT BOT Smart Object (mqtt-bot-smartobject)
 */
public interface IMqttSmartObjectDevice {

    public void init(MqttSmartObjectConfiguration smartObjectConfiguration,
                     IMqttClient mqttClient,
                     String deviceId,
                     String baseTopic,
                     Map<String, SmartObjectResource<?>> resourceMap);

    public void start();

    public void stop();
}

