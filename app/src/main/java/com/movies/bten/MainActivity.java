package com.movies.bten;

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
import com.movies.bten.commons.Constants;
import com.movies.bten.commons.ui.list.ListViewAdapter;
import com.movies.bten.utils.http.VolleyUtils;
import com.movies.bten.view.MovieListItemView;
import com.movies.bten.view.MovieListItemViewDelegate;
import com.movies.bten.view.data.MovieList;
import com.movies.bten.view.data.Result;

public class MainActivity extends AppCompatActivity {
    private ListView moviesList;
    private ListViewAdapter<Result, MovieListItemView<Result>> listViewAdapter;
    private MovieList movieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        moviesList = (ListView) findViewById(R.id.list_movies);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolBarMain);
        setSupportActionBar(myToolbar);

        this.loadMovies();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem yearItem = menu.findItem(R.id.spinner_year);
        Spinner yearSpinner = (Spinner) MenuItemCompat.getActionView(yearItem);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.years_array, android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(adapter);
        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        MenuItem genreItem = menu.findItem(R.id.spinner_genre);
        Spinner genreSpinner = (Spinner) MenuItemCompat.getActionView(genreItem);
        ArrayAdapter<CharSequence> genreAdapter = ArrayAdapter.createFromResource(this, R.array.genre_arry, android.R.layout.simple_spinner_dropdown_item);
        genreSpinner.setAdapter(genreAdapter);
        genreSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

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
        listViewAdapter = new ListViewAdapter<Result, MovieListItemView<Result>>(this, aClass, movieListItemViewDelegate,
                R.layout.movie_list_item, movieList.getResults(),
                android.R.color.transparent, android.R.color.transparent);
        moviesList.setAdapter(listViewAdapter);
    }

    private void loadMovies() {
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.BASE_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Gson gson = new Gson();
                    movieList = gson.fromJson(response, MovieList.class);
                    System.out.println("Movies>>>>" + movieList.getResults().size());
                    loadMovieList();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

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
