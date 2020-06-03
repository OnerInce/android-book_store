package com.zurefaseverler.kithub;

public class OrderConfirmOrders {

    private String orderid;
    private String userName;
    private String date;
    private String numofbook;
    private float totalamount;
    private String shipper;
    private String address;

    public OrderConfirmOrders(String orderid, String userName, String date, String numofbook, float totalamount, String address, String shipper) {
        this.orderid = orderid;
        this.userName = userName;
        this.date = date;
        this.numofbook = numofbook;
        this.totalamount = totalamount;
        this.address = address;
        this.shipper = shipper;
    }

    public String getAddress() {
        return address;
    }

    public String getShipper() {
        return shipper;
    }

    public String getOrderid() {
        return orderid;
    }

    public String getUserName() {
        return userName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNumofbook() {
        return numofbook;
    }

    public float getTotalamount() {
        return totalamount;
    }

}
