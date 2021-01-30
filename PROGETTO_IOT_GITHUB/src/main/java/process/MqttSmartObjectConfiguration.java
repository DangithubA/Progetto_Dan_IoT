package process;

/**
 * @author: Daniele Barbieri Powered by Marco Picone
 * @date: 30/01/2021
 * @project: Progetto_Dan_IoT
 */


public class MqttSmartObjectConfiguration {

    private String mqttBrokerAddress;

    private int mqttBrokerPort;

    private String deviceNameSpace;

    private int eventUpdateTimeMs;

    private int telemetryUpdateTimeMs;

    private int totalTelemetryMessageLimit = 10000;

    private int startUpDelayMs = 5000;

    private int mqttOutgoingClientQoS = 0;

    public MqttSmartObjectConfiguration() {
    }

    public MqttSmartObjectConfiguration(String mqttBrokerAddress,
                                        int mqttBrokerPort,
                                        String deviceNameSpace,
                                        int eventUpdateTimeMs,
                                        int telemetryUpdateTimeMs,
                                        int totalTelemetryMessageLimit,
                                        int startUpDelayMs,
                                        int mqttOutgoingClientQoS) {
        this.mqttBrokerAddress = mqttBrokerAddress;
        this.mqttBrokerPort = mqttBrokerPort;
        this.deviceNameSpace = deviceNameSpace;
        this.eventUpdateTimeMs = eventUpdateTimeMs;
        this.telemetryUpdateTimeMs = telemetryUpdateTimeMs;
        this.totalTelemetryMessageLimit = totalTelemetryMessageLimit;
        this.startUpDelayMs = startUpDelayMs;
        this.mqttOutgoingClientQoS = mqttOutgoingClientQoS;
    }

    public String getMqttBrokerAddress() {
        return mqttBrokerAddress;
    }

    public void setMqttBrokerAddress(String mqttBrokerAddress) {
        this.mqttBrokerAddress = mqttBrokerAddress;
    }

    public int getMqttBrokerPort() {
        return mqttBrokerPort;
    }

    public void setMqttBrokerPort(int mqttBrokerPort) {
        this.mqttBrokerPort = mqttBrokerPort;
    }

    public String getDeviceNameSpace() {
        return deviceNameSpace;
    }

    public void setDeviceNameSpace(String deviceNameSpace) {
        this.deviceNameSpace = deviceNameSpace;
    }

    public int getEventUpdateTimeMs() {
        return eventUpdateTimeMs;
    }

    public void setEventUpdateTimeMs(int eventUpdateTimeMs) {
        this.eventUpdateTimeMs = eventUpdateTimeMs;
    }

    public int getTelemetryUpdateTimeMs() {
        return telemetryUpdateTimeMs;
    }

    public void setTelemetryUpdateTimeMs(int telemetryUpdateTimeMs) {
        this.telemetryUpdateTimeMs = telemetryUpdateTimeMs;
    }

    public int getTotalTelemetryMessageLimit() {
        return totalTelemetryMessageLimit;
    }

    public void setTotalTelemetryMessageLimit(int totalTelemetryMessageLimit) {
        this.totalTelemetryMessageLimit = totalTelemetryMessageLimit;
    }

    public int getStartUpDelayMs() {
        return startUpDelayMs;
    }

    public void setStartUpDelayMs(int startUpDelayMs) {
        this.startUpDelayMs = startUpDelayMs;
    }

    public int getMqttOutgoingClientQoS() {
        return mqttOutgoingClientQoS;
    }

    public void setMqttOutgoingClientQoS(int mqttOutgoingClientQoS) {
        this.mqttOutgoingClientQoS = mqttOutgoingClientQoS;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("MqttSmartObjectConfiguration{");
        sb.append("mqttBrokerAddress='").append(mqttBrokerAddress).append('\'');
        sb.append(", mqttBrokerPort=").append(mqttBrokerPort);
        sb.append(", deviceNameSpace='").append(deviceNameSpace).append('\'');
        sb.append(", eventUpdateTimeMs=").append(eventUpdateTimeMs);
        sb.append(", telemetryUpdateTimeMs=").append(telemetryUpdateTimeMs);
        sb.append(", totalTelemetryMessageLimit=").append(totalTelemetryMessageLimit);
        sb.append(", startUpDelayMs=").append(startUpDelayMs);
        sb.append(", mqttOutgoingClientQoS=").append(mqttOutgoingClientQoS);
        sb.append('}');
        return sb.toString();
    }
}
