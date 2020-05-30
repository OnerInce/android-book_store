package com.zurefaseverler.kithub;

public class CommentObj {
    private String userName, usersComment, date;
    private int userRate;

    public CommentObj(String userName, String usersComment, String date, int userRate) {
        this.userName = userName;
        this.usersComment = usersComment;
        this.date = date;
        this.userRate = userRate;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUsersComment() {
        return usersComment;
    }

    public void setUsersComment(String usersComment) {
        this.usersComment = usersComment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getUserRate() {
        return userRate;
    }

    public void setUserRate(int userRate) {
        this.userRate = userRate;
    }


}
