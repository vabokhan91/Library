package by.epam.bokhan.entity;


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
