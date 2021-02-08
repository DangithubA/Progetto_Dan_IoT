package com.station.cooking.cheese.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.station.cooking.cheese.utils.SenMLPack;
import com.station.cooking.cheese.utils.SenMLRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;

/**
 * @author: Daniele Barbieri
 * @date: 30/01/2021
 * @project: Progetto_Dan_IoT
 */

public class ValveResource extends SmartObjectResource<Double> {

    private static final Logger logger = LoggerFactory.getLogger(ValveResource.class);

    private static String UNIT_VALUE = "percentage";

    private static String TYPE_VALUE = "iot:actuator:valve";

    private Random random;

    public ValveResource() {
        super(UUID.randomUUID().toString(),
                UNIT_VALUE,
                TYPE_VALUE);

        //this.random = new Random(System.currentTimeMillis());
        //this.value = this.random.nextBoolean();
        //this.value = Boolean.FALSE;
        this.objectMapper = new ObjectMapper();
        System.out.println(this.value);
    }

    /**
     * Method used to refresh emulated com.station.cooking.cheese.resource internal value
     * Use this.value to update the internal field (type T)
     */

    @Override
    public void refreshValue() {
    //this.value = Boolean.FALSE;
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
            senMLRecord.setU(UNIT_VALUE);
            senMLRecord.setV(this.value);
            senMLRecord.setT(System.currentTimeMillis());
            senMLPack.add(senMLRecord);

            return Optional.of(this.objectMapper.writeValueAsString(senMLPack));

        } catch (Exception e) {
            return Optional.empty();
        }
    }

}
