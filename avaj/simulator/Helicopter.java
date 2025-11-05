package avaj.simulator;

public class Helicopter extends Aircraft {
    public Helicopter(long p_id, String p_name, Coordinates p_coordinates) {
        super(p_id, p_name, p_coordinates);
    }

    @Override
    public void updateConditions() {
        String weather = weatherTower.getWeather(coordinates);
        int newLongitude = coordinates.getLongitude();
        int newLatitude = coordinates.getLatitude();
        int newHeight = coordinates.getHeight();

        switch (weather) {
            case "SUN":
                newLongitude += 10;
                newHeight += 2;
                System.out.println("Helicopter#" + name + "(" + id + "): This is hot.");
                break;
            case "RAIN":
                newLongitude += 5;
                System.out.println("Helicopter#" + name + "(" + id + "): Rain makes flying difficult.");
                break;
            case "FOG":
                newLongitude += 1;
                System.out.println("Helicopter#" + name + "(" + id + "): Can barely see through this fog.");
                break;
            case "SNOW":
                newHeight -= 12;
                System.out.println("Helicopter#" + name + "(" + id + "): My rotor is going to freeze!");
                break;
        }

        newHeight = Math.max(0, Math.min(100, newHeight));
        newLongitude = Math.max(0, newLongitude);
        newLatitude = Math.max(0, newLatitude);

        coordinates = new Coordinates(newLongitude, newLatitude, newHeight);

        if (coordinates.getHeight() == 0) {
            System.out.println("Helicopter#" + name + "(" + id + ") landing.");
            weatherTower.unregister(this);
        }
    }
}