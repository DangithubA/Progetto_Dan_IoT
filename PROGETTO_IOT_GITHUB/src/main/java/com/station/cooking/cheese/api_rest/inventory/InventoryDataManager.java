package com.station.cooking.cheese.api_rest.inventory;


import com.station.cooking.cheese.api_rest.exceptions.IInventoryDataException;
import com.station.cooking.cheese.device.PanelMqttSmartObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.station.cooking.cheese.process.MqttSmartObjectProcess;

import java.util.List;


    public class InventoryDataManager implements IInventoryData{
        final protected Logger logger = LoggerFactory.getLogger(InventoryDataManager.class);


    @Override
    public List<PanelMqttSmartObject> getControlPanels() throws IInventoryDataException {

        return (List<PanelMqttSmartObject>) MqttSmartObjectProcess.panelsList.values();

    }
}
