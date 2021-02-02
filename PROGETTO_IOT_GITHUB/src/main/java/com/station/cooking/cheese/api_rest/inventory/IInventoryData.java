package com.station.cooking.cheese.api_rest.inventory;


import com.station.cooking.cheese.api_rest.exceptions.IInventoryDataException;
import com.station.cooking.cheese.device.PanelMqttSmartObject;

import java.util.HashMap;

public interface IInventoryData {

    public HashMap<String, PanelMqttSmartObject> getControlPanels() throws IInventoryDataException;
    public void createControlPanel(String id, PanelMqttSmartObject panelMqttSmartObject) throws IInventoryDataException;

}
