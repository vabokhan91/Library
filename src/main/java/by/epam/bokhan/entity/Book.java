package by.epam.bokhan.entity;

import java.util.List;

/**
 * Created by vbokh on 24.07.2017.
 */
public class Book {
    private int id;
    private String title;
    private int pages;
    private String isbn;
    private int year;
    private String publisher;
    private String description;
    private List<Genre> genre;
    private Location location;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Genre> getGenre() {
        return genre;
    }

    public void setGenre(List<Genre> genre) {
        this.genre = genre;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
