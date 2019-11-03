package com.upane.blogapplicationforexperiment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

import java.util.*;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    User user;
    List<Message> messages;
    ListView lvMessage;
    MessageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initMainWindow();
        addTestData();
        user=new User(getResources().getString(R.string.nav_header_title));
        user.setImageResCode(R.drawable.userimage);
        user.setDevice("TestDevice");
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

    private void addTestData()
    {

        User user1=new User("张三");
        user1.setImageResCode(R.mipmap.user1);
        user1.setDevice("HuaWei P20");
        String content1="Hello World!";
        Message message1=new Message(user1,new Date(),content1);

        User user2=new User("Shuyo");
        user2.setImageResCode(R.mipmap.user2);
        user2.setDevice("Sony Xperia 5");
        String content2="人们崇拜资本所具有的勃勃生机，崇拜其神话色彩，崇拜东京地价，崇拜“保时捷”那闪闪发光的标志。除此之外，这个世界上再不存在任何神话。\n" + "在这样的世界上，哲学愈发类似经营学，愈发紧贴时代的脉搏。";
        Message message2=new Message(user2,new Date(),content2);

        User user3=new User("OA");
        user3.setImageResCode(R.mipmap.user3);
        user3.setDevice("iPhone");
        String content3="Think of nothing things,think of wind.";
        Message message3=new Message(user3,new Date(),content3);

        messages=new ArrayList<Message>();
        messages.add(message1);
        messages.add(message2);
        messages.add(message3);
        adapter=new MessageAdapter(this,messages);
        ListView lv=findViewById(R.id.lvMessage);
        lv.setAdapter(adapter);
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

        ImageView iv=findViewById(R.id.ivUser);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,SettingActivity.class);
                startActivity(intent);
            }
        });
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
            newMes=new Message(user,new Date(),message);
            messages.add(newMes);
        }
        adapter.notifyDataSetChanged();
    }
}