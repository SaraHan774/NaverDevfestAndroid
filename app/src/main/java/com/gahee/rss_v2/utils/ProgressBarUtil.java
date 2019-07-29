package com.gahee.rss_v2.utils;

import android.os.Handler;
import android.util.Log;
import android.widget.ProgressBar;

public class ProgressBarUtil{

    private static final String TAG = "ProgressBarUtil";

    private int progress = 0;
    private Handler handler = new Handler();
    private ProgressBar [] progressBars;
    //프로그레스 바 6개가 있다 -> 6 개의 어레이를 넘겨준다.
    //어댑터에서 프로그레스바의 상태를 업데이트 한다
    //어댑터의 포지션을 넘겨준다 -> 6으로 나눈 나머지를 프로그래스바의 인덱스로 정한다
    //프로그래스 바의 인덱스가 들어가면 0 ~ 5 사이의 숫자를 넘겨 받았을 때
    // x 이하의 프로그래스바는 set progress 를 하고 x 초과 인덱스에 대해서는 set 0 를 한다

    private void mSetProgressBarProgress(ProgressBar progressBar){
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
