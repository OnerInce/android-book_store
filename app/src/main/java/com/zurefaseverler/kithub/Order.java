package com.zurefaseverler.kithub;

public class Order {

    private int book_id, quantity, single_price;

    public Order(int book_id, int quantity, int single_price) {
        this.book_id = book_id;
        this.quantity = quantity;
        this.single_price = single_price;
    }

    public int getBook_id() {
        return book_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getSingle_price() {
        return single_price;
    }

    @Override
    public String toString() {
        return "Order{" +
                "book_id=" + book_id +
                ", quantity=" + quantity +
                ", single_price=" + single_price +
                '}';
    }
}
