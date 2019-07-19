package com.gahee.rss_v2;

import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingComponent;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class FeedBindingComponent implements DataBindingComponent {

    @BindingAdapter(value = {"android:pagerAdapter"}, requireAll = false)
    public static void setViewPager(ViewPager viewPager, PagerAdapter pagerAdapter){
        viewPager.setAdapter(pagerAdapter);
    }

}
