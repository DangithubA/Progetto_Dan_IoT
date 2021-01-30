package resource;

import java.util.Random;
import java.util.UUID;

/**
 * @author Marco Picone, Ph.D. - picone.m@gmail.com
 * @project mqtt-demo-smartobject
 * @created 12/10/2020 - 19:28
 */
public class TemperatureSensorResource extends SmartObjectResource<Double> {

    private Random random;

    private final double MAX_TEMPERATURE_VALUE = 30.0;

    private final double MIN_TEMPERATURE_VALUE = 20.0;

    private final double MAX_OFFSET = +3.0;

    private final double MIN_OFFSET = -3.0;

    public static final String RESOURCE_UNIT = "C";

    public static final String RESOURCE_TYPE = "iot:sensor:temperature";

    public TemperatureSensorResource(){
        super(UUID.randomUUID().toString(), TemperatureSensorResource.RESOURCE_UNIT, TemperatureSensorResource.RESOURCE_TYPE);
        this.random = new Random(System.currentTimeMillis());
        this.value = MIN_TEMPERATURE_VALUE + this.random.nextDouble() * (MAX_TEMPERATURE_VALUE - MIN_TEMPERATURE_VALUE);
    }

    @Override
    public void refreshValue() {
        this.value = this.value + (MIN_OFFSET + new Random().nextDouble() * (MAX_OFFSET - MIN_OFFSET));
    }

}
