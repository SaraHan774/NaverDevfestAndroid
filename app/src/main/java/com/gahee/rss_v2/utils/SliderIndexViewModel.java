package com.gahee.rss_v2.utils;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SliderIndexViewModel extends ViewModel {
    private static final String TAG = "SliderIndexViewModel";

    private MutableLiveData<Integer> reutersSliderIndex = new MutableLiveData<>();
    private MutableLiveData<Integer> timeSliderIndex = new MutableLiveData<>();
    private MutableLiveData<Integer> wwfSliderIndex = new MutableLiveData<>();


    public void setReutersSliderIndex(int reutersSliderIndex) {
        Log.d(TAG, "setReutersSliderIndex: " + reutersSliderIndex);
        this.reutersSliderIndex.setValue(reutersSliderIndex);
    }

    public void setTimeSliderIndex(int timeSliderIndex) {
        this.timeSliderIndex.setValue(timeSliderIndex);
    }

    public void setWwfSliderIndex(int wwfSliderIndex) {
        Log.d(TAG, "setWwfSliderIndex: " + wwfSliderIndex);
        this.wwfSliderIndex.setValue(wwfSliderIndex);
    }

    public MutableLiveData<Integer> getReutersSliderIndex(){
        return reutersSliderIndex;
    }

    public MutableLiveData<Integer> getTimeSliderIndex() {
        return timeSliderIndex;
    }

    public MutableLiveData<Integer> getWwfSliderIndex() {
        return wwfSliderIndex;
    }
}
