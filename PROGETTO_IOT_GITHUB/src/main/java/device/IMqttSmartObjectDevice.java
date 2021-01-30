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
                     IMqttClient mqttClient,
                     String deviceId,
                     String baseTopic,
                     Map<String, SmartObjectResource<?>> resourceMap);

    public void start();

    public void stop();
}

