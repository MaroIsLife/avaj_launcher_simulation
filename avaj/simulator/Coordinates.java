package avaj.simulator;

public class Coordinates {
    private int longitude;
    private int latitude;
    private int height;

    Coordinates(int p_longitude, int p_latitude, int p_height) {
        this.longitude = Math.max(0, p_longitude);
        this.latitude = Math.max(0, p_latitude);
        this.height = Math.max(0, Math.min(100, p_height));
    }

    public int getLongitude() {
        return longitude;
    }

    public int getLatitude() {
        return latitude;
    }

    public int getHeight() {
        return height;
    }
}