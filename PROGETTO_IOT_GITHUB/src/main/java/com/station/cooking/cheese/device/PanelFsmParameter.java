package com.station.cooking.cheese.device;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.station.cooking.cheese.model.RecipeDescriptor;
import org.apache.commons.lang3.ArrayUtils;
import org.checkerframework.checker.units.qual.Temperature;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import com.station.cooking.cheese.resource.MotorMixerResource;
import com.station.cooking.cheese.resource.ValveResource;
import com.station.cooking.cheese.resource.TemperatureSensorResource;


public class PanelFsmParameter {

        private static RecipeDescriptor recipe;

        private static ObjectMapper objectMapper = new ObjectMapper();

        private static ArrayList<String> phases = new ArrayList<>();

        private static ArrayList<Double> temperatures= new ArrayList<>();

        private static ArrayList<Double> times= new ArrayList<>();

        //public Set<Double> Temperatures;


        public static void init() {

            // INSERITO PER CARICARE LA RICETTA

            try {

                //recipe = om.readValue(new File("src/main/java/com.station.cooking.cheese.data/recipe.json"), RecipeDescriptor.class);
                recipe = objectMapper.readValue(new File("data/recipe.json"), RecipeDescriptor.class);
                // Stampa il set di partenza della sonda prelevato dalla ricetta ricevuta
                //System.out.println("Set di partenza della sonda prelevato dalla ricetta ricevuta");
                //System.out.println(recipe.getTemperatures().get(0));

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        public static void main(String[] args) {

            String current_phases;


            init();
            System.out.println(recipe.getTemperatures().get(0));
            phases.addAll(recipe.getPhases());
            temperatures.addAll(recipe.getTemperatures());
            times.addAll(recipe.getTimes());

            for(int i=0; i<phases.size(); i++){
                System.out.println("Quante fasi =" + phases.size());
                System.out.println("Fase in corso =" + phases.get(i));

                TemperatureSensorResource current_temperature_phase = new TemperatureSensorResource(recipe);
                    if(i==0){
                        current_temperature_phase.setValue(temperatures.get(i)-5.0);
                    }else {

                        current_temperature_phase.setValue(temperatures.get(i) - 10.0);
                        //temperaturephase.getValue();
                    }
                     System.out.println("Temperatura di partenza ="+current_temperature_phase.getValue());

                    while (current_temperature_phase.getValue()<temperatures.get(i)){
                        current_temperature_phase.refreshValue();
                        System.out.println("Temperatura aggiornata ="+current_temperature_phase.getValue());
                    }
               }

        }
    }


