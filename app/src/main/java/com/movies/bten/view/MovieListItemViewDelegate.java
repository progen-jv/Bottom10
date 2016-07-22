package com.movies.bten.view;

import com.movies.bten.commons.ui.list.ListItemViewDelegate;
import com.movies.bten.view.data.Result;

/**
 * Created by Progen on 22/7/16.
 */
public interface MovieListItemViewDelegate extends ListItemViewDelegate {
    public void notifyMovieSelection(Result item);
}
