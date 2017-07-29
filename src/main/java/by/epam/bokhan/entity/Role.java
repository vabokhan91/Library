package by.epam.bokhan.entity;

/**
 * Created by vbokh on 29.07.2017.
 */
public enum Role {
    GUEST("Guest"),CLIENT("Client"),LIBRARIAN("Librarian"),ADMINISTRATOR("Administrator");

    private String name;
    Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
