package by.epam.bokhan.entity;


public enum Location {
    STORAGE("Хранилище"), READING_ROOM("Читательский зал"), SUBSCRIPTION("Абонемент"),ONLINE_ORDER("Онлайн заказ");
    /*Name of the book location*/
    private String name;

    Location(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
