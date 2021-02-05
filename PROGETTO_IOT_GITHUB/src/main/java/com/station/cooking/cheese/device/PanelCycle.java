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

import com.station.cooking.cheese.device.PanelFsmParameter;

import static java.lang.Thread.sleep;





public class PanelCycle {

    private PanelMqttSmartObject panelMqttSmartObject;

    private static RecipeDescriptor recipe;

    private static ObjectMapper objectMapper = new ObjectMapper();

    private static ArrayList<String> phases = new ArrayList<>();

    private static ArrayList<Double> temperatures = new ArrayList<>();

    private static ArrayList<Double> times = new ArrayList<>();

    //public Set<Double> Temperatures;


    public PanelCycle(PanelMqttSmartObject panelMqttSmartObject) {
        this.panelMqttSmartObject = panelMqttSmartObject;
        this.phases.addAll(recipe.getPhases());
        this.temperatures.addAll(recipe.getTemperatures());
        this.times.addAll(recipe.getTimes());
        //this.panelMqttSmartObject.getResourceMap();

    }

    /**

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

    public static void main(String[] args) throws InterruptedException {

        init();
        System.out.println(recipe.getTemperatures().get(0));
        phases.addAll(recipe.getPhases());
        temperatures.addAll(recipe.getTemperatures());
        times.addAll(recipe.getTimes());

        TemperatureSensorResource current_temperature_phase = new TemperatureSensorResource(recipe);
        ValveResource valve_steam = new ValveResource();
        MotorMixerResource motor_mixer = new MotorMixerResource();

*/
    public void panelCycleRun() {

        double current_temperature_phase = 0.0;
        double time_phase_set = 0.0;
        int time_phase_real = 0;

        //panelMqttSmartObject.getResourceMap().get(TemperatureSensorResource.getValue());

        /**
        for (int i = 0; i < phases.size(); i++) {
            System.out.println("Quante fasi =" + phases.size());
            System.out.println("Fase in corso =" + phases.get(i));

            time_phase_set = times.get(i);
            //int time_phase_real = 0;

            if (i == 0) {
                this.resourceMap
            //current_temperature_phase.setValue(temperatures.get(i) - 5.0);

            } else {

                current_temperature_phase.setValue(temperatures.get(i) - 10.0);

            }
            System.out.println("Temperatura di partenza =" + current_temperature_phase.getValue());

            // second step phase temperature remains constant mixer ON Valve CLOSE
            while (current_temperature_phase.getValue() <= temperatures.get(i) || time_phase_real < time_phase_set) {

                if (current_temperature_phase.getValue() <= temperatures.get(i)) {
                    // first step phase temperature raises mixer ON Valve OPEN
                    sleep(1000);
                    current_temperature_phase.refreshValue();
                    valve_steam.setValue(true);
                    motor_mixer.setValue(true);

                    System.out.println("Temperatura aggiornata =" + current_temperature_phase.getValue());
                    System.out.println("Motore mixer =" + motor_mixer.getValue());
                    System.out.println("Valvola vapore =" + valve_steam.getValue());

                } else {
                    // second step phase temperature remains constant mixer ON Valve CLOSE
                    sleep(1000);
                    time_phase_real += 1;
                    valve_steam.setValue(false);
                    motor_mixer.setValue(true);

                    System.out.println("Temperatura aggiornata =" + current_temperature_phase.getValue());
                    System.out.println("Motore mixer =" + motor_mixer.getValue());
                    System.out.println("Valvola vapore =" + valve_steam.getValue());
                    System.out.println("Tempo fase =" + time_phase_real);
                }
            }
        }
    }

    */
    }

}
