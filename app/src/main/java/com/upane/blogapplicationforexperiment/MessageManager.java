package com.upane.blogapplicationforexperiment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.widget.ListView;

import java.util.*;

public class MessageManager {
    private MessageService messageService;
    private Context c;
    public MessageManager(Context c)
    {
        this.c=c;
        messageService=new MessageService(c);
    }

    public void addMessage(Message message)
    {
        boolean isAdd=true;
        List<Message> messageList=getMessages();
        if(messageList.size()!=0)for(Message tempMsg:getMessages())
        {
            if(message.equals(tempMsg))
            {
                isAdd=false;
                break;
            }
        }
        if(isAdd) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("user_name", message.getUser().getUserName());
            contentValues.put("content", message.getContent());
            contentValues.put("post_date", message.getPostDate().toString());
            messageService.insert(contentValues);
        }
    }

    public List<Message> getMessages()
    {
        UserManager userManager=new UserManager(c);
        Cursor c=messageService.query();
        c.moveToFirst();
        List<Message> messages=new ArrayList<>();
        if(c.getCount()!=0) {
            while (true) {
                User tempUser = null;
                List<User> users = userManager.getUsers();
                for (User user : users) {
                    String returnString = c.getString(1);
                    if (user.getUserName().equals(returnString)) {
                        tempUser = user;
                        break;
                    }

                }
                Message message = new Message(tempUser, new Date(c.getString(3)), c.getString(2));
                messages.add(message);
                if (c.isLast()) break;
                else c.moveToNext();
            }
        }
        return messages;
    }

    public void DeleteMessage(int id)
    {
        messageService.del(String.valueOf(id));
    }
}
