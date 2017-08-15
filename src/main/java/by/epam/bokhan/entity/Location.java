package by.epam.bokhan.entity;


public enum Location {
    STORAGE("storage"), READING_ROOM("reading_room"), SUBSCRIPTION("subscription"),ONLINE_ORDER("online_order");

    private String name;

    Location(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
