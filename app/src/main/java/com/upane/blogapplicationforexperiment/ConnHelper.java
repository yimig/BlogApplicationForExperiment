package com.upane.blogapplicationforexperiment;

import com.google.gson.Gson;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class ConnHelper {
    private List<Message> messagesList;
    private List<User> usersList;
    private String imgType;
    private byte[] imgBytes;

    public boolean getImg(int imgId)
    {
        boolean isSuccess=false;
        Booter booter=new Booter();
        booter.setReturn(true);
        booter.setOrderType("img");
        booter.setData(String.valueOf(imgId));
        try {
            imgBytes=getImg(postInfo(booter));
        }
        catch (IOException e){isSuccess=false;}
        return isSuccess;
    }

    public boolean getMessages(User user)
    {
        boolean isSuccess=false;
        Booter booter=new Booter();
        booter.setReturn(true);
        booter.setOrderType("message");
        booter.setDefaultUser(user);
        try {
            String resStr=getText(postInfo(booter));
            isSuccess=checkResult(resStr);
            Gson gson=new Gson();
            MessageList messageList=gson.fromJson(resStr,MessageList.class);
            messagesList=messageList.getCollection();
        }
        catch (IOException e){isSuccess=false;}
        return isSuccess;
    }

    public boolean getUsers(User user)
    {
        boolean isSuccess=false;
        Booter booter=new Booter();
        booter.setReturn(true);
        booter.setOrderType("user");
        booter.setDefaultUser(user);
        try {
            String resStr=getText(postInfo(booter));
            isSuccess=checkResult(resStr);
            Gson gson=new Gson();
            UserList userList=gson.fromJson(resStr,UserList.class);
            usersList=userList.getCollection();
        }
        catch (IOException e){isSuccess=false;}
        return isSuccess;
    }

    public boolean addMessages(Message msg)
    {
        boolean isSuccess=false;
        Booter booter=new Booter();
        booter.setReturn(false);
        booter.setOrderType("message");
        booter.setDefaultUser(msg.getUser());
        booter.setMsgData(msg);
        try {
            isSuccess=checkResult(getText(postInfo(booter)));
        }catch (IOException e){isSuccess=false;}
        return isSuccess;
    }

    private static HttpURLConnection postInfo(Booter booter) throws IOException
    {
        URL postUrl = new URL("http://192.168.43.195:8080/Blog_Service_Exp_war_exploded/DealServlet");
        HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestMethod("POST");
        connection.setUseCaches(false);
        connection.setInstanceFollowRedirects(true);
        connection.setRequestProperty("Content-Type","application/json");
        connection.connect();
        DataOutputStream out = new DataOutputStream(connection
                .getOutputStream());
        Gson gson=new Gson();
        String sendStr=gson.toJson(booter);
        String content = URLEncoder.encode(sendStr, "utf-8");
        out.writeBytes(content);
        out.flush();
        out.close();
        return connection;
    }

    private String getText(HttpURLConnection connection) throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line="",result="";
        while ((line = reader.readLine()) != null){
            result+=line;
        }
        reader.close();
        connection.disconnect();
        if(!result.equals(""))result=java.net.URLDecoder.decode(result,"utf-8");
        return result;
    }

    private byte[] getImg(HttpURLConnection connection) throws IOException
    {
        imgType=connection.getContentType().split("/|;")[1];
        InputStream stream=connection.getInputStream();
        byte[] b=new byte[connection.getContentLength()];
        stream.read(b);
        stream.close();
        connection.disconnect();
        return b;
    }


    private boolean checkResult(String responseStr)
    {
        return responseStr.equals("$Service ERROR$")?false:true;
    }

    public List<Message> getMessagesList() {
        return messagesList;
    }

    public void setMessagesList(List<Message> messagesList) {
        this.messagesList = messagesList;
    }

    public List<User> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<User> usersList) {
        this.usersList = usersList;
    }

    public String getImgType() {
        return imgType;
    }

    public void setImgType(String imgType) {
        this.imgType = imgType;
    }

    public byte[] getImgBytes() {
        return imgBytes;
    }

    public void setImgBytes(byte[] imgBytes) {
        this.imgBytes = imgBytes;
    }
}
