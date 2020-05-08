package com.zurefaseverler.kithub;
public class Book {
    private int book_id, author_id, stockQuantity, category_id, bookType_id, price, sales, ratedCount;
    private float rating;
    private String ISBN, title, summary, image;

    public Book(String title) {
        this.title = title;
    }

    public int getBook_id() {
        return book_id;
    }

    public int getAuthor_id() {
        return author_id;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public int getCategory_id() {
        return category_id;
    }

    public int getBookType_id() {
        return bookType_id;
    }

    public int getPrice() {
        return price;
    }

    public int getSales() {
        return sales;
    }

    public int getRatedCount() {
        return ratedCount;
    }

    public float getRating() {
        return rating;
    }

    public String getISBN() {
        return ISBN;
    }

    public String getTitle() {
        return title;
    }

    public String getSummary() {
        return summary;
    }

    public String getImage() {
        return image;
    }



}
