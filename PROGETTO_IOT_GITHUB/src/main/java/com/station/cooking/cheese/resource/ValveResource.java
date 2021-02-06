package com.station.cooking.cheese.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.UUID;

/**
 * @author: Daniele Barbieri Powered by Marco Picone
 * @date: 30/01/2021
 * @project: Progetto_Dan_IoT
 */

public class ValveResource extends SmartObjectResource<Double> {

    private static final Logger logger = LoggerFactory.getLogger(ValveResource.class);

    private static String UNIT_VALUE = "Boolean";

    private static String TYPE_VALUE = "iot:actuator:valve";

    private Random random;

    public ValveResource() {
        super(UUID.randomUUID().toString(),
                UNIT_VALUE,
                TYPE_VALUE);

        //this.random = new Random(System.currentTimeMillis());
        //this.value = this.random.nextBoolean();
        //this.value = Boolean.FALSE;                     ultimo tentativo
        System.out.println(this.value);
    }

    /**
     * Method used to refresh emulated com.station.cooking.cheese.resource internal value
     * Use this.value to update the internal field (type T)
     */

    @Override
    public void refreshValue() {
        //this.value = Boolean.FALSE;           ultimo tentativo
        //this.value = this.random.nextBoolean();
    }
    //public Boolean refreshValue(Boolean value) {
    // this.value = this.random.nextBoolean();
    //  return this.value;
    //}

    @Override
    public void setValue(Double value) {
        this.value = value;

    }
}
