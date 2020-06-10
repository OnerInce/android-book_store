package com.zurefaseverler.kithub;

public class OldCommentsObj {

    private String bookname, comment, date;
    private int rate;

    public OldCommentsObj(String bookname, String comment, String date, int rate) {
        this.bookname = bookname;
        this.comment = comment;
        this.date = date;
        this.rate = rate;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getComment() {
        return comment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getRate() {
        return rate;
    }
}
