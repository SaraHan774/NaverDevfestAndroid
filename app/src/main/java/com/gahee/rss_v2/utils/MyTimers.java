package com.gahee.rss_v2.utils;

import android.content.Context;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.TimerTask;

public class MyTimers {

    private static final String TAG = "MyTimers";
    private int listSize;

    public MyTimers(int listSize){
        this.listSize = listSize;
        Log.d(TAG, "MyTimers: " + listSize);
    }


    public class SliderTimer extends TimerTask {
        private Context context;
        private ViewPager viewPager;
        private ProgressBarUtil progressBarUtil;

        public SliderTimer(Context context, ViewPager viewPager, ProgressBarUtil progressBarUtil){
            this.context = context;
            this.viewPager = viewPager;
            this.progressBarUtil = progressBarUtil;
        }


        @Override
        public void run() {
            ((AppCompatActivity)context).runOnUiThread(
                    new Runnable() {
                        @Override
                        public void run() {
                                int currentPage = viewPager.getCurrentItem();
                                Log.d(TAG, "run: current page !!! " + currentPage);
                                if(currentPage < listSize - 1){
                                    Log.d(TAG, "run: " + listSize);
                                    viewPager.setCurrentItem(currentPage +1, true);
                                    Log.d(TAG, "run: current page + 1 " + currentPage + 1);
                                    if(progressBarUtil != null){
                                        progressBarUtil.resetProgressBarToUserSelection(viewPager.getCurrentItem() + 1);
                                    }
                                    Log.d(TAG, "run: set current item" + viewPager.getCurrentItem());
                                }else{
                                    viewPager.setCurrentItem(0);
                                    if(progressBarUtil !=null){
                                        progressBarUtil.resetProgressBarToUserSelection(0);
                                    }
                                }
                            }

                    }
            );
        }

    }

    public SliderTimer getSliderTimer(Context context, ViewPager viewPager, ProgressBarUtil progressBarUtil){
        return new SliderTimer(context, viewPager, progressBarUtil);
    }



}
