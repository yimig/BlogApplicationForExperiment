package com.upane.blogapplicationforexperiment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.*;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private User user;
    private ListView lvMessage;
    private List<Message> messages;
    private MessageAdapter adapter;
    private MessageManager messageManager;
    private UserManager userManager;
    private Context context=this;
    private Message newMsg;
    private boolean isSendMsg=false;
    NetHandler handler=new NetHandler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initMainWindow();
        messageManager=new MessageManager(this);
        userManager=new UserManager(this);
        messages=new ArrayList<>();
        //addTestData();
//        user=new User(getResources().getString(R.string.nav_header_title));
//        user.setImageResCode(R.drawable.userimage);
//        user.setDevice("TestDevice");
//        user.setSignInDate(new Date());
        //userManager.addUser(user);
        adapter=new MessageAdapter(this,messages);
        lvMessage=findViewById(R.id.lvMessage);
        lvMessage.setAdapter(adapter);
        getList();
    }

    private void initDefaultUser()
    {
        new DefaultUserThread().start();
    }

    private void initMainWindow()
    {
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.btnAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,EditMessageActivity.class);
                startActivityForResult(intent,100);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        lvMessage=findViewById(R.id.lvMessage);
    }

//    private void addTestData()
//    {
//
//        User user1=new User("张三");
//        user1.setImageResCode(R.mipmap.user1);
//        user1.setDevice("HuaWei P20");
//        user1.setSignInDate(new Date());
//        String content1="Hello World!";
//        Message message1=new Message(user1,new Date(),content1);
//
//        User user2=new User("Shuyo");
//        user2.setImageResCode(R.mipmap.user2);
//        user2.setDevice("Sony Xperia 5");
//        user2.setSignInDate(new Date());
//        String content2="人们崇拜资本所具有的勃勃生机，崇拜其神话色彩，崇拜东京地价，崇拜“保时捷”那闪闪发光的标志。除此之外，这个世界上再不存在任何神话。\n" + "在这样的世界上，哲学愈发类似经营学，愈发紧贴时代的脉搏。";
//        Message message2=new Message(user2,new Date(),content2);
//
//        User user3=new User("OA");
//        user3.setImageResCode(R.mipmap.user3);
//        user3.setDevice("iPhone");
//        user3.setSignInDate(new Date());
//        String content3="Think of nothing things,think of wind.";
//        Message message3=new Message(user3,new Date(),content3);
//        userManager.addUser(user1);
//        userManager.addUser(user2);
//        userManager.addUser(user3);
//        messageManager.addMessage(message1);
//        messageManager.addMessage(message2);
//        messageManager.addMessage(message3);
//        refreshList();
//        adapter=new MessageAdapter(this,messages);
//        ListView lv=findViewById(R.id.lvMessage);
//        lv.setAdapter(adapter);
//    }

    private void refreshList(List<Message> list)
    {
        if(messages.size()!=0)messages.clear();
        messages.addAll(list);
    }

    private void getList()
    {
        (new NetThread()).start();
    }

    public class NetThread extends Thread {
        @Override
        public void run() {
            super.run();
            ConnHelper connHelper=new ConnHelper();
            if(isSendMsg)
            {
                MessageManager msm=new MessageManager(context);
                msm.addMessage(newMsg);
                isSendMsg=false;
            }
            connHelper.getMessages(user);
            List<Message> msgs=connHelper.getMessagesList();
            ImgManager imgManager=new ImgManager(context);
            imgManager.SyncImgDataBase(msgs);
            refreshList(msgs);
            handler.obtainMessage(1).sendToTarget();

        }
    }

    public class DefaultUserThread extends Thread {
        @Override
        public void run() {
            super.run();
            ConnHelper connHelper=new ConnHelper();
            connHelper.getUsers(null);
            for (User tempu:connHelper.getUsersList()) {
                if(tempu.getUserName().equals("OA"))user=tempu;
            }
            ImgManager imgManager=new ImgManager(context);
            imgManager.appendUserImg(user);
            handler.obtainMessage(0).sendToTarget();

        }
    }

    public class NetHandler extends Handler{
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case 0:{
                    TextView tvUserName=findViewById(R.id.tvUserName);
                    ImageView ivUserImg=findViewById(R.id.ivUserImg);
                    tvUserName.setText(user.getUserName());
                    ivUserImg.setImageBitmap(BitmapFactory.decodeFile(context.getFilesDir().getAbsolutePath()+"/"+user.getImagePath()));
                    break;
                }
                case 1:{
                    adapter.notifyDataSetChanged();
                    break;
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.main, menu);

        ImageView iv=findViewById(R.id.ivUserImg);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,SettingActivity.class);
                startActivity(intent);
            }
        });
        initDefaultUser();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_message) {
            // Handle the camera action
        } else if (id == R.id.nav_favorite) {

        } else if (id == R.id.nav_setting) {

        } else if (id == R.id.nav_tools) {}

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String message=data.getStringExtra("content");
        Message newMes;
        if(message!=null){
            newMsg=new Message(user,new Date(),message);
            isSendMsg=true;
            getList();
        }
    }
}
