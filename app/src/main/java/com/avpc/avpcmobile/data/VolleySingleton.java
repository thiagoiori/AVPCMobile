package com.avpc.avpcmobile.data;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by thiago on 30/06/17.
 */

public class VolleySingleton {

    private VolleySingleton INSTANCE;
    private RequestQueue mRequestQueue;
    private Context mContext;

    public VolleySingleton(Context context) {
        mContext = context;
        mRequestQueue = getRequestQueue();
    }

    public synchronized VolleySingleton getInstance(Context context) {
        if (INSTANCE == null)
            INSTANCE = new VolleySingleton(context);

        return INSTANCE;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }

        return mRequestQueue;
    }
}
