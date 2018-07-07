package com.example.kms.kiran_c.kms_swip;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.kms.kiran_c.R;


public class tab4 extends Fragment {


    Button bt;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab4, container, false);

        Button mButton = (Button) view.findViewById(R.id.bBD);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // here you set what you want to do when user clicks your button,
                Intent intent = new Intent("com.example.kms.kiran_c.BluetoothDemo");
                startActivity(intent);
            }
        });


        return view;

    }
}
