package avaj.simulator;

public class Aircraft extends Flyable {
    protected long id;
    protected String name;
    protected Coordinates coordinates;

    protected Aircraft(long p_id, String p_name, Coordinates p_coordinates) {
        this.id = p_id;
        this.name = p_name;
        this.coordinates = p_coordinates;
    }

    public String getName() {
        return this.name;
    }

    public long getId() {
        return this.id;
    }

    @Override
    public void updateConditions() {
    }
}