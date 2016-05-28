package airport.data.destination;

/**
 * Пункт назначения.
 */
public class Destination {

    /**
     * Название
     */
    private String name;

    public Destination() {
    }

    public Destination(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
