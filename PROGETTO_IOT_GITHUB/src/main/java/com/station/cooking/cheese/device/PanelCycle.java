package com.station.cooking.cheese.device;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.station.cooking.cheese.model.RecipeDescriptor;
import com.station.cooking.cheese.resource.SmartObjectResource;
import org.apache.commons.lang3.ArrayUtils;
import org.checkerframework.checker.units.qual.Temperature;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

import com.station.cooking.cheese.resource.MotorMixerResource;
import com.station.cooking.cheese.resource.ValveResource;
import com.station.cooking.cheese.resource.TemperatureSensorResource;

import com.station.cooking.cheese.device.PanelFsmParameter;
import org.eclipse.paho.client.mqttv3.MqttException;

import javax.imageio.stream.ImageInputStream;

import static java.lang.Thread.sleep;


/**
 * @author: Daniele Barbieri
 * @date: 30/01/2021
 * @project: Progetto_Dan_IoT
 */



public class PanelCycle {

    private PanelMqttSmartObject panelMqttSmartObject;

    private static RecipeDescriptor recipe;

    private static ObjectMapper objectMapper = new ObjectMapper();

    private ArrayList<String> phases = new ArrayList<>();

    private ArrayList<Double> temperatures = new ArrayList<>();

    private ArrayList<Double> times = new ArrayList<>();

    private final double[] current_temperature_phase = {0.0};

    private Boolean simulating = false;

    //public Set<Double> Temperatures;


    public PanelCycle(PanelMqttSmartObject panelMqttSmartObject) {
        this.panelMqttSmartObject = panelMqttSmartObject;
        //this.phases.addAll(recipe.getPhases());
        //this.temperatures.addAll(recipe.getTemperatures());
        //this.times.addAll(recipe.getTimes());
        //this.phases = recipe.getPhases();
        //this.temperatures = recipe.getTemperatures();
        //this.times = recipe.getTimes();
        //this.panelMqttSmartObject.getResourceMap();
        this.phases = panelMqttSmartObject.getRecipe().getPhases();
        this.temperatures = panelMqttSmartObject.getRecipe().getTemperatures();
        this.times = panelMqttSmartObject.getRecipe().getTimes();

        System.out.println(String.format("Tempretaure %s , %s", panelMqttSmartObject.getDeviceId(), temperatures));

    }



    public void simulateWrongTemperature(){

        Thread threadModifyTemp = new Thread(() -> {
            try {
                current_temperature_phase[0] = current_temperature_phase[0] + 100;
                System.out.println("------------------------------TEMP SET TO +100!!");
                Thread.sleep(5000);
                current_temperature_phase[0] = current_temperature_phase[0] - 100;
                System.out.println("------------------------------TEMP SET TO -100");

            } catch (Exception e) {
                e.printStackTrace();
            }

        });
        threadModifyTemp.start();

    }

