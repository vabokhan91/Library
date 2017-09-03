package by.epam.bokhan.entity;

import java.time.LocalDate;


public class Order {
    /*Order id*/
    private int id;
    /*Ordered book*/
    private Book book;
    /*User who made order*/
    private User user;
    /*Date of the order*/
    private LocalDate orderDate;
    /*Order expiration date*/
    private LocalDate expirationDate;
    /*Order return date*/
    private LocalDate returnDate;
    /*Librarian who executed order*/
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (id != order.id) return false;
        if (book != null ? !book.equals(order.book) : order.book != null) return false;
        if (user != null ? !user.equals(order.user) : order.user != null) return false;
        if (orderDate != null ? !orderDate.equals(order.orderDate) : order.orderDate != null) return false;
        if (expirationDate != null ? !expirationDate.equals(order.expirationDate) : order.expirationDate != null)
            return false;
        if (returnDate != null ? !returnDate.equals(order.returnDate) : order.returnDate != null) return false;
        return librarian != null ? librarian.equals(order.librarian) : order.librarian == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (book != null ? book.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (orderDate != null ? orderDate.hashCode() : 0);
        result = 31 * result + (expirationDate != null ? expirationDate.hashCode() : 0);
        result = 31 * result + (returnDate != null ? returnDate.hashCode() : 0);
        result = 31 * result + (librarian != null ? librarian.hashCode() : 0);
        return result;
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
