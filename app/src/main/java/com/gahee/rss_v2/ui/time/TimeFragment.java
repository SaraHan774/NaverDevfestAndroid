package com.gahee.rss_v2.ui.time;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.gahee.rss_v2.R;
import com.gahee.rss_v2.data.time.model.TimeArticle;
import com.gahee.rss_v2.remoteSource.RemoteViewModel;
import com.gahee.rss_v2.ui.TimeArticleViewModel;

import java.util.ArrayList;

public class TimeFragment extends Fragment {

    private RemoteViewModel remoteViewModel;
    private ViewPager viewPagerTime;
    private ViewPager viewPagerTimeMedias;
    private TimeArticleViewModel timeArticleViewModel;


    public TimeFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        remoteViewModel = ViewModelProviders.of(this).get(RemoteViewModel.class);
        timeArticleViewModel = ViewModelProviders.of(this).get(TimeArticleViewModel.class);

    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_time, container, false);

        remoteViewModel.getTimeArticleLiveData().observe(this, new Observer<ArrayList<TimeArticle>>() {
            @Override
            public void onChanged(ArrayList<TimeArticle> timeArticles) {

                //view pager that displays text
                viewPagerTime = view.findViewById(R.id.view_pager_time_outer);
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

            }
        });

        timeArticleViewModel.getSeletedArticle().observe(this, new Observer<TimeArticle>() {
            @Override
            public void onChanged(TimeArticle timeArticle) {
                //view pager that displays media
                Log.d("TimeArticleViewModel", "get selected article - view model " + timeArticle);
                viewPagerTimeMedias = view.findViewById(R.id.view_pager_time_inner);
                TimeInnerPagerAdapter innerPagerAdapter = new TimeInnerPagerAdapter(getContext(), timeArticle);
                viewPagerTimeMedias.setAdapter(innerPagerAdapter);
                viewPagerTimeMedias.addOnPageChangeListener(onPageChangeListenerTimeMedia);
            }
        });

        return view;
    }


    private ViewPager.OnPageChangeListener onPageChangeListenerTimeMedia = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
