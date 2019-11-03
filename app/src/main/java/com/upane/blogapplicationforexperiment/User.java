package com.upane.blogapplicationforexperiment;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import java.util.Date;

public class User {
    private String userName,gender,eMail,device;
    private Date signInDate;
    private int imageResCode;
    public User(String userName)
    {
        this.userName=userName;
    }

    public int getImageResCode() {
        return imageResCode;
    }

    public String getUserName()
    {
        return userName;
    }

    public String getGender()
    {
        return gender;
    }

    public Date getSignInDate()
    {
        return signInDate;
    }

    public String geteMail() {
        return eMail;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setSignInDate(Date signInDate) {
        this.signInDate = signInDate;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setImageResCode(int imageResCode) {
        this.imageResCode = imageResCode;
    }
}
