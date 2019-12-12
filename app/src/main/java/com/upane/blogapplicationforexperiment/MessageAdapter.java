package com.upane.blogapplicationforexperiment;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.*;
import android.view.View;

import java.io.File;
import java.util.List;

public class MessageAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<Message> messages;
    private Context context;


    public MessageAdapter(Context context , List<Message> messages){
        this.context=context;
        this.mInflater = LayoutInflater.from(context);
        this.messages=messages;
    }


    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return messages.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return messages.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder = null;

        if (convertView == null) {

            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_listview, null);
            holder.userName= (TextView)convertView.findViewById(R.id.userName);
            holder.userDevice = (TextView)convertView.findViewById(R.id.userDevice);
            holder.postDate = (TextView) convertView.findViewById(R.id.postDate);
            holder.content = (TextView) convertView.findViewById(R.id.content);
            holder.userImage = (ImageView) convertView.findViewById(R.id.userImg);
            holder.btnMore=convertView.findViewById(R.id.btnMore);
            holder.btnMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //这里添加绑定pop menu的方法
                }
            });
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        holder.userName.setText(messages.get(position).getUser().getUserName());
        holder.userDevice.setText(messages.get(position).getUser().getDevice());
        holder.postDate.setText(messages.get(position).getDateString());
        holder.content.setText(messages.get(position).getContent());
        holder.userImage.setImageBitmap(BitmapFactory.decodeFile(context.getFilesDir().getAbsolutePath()+"/"+messages.get(position).getUser().getImagePath()));
        return convertView;
    }


    public final class ViewHolder{

        public TextView userName,userDevice,postDate,content;
        public ImageView userImage;
        public ImageButton btnMore;
    }

}
