package api_rest.inventory;


import api_rest.exceptions.IInventoryDataException;
import device.PanelMqttSmartObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import process.MqttSmartObjectProcess;

import java.util.List;


    public class InventoryDataManager implements IInventoryData{
        final protected Logger logger = LoggerFactory.getLogger(InventoryDataManager.class);


    @Override
    public List<PanelMqttSmartObject> getControlPanels() throws IInventoryDataException {

        return (List<PanelMqttSmartObject>) MqttSmartObjectProcess.panelsList.values();

    }
}
