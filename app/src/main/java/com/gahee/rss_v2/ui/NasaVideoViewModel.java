package com.gahee.rss_v2.ui;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gahee.rss_v2.data.nasa.tags.Item;

public class NasaVideoViewModel extends ViewModel {
    private static final String TAG = "NasaVideoViewModel";

    public static NasaVideoViewModel instance = null;
    private final MutableLiveData<Item> selectedVideo = new MutableLiveData<>();

    public static NasaVideoViewModel getInstance() {
        if(instance == null){
            instance = new NasaVideoViewModel();
        }
        return instance;
    }

    public void setSelectedVideo(Item item){
        selectedVideo.setValue(item);
        Log.d(TAG, "set selected video in view model : item : " + item );
    }

    public MutableLiveData<Item> getSelectedVideo(){
        Log.d(TAG, "returning selected video from view model");
        return selectedVideo;
    }

}
