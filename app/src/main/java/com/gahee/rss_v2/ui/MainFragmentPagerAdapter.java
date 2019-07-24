package com.gahee.rss_v2.ui;

import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.gahee.rss_v2.ui.fragments.NasaFragment;
import com.gahee.rss_v2.ui.fragments.TimeFragment;
import com.gahee.rss_v2.ui.fragments.WWFFragment;
import com.gahee.rss_v2.ui.fragments.YoutubeFragment;

public class MainFragmentPagerAdapter extends FragmentPagerAdapter {
    private static final String TAG = "FragmentPagerAdapter";

    private final Fragment [] fragments = {
            new TimeFragment(), new NasaFragment(),
            new YoutubeFragment(), new WWFFragment()
    };

    public MainFragmentPagerAdapter(FragmentManager fragmentManager){
        super(fragmentManager);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Log.d(TAG, "instantiate item :  " + position);
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        fragments[position] = fragment;
        return fragment;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 :
                Log.d(TAG, "get item position : " + position);
                return fragments[position];
            case 1 :
                Log.d(TAG, "get item position : " + position);
                return fragments[position];
            case 2 :
                Log.d(TAG, "get item position : " + position);
                return fragments[position];
            case 3 :
                Log.d(TAG, "get item position : " + position);
                return fragments[position];
            default:
                return fragments[0];
        }
    }

    @Override
    public int getCount() {
        return fragments.length;
    }


}
