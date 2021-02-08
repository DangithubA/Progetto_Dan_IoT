package com.station.cooking.cheese.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.station.cooking.cheese.device.PanelMqttSmartObject;
import com.station.cooking.cheese.process.MqttSmartObjectConfiguration;
import com.station.cooking.cheese.resource.SmartObjectResource;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.HashMap;
import java.util.concurrent.Semaphore;


/**
 * @author: Daniele Barbieri
 * @date: 30/01/2021
 * @project: Progetto_Dan_IoT
 */


public class PanelDescriptor implements Serializable {

    private RecipeDescriptor recipe;

    private String deviceId;

    public PanelDescriptor(){}

    public PanelDescriptor(String deviceId, RecipeDescriptor recipe) {
        this.recipe = recipe;
        this.deviceId = deviceId;
    }

    public RecipeDescriptor getRecipe() {
        return recipe;
    }

    public void setRecipe(RecipeDescriptor recipe) {
        this.recipe = recipe;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public String toString() {
        return "PanelDescriptor{" +
                "recipe=" + recipe +
                ", deviceId='" + deviceId + '\'' +
                '}';
    }
}
