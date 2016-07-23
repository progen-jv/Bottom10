package com.movies.bten.utils.http;

import com.movies.bten.view.data.Genres;
import com.movies.bten.view.data.MovieList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Progen on 23/7/16.
 */

/* This singleton is wrote simply to hold data, not a good idea */
public class DataHolder {
    private static DataHolder _instance;
    private MovieList movieList = new MovieList();
    private Genres genres;
    private List<Integer> yearList = new ArrayList<>();

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

    public List<Integer> getYearList() {
        return yearList;
    }

    public void setYearList(List<Integer> yearList) {
        this.yearList = yearList;
    }

    /* TODO: May be should take current year from date */
    public void populateYears() {
        for (int i = 2016; i >= 1970; i--) {
            yearList.add(i);
        }
    }
}
