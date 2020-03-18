package com.example.vastum;

public class usersInfo {
    String userPoints,userSoldProduct,userID;

    public usersInfo(){

    }

    public usersInfo(String uID){
        userID = uID;
        userPoints="";
        userSoldProduct="";
    }

    public usersInfo(String userPoints, String userSoldProduct, String userID) {
        this.userPoints = userPoints;
        this.userSoldProduct = userSoldProduct;
        this.userID = userID;
    }

    public String getUserPoints() {
        return userPoints;
    }

    public void setUserPoints(String userPoints) {
        this.userPoints = userPoints;
    }

    public String getUserSoldProduct() {
        return userSoldProduct;
    }

    public void setUserSoldProduct(String userSoldProduct) {
        this.userSoldProduct = userSoldProduct;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
