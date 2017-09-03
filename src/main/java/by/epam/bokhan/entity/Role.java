package by.epam.bokhan.entity;


public enum Role {
    GUEST("Гость"), CLIENT("Клиент"), LIBRARIAN("Библиотекарь"), ADMINISTRATOR("Администратор");

    /*Role name*/
    private String name;

    Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
