package com.station.cooking.cheese.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.station.cooking.cheese.model.RecipeDescriptor;
import com.station.cooking.cheese.utils.SenMLPack;
import com.station.cooking.cheese.utils.SenMLRecord;

import java.util.Optional;
import java.util.UUID;

/**
 * @author: Daniele Barbieri
 * @date: 30/01/2021
 * @project: Progetto_Dan_IoT
 */

public class TemperatureSensorResource extends SmartObjectResource<Double> {

    private RecipeDescriptor recipe;

    private double startTemperature;

    public static final String RESOURCE_UNIT = "Cel";

    public static final String RESOURCE_TYPE = "iot:sensor:temperature";


    public TemperatureSensorResource(RecipeDescriptor recipe) {
        super(UUID.randomUUID().toString(), TemperatureSensorResource.RESOURCE_UNIT, TemperatureSensorResource.RESOURCE_TYPE);
        this.recipe = recipe;
        //this.startTemperature = this.recipe.getTemperatures().get(0)-5.0;
        System.out.println("Valore di partenza della sonda = ( set temperatura fase 1 della ricetta ) - 5 °C");
        System.out.println(this.recipe.getTemperatures().get(0) - 5.0);
        this.value = this.recipe.getTemperatures().get(0) - 5.0;
        //this.startTemperature = 20.0;
        //this.value = startTemperature;
        //this.value = 20.0;
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Method used to refresh emulated com.station.cooking.cheese.resource internal value
     * Use this.value to update the internal field (type T)
     */

    @Override
    public void refreshValue() {
        this.value = this.value + 0.5;
    }


    @Override
    public void setValue(Double value) {
        this.value = value;
    }

    /**
     * Create the SenML Response with the updated value and the resource information
     * @return
     */

    @Override
    public Optional<String> getJsonSenmlResponse() {

        try {

            SenMLPack senMLPack = new SenMLPack();

            SenMLRecord senMLRecord = new SenMLRecord();
            senMLRecord.setBn(String.format("%s", this.getId()));
            //senMLRecord.setBver(SENSOR_VERSION);
            senMLRecord.setU(RESOURCE_UNIT);
            senMLRecord.setV(this.value);
            senMLRecord.setT(System.currentTimeMillis());

            senMLPack.add(senMLRecord);

            return Optional.of(this.objectMapper.writeValueAsString(senMLPack));

        } catch (Exception e) {
            return Optional.empty();
        }
    }


}
