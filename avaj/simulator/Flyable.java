package avaj.simulator;

public abstract class Flyable {
    protected WeatherTower weatherTower;

    public abstract void updateConditions();
    public abstract void registerTower(WeatherTower p_tower);
}