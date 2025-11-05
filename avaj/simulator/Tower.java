package avaj.simulator;

import java.util.ArrayList;
import java.util.List;

public class Tower {
    private List<Flyable> observers = new ArrayList<>();

    public void register(Flyable p_flyable) {
        if (!observers.contains(p_flyable)) {
            observers.add(p_flyable);
            System.out.println("Tower says: " + getAircraftInfo(p_flyable) + " registered to weather tower.");
        }
    }

    public void unregister(Flyable p_flyable) {
        if (observers.remove(p_flyable)) {
            System.out.println("Tower says: " + getAircraftInfo(p_flyable) + " unregistered from weather tower.");
        }
    }

    protected void conditionChanged() {
        List<Flyable> currentObservers = new ArrayList<>(observers);
        for (Flyable flyable : currentObservers) {
            flyable.updateConditions();
        }
    }

    private String getAircraftInfo(Flyable flyable) {
        if (flyable instanceof Aircraft) {
            Aircraft aircraft = (Aircraft) flyable;
            return aircraft.getClass().getSimpleName() + "#" + aircraft.getName() + "(" + aircraft.getId() + ")";
        }
        return "Unknown";
    }
}