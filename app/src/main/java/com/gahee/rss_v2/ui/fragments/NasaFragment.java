package com.gahee.rss_v2.ui.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gahee.rss_v2.R;
import com.gahee.rss_v2.data.nasa.model.ChannelObj;
import com.gahee.rss_v2.remoteSource.RemoteViewModel;
import com.gahee.rss_v2.ui.pagerAdapters.NasaPagerAdapter;
import com.google.android.exoplayer2.ui.PlayerView;

import java.util.ArrayList;


public class NasaFragment extends Fragment {

    public NasaFragment() {
        // Required empty public constructor
    }

    public static NasaFragment newInstance() {
        NasaFragment fragment = new NasaFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_nasa, container, false);
        PlayerView playerView = view.findViewById(R.id.video_view);
        final ViewPager viewPager = view.findViewById(R.id.view_pager_nasa);

        viewPager.addOnPageChangeListener(listener);

        RemoteViewModel remoteViewModel = ViewModelProviders.of(this).get(RemoteViewModel.class);
        remoteViewModel.getChannelMutableLiveData().observe(this, new Observer<ArrayList<ChannelObj>>() {
            @Override
            public void onChanged(ArrayList<ChannelObj> channelObjs){
                PagerAdapter pagerAdapter = new NasaPagerAdapter(getContext(), channelObjs.get(0));
                viewPager.setAdapter(pagerAdapter);
            }
        });

        return view;
    }

    ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {
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
