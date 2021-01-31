package api_rest.inventory;


import api_rest.exceptions.IInventoryDataException;
import device.PanelMqttSmartObject;
import java.util.List;

public interface IInventoryData {

    public List<PanelMqttSmartObject> getControlPanels() throws IInventoryDataException;

}
