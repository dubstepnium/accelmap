package com.herokuapp.accelomap.opengltest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

public class DataLoader extends AsyncTaskLoader<Integer> {
    private int id;
    private String urlId;
    private String urlSend;

    public DataLoader(@NonNull Context context, String urlId, String urlSend, int id) {
        super(context);
        this.id = id;
        this.urlId = urlId;
        this.urlSend = urlSend;
    }

    @Nullable
    @Override
    public Integer loadInBackground() {
        HttpHandler httpHandler = new HttpHandler();

        if(id != -1) {
            httpHandler.postAccelData(urlSend);

        } else
            id = httpHandler.getId(urlId);

        return id;
    }
}
