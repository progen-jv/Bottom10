package com.movies.bten.utils.http;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.movies.bten.utils.cache.LruBitmapCache;

/**
 * Created by Progen on 22/7/16.
 */
public class VolleyUtils {
    private static VolleyUtils mVolleyUtils;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private static Context mCtx;


    private VolleyUtils(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();
        mImageLoader = new ImageLoader(mRequestQueue, new LruBitmapCache(
                LruBitmapCache.getCacheSize(mCtx)));
    }

    public static synchronized VolleyUtils getInstance(Context context) {
        if (mVolleyUtils == null) {
            mVolleyUtils = new VolleyUtils(context);
        }
        return mVolleyUtils;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }
}
