package com.zurefaseverler.kithub;

public class OrderDetailsBookObj {

    private String bookName, quantity;
    private float piecePrice;

    public OrderDetailsBookObj(String bookName, String quantity, float piecePrice) {
        this.bookName = bookName;
        this.quantity = quantity;
        this.piecePrice = piecePrice;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
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

    public void setPiecePrice(float piecePrice) {
        this.piecePrice = piecePrice;
    }
}
