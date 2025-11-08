package avaj.simulator;

public class InvalidAircraftTypeException extends Exception {
    public InvalidAircraftTypeException(String type) {
        super("Unknown aircraft type: " + type);
    }
}