package com.gahee.rss_v2.utils;

import android.os.Handler;
import android.util.Log;
import android.widget.ProgressBar;

public class ProgressBarUtil{

    private static final String TAG = "ProgressBarUtil";

    private int progress = 0;
    private final Handler handler = new Handler();
    private ProgressBar [] progressBars;

    private void mSetProgressBarProgress(ProgressBar progressBar){
        progress = progressBar.getProgress();
        Runnable runnable = () -> {
            while(progress < 60){
                progress += 1;
                handler.post(() -> progressBar.setProgress(progress));
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
    }

    private void refreshAllProgressBars(ProgressBar [] progressBar){
        for(ProgressBar p : progressBar){
            p.setProgress(0);
        }
    }

    private void mResetProgressBarToUserSelection(int adapterIndex){
        int progressBarIndex = adapterIndex % 6;

        Log.d(TAG, "mResetProgressBarToUserSelection: adapter index  " + adapterIndex + " progress bar " + progressBarIndex);

        for(int i = 0; i < progressBars.length; i++){
            if(i <= progressBarIndex){
                progressBars[i].setProgress(60);
            }else{
                progressBars[i].setProgress(0);
            }
        }

        for(ProgressBar p : progressBars){
            Log.d(TAG, "status " + p.getProgress());
        }
    }

    public void resetProgressBarToUserSelection(int viewPagerIndex){
        mResetProgressBarToUserSelection(viewPagerIndex);
    }

    public void setProgressBarsProgress(ProgressBar progressBar){
        mSetProgressBarProgress(progressBar);
    }

    public void setProgressBars(ProgressBar [] progressBars){
        Log.d(TAG, "setProgressBars: HOW MANY ? " + progressBars.length);
        this.progressBars = progressBars;
    }


}
