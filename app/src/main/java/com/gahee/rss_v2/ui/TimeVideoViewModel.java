package com.gahee.rss_v2.ui;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gahee.rss_v2.data.time.model.TimeArticle;


public class TimeVideoViewModel extends ViewModel{
    private static final String TAG = "TimeVideoViewModel";
    private static TimeVideoViewModel instance;

    private final MutableLiveData<TimeArticle> selectedVideo = new MutableLiveData<>();

    public static TimeVideoViewModel getInstance() {
        if(instance == null){
            instance = new TimeVideoViewModel();
        }
        return instance;
    }

    public void setSelectedVideo(TimeArticle timeArticle){
        selectedVideo.setValue(timeArticle);
        Log.d(TAG, "set selected video in view model  " + timeArticle);
    }

    public MutableLiveData<TimeArticle> getSelectedVideo(){
        Log.d(TAG, "returning selected video from view model");
        return selectedVideo;
    }


}
