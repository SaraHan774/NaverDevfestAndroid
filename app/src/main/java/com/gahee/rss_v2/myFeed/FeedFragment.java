package com.gahee.rss_v2.myFeed;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gahee.rss_v2.R;
import com.gahee.rss_v2.databinding.FragmentFeedBinding;
import com.gahee.rss_v2.remoteDataSource.RemoteViewModel;
import com.gahee.rss_v2.retrofitNasa.model.ChannelObj;

import java.util.ArrayList;

public class FeedFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;
    public FeedFragment() {
        // Required empty public constructor
    }

    public static FeedFragment newInstance(String param1, String param2) {
        FeedFragment fragment = new FeedFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final FragmentFeedBinding fragmentFeedBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),
                R.layout.fragment_feed, container, false);

        RemoteViewModel remoteViewModel = ViewModelProviders.of(this).get(RemoteViewModel.class);

        remoteViewModel.getChannelMutableLiveData().observe(this, new Observer<ArrayList<ChannelObj>>() {
            @Override
            public void onChanged(ArrayList<ChannelObj> channelObjs) {
                FeedRvAdapter feedRvAdapter = new FeedRvAdapter(getContext(), channelObjs);
                RecyclerView recyclerView = fragmentFeedBinding.rvFeedFragment;
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(feedRvAdapter);
            }
        });

        return fragmentFeedBinding.getRoot();
    }



}