    public void panelCycleRun() throws InterruptedException, MqttException, JsonProcessingException {

        final double[] motor_percentage_work = {0.0};
        final double[] valve_percentage_work = {0.0};
        double time_phase_set = 0.0;
        double time_phase_real = 0.0;


        for (int i = 0; i < phases.size(); i++) {
            System.out.println("Quante fasi =" + phases.size());
            System.out.println("Fase in corso =" + phases.get(i));
            Double temp = new Double(temperatures.get(i));
            time_phase_set = times.get(i);
            time_phase_real = 0.0;
            int index = i;

            if (i == 0) {

                // al primo ciclo mette il valore simulato della sonda di temperatura a -5 °C dal SET
                panelMqttSmartObject.getResourceMap().entrySet().forEach(mapResourceEntry-> {
                            if (mapResourceEntry.getKey() == "Temperature") {
                                mapResourceEntry.getValue().setValue(temp-5.0);
                                current_temperature_phase[0] = mapResourceEntry.getValue().getValue();
                                System.out.println(String.format("TEMPERATURA DI PARTENZA FASE %s =", index+1)+" "+Arrays.toString(current_temperature_phase));

                                //System.out.println(mapResourceEntry.getValue().getValue());     // ESEMPIO
                                //mapResourceEntry.getValue().setValue(temperatures.get(index));  //
                                //System.out.println("STAMPA TEMPERATURA");                       //
                                //System.out.println(mapResourceEntry.getValue().getValue());     //

                            }

                });

            } else {
                // al secondo ciclo mette il valore simulato della sonda di temperatura a -10 °C dal SET
                panelMqttSmartObject.getResourceMap().entrySet().forEach(mapResourceEntry -> {
                    if (mapResourceEntry.getKey() == "Temperature") {
                        mapResourceEntry.getValue().setValue(temp - 10.0);
                        current_temperature_phase[0] = mapResourceEntry.getValue().getValue();
                        System.out.println(String.format("TEMPERATURA DI PARTENZA FASE %s =", index + 1) + " " + Arrays.toString(current_temperature_phase));

                    }
                });
            }

            while (current_temperature_phase[0] < temperatures.get(i) || time_phase_real <= time_phase_set) {
                System.out.println("DEBUG DEVICE ID = "+panelMqttSmartObject.getDeviceId());
                System.out.println("DEBUG");
                System.out.println(current_temperature_phase[0]);
                System.out.println(temperatures.get(i));
                System.out.println(time_phase_real);
                System.out.println(time_phase_set);
                System.out.println(i);


                if (current_temperature_phase[0] < temperatures.get(i)) {
                    // first step phase temperature raises & mixer is ON & Valve is OPEN
                    sleep(910);
                    panelMqttSmartObject.getResourceMap().entrySet().forEach(mapResourceEntry -> {
                        System.out.println("VALORI INTERNI PANNELLO ID = "+panelMqttSmartObject.getDeviceId());
                        if (mapResourceEntry.getKey() == "Temperature") {
                            mapResourceEntry.getValue().refreshValue();
                            current_temperature_phase[0] = mapResourceEntry.getValue().getValue();
                            System.out.println(String.format("TEMPERATURA ATTUALE DELLA FASE %s =", index + 1) + " " + Arrays.toString(current_temperature_phase));
                        } else if (mapResourceEntry.getKey() == "Mixer") {
                            mapResourceEntry.getValue().setValue(100.0);
                            motor_percentage_work[0] = mapResourceEntry.getValue().getValue();
                            System.out.println(String.format("VELOCITA' MIXER %s =", index + 1) + " " + Arrays.toString(motor_percentage_work));
                        } else if (mapResourceEntry.getKey() == "SteamValve") {
                            mapResourceEntry.getValue().setValue(100.0);
                            valve_percentage_work[0] = mapResourceEntry.getValue().getValue();
                            System.out.println(String.format("APERTURA VALVOLA VAPORE %s =", index + 1) + " " + Arrays.toString(valve_percentage_work));
                        }
                    });

                } else {

                    if(this.panelMqttSmartObject.getDeviceId().equals("P01") && i == 1 && !simulating){
                        simulating = true;
                        simulateWrongTemperature();
                    }

                    if(current_temperature_phase[0] > temperatures.get(i) + 10){

                        //SCATTA L'ALLARME E LO INVIA FINCHE' LA TEMPERATURA NON SI RISTABILIZZA, IL RESTO NON VA AVANTI

                        panelMqttSmartObject.publishAlarmEvent(String.format("%s/event", panelMqttSmartObject.getBaseTopic()), "TEMPERATURE_ALARM_P01");
                        Thread.sleep(900);

                    } else {
                        // second step phase temperature remains constant & mixer is ON & Valve is CLOSE
                        sleep(910);
                        panelMqttSmartObject.getResourceMap().entrySet().forEach(mapResourceEntry -> {
                            System.out.println("VALORI INTERNI PANNELLO ID = "+panelMqttSmartObject.getDeviceId());
                            if (mapResourceEntry.getKey() == "Temperature") {
                                //mapResourceEntry.getValue().getValue();
                                //current_temperature_phase[0] = mapResourceEntry.getValue().getValue();
                                System.out.println(String.format("TEMPERATURA ATTUALE DELLA FASE %s =", index + 1) + " " + Arrays.toString(current_temperature_phase));
                            } else if (mapResourceEntry.getKey() == "Mixer") {
                                mapResourceEntry.getValue().setValue(50.0);
                                motor_percentage_work[0] = mapResourceEntry.getValue().getValue();
                                System.out.println(String.format("VELOCITA' MIXER %s =", index + 1) + " " + Arrays.toString(motor_percentage_work));
                            } else if (mapResourceEntry.getKey() == "SteamValve") {
                                mapResourceEntry.getValue().setValue(0.0);
                                valve_percentage_work[0] = mapResourceEntry.getValue().getValue();
                                System.out.println(String.format("APERTURA VALVOLA VAPORE %s =", index + 1) + " " + Arrays.toString(valve_percentage_work));
                            }

                        });
                        time_phase_real = time_phase_real+1.0;
                    }
                }

            }

        }

        panelMqttSmartObject.stop();

    }
}





