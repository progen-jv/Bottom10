package com.movies.bten.screen;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.movies.bten.R;
import com.movies.bten.commons.Constants;
import com.movies.bten.commons.ui.adapter.GenreAdapter;
import com.movies.bten.commons.ui.list.ListViewAdapter;
import com.movies.bten.commons.util.AppLog;
import com.movies.bten.commons.util.MessageUtil;
import com.movies.bten.commons.util.ResourcesUtil;
import com.movies.bten.utils.http.DataHolder;
import com.movies.bten.utils.http.VolleyUtils;
import com.movies.bten.view.MovieListItemView;
import com.movies.bten.view.MovieListItemViewDelegate;
import com.movies.bten.view.data.MovieList;
import com.movies.bten.view.data.Result;

public class MainActivity extends AppCompatActivity {
    private ListView moviesList;
    private ListViewAdapter<Result, MovieListItemView<Result>> listViewAdapter;
    private static int searchGenre;
    private static int searchYear;
    private static int searchOrder;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        moviesList = (ListView) findViewById(R.id.list_movies);

        Toolbar appToolbar = (Toolbar) findViewById(R.id.toolBarMain);
        appToolbar.setTitle("");
        appToolbar.setLogo(R.mipmap.ic_launcher);
        setSupportActionBar(appToolbar);


        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Processing your request, please wait");
        this.loadMovieList();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem yearItem = menu.findItem(R.id.spinner_year);
        Spinner yearSpinner = (Spinner) MenuItemCompat.getActionView(yearItem);
        ArrayAdapter<Integer> yearAdapter = new ArrayAdapter<Integer>(this, R.layout.item_genre, R.id.txtGenreName, DataHolder.getInstance().getYearList());
        yearAdapter.setDropDownViewResource(R.layout.spinner_drop_down);

        yearSpinner.setAdapter(yearAdapter);
        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                searchYear = DataHolder.getInstance().getYearList().get(i);
                loadMovies();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        /* Load the genres drop down */
        MenuItem genreItem = menu.findItem(R.id.spinner_genre);
        Spinner genreSpinner = (Spinner) MenuItemCompat.getActionView(genreItem);
        GenreAdapter genreAdapter = new GenreAdapter(this, DataHolder.getInstance().getGenres().getGenres());
        genreAdapter.setDropDownViewResource(R.layout.spinner_drop_down);

        genreSpinner.setAdapter(genreAdapter);
        genreSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                searchGenre = DataHolder.getInstance().getGenres().getGenres().get(i).getId();
                loadMovies();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressWarnings("unchecked")
    private void loadMovieList() {
        Class<MovieListItemView<Result>> aClass = (Class<MovieListItemView<Result>>) (Class<?>) MovieListItemView.class;

        moviesList = (ListView) findViewById(R.id.list_movies);
        listViewAdapter = new ListViewAdapter<>(this, aClass, movieListItemViewDelegate,
                R.layout.movie_list_item, DataHolder.getInstance().getMovieList().getResults(),
                android.R.color.transparent, android.R.color.transparent);
        moviesList.setAdapter(listViewAdapter);
    }

    private void loadMovies() {
        try {
            progressDialog.show();
            String url = String.format(Constants.DISCOVER_URL, searchYear, searchGenre);
            AppLog.debug("URL", url);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    Gson gson = new Gson();
                    MovieList movies = gson.fromJson(response, MovieList.class);
                    DataHolder.getInstance().getMovieList().getResults().clear();
                    DataHolder.getInstance().getMovieList().getResults().addAll(movies.getResults());
                    listViewAdapter.notifyDataSetChanged();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    MessageUtil.showMessage(ResourcesUtil.getString(R.string.error), true);
                }
            });
            VolleyUtils.getInstance(this).addToRequestQueue(stringRequest);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private MovieListItemViewDelegate movieListItemViewDelegate = new MovieListItemViewDelegate() {
        @Override
        public void notifyMovieSelection(Result item) {

        }
    };
}
