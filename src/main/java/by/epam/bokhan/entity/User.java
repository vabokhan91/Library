package by.epam.bokhan.entity;

/**
 * Created by vbokh on 13.07.2017.
 */
public class User {
    private int id;
    private String name;
    private String surname;
    private String patronymic;
    private String address;
    private int roleId;
    private String login;
    private String password;
    private String mobilePhone;
    private boolean isBlocked;

    public User() {
    }

    public User(int id, String name, String surname, String patronymic, int roleId, String login) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.roleId = roleId;
        this.login = login;
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

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
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
        if (blocked == 0) {
            isBlocked = false;
        } else isBlocked = true;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", address='" + address + '\'' +
                ", roleId=" + roleId +
                ", login='" + login + '\'' +
                ", mobilePhone='" + mobilePhone + '\'' +
                '}';
    }
}
