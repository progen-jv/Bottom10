package com.movies.bten.screen;

import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.movies.bten.R;
import com.movies.bten.commons.util.AppLog;

import java.io.IOException;

public class MovieTrailerActivity extends AppCompatActivity implements SurfaceHolder.Callback, View.OnClickListener {
    private final String TAG = MovieTrailerActivity.class.getName();
    public static final String TRAILER_URL = "TRAILER_URL";

    private MediaPlayer mediaPlayer;
    private SurfaceView mPreview;
    private SurfaceHolder surfaceHolder;
    private String trailerURL;
    private ProgressBar progressBar;
    private ImageButton btnClose;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_movie_trailer);

        progressBar = (ProgressBar) findViewById(R.id.progress);
        progressBar.setVisibility(View.VISIBLE);
        btnClose = (ImageButton) findViewById(R.id.btnClose);
        btnClose.setOnClickListener(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            trailerURL = bundle.getString(TRAILER_URL);
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        prepareSurface();
    }

    private void prepareSurface() {
        getWindow().setFormat(PixelFormat.UNKNOWN);
        mPreview = (SurfaceView) findViewById(R.id.surfaceTrailer);

        mediaPlayer = new MediaPlayer();
        surfaceHolder = mPreview.getHolder();
        surfaceHolder.addCallback(MovieTrailerActivity.this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
            }
        } catch (Exception ex) {
            AppLog.error(TAG, ex);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        System.out.println("surfaceCreated is called");
        mediaPlayer.setDisplay(surfaceHolder);
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                play();
            }
        });
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        try {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
            }
        } catch (Exception ex) {
            AppLog.error(TAG, ex);
        }
    }

    private void play() {
        try {
            mediaPlayer.setDataSource(trailerURL);
            mediaPlayer.prepare();
            mediaPlayer.start();
            progressBar.setVisibility(View.INVISIBLE);
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    finish();
                }
            });
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnClose:
                finish();
                break;
        }
    }

}
