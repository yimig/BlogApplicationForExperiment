package com.upane.blogapplicationforexperiment;

public class Booter {
    private User DefaultUser;
    private boolean isReturn;
    private String OrderType;
    private String data;
    private User userData;
    private Message msgData;

    public User getDefaultUser() {
        return DefaultUser;
    }

    public void setDefaultUser(User defaultUser) {
        DefaultUser = defaultUser;
    }

    public boolean isReturn() {
        return isReturn;
    }

    public void setReturn(boolean aReturn) {
        isReturn = aReturn;
    }

    public String getOrderType() {
        return OrderType;
    }

    public void setOrderType(String orderType) {
        OrderType = orderType;
    }

    public User getUserData() {
        return userData;
    }

    public void setUserData(User userData) {
        this.userData = userData;
    }

    public Message getMsgData() {
        return msgData;
    }

    public void setMsgData(Message msgData) {
        this.msgData = msgData;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
