package com.gahee.rss_v2.remoteSource.imageLabel;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class ImgAsyncTaskLoader extends AsyncTaskLoader <String>{

    private static final String TAG = "ImgAsyncTaskLoader";

    private Context context;
    private String serverUrl;
    private String jsonPostString;
    private String cachedData;


    public ImgAsyncTaskLoader(@NonNull Context context, String serverUrl, String jsonPostString) {
        super(context);
        this.context = context;
        this.serverUrl = serverUrl;
        this.jsonPostString = jsonPostString;
    }

    @Nullable
    @Override
    public String loadInBackground() {
        String jsonResultStrings = ImageLabeling.sendREST(serverUrl, jsonPostString);
        return jsonResultStrings;
    }


    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if(cachedData != null){
            Log.d(TAG, "using cached data");

            deliverResult(cachedData);
        }else{
            Log.d(TAG, "no cached data, forcing load");
            forceLoad();
        }
    }


    @Override
    public void deliverResult(@Nullable String data) {
        super.deliverResult(data);
        //save data for later retrieval
        Log.d(TAG, "method deliverResult running");
        cachedData = data;
    }


}
