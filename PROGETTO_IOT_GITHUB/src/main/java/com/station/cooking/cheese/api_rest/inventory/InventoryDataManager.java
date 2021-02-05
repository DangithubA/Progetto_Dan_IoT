package com.station.cooking.cheese.api_rest.inventory;


import com.station.cooking.cheese.api_rest.exceptions.IInventoryDataException;
import com.station.cooking.cheese.device.PanelMqttSmartObject;
import com.station.cooking.cheese.model.PanelDescriptor;
import com.station.cooking.cheese.model.RecipeDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;


public class InventoryDataManager implements IInventoryData{


    final protected Logger logger = LoggerFactory.getLogger(InventoryDataManager.class);

    public static HashMap<String, PanelDescriptor> panelsList = new HashMap<>();



    @Override
    public HashMap<String, PanelDescriptor> getControlPanels() throws IInventoryDataException, IOException, ClassNotFoundException {

        serializeFromFile();

        System.out.println(String.format("%s", panelsList.keySet()));

        return panelsList;

    }

    @Override
    public void createRecipe(String panel_id, RecipeDescriptor recipeDescriptor) throws IInventoryDataException, IOException, ClassNotFoundException {

        serializeFromFile();

        if(panelsList.containsKey(panel_id)){
            panelsList.get(panel_id).setRecipe(recipeDescriptor);
        }

        serializeToFile();

    }


    @Override
    public void updateTemp(ArrayList<Double> temps, String panel_id) throws IOException, ClassNotFoundException {

        serializeFromFile();

        if(panelsList.containsKey(panel_id)){
            RecipeDescriptor recipe = panelsList.get(panel_id).getRecipe();
            recipe.setTemperatures(temps);
            panelsList.get(panel_id).setRecipe(recipe);
        }

        serializeToFile();

    }
    @Override
    public void updateTime(ArrayList<Double> times, String panel_id) throws IOException, ClassNotFoundException {

        serializeFromFile();

        if(panelsList.containsKey(panel_id)){
            RecipeDescriptor recipe = panelsList.get(panel_id).getRecipe();
            recipe.setTimes(times);
            panelsList.get(panel_id).setRecipe(recipe);
        }

        serializeToFile();

    }
    @Override
    public void updatePhase(ArrayList<String> phases, String panel_id) throws IOException, ClassNotFoundException {

        serializeFromFile();

        if(panelsList.containsKey(panel_id)){
            RecipeDescriptor recipe = panelsList.get(panel_id).getRecipe();
            recipe.setPhases(phases);
            panelsList.get(panel_id).setRecipe(recipe);
        }

        serializeToFile();

    }

    @Override
    public void deleteRecipe(String panel_id) throws IInventoryDataException, IOException, ClassNotFoundException {
        panelsList.get(panel_id).setRecipe(null);
    }

    public void serializeFromFile() throws IOException, ClassNotFoundException {

        FileInputStream fi = new FileInputStream(new File("data/myObjects.txt"));
        ObjectInputStream oi = new ObjectInputStream(fi);

        // Read objects
        panelsList = (HashMap<String, PanelDescriptor>) oi.readObject();

        System.out.println(panelsList.toString());

        oi.close();
        fi.close();
    }
    public void serializeToFile() throws IOException, ClassNotFoundException {

        FileOutputStream f = new FileOutputStream(new File("data/myObjects.txt"));
        ObjectOutputStream o = new ObjectOutputStream(f);

        // Write objects to file
        o.writeObject(panelsList);

        o.close();
        f.close();
    }

}
