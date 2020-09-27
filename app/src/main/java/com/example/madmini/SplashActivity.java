package com.example.madmini;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.madmini.ui.Login;
import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME = 3000;
    ImageView image;
    TextView hi,welcome;
    Animation topanim,bottomanim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        image = findViewById(R.id.imageView);
        hi = findViewById(R.id.textView);
        welcome = findViewById(R.id.textView2);

        //Animations
        topanim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomanim = AnimationUtils.loadAnimation(this, R.anim.botton_animation);

        image.setAnimation(topanim);
        hi.setAnimation(bottomanim);
        welcome.setAnimation(bottomanim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                FirebaseAuth.getInstance().signOut();
                Intent n = new Intent(SplashActivity.this, Login.class);
                startActivity(n);
                finish();

            }
        },SPLASH_TIME);
    }
}