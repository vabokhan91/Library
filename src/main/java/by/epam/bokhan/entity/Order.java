package by.epam.bokhan.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.Locale;

/**
 * Created by vbokh on 29.07.2017.
 */
public class Order {
    private int id;
    private Book book;
    private User user;
    private LocalDate orderDate;
    private LocalDate expirationDate;
    private LocalDate returnDate;
    private User librarian;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public User getLibrarian() {
        return librarian;
    }

    public void setLibrarian(User librarian) {
        this.librarian = librarian;
    }

    @Override
    public String toString() {
        return "Order{" +
                "book=" + book +
                ", user=" + user +
                ", orderDate=" + orderDate +
                ", expirationDate=" + expirationDate +
                ", returnDate=" + returnDate +
                ", librarian=" + librarian +
                '}';
    }
}
