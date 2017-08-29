package by.epam.bokhan.entity;


public class OnlineOrder extends Order {
    private Location location;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
