package exception;

/**
 * @author: Daniele Barbieri Powered by Marco Picone
 * @date: 30/01/2021
 * @project: Progetto_Dan_IoT
 */

public class MqttSmartObjectConfigurationException extends Exception {

    public MqttSmartObjectConfigurationException(String msg){
        super(msg);
    }
}