package com.zurefaseverler.kithub;

public class Cart {
    private String image, pid, pname, quantity;
    private float discount, price, totalPrice = 0;

    public Cart(String pid, String pname, float price, String quantity, float discount, String image, float totalPrice) {
        this.pid = pid;
        this.pname = pname;
        this.price = price;
        this.quantity = quantity;
        this.discount = discount;
        this.image = image;
        this.totalPrice += totalPrice;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public String getImage() {
        return image;
    }

    public String getPid() {
        return pid;
    }

    public String getPname() {
        return pname;
    }

    public float getPrice() {
        return price;
    }

    public String getQuantity() {
        return quantity;
    }

    public float getDiscount() {
        return discount;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
