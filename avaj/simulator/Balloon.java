package avaj.simulator;

public class Balloon extends Aircraft {
    public Balloon(long p_id, String p_name, Coordinates p_coordinates) {
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
                newLongitude += 2;
                newHeight += 4;
                System.out.println("Balloon#" + name + "(" + id + "): Sunny day as usual!");
                break;
            case "RAIN":
                newHeight -= 5;
                System.out.println("Balloon#" + name + "(" + id + "): Rain nice bro");
                break;
            case "FOG":
                newHeight -= 3;
                System.out.println("Balloon#" + name + "(" + id + "): Fog! I can't see anything.");
                break;
            case "SNOW":
                newHeight -= 15;
                System.out.println("Balloon#" + name + "(" + id + "): It's snowing. Is that even possible in MOROCCO???");
                break;
        }

        newHeight = Math.max(0, Math.min(100, newHeight));
        newLongitude = Math.max(0, newLongitude);
        newLatitude = Math.max(0, newLatitude);

        coordinates = new Coordinates(newLongitude, newLatitude, newHeight);

        if (coordinates.getHeight() == 0) {
            System.out.println("Balloon#" + name + "(" + id + ") landing.");
            weatherTower.unregister(this);
        }
    }
}