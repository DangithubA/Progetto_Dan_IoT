package com.station.cooking.cheese.api_rest.inventory;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;
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
            serializeToFile();
        }



    }


    @Override
    public void updateTemp(ArrayList<Double> temps, String panel_id) throws IOException, ClassNotFoundException {

        serializeFromFile();

        if(panelsList.containsKey(panel_id)){
            RecipeDescriptor recipe = panelsList.get(panel_id).getRecipe();
            recipe.setTemperatures(temps);
            panelsList.get(panel_id).setRecipe(recipe);
            serializeToFile();
        }



    }
    @Override
    public void updateTime(ArrayList<Double> times, String panel_id) throws IOException, ClassNotFoundException {

        serializeFromFile();

        if(panelsList.containsKey(panel_id)){
            RecipeDescriptor recipe = panelsList.get(panel_id).getRecipe();
            recipe.setTimes(times);
            panelsList.get(panel_id).setRecipe(recipe);
            serializeToFile();
        }



    }
    @Override
    public void updatePhase(ArrayList<String> phases, String panel_id) throws IOException, ClassNotFoundException {

        serializeFromFile();

        if(panelsList.containsKey(panel_id)){
            RecipeDescriptor recipe = panelsList.get(panel_id).getRecipe();
            recipe.setPhases(phases);
            panelsList.get(panel_id).setRecipe(recipe);
            serializeToFile();
        }



    }

    @Override
    public void deleteRecipe(String panel_id) throws IInventoryDataException, IOException, ClassNotFoundException {
        panelsList.get(panel_id).setRecipe(null);
    }

    public void serializeFromFile() throws IOException, ClassNotFoundException {

        ObjectMapper mapper = new ObjectMapper();

        TypeFactory factory;
        MapType type;

        factory = TypeFactory.defaultInstance();
        type    = factory.constructMapType(HashMap.class, String.class, PanelDescriptor.class);

        panelsList = mapper.readValue(new File("data/panelsDatabase.json"), type);

    }
    public void serializeToFile() throws IOException, ClassNotFoundException {

        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.writeValue(new File("data/panelsDatabase.json"), panelsList);

    }

}
