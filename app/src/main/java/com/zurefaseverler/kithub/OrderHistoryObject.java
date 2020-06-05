package com.zurefaseverler.kithub;

class OrderHistoryObject {

    private String productName,productPrice,productDate;
    private String productImage;

    private String order_id;
    private String bookCount;

    OrderHistoryObject(String productName, String productPrice, String productDate, String productImage, String bookCount, String order_id){
        this.productDate=productDate;
        this.productName=productName;
        this.productPrice=productPrice;
        this.productImage=productImage;
        this.order_id = order_id;

        int count = Integer.parseInt(bookCount) - 1;
        this.bookCount = Integer.toString(count);
    }
    String getBookCount() {
        return bookCount;
    }
    String getOrder_id() {
        return order_id;
    }
    String getProductImage() {
        return productImage;
    }
    String getProductName() {
        return productName;
    }
    String getProductPrice() {
        return productPrice;
    }
    String getProductDate() {
        return productDate;
    }
}
