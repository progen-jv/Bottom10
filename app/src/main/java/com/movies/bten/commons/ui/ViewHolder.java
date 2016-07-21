package com.movies.bten.commons.ui;

import android.view.View;


public abstract class ViewHolder {
    protected View convertView;


    public ViewHolder(View convertView) {
        this.convertView = convertView;
    }


    public abstract void initializeComponents();


    public abstract void updateComponents();


    @SuppressWarnings("unchecked")
    protected <A> A findViewById(int id) {
        return (A) convertView.findViewById(id);
    }
}
