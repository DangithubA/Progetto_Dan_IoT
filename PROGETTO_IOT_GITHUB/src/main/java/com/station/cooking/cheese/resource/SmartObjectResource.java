package com.station.cooking.cheese.resource;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;

/**
 * @author: Daniele Barbieri
 * @date: 30/01/2021
 * @project: Progetto_Dan_IoT
 */

public abstract class SmartObjectResource<T>{

    private String id;

    private String unit;

    private String type;

    protected ObjectMapper objectMapper;

    private Double increase;

    protected Double value;

    public SmartObjectResource() {
    }

    public SmartObjectResource(String id, String unit, String type) {
        this.id = id;
        this.unit = unit;
        this.type = type;
        //this.objectmapper = new ObjectMapper();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getValue(){
        return this.value;
    }

    public Double getIncrease() {
        return increase;
    }

    public void setIncrease(Double increase) {
        this.increase = increase;
    }

    /**
     * Method used to refresh emulated com.station.cooking.cheese.resource internal value
     * Use this.value to update the internal field (type T)
     */

    // public abstract T refreshValue(T increase);  // MODIFICATO DA DAN INCREASE E RETURN T
    public abstract void refreshValue();

    public abstract void setValue(Double Value);

   // public void setValue(T value) {
   //     this.value = value;
   // }  // SI PUO USARE PER SETTARE IL VALORE INIZIALE DELLA SONDA ?

    public abstract Optional<String> getJsonSenmlResponse();

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("SmartObjectResource{");
        sb.append("id='").append(id).append('\'');
        sb.append(", unit='").append(unit).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append(", value=").append(value);
        sb.append('}');
        return sb.toString();
    }
}
