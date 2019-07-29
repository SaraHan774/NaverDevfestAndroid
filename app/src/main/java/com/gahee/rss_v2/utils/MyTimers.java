package com.gahee.rss_v2.utils;

import android.content.Context;
import android.util.Log;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.viewpager.widget.ViewPager;

import com.gahee.rss_v2.MainActivity;

import java.util.ArrayList;
import java.util.TimerTask;

public class MyTimers {

    private static final String TAG = "MyTimers";
    private ArrayList arrayList = new ArrayList();


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
                                if(viewPager.getCurrentItem() < arrayList.size() - 1){
                                    Log.d(TAG, "run: get current item " + viewPager.getCurrentItem());
                                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                                    progressBarUtil.resetProgressBarToUserSelection(viewPager.getCurrentItem() + 1);
                                    Log.d(TAG, "run: set current item" + viewPager.getCurrentItem());
                                }else{
                                    viewPager.setCurrentItem(0);
                                    progressBarUtil.resetProgressBarToUserSelection(0);
                                }
                            }

                    }
            );
        }

    }

    public SliderTimer getSliderTimer(Context context, ViewPager viewPager, ProgressBarUtil progressBarUtil){
        return new SliderTimer(context, viewPager, progressBarUtil);
    }

    public void setArticleData(ArrayList arrayList){
        this.arrayList = arrayList;
    }


}
