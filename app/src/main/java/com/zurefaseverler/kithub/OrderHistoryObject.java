package com.zurefaseverler.kithub;

public class OrderHistoryObject {

    private String productName,productPrice,productDate;
    private int productImage;

    public OrderHistoryObject(String productName,String  productPrice,String productDate,int productImage){
        this.productDate=productDate;
        this.productName=productName;
        this.productPrice=productPrice;
        this.productImage=productImage;

    }

    public int getProductImage() {
        return productImage;
    }

    public void setProductImage(int productImage) {
        this.productImage = productImage;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductDate() {
        return productDate;
    }

    public void setProductDate(String productDate) {
        this.productDate = productDate;
    }
}
