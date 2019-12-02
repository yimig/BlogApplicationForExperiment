package com.upane.blogapplicationforexperiment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.*;

public class UserManager {
    private UserService userService;
    public UserManager(Context c)
    {
        userService=new UserService(c);
    }

    public void addUser(User user)
    {
        boolean isAdd=true;
        List<User> userList=getUsers();
        if(userList.size()!=0)for(User tempUser:userList)
        {
            if(user.equals(tempUser))
            {
                isAdd=false;
                break;
            }
        }
        if(isAdd) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("user_name", user.getUserName());
            contentValues.put("gender", user.getGender());
            contentValues.put("e_mail", user.geteMail());
            contentValues.put("device", user.getDevice());
            contentValues.put("date", user.getSignInDate().toString());
            contentValues.put("img_code", user.getImageResCode());
            userService.insert(contentValues);
        }
    }

    public List<User> getUsers()
    {
        Cursor c=userService.query();
        c.moveToFirst();
        List<User> users = new ArrayList<>();
        if(c.getCount()!=0) {
            while (true) {
                User user = new User(c.getString(0));
                user.setGender(c.getString(1));
                user.seteMail(c.getString(2));
                user.setDevice(c.getString(3));
                user.setSignInDate(new Date(c.getString(4)));
                user.setImageResCode(c.getInt(5));
                users.add(user);
                if (c.isLast()) break;
                else c.moveToNext();
            }
        }
        return users;
    }

    public void DeleteUser(String userName)
    {
        userService.del(userName);
    }
}
