package com.zurefaseverler.kithub;

public class OrderConfirmOrders {

    private String orderid;
    private String userid;
    private String date;
    private String numofbook;
    private String totalamount;
    private String shipper;
    private String address;

    public OrderConfirmOrders(String orderid, String userid, String date, String numofbook, String totalamount, String address, String shipper) {
        this.orderid = orderid;
        this.userid = userid;
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

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
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

    public void setNumofbook(String numofbook) {
        this.numofbook = numofbook;
    }

    public String getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(String totalamount) {
        this.totalamount = totalamount;
    }
}
