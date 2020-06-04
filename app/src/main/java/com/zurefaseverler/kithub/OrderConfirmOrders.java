package com.zurefaseverler.kithub;

public class OrderConfirmOrders {

    private String orderid, userName, date, numofbook, shipper, address, status;
    private float totalamount;

    public OrderConfirmOrders(String orderid, String userName, String date, String numofbook, float totalamount, String address, String shipper, String status) {
        this.orderid = orderid;
        this.userName = userName;
        this.date = date;
        this.numofbook = numofbook;
        this.totalamount = totalamount;
        this.address = address;
        this.shipper = shipper;
        this.status = status;
    }

    public String getStatus() {
        return status;
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
