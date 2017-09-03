package by.epam.bokhan.entity;

import java.util.ArrayList;
import java.util.List;


public class Book {
    /*Book id*/
    private int id;
    /*Book title*/
    private String title;
    /*Number of book pages*/
    private int pages;
    /*Isbn of the book*/
    private String isbn;
    /*Year of publishing*/
    private int year;
    /*Book publisher*/
    private Publisher publisher;
    /*Book description*/
    private String description;
    /*Book genres*/
    private List<Genre> genre;
    /*Book location*/
    private Location location;
    /*Book authors*/
    private List<Author> authors;
    /*Orders of this book*/
    private List<Order> orders;
    /*Book image*/
    private String image;

    public Book() {
        genre = new ArrayList<>();
        authors = new ArrayList<>();
        orders = new ArrayList<>();
    }

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

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
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

    public void addGenre(Genre genre) {
        this.genre.add(genre);
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public void addAuthor(Author author) {
        authors.add(author);
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        return id == book.id && pages == book.pages &&
                year == book.year && (title != null ? title.equals(book.title) : book.title == null)
                && (isbn != null ? isbn.equals(book.isbn) : book.isbn == null);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + pages;
        result = 31 * result + (isbn != null ? isbn.hashCode() : 0);
        result = 31 * result + year;
        return result;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", pages=" + pages +
                ", isbn='" + isbn + '\'' +
                ", year=" + year +
                ", publisher='" + publisher + '\'' +
                ", genre=" + genre +
                ", location=" + location +
                '}';
    }
}
