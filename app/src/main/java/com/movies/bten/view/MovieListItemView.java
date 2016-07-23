package com.movies.bten.view;

import android.text.Html;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.movies.bten.R;
import com.movies.bten.app.AppMain;
import com.movies.bten.commons.Constants;
import com.movies.bten.commons.ui.list.ListItemView;
import com.movies.bten.commons.ui.list.ListItemViewDelegate;
import com.movies.bten.utils.http.VolleyUtils;
import com.movies.bten.view.data.Result;

/**
 * Created by Progen on 22/7/16.
 */
public class MovieListItemView<T> extends ListItemView<T> {
    private NetworkImageView imgPoster;
    private TextView txtTitle;
    private RatingBar ratingBar;
    private TextView txtNoOfVotes;
    private TextView txtLanguage;
    private TextView txtReleaseDate;
    private ImageLoader mImageLoader;


    public MovieListItemView(View convertView, T object, ListItemViewDelegate delegate, int position) {
        super(convertView, object, delegate, position);
        mImageLoader = VolleyUtils.getInstance(AppMain.getInstance().getApplicationContext()).getImageLoader();
    }

    @Override
    public void initUIComponents() {
        imgPoster = findViewById(R.id.img_poster);
        txtTitle = findViewById(R.id.txtTitle);
        ratingBar = findViewById(R.id.txtRating);
        txtNoOfVotes = findViewById(R.id.txtNoOfVotes);
        txtLanguage = findViewById(R.id.txtLanguage);
        txtReleaseDate = findViewById(R.id.txtReleaseDate);
    }

    @Override
    public void selectListItemViewUI() {

    }

    @Override
    public void selectListItemView() {
        MovieListItemViewDelegate movieListItemViewDelegate = getDelegate();
        Result movie = getObject();
        movieListItemViewDelegate.notifyMovieSelection(movie);
    }

    @Override
    public void deselectListItemViewUI() {

    }

    @Override
    protected void updateListItemView(T object, int position) {
        super.updateListItemView(object, position);
        Result movie = getObject();
        txtTitle.setText(movie.getTitle());
        imgPoster.setErrorImageResId(R.mipmap.missing_media);
        imgPoster.setImageUrl(Constants.IMAGE_BASE + movie.getPosterPath(), mImageLoader);
        ratingBar.setRating(movie.getVoteAverage());
        txtNoOfVotes.setText(movie.getVoteCount() + " votes");
        // TODO: Not a good thing, need to move the static text to UI xml file
        txtLanguage.setText(Html.fromHtml("<b>Language:</b> " + Constants.getLangName(movie.getOriginalLanguage())));
        txtReleaseDate.setText(Html.fromHtml("<b>Released on:</b> " + movie.getReleaseDate()));
    }

}
