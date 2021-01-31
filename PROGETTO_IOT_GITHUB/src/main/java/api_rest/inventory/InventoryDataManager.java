package api_rest.inventory;


import api_rest.exceptions.IInventoryDataConflict;
import api_rest.exceptions.IInventoryDataException;
import com.fasterxml.jackson.databind.ObjectMapper;
import device.DemoMqttSmartObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import process.MqttSmartObjectProcess;
import utils.SenMLPack;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;


    public class InventoryDataManager implements IInventoryData{
        final protected Logger logger = LoggerFactory.getLogger(InventoryDataManager.class);


    @Override
    public List<DemoMqttSmartObject> getControlPanels() throws IInventoryDataException {

        return (List<DemoMqttSmartObject>) MqttSmartObjectProcess.panelsList.values();

    }
}
