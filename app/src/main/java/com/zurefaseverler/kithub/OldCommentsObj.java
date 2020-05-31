package com.zurefaseverler.kithub;

public class OldCommentsObj {

    String bookname, comment, date;

    int rate;

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

    public void setComment(String comment) {
        this.comment = comment;
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

    public void setRate(int rate) {
        this.rate = rate;
    }
}
