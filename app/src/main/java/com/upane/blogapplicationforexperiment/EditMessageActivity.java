package com.upane.blogapplicationforexperiment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class EditMessageActivity extends AppCompatActivity {
    ImageButton btnSend;
    EditText textbox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_message);
        btnSend=findViewById(R.id.btnSend);
        textbox=findViewById(R.id.etContent);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.putExtra("content",textbox.getText().toString());
                setResult(100,intent);
                finish();
            }
        });
    }



    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if(keyCode == KeyEvent.KEYCODE_BACK){
            Intent intent=new Intent();
            setResult(100,intent);
            finish();
        }
        return false;
    }
}
