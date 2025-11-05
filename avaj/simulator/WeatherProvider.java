package avaj.simulator;

public class WeatherProvider {
    private static WeatherProvider instance = null;
    private String[] weather = {"RAIN", "FOG", "SUN", "SNOW"};

    private WeatherProvider() {
    }

    public static WeatherProvider getProvider() {
        if (instance == null) {
            instance = new WeatherProvider();
        }
        return instance;
    }

    public String getCurrentWeather(Coordinates p_coordinates) {
        int sum = p_coordinates.getLongitude() +
                p_coordinates.getLatitude() +
                p_coordinates.getHeight();
        return weather[sum % 4];
    }
}