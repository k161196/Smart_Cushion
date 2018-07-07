package com.example.kms.kiran_c.activitydemo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.kms.kiran_c.R;


public class Display extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display);
        String username = getIntent().getStringExtra("Username");

        TextView tv = (TextView)findViewById(R.id.TVusername);
        tv.setText(username);

    }
}
