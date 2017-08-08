package by.epam.bokhan.entity;

import java.time.LocalDate;

/**
 * Created by vbokh on 03.08.2017.
 */
public class Author {
    private int id;
    private String name;
    private String surname;
    private String patronymic;
    private LocalDate dateOfBirth;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        String t = patronymic!=null?patronymic:"";
        String s = name + " " + surname+ " " + t + ";";
        return s;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Author author = (Author) o;

        if (!name.equals(author.name)) return false;
        if (!surname.equals(author.surname)) return false;
        return patronymic != null ? patronymic.equals(author.patronymic) : author.patronymic == null;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + surname.hashCode();
        result = 31 * result + (patronymic != null ? patronymic.hashCode() : 0);
        return result;
    }
}