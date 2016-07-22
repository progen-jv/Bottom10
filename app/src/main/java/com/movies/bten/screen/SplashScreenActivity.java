package com.movies.bten.screen;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.movies.bten.R;

public class SplashScreenActivity extends AppCompatActivity implements Animation.AnimationListener {
    private ImageView imgLogo;
    private TextView txtAppName;
    private TextView txtAppDesc;

    private Animation circleAnimation;
    private Animation logoAnimation;
    private Animation descAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        imgLogo = (ImageView) findViewById(R.id.square_red);
        txtAppName = (TextView) findViewById(R.id.txtAppName);
        txtAppDesc = (TextView) findViewById(R.id.txtAppDesc);

        circleAnimation = AnimationUtils.loadAnimation(this, R.anim.grow);
        imgLogo.startAnimation(circleAnimation);
        circleAnimation.setAnimationListener(this);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (animation == circleAnimation) {
            logoAnimation = AnimationUtils.loadAnimation(SplashScreenActivity.this, R.anim.grow_fast);
            logoAnimation.setAnimationListener(this);
            txtAppName.startAnimation(logoAnimation);
        } else if (animation == logoAnimation) {
            descAnimation = AnimationUtils.loadAnimation(SplashScreenActivity.this, R.anim.abc_slide_in_top);
            descAnimation.setFillAfter(true);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    txtAppDesc.startAnimation(descAnimation);
                }
            }, 500);
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
