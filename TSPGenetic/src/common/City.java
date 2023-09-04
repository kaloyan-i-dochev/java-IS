package common;

public class City {
    private double x;
    private double y;
    private String name; // Optional

    public City(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public City(double x, double y, String name) { // Optional constructor with name
        this.x = x;
        this.y = y;
        this.name = name;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public String getName() { // Optional
        return name;
    }

    public double distanceTo(City other) {
        double dx = this.x - other.x;
        double dy = this.y - other.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public static double distanceTo(City first, City second) {
        double dx = first.x - second.x;
        double dy = first.y - second.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    @Override
    public String toString() { // Optional, for easier debugging
        return name != null ? name : "(" + x + ", " + y + ")";
    }
}
