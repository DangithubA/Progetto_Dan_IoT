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


public class MotorMixerResource extends SmartObjectResource<Boolean> {

    private static final Logger logger = LoggerFactory.getLogger(MotorMixerResource.class);

    private static String UNIT_VALUE = "Boolean";

    private static String TYPE_VALUE = "iot:actuator:motor";

    private Random random;

    public MotorMixerResource() {
        super(UUID.randomUUID().toString(),
                UNIT_VALUE,
                TYPE_VALUE);

        this.random = new Random(System.currentTimeMillis());
        this.value = this.random.nextBoolean();
        // this.value = Boolean.FALSE;
        // this.value = false;
    }

    /**
     * Method used to refresh emulated com.station.cooking.cheese.resource internal value
     * Use this.value to update the internal field (type T)
     */

    @Override
    public void refreshValue() {
        this.value = this.random.nextBoolean();
    }
    //public Boolean refreshValue(Boolean value) {
        // this.value = this.random.nextBoolean();
        //return this.value;
    //}


}


/**
 if (condition) {
 // block of code to be executed if the condition is true
 } else {
 // block of code to be executed if the condition is false
 }
 */