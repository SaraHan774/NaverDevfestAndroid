package com.gahee.rss_v2.ui.time;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.gahee.rss_v2.R;
import com.gahee.rss_v2.remoteSource.RemoteViewModel;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import static com.gahee.rss_v2.utils.Constants.TIME_INNER_SLIDER_TIME_INTERVAL;

public class TimeFragment extends Fragment {

    private RemoteViewModel remoteViewModel;
    private ViewPager viewPagerTime;
    private TimeArticleViewModel timeArticleViewModel;
//    private YouTubePlayerView youTubePlayerView;
    private TextView textView;

    //animation
    private Animation timeCardUp;
    private Animation timeCardDown;
    private ViewPager viewPagerTimeMedias;

    private Timer timer;


    public TimeFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        remoteViewModel = ViewModelProviders.of(this).get(RemoteViewModel.class);
        timeArticleViewModel = ViewModelProviders.of(this).get(TimeArticleViewModel.class);

        //getting animations
        timeCardUp = AnimationUtils.loadAnimation(getContext(), R.anim.time_card_up);
        timeCardUp.setInterpolator(new DecelerateInterpolator());
        timeCardDown = AnimationUtils.loadAnimation(getContext(), R.anim.time_card_down);
        timeCardDown.setInterpolator(new AccelerateInterpolator());


    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_time, container, false);
        textView = fragmentView.findViewById(R.id.time_channel_title);

        remoteViewModel.getTimeChannelMutableLiveData().observe(this, timeChannels -> {
            textView.setText(timeChannels.get(0).getmChannelTitle());
        });

        remoteViewModel.getTimeArticleMutableLiveData().observe(this, timeArticles -> {

            //fragmentView pager that displays text
            viewPagerTime = fragmentView.findViewById(R.id.view_pager_time_outer);
            TimePagerAdapter pagerAdapter = new TimePagerAdapter(getContext(), timeArticles);
            viewPagerTime.setAdapter(pagerAdapter);
            timeArticleViewModel.setSelectedArticle(timeArticles.get(0));


            viewPagerTime.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    timeArticleViewModel.setSelectedArticle(timeArticles.get(position));
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

        });


        timeArticleViewModel.getSeletedArticle().observe(this, timeArticle -> {
            if(timer != null){
                timer.cancel();
            }

            //fragmentView pager that displays media
            Log.d("TimeArticleViewModel", "get selected article - fragmentView model " + timeArticle);
            viewPagerTimeMedias = fragmentView.findViewById(R.id.view_pager_time_inner);
            TimeInnerPagerAdapter innerPagerAdapter = new TimeInnerPagerAdapter(getContext(), timeArticle);
            viewPagerTimeMedias.setAdapter(innerPagerAdapter);
//                viewPagerTimeMedias.setPageMargin(80);
            viewPagerTimeMedias.startAnimation(timeCardUp);


            viewPagerTimeMedias.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

            if(timeArticle.getContent().size() > 1){
                timer = new Timer();
                timer.scheduleAtFixedRate(new TimeSliderTimer(timeArticle.getContent().size()), TIME_INNER_SLIDER_TIME_INTERVAL, TIME_INNER_SLIDER_TIME_INTERVAL);
            }else if(timeArticle.getContent().size() > 1 || timeArticle.getmYoutubeThumbnailLinks() != null){
                timer = new Timer();
                timer.scheduleAtFixedRate(new TimeSliderTimer(timeArticle.getContent().size() +
                        timeArticle.getmYoutubeThumbnailLinks().size()), TIME_INNER_SLIDER_TIME_INTERVAL, TIME_INNER_SLIDER_TIME_INTERVAL);
            }

        });
        return fragmentView;
    }


    private class TimeSliderTimer extends TimerTask{
        private final int listSize;

        TimeSliderTimer(int listSize){
            this.listSize = listSize;
        }
        @Override
        public void run() {
            ((AppCompatActivity) Objects.requireNonNull(getContext())).runOnUiThread(
                    () -> {

                        if(viewPagerTimeMedias.getCurrentItem() < listSize - 1){
                            viewPagerTimeMedias.setCurrentItem(viewPagerTimeMedias.getCurrentItem() +1, true);
                        }else{
                            viewPagerTimeMedias.setCurrentItem(0);
                        }
                    }
            );
        }
    }
}
