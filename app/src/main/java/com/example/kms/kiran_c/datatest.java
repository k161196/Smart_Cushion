package com.example.kms.kiran_c;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class datatest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datatest);
        TextView newtxt=(TextView) findViewById(R.id.view);
        Intent intent = getIntent();

        String fName = intent.getStringExtra("fname");

        newtxt.setText("Your name is: " + fName);

    }

}
