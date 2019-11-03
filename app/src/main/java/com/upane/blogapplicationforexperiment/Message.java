package com.upane.blogapplicationforexperiment;

import android.media.Image;

import java.util.Date;

public class Message {
    private User user;
    private String content;
    private Date postDate;
    public Message(User user,Date postDate,String content)
    {
        this.user=user;
        this.content=content;
        this.postDate=postDate;
    }

    public User getUser() {
        return user;
    }

    public String getPostDate() {
        return postDate.getYear()+1990+"/"+postDate.getMonth()+"/"+postDate.getDay()+" "+postDate.getHours()+":"+postDate.getMinutes();
    }

    public String getContent() {
        return content;
    }
}
