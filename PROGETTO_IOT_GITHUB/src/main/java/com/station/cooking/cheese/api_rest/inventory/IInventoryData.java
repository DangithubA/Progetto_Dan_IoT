package com.station.cooking.cheese.api_rest.inventory;


import com.station.cooking.cheese.api_rest.exceptions.IInventoryDataException;
import com.station.cooking.cheese.device.PanelMqttSmartObject;
import java.util.List;

public interface IInventoryData {

    public List<PanelMqttSmartObject> getControlPanels() throws IInventoryDataException;

}
