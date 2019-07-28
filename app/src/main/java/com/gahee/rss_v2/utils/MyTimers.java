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
    private boolean isProgressBarReset = false;


    public class SliderTimer extends TimerTask {
        private Context context;
        private ViewPager viewPager;
        private ProgressBar[] progressBars;
        private int index = 0;
        private MutableLiveData<Integer> sliderIndexMutableLiveData = new MutableLiveData<>();
        private SliderIndexViewModel sliderIndexViewModel;

        public SliderTimer(Context context, ViewPager viewPager, ProgressBar [] progressBars){
            this.context = context;
            this.viewPager = viewPager;
            this.progressBars = progressBars;
            sliderIndexViewModel = new SliderIndexViewModel();
        }


        @Override
        public void run() {
            ((AppCompatActivity)context).runOnUiThread(
                    new Runnable() {
                        @Override
                        public void run() {

                            if(isProgressBarReset){
                                for(int i = 0; i < index; i++){
                                    progressBars[i].setProgress(60);
                                }
                                for(int i = index; i < progressBars.length; i++){
                                    progressBars[i].setProgress(0);
                                }
                                isProgressBarReset = false;
                            }else{
                                if(viewPager.getCurrentItem() < arrayList.size() - 1){
                                    if (index < progressBars.length) {
                                        Log.d(TAG, "run: " + index);
                                        progressBars[index++].setProgress(60);
                                    } else {
                                        index = 0;
                                        for(int i =0; i < progressBars.length; i++){
                                            progressBars[i].setProgress(0);
                                        }
                                        progressBars[index++].setProgress(60);
                                    }
                                    Log.d(TAG, "progress bar index : " + index);
                                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                                }else{
                                    viewPager.setCurrentItem(0);
                                }
                            }


                        }
                    }
            );
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            Log.d(TAG, "setIndex: " + index);
            this.index = index;
        }
    }

    public SliderTimer getSliderTimer(Context context, ViewPager viewPager, ProgressBar [] progressBars){
        return new SliderTimer(context, viewPager, progressBars);
    }

    public void setArticleData(ArrayList arrayList){
        this.arrayList = arrayList;
    }

    public void resetProgressBarPosition(SliderTimer sliderTimer, int viewPagerPosition){
        Log.d(TAG, "resetProgressBarPosition: " + (viewPagerPosition%6));
        sliderTimer.setIndex(( viewPagerPosition % 6 ));
        isProgressBarReset = true;
    }

}
