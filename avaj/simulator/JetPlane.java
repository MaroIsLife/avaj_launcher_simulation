package avaj.simulator;

public class JetPlane extends Aircraft {
    public JetPlane(long p_id, String p_name, Coordinates p_coordinates) {
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
                newLatitude += 10;
                newHeight += 2;
                System.out.println("JetPlane#" + name + "(" + id + "): Let's enjoy the good weather and take some pics.");
                break;
            case "RAIN":
                newLatitude += 5;
                System.out.println("JetPlane#" + name + "(" + id + "): It's raining. Better watch out for lightings.");
                break;
            case "FOG":
                newLatitude += 1;
                System.out.println("JetPlane#" + name + "(" + id + "): Visibility is low. Flying carefully.");
                break;
            case "SNOW":
                newHeight -= 7;
                System.out.println("JetPlane#" + name + "(" + id + "): OMG! Winter is coming!");
                break;
        }

        newHeight = Math.max(0, Math.min(100, newHeight));
        newLongitude = Math.max(0, newLongitude);
        newLatitude = Math.max(0, newLatitude);

        coordinates = new Coordinates(newLongitude, newLatitude, newHeight);

        if (coordinates.getHeight() == 0) {
            System.out.println("JetPlane#" + name + "(" + id + ") landing.");
            weatherTower.unregister(this);
        }
    }
}