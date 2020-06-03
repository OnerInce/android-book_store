package com.zurefaseverler.kithub;

public class OrderDetailsBookObj {

    private String bookName, quantity;
    private float piecePrice, discount;

    OrderDetailsBookObj(String bookName, String quantity, float piecePrice, float discount) {
        this.bookName = bookName;
        this.quantity = quantity;
        this.piecePrice = piecePrice;
        this.discount = discount;
    }

    public String getBookName() {
        return bookName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public float getPiecePrice() {
        return piecePrice;
    }

    public float getDiscount() {
        return discount;
    }
}
