package com.station.cooking.cheese.resource;

import com.station.cooking.cheese.model.RecipeDescriptor;
import java.util.UUID;

/**
 * @author: Daniele Barbieri Powered by Marco Picone
 * @date: 30/01/2021
 * @project: Progetto_Dan_IoT
 */

public class TemperatureSensorResource extends SmartObjectResource<Double> {

    private RecipeDescriptor recipe;

    private double startTemperature;

    public static final String RESOURCE_UNIT = "C";

    public static final String RESOURCE_TYPE = "iot:sensor:temperature";

    public TemperatureSensorResource(RecipeDescriptor recipe){
        super(UUID.randomUUID().toString(), TemperatureSensorResource.RESOURCE_UNIT, TemperatureSensorResource.RESOURCE_TYPE);
        this.recipe = recipe;
        //this.startTemperature = this.recipe.getTemperatures().get(0)-5.0;
        System.out.println("Valore di partenza della sonda = ( set temperatura fase 1 della ricetta ) - 5 °C");
        System.out.println(this.recipe.getTemperatures().get(0)-5.0);
        this.value = this.recipe.getTemperatures().get(0)-5.0;
        //this.startTemperature = 20.0;
        //this.value = startTemperature;
        //this.value = 20.0;
    }

    /**
     * Method used to refresh emulated com.station.cooking.cheese.resource internal value
     * Use this.value to update the internal field (type T)
     */

    @Override
    public void refreshValue() {
        this.value =this.value + 0.5;
        //this.startTemperature =this.startTemperature + 0.5;
        //this.value = this.value + 0.5;
        }
    //public Double refreshValue(Double increase) {
    //    this.value = this.value + increase;
    //    return this.value;
    //}  // MODIFICATO DA DAN AGGIUNTO INCREASE + RETURN VALUE

}
