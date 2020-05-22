package com.zurefaseverler.kithub;

public class MainPageBook {

    private String imagepath, bookname, authorname, discountamount, price;

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getAuthorname() {
        return authorname;
    }

    public void setAuthorname(String authorname) {
        this.authorname = authorname;
    }

    public String getDiscountamount() {
        return discountamount;
    }

    public void setDiscountamount(String discountamount) {
        this.discountamount = discountamount;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public MainPageBook(String imagepath, String bookname, String authorname, String discountamount, String price) {
        this.imagepath = imagepath;
        this.bookname = bookname;
        this.authorname = authorname;
        this.discountamount = discountamount;
        this.price = price;
    }
}

