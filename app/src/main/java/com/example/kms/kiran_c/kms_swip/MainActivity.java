package com.example.kms.kiran_c.kms_swip;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import com.example.kms.kiran_c.R;

//this project developped by iheb ben salem@IBSSoft
public class MainActivity extends FragmentActivity {

    ViewPager pager;
PagerTabStrip tab_strp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_tab);
        ma_pager_adapter mapager=new ma_pager_adapter(getSupportFragmentManager());
        pager=(ViewPager)findViewById(R.id.pager);

        pager.setAdapter(mapager);
tab_strp=(PagerTabStrip)findViewById(R.id.tab_strip);
tab_strp.setTextColor(Color.WHITE);
     //   tab_strp.setTextSize(14,14);
       // tab_strp.setTabIndicatorColor(Color.WHITE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.share) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
