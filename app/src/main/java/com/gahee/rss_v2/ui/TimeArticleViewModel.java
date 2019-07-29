package com.gahee.rss_v2.ui;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gahee.rss_v2.data.time.model.TimeArticle;


public class TimeArticleViewModel extends ViewModel{
    private static final String TAG = "TimeArticleViewModel";
    private static TimeArticleViewModel instance;

    private final MutableLiveData<TimeArticle> selectedTimeArticle = new MutableLiveData<>();

    public static TimeArticleViewModel getInstance() {
        if(instance == null){
            instance = new TimeArticleViewModel();
        }
        return instance;
    }

    public void setSelectedArticle(TimeArticle timeArticle){
        selectedTimeArticle.setValue(timeArticle);
        Log.d(TAG, "set selected video in view model  " + timeArticle);
    }

    public MutableLiveData<TimeArticle> getSeletedArticle(){
        Log.d(TAG, "returning selected video from view model");
        return selectedTimeArticle;
    }


}
