package resource;

/**
 * @author Marco Picone, Ph.D. - picone.m@gmail.com
 * @project mqtt-demo-smartobject
 * @created 12/10/2020 - 19:27
 */
public abstract class SmartObjectResource<T> {

    private String id;

    private String unit;

    private String type;

    protected T value;

    public SmartObjectResource() {
    }

    public SmartObjectResource(String id, String unit, String type) {
        this.id = id;
        this.unit = unit;
        this.type = type;
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

    public T getValue(){
        return this.value;
    }

    /**
     * Method used to refresh emulated resource internal value
     * Use this.value to update the internal field (type T)
     */
    public abstract void refreshValue();

    public void setValue(T value) {
        this.value = value;
    }

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
