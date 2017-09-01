package by.epam.bokhan.entity;


public enum  OrderStatus {
    BOOKED("Заказано"),CANCELED("Отменено"),EXECUTED("Исполнен");

    private String name;

    OrderStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
