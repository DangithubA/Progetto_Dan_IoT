package resource;

import device.DemoMqttSmartObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.UUID;

/**
 * @author: Daniele Barbieri Powered by Marco Picone
 * @date: 30/01/2021
 * @project: Progetto_Dan_IoT
 */

public class ValveResource extends SmartObjectResource<Boolean> {

    private static final Logger logger = LoggerFactory.getLogger(ValveResource.class);

    private static String UNIT_VALUE = "Boolean";

    private static String TYPE_VALUE = "iot:actuator:valve";

    private Random random;

    public ValveResource() {
        super(UUID.randomUUID().toString(),
                UNIT_VALUE,
                TYPE_VALUE);

        this.random = new Random(System.currentTimeMillis());
        this.value = this.random.nextBoolean();
    }

    /**
     * Method used to refresh emulated resource internal value
     * Use this.value to update the internal field (type T)
     */

    @Override
    public Boolean refreshValue(Boolean increase) {
        this.value = this.random.nextBoolean();
        return this.value;
    }


}
