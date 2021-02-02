package com.station.cooking.cheese.api_rest.inventory;


import com.station.cooking.cheese.api_rest.exceptions.IInventoryDataException;
import com.station.cooking.cheese.device.PanelMqttSmartObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;


public class InventoryDataManagerTest{

    private static InventoryDataManagerTest instance = new InventoryDataManagerTest();
    private InventoryDataManagerTest(){}

    final protected Logger logger = LoggerFactory.getLogger(InventoryDataManagerTest.class);

    public static HashMap<String, PanelMqttSmartObject> panelsList = new HashMap<>();

    public static InventoryDataManagerTest getInstance(){
        return instance;
    }


    protected HashMap<String, PanelMqttSmartObject> getControlPanels(){

        System.out.println(String.format("%s", panelsList.keySet()));

        return panelsList;

    }

    public void createControlPanel(String id, PanelMqttSmartObject panelMqttSmartObject) throws IInventoryDataException {
        panelsList.put(id, panelMqttSmartObject);
        System.out.println(String.format("%s,%s,%s", panelsList.keySet(), id, panelMqttSmartObject));
    }
}
