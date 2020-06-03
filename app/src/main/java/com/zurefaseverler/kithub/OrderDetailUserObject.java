package com.zurefaseverler.kithub;

public class OrderDetailUserObject {

    private String orderDetailName,orderDetailPrice;
    private int orderDetailImage;

    public OrderDetailUserObject(String orderDetailName, String orderDetailPrice, int orderDetailImage) {
        this.orderDetailName = orderDetailName;
        this.orderDetailPrice = orderDetailPrice;
        this.orderDetailImage = orderDetailImage;
    }


    public String getOrderDetailName() {
        return orderDetailName;
    }

    public void setOrderDetailName(String orderDetailName) {
        this.orderDetailName = orderDetailName;
    }

    public String getOrderDetailPrice() {
        return orderDetailPrice;
    }

    public void setOrderDetailPrice(String orderDetailPrice) {
        this.orderDetailPrice = orderDetailPrice;
    }

    public int getOrderDetailImage() {
        return orderDetailImage;
    }

    public void setOrderDetailImage(int orderDetailImage) {
        this.orderDetailImage = orderDetailImage;
    }
}
