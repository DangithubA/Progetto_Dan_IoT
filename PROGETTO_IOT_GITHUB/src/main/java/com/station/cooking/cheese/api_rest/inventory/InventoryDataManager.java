package com.station.cooking.cheese.api_rest.inventory;


import com.station.cooking.cheese.api_rest.exceptions.IInventoryDataException;
import com.station.cooking.cheese.device.PanelMqttSmartObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;


public class InventoryDataManager implements IInventoryData{

    private static InventoryDataManager instance = new InventoryDataManager();
    private InventoryDataManager(){}

    final protected Logger logger = LoggerFactory.getLogger(InventoryDataManager.class);

    public static HashMap<String, PanelMqttSmartObject> panelsList = new HashMap<>();

    public static InventoryDataManager getInstance(){
        return instance;
    }



    @Override
    public HashMap<String, PanelMqttSmartObject> getControlPanels() throws IInventoryDataException {

        System.out.println(String.format("%s", panelsList.keySet()));

        return panelsList;

    }

    @Override
    public void createControlPanel(String id, PanelMqttSmartObject panelMqttSmartObject) throws IInventoryDataException {
        panelsList.put(id, panelMqttSmartObject);
        System.out.println(String.format("%s,%s,%s", panelsList.keySet(), id, panelMqttSmartObject));
    }
}
