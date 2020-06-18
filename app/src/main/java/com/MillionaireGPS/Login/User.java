package com.MillionaireGPS.Login;

import com.google.gson.annotations.SerializedName;

public class User {


    private int id;
    @SerializedName("codev")
    private String userCode;
    @SerializedName("namev")
    private String userName;

    public User() {
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}