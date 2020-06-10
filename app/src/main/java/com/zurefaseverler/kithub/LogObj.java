package com.zurefaseverler.kithub;

public class LogObj {

    private String log_type, userid, username, useremail, phone, adress, logDate, cartOpType, bookId, totalAmount, numOfBook, orderId, commentID, commentRate, comment;

    public String getLog_type() {
        return log_type;
    }

    public String getUserid() {
        return userid;
    }

    public String getUsername() {
        return username;
    }

    public String getUseremail() {
        return useremail;
    }

    public String getPhone() {
        return phone;
    }

    public String getAdress() {
        return adress;
    }

    public String getlogDate() {
        return logDate;
    }

    public String getCartOpType() {
        return cartOpType;
    }

    public String getBookId() {
        return bookId;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public String getNumOfBook() {
        return numOfBook;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getCommentID() {
        return commentID;
    }

    public String getCommentRate() {
        return commentRate;
    }

    public String getComment() {
        return comment;
    }

    public LogObj(String log_type, String userid, String username, String useremail, String phone, String adress, String date, String cartOpType, String bookId, String totalAmount, String numOfBook, String orderId, String commentID, String commentRate, String comment) {
        this.log_type = log_type;
        this.userid = userid;
        this.username = username;
        this.useremail = useremail;
        this.phone = phone;
        this.adress = adress;
        this.logDate = date;
        this.cartOpType = cartOpType;
        this.bookId = bookId;
        this.totalAmount = totalAmount;
        this.numOfBook = numOfBook;
        this.orderId = orderId;
        this.commentID = commentID;
        this.commentRate = commentRate;
        this.comment = comment;

    }
}
