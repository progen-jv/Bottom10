package com.movies.bten.utils.http;

import com.movies.bten.view.data.Genres;
import com.movies.bten.view.data.MovieList;

/**
 * Created by Progen on 23/7/16.
 */

/* This singleton is wrote simply to hold data, not a good idea */
public class DataHolder {
    private static DataHolder _instance;
    private MovieList movieList;
    private Genres genres;

    public static DataHolder getInstance() {
        if (_instance == null) {
            _instance = new DataHolder();
        }
        return _instance;
    }

    public MovieList getMovieList() {
        return movieList;
    }

    public void setMovieList(MovieList movieList) {
        this.movieList = movieList;
    }

    public Genres getGenres() {
        return genres;
    }

    public void setGenres(Genres genres) {
        this.genres = genres;
    }
}
