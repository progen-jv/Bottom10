package com.movies.bten.screen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import com.movies.bten.view.data.Movie;

public class MovieDetailsActivity extends AppCompatActivity {
    private static final String TAG = MovieDetailsActivity.class.getName();
    public static final String MOVIE_ID = "MOVIE_ID";
    private int movieId;
    private ImageLoader mImageLoader;

    private TextView txtTitle;
    private TextView txtSynopsis;
    private TextView txtVotes;
    private TextView txtRelease;
    private TextView txtRuntime;
    private RatingBar ratingBar;
    private NetworkImageView imgPoster;
    private TextView txtCasts;


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

        this.fetchMovieDetails();
        this.getCastAndCrew();

    }

    private void fetchMovieDetails() {
        try {
            String url = String.format(Constants.DETAILS_URL, movieId);
            AppLog.debug("Details URL", url);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
//                    progressDialog.dismiss();
                    Gson gson = new Gson();
                    Movie movie = gson.fromJson(response, Movie.class);
                    populateUIElements(movie);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
//                    progressDialog.dismiss();
                    MessageUtil.showMessage(ResourcesUtil.getString(R.string.error), true);
                }
            });
            VolleyUtils.getInstance(this).addToRequestQueue(stringRequest);
        } catch (Exception ex) {
            AppLog.error(TAG, ex);
        }
    }


    private void populateUIElements(Movie movie) {
        imgPoster.setImageUrl(Constants.IMAGE_BASE + movie.getPosterPath(), mImageLoader);
        imgPoster.setErrorImageResId(R.drawable.list_item_selected);
        txtTitle.setText(movie.getTitle());
        txtSynopsis.setText(movie.getOverview());
        ratingBar.setRating(movie.getVoteAverage());
        txtVotes.setText(movie.getVoteCount() + " votes");
        txtRelease.setText("Released on: " + movie.getReleaseDate());
        txtRuntime.setText(movie.getRuntime() + " mins");
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
                    txtCasts.setText(getTopCasts(casts, 15));
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
        StringBuilder strCast = new StringBuilder("Casts: ");
        try {
            int count = 0;
            for (Cast cast : casts.getCast()) {
                if (count < howMany) {
                    strCast.append(cast.getName()).append(", ");
                }
            }
            return strCast.substring(0, strCast.length() - 2);
        } catch (Exception ex) {
            AppLog.error(TAG, ex);
            return "Unknown";
        }
    }
}
