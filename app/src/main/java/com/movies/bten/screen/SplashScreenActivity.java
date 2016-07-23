package com.movies.bten.screen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.movies.bten.R;
import com.movies.bten.commons.Constants;
import com.movies.bten.commons.util.DeviceInfo;
import com.movies.bten.commons.util.MessageUtil;
import com.movies.bten.commons.util.ResourcesUtil;
import com.movies.bten.utils.http.DataHolder;
import com.movies.bten.utils.http.VolleyUtils;
import com.movies.bten.view.data.Genres;

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

        /* Initialize the animation */
        circleAnimation = AnimationUtils.loadAnimation(this, R.anim.grow);
        logoAnimation = AnimationUtils.loadAnimation(SplashScreenActivity.this, R.anim.grow_fast);
        descAnimation = AnimationUtils.loadAnimation(SplashScreenActivity.this, android.R.anim.slide_in_left);
        descAnimation.setFillAfter(true);

        logoAnimation.setAnimationListener(this);
        circleAnimation.setAnimationListener(this);
        descAnimation.setAnimationListener(this);

        imgLogo.startAnimation(circleAnimation);

        DataHolder.getInstance().populateYears();
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (animation == circleAnimation) {
            txtAppName.startAnimation(logoAnimation);
        } else if (animation == logoAnimation) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    txtAppDesc.startAnimation(descAnimation);
                }
            }, 500);
        } else if (animation == descAnimation) {
            if (!DeviceInfo.isConnected()) {
                MessageUtil.showMessage(ResourcesUtil.getString(R.string.no_network), true);
                finish();
            } else {
                this.loadGenres();
            }
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    private void loadGenres() {
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.GENRE_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Gson gson = new Gson();
                    DataHolder.getInstance().setGenres(gson.fromJson(response, Genres.class));
                    showMainScreen();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    MessageUtil.showMessage(ResourcesUtil.getString(R.string.error), true);
                }
            });
            VolleyUtils.getInstance(this).addToRequestQueue(stringRequest);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private void showMainScreen() {
        try {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
