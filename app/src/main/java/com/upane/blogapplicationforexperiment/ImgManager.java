package com.upane.blogapplicationforexperiment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ImgManager {
    private ImgService imgService;
    private Context context;

    public ImgManager(Context c)
    {
        this.context=c;
        imgService=new ImgService(c);
    }

    public void SyncImgDataBase(List<Message> msgs)
    {
        for (Message msg:msgs) {
            appendUserImg(msg.getUser());
        }
    }

    public void appendUserImg(User user)
    {
        user.setImagePath(getImg(user.getImageResCode()));
    }

    private String getImg(int imgId)
    {
        String path="";
        String res=getImgPath(imgId);
        if(res.equals(""))path=getAndSaveBitmap(imgId);
        else path=getImgPath(imgId);
        return path;
    }

    private String getAndSaveBitmap(int imgId)
    {
        ConnHelper connHelper=new ConnHelper();
        connHelper.getImg(imgId);
        String fileName=imgId+"."+connHelper.getImgType();
        saveImgFile(connHelper.getImgBytes(),fileName);
        addImg(imgId,fileName);
        return fileName;
    }

    private void saveImgFile(byte[] imgByte,String fileName)
    {
        try{
            FileOutputStream fileOutputStream=context.openFileOutput(fileName,Context.MODE_PRIVATE);
            fileOutputStream.write(imgByte);
        }catch (FileNotFoundException e){System.out.println(e.fillInStackTrace());}
        catch (IOException e){System.out.println(e.getStackTrace());}

    }


    public String getImgPath(int imgId)
    {
        Cursor c=imgService.query(imgId);
        String path="";
        c.moveToFirst();
        if(c.getCount()!=0)
        {
            path=c.getString(1);
        }
        return path;
    }

    private void addImg(int imgId,String imgPath)
    {
        ContentValues contentValues=new ContentValues();
        contentValues.put("img_id",imgId);
        contentValues.put("img_path",imgPath);
        imgService.insert(contentValues);
    }

    public void deleteImg(int imgId)
    {
        imgService.del(imgId);
    }
}
