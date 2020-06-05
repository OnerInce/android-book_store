package com.zurefaseverler.kithub;

public class OrderDetailUserObject {

    private String orderDetailName,orderDetailPrice;
    private String orderDetailImage;



    private String orderDetailQuantity;

    public OrderDetailUserObject(String orderDetailName, String orderDetailPrice, String orderDetailImage, String orderDetailQuantity) {
        this.orderDetailName = orderDetailName;
        this.orderDetailPrice = orderDetailPrice;
        this.orderDetailImage = orderDetailImage;
        this.orderDetailQuantity = orderDetailQuantity;
    }


    public String getOrderDetailName() {
        return orderDetailName;
    }

    public void setOrderDetailName(String orderDetailName) {
        this.orderDetailName = orderDetailName;
    }
    public String getOrderDetailQuantity() {
        return orderDetailQuantity;
    }
    public String getOrderDetailPrice() {
        return orderDetailPrice;
    }

    public void setOrderDetailPrice(String orderDetailPrice) {
        this.orderDetailPrice = orderDetailPrice;
    }

    public String getOrderDetailImage() {
        return orderDetailImage;
    }

    public void setOrderDetailImage(String orderDetailImage) {
        this.orderDetailImage = orderDetailImage;
    }
}
