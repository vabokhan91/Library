package by.epam.bokhan.entity;


public enum Location {
    STORAGE("Хранилище"), READING_ROOM("Читательский зал"), SUBSCRIPTION("Абонемент"),ONLINE_ORDER("Онлайн заказ");

    private String name;

    Location(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
