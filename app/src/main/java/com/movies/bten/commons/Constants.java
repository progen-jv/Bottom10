package com.movies.bten.commons;

/**
 * Created by Progen on 22/7/16.
 */
public class Constants {
    public static final String API_KEY = "3286a767ba08915afee23cefadaa4f1a";
    public static final String BASE_URL = "https://api.themoviedb.org/3/";
    public static final String IMAGE_BASE = "https://image.tmdb.org/t/p/w370/";

    public static final String DISCOVER_URL = BASE_URL + "discover/movie?primary_release_year=%d&with_genres=%d&sort_by=vote_count.asc&include_video=true&api_key=" + API_KEY;
    public static final String GENRE_URL = BASE_URL + "genre/movie/list?api_key=" + API_KEY;
    //- Search
    //https://api.themoviedb.org/3/discover/movie?primary_release_year=2014&with_genres=28&sort_by=vote_count.asc&include_video=true&api_key=3286a767ba08915afee23cefadaa4f1a
    //- Genre List
    //http://api.themoviedb.org/3/genre/movie/list?api_key=3286a767ba08915afee23cefadaa4f1a
    // - Image repo
    //https://image.tmdb.org/t/p/w370/qfl2VEQzbXOUWIlXGfUbFH0rMtZ.jpg
    // - Trailers
    //http://api.themoviedb.org/3/movie/296092/videos?api_key=3286a767ba08915afee23cefadaa4f1a
    // - Movie details
    //http://api.themoviedb.org/3/movie/253649?api_key=3286a767ba08915afee23cefadaa4f1a
    // - Credits
    //http://api.themoviedb.org/3/movie/118340/credits?api_key=3286a767ba08915afee23cefadaa4f1a
}
