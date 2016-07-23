package com.movies.bten.screen;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.movies.bten.R;
import com.movies.bten.app.AppMain;
import com.movies.bten.commons.Constants;
import com.movies.bten.commons.util.AppLog;
import com.movies.bten.commons.util.MessageUtil;
import com.movies.bten.commons.util.ResourcesUtil;
import com.movies.bten.utils.http.VolleyUtils;
import com.movies.bten.view.data.Cast;
import com.movies.bten.view.data.Casts;
import com.movies.bten.view.data.Genre;
import com.movies.bten.view.data.Movie;

public class MovieDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = MovieDetailsActivity.class.getName();
    public static final String MOVIE_ID = "MOVIE_ID";
    private int movieId;
    private ImageLoader mImageLoader;
    private ProgressDialog progressDialog;

    private TextView txtTitle;
    private TextView txtSynopsis;
    private TextView txtVotes;
    private TextView txtRelease;
    private TextView txtRuntime;
    private RatingBar ratingBar;
    private NetworkImageView imgPoster;
    private TextView txtCasts;
    private TextView txtGenres;
    private ImageView imgTrailer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            movieId = bundle.getInt(MOVIE_ID);
        }
        mImageLoader = VolleyUtils.getInstance(AppMain.getInstance().getApplicationContext()).getImageLoader();

        Toolbar appToolbar = (Toolbar) findViewById(R.id.toolBarMain);
        appToolbar.setTitle("");
        appToolbar.setLogo(R.mipmap.ic_launcher);
        setSupportActionBar(appToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        imgPoster = (NetworkImageView) findViewById(R.id.imgPoster);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtSynopsis = (TextView) findViewById(R.id.txtSynopsis);
        txtVotes = (TextView) findViewById(R.id.txtNoOfVotes);
        txtRelease = (TextView) findViewById(R.id.txtReleaseDate);
        txtRuntime = (TextView) findViewById(R.id.txtRuntime);
        ratingBar = (RatingBar) findViewById(R.id.ratingMovie);
        txtCasts = (TextView) findViewById(R.id.txtCasts);
        txtGenres = (TextView) findViewById(R.id.txtGenres);
        imgTrailer = (ImageView) findViewById(R.id.imgTrailer);

        imgTrailer.setOnClickListener(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(ResourcesUtil.getString(R.string.processing));

        this.fetchMovieDetails();
        this.getCastAndCrew();

    }

    private void fetchMovieDetails() {
        try {
            progressDialog.show();
            String url = String.format(Constants.DETAILS_URL, movieId);
            AppLog.debug("Details URL", url);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    Gson gson = new Gson();
                    Movie movie = gson.fromJson(response, Movie.class);
                    populateUIElements(movie);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    MessageUtil.showMessage(ResourcesUtil.getString(R.string.error), true);
                    finish();
                }
            });
            VolleyUtils.getInstance(this).addToRequestQueue(stringRequest);
        } catch (Exception ex) {
            AppLog.error(TAG, ex);
        }
    }


    private void populateUIElements(Movie movie) {
        imgPoster.setImageUrl(Constants.IMAGE_BASE + movie.getPosterPath(), mImageLoader);
        imgPoster.setErrorImageResId(R.mipmap.missing_media);
        txtTitle.setText(movie.getTitle());
        txtSynopsis.setText(movie.getOverview());
        ratingBar.setRating(movie.getVoteAverage());
        txtVotes.setText(movie.getVoteCount() + " votes");
        txtRelease.setText(Html.fromHtml("<b>Released on:</b> " + movie.getReleaseDate()));
        txtRuntime.setText(movie.getRuntime() + " mins");
        txtGenres.setText(Html.fromHtml(getGenres(movie)));
    }

    private void getCastAndCrew() {
        try {
            String url = String.format(Constants.CREDITS_URL, movieId);
            AppLog.debug("Credits URL", url);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Gson gson = new Gson();
                    Casts casts = gson.fromJson(response, Casts.class);
                    txtCasts.setText(Html.fromHtml(getTopCasts(casts, 15)));
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    MessageUtil.showMessage(ResourcesUtil.getString(R.string.error), true);
                    txtCasts.setText("Unknown");
                }
            });
            VolleyUtils.getInstance(this).addToRequestQueue(stringRequest);
        } catch (Exception ex) {
            AppLog.error(TAG, ex);
        }
    }

    private String getTopCasts(Casts casts, int howMany) {
        StringBuilder strCast = new StringBuilder("<b>Casts:</b>");
        try {
            int count = 0;
            for (Cast cast : casts.getCast()) {
                if (count < howMany) {
                    strCast.append(" ").append(cast.getName()).append(",");
                }
            }
            strCast.trimToSize();
            strCast.deleteCharAt(strCast.length() - 1);
            return strCast.toString();
        } catch (Exception ex) {
            AppLog.error(TAG, ex);
            return "Unknown";
        }
    }

    private String getGenres(Movie movie) {
        StringBuilder strGenre = new StringBuilder("<b>Genre:</b> ");
        try {
            for (Genre genre : movie.getGenres()) {
                strGenre.append(" ").append(genre.getName()).append(",");
            }
            strGenre.trimToSize();
            strGenre.deleteCharAt(strGenre.length() - 1);
            return strGenre.toString();
        } catch (Exception ex) {
            AppLog.error(TAG, ex);
            return "NA";
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgTrailer:
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(MovieDetailsActivity.this, MovieTrailerActivity.class);
                        intent.putExtra(MovieTrailerActivity.TRAILER_URL, "http://www.mp4point.com/downloads/8feeca1a540b.mp4");
                        startActivity(intent);
                    }
                }, 100);
                break;
        }
    }
}
