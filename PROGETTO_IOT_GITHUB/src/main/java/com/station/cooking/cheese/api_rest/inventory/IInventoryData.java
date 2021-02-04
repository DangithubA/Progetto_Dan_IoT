package com.station.cooking.cheese.api_rest.inventory;


import com.station.cooking.cheese.api_rest.exceptions.IInventoryDataException;
import com.station.cooking.cheese.device.PanelMqttSmartObject;
import com.station.cooking.cheese.model.PanelDescriptor;
import com.station.cooking.cheese.model.RecipeDescriptor;

import java.io.IOException;
import java.util.HashMap;

public interface IInventoryData {

    public HashMap<String, PanelDescriptor> getControlPanels() throws IInventoryDataException, IOException, ClassNotFoundException;
    public void createRecipe(String panel_id, RecipeDescriptor recipeDescriptor) throws IInventoryDataException, IOException, ClassNotFoundException;
    public void deleteRecipe(String panel_id) throws IInventoryDataException, IOException, ClassNotFoundException;

}
