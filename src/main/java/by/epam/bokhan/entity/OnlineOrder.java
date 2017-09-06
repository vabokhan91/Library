package by.epam.bokhan.entity;


public class OnlineOrder extends Order {

    /*Status of the online order*/
    private OrderStatus orderStatus;

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
