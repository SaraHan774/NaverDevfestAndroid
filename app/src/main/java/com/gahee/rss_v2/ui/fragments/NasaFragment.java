package com.gahee.rss_v2.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gahee.rss_v2.R;
import com.gahee.rss_v2.data.nasa.model.ArticleObj;
import com.gahee.rss_v2.data.nasa.model.ChannelObj;
import com.gahee.rss_v2.data.nasa.tags.Item;
import com.gahee.rss_v2.remoteSource.RemoteViewModel;
import com.gahee.rss_v2.ui.NasaVideoViewModel;
import com.gahee.rss_v2.ui.pagerAdapters.NasaInnerPagerAdapter;
import com.google.android.exoplayer2.ui.PlayerView;

import java.util.ArrayList;


public class NasaFragment extends Fragment {

    private NasaInnerFragment nasaInnerFragment;
    private NasaVideoViewModel nasaVideoViewModel;

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
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_nasa_host, container, false);
        final PlayerView playerView = view.findViewById(R.id.video_view);

        nasaVideoViewModel = ViewModelProviders.of(getActivity()).get(NasaVideoViewModel.class);
        nasaVideoViewModel.getSelectedVideo().observe(this, new Observer<Item>() {
            @Override
            public void onChanged(Item item) {
                //set player to this item's video url

            }
        });
        nasaInnerFragment = NasaInnerFragment.newInstance();
        fragmentTransactionHelper(nasaInnerFragment);

        return view;
    }

    private void fragmentTransactionHelper(final Fragment fragment)   {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.nasa_place_holder_below, fragment);
        transaction.commit();
    }


}
