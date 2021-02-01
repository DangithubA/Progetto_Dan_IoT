package com.station.cooking.cheese.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * @author: Daniele Barbieri Powered by Marco Picone
 * @date: 30/01/2021
 * @project: Progetto_Dan_IoT
 */


public class RecipeDescriptor {

    @JsonProperty("nome_ricetta")
    private String name;

    @JsonProperty("fasi")
    private ArrayList<String> phases;

    @JsonProperty("temperature")
    private ArrayList<Double> temperatures;

    @JsonProperty("tempi")
    private ArrayList<Double> times;



    public RecipeDescriptor() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getPhases() {
        return phases;
    }

    public void setPhases(ArrayList<String> phases) {
        this.phases = phases;
    }

    public ArrayList<Double> getTemperatures() {
        return temperatures;
    }

    public void setTemperatures(ArrayList<Double> temperatures) {
        this.temperatures = temperatures;
    }

    public ArrayList<Double> getTimes() {
        return times;
    }

    public void setTimes(ArrayList<Double> times) {
        this.times = times;
    }


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("RecipeDescriptor{");
        sb.append("name='").append(name).append('\'');
        sb.append(", phases=").append(phases);
        sb.append(", temperatures=").append(temperatures);
        sb.append(", times=").append(times);
        sb.append('}');
        return sb.toString();
    }

}
