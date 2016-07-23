package com.movies.bten.commons.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.movies.bten.R;
import com.movies.bten.view.data.Genre;

import java.util.List;

/**
 * Created by Progen on 23/7/16.
 */
public class GenreAdapter extends ArrayAdapter<Genre> {

    public GenreAdapter(Context context, List<Genre> genres) {
        super(context, R.layout.item_genre, R.id.txtGenreName, genres);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Genre genre = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_genre, parent, false);
            viewHolder.genreId = (TextView) convertView.findViewById(R.id.txtGenreId);
            viewHolder.genreName = (TextView) convertView.findViewById(R.id.txtGenreName);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.genreId.setText(String.valueOf(genre.getId()));
        viewHolder.genreName.setText(genre.getName());
        return convertView;
    }


    private static class ViewHolder {
        TextView genreId;
        TextView genreName;
    }

}
