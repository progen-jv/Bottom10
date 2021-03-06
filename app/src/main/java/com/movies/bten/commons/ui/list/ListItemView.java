package com.movies.bten.commons.ui.list;

import android.view.View;

/**
 * Created with IntelliJ IDEA.
 * User: Progen
 * Date: 20/7/13
 * Time: 1:08 PM
 * To change this template use File | Settings | File Templates.
 */

public abstract class ListItemView<T> {
    protected View convertView;
    protected int position;
    protected T object;
    protected ListItemViewDelegate delegate;

    public ListItemView(View convertView, T object, ListItemViewDelegate delegate, int position) {
        this.convertView = convertView;
        this.object = object;
        this.delegate = delegate;
        this.position = position;
    }


    public abstract void initUIComponents();


    public abstract void selectListItemViewUI();


    public abstract void selectListItemView();


    public abstract void deselectListItemViewUI();


    protected void updateListItemView(T object, int position) {
        this.object = object;
        this.position = position;
    }


    @SuppressWarnings("unchecked")
    protected <A> A getObject() {
        return (A) object;
    }


    @SuppressWarnings("unchecked")
    protected <A> A getDelegate() {
        return (A) delegate;
    }


    @SuppressWarnings("unchecked")
    protected <A> A findViewById(int id) {
        return (A) convertView.findViewById(id);
    }
}