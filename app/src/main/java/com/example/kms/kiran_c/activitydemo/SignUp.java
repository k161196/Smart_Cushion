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
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kms.kiran_c.R;


public class SignUp extends Activity {
    private static final int REQUEST_SIGNUP = 0;
    Animation animTranslate;
    DatabaseHelper helper = new DatabaseHelper(this);
    ActivityOptionsCompat compat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        animTranslate = AnimationUtils.loadAnimation(this,
                R.anim.anim_translate);
        if(Build.VERSION.SDK_INT>=21){
            Slide slide=new Slide();
            slide.setDuration(800);
            getWindow().setEnterTransition(slide);
            TransitionInflater inflater=TransitionInflater.from(this);
            Transition transition=inflater.inflateTransition(R.transition.transition_a);
            getWindow().setExitTransition(transition);

        }
        TextView linksu=(TextView)findViewById(R.id.link_login);
        linksu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                v.startAnimation(animTranslate);
               /* Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);*/
                startActivity(new Intent(SignUp.this, MainActivity.class), compat.toBundle());
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        finish();
                    }
                }, 2000);

            }
        });
    }

    public void onSignUpClick(View v)
    {
        if(v.getId()== R.id.Bsignupbutton)
        {
            final Animation animAlpha = AnimationUtils.loadAnimation(this,
                    R.anim.anim_alpha);
            v.startAnimation(animAlpha);
            EditText name = (EditText)findViewById(R.id.TFname);
            EditText email = (EditText)findViewById(R.id.TFAge);
            EditText uname = (EditText)findViewById(R.id.TFuname);
            EditText pass1 = (EditText)findViewById(R.id.TFpass1);
            EditText pass2 = (EditText)findViewById(R.id.TFpass2);

            String namestr = name.getText().toString();
            String emailstr = email.getText().toString();
            String unamestr = uname.getText().toString();
            String pass1str = pass1.getText().toString();
            String pass2str = pass2.getText().toString();

            if(!pass1str.equals(pass2str))
            {
                //popup msg
                Toast pass = Toast.makeText(SignUp.this, "Passwords don't match!", Toast.LENGTH_SHORT);
                pass.show();
            }
            else
            {
                //insert the detailes in database
                Contact c = new Contact();
                c.setName(namestr);
                c.setEmail(emailstr);
                c.setUname(unamestr);
                c.setPass(pass1str);

                helper.insertContact(c);
                Toast.makeText(getApplicationContext(), "Save Done ", Toast.LENGTH_SHORT).show();
                ActivityOptionsCompat compat= ActivityOptionsCompat.makeSceneTransitionAnimation(this, null);
                startActivity(new Intent(SignUp.this, com.example.kms.kiran_c.kms_swip.MainActivity.class), compat.toBundle());
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                       finish();
                    }
                }, 2000);
            }

        }

    }
    @Override
    public void onStart(){
        super.onStart();
        compat= ActivityOptionsCompat.makeSceneTransitionAnimation(this, null);

    }
    @Override
    public void onBackPressed()
    {
finish();
    }

}
