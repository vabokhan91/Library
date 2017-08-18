package by.epam.bokhan.entity;

import java.util.ArrayList;
import java.util.List;


public class User {
    private int id;
    private String name;
    private String surname;
    private String patronymic;
    private String address;
    private Role role;
    private String login;
    private String password;
    private String mobilePhone;
    private boolean isBlocked;
    private List<Order> orders = new ArrayList<>();
    private int libraryCardNumber;
    private  String photo;

    public User() {
    }

    public int getId() {
        return this.id;
    }

    public String getLogin() {
        return this.login;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(int blocked) {
        if (blocked == 1) {
            isBlocked = true;
        }
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public int getLibraryCardNumber() {
        return libraryCardNumber;
    }

    public void setLibraryCardNumber(int libraryCardNumber) {
        this.libraryCardNumber = libraryCardNumber;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", address='" + address + '\'' +
                ", role=" + role +
                ", login='" + login + '\'' +
                ", mobilePhone='" + mobilePhone + '\'' +
                '}';
    }
}
