package com.example.kms.kiran_c.activitydemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityOptionsCompat;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kms.kiran_c.R;
import com.example.kms.kiran_c.bluetooth_data;

public class MainActivity extends Activity {

    DatabaseHelper helper = new DatabaseHelper(this);

    private static final int REQUEST_SIGNUP = 0;
    Animation animAlpha;
    Animation animTranslate;
    ActivityOptionsCompat compat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT>=21){
            Slide slide=new Slide();
            slide.setDuration(1000);
            getWindow().setEnterTransition(slide);
            TransitionInflater inflater=TransitionInflater.from(this);
            Transition transition=inflater.inflateTransition(R.transition.transition_a);
            getWindow().setExitTransition(transition);

        }
        setContentView(R.layout.activity_main);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

       animTranslate = AnimationUtils.loadAnimation(this,
                R.anim.anim_translate);
        animAlpha = AnimationUtils.loadAnimation(this,
                R.anim.anim_alpha);

        TextView linksu=(TextView)findViewById(R.id.link_signup);
        linksu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity

                v.startAnimation(animTranslate);
                startActivity(new Intent(MainActivity.this, SignUp.class), compat.toBundle());

                /*Intent intent = new Intent(getApplicationContext(), SignUp.class);
                startActivityForResult(intent, REQUEST_SIGNUP);*/
            }
        });

    }
    @Override
    public void onStart(){
        super.onStart();
        compat= ActivityOptionsCompat.makeSceneTransitionAnimation(this, null);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void onButtonClick(View v)
    {
        if(v.getId() == R.id.Blogin)
        {
            v.startAnimation(animAlpha);
            EditText a = (EditText)findViewById(R.id.TFusername);
            String str = a.getText().toString();
            EditText b = (EditText)findViewById(R.id.TFpassword);
            String pass = b.getText().toString();

            String password = helper.searchPass(str);
            if(pass.equals(password)) {

                ActivityOptionsCompat compat= ActivityOptionsCompat.makeSceneTransitionAnimation(this, null);
                startActivity(new Intent(MainActivity.this, bluetooth_data.class), compat.toBundle());
            }
            else
            {
                Toast temp = Toast.makeText(MainActivity.this, "Username and password don't match!", Toast.LENGTH_SHORT);
                temp.show();
            }



        }

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

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
               finish();
            }
        }, 2000);
    }
}
