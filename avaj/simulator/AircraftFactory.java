package avaj.simulator;

public class AircraftFactory {
    private static AircraftFactory instance = null;
    private static long idCounter = 0;

    private AircraftFactory() {
    }

    public static AircraftFactory getInstance() {
        if (instance == null) {
            instance = new AircraftFactory();
        }
        return instance;
    }

    public Flyable newAircraft(String p_type, String p_name, Coordinates p_coordinates) {
        long id = nextId();
        switch (p_type.toLowerCase()) {
            case "jetplane":
                return new JetPlane(id, p_name, p_coordinates);
            case "helicopter":
                return new Helicopter(id, p_name, p_coordinates);
            case "baloon":
                return new Balloon(id, p_name, p_coordinates);
            default:
                return null;
        }
    }

    private long nextId() {
        return ++idCounter;
    }
}