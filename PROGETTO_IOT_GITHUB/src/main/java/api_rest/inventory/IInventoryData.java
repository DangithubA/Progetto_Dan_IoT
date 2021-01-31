package api_rest.inventory;


import api_rest.exceptions.IInventoryDataException;
import device.DemoMqttSmartObject;
import java.util.List;

public interface IInventoryData {

    public List<DemoMqttSmartObject> getControlPanels() throws IInventoryDataException;

}
