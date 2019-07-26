package com.gahee.rss_v2.utils;

import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ProgressBarUtil{

    private int progress = 0;
    private Handler handler = new Handler();

    public void setSliderProgress(ProgressBar progressBar){
        progress = progressBar.getProgress();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while(progress < 60){
                    progress += 1;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(progress);
                        }
                    });
                }
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
    }

    public void refreshAllProgressBars(ProgressBar [] progressBar){
        for(ProgressBar p : progressBar){
            p.setProgress(0);
        }
    }


}
