package com.gahee.rss_v2.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gahee.rss_v2.R;

public class HostFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;

    private String mParam1;
    private String mParam2;
    public HostFragment() {
        // Required empty public constructor
    }

    public static HostFragment newInstance(String param1, String param2) {
        HostFragment fragment = new HostFragment();
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
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_feed, container, false);
        viewPager = view.findViewById(R.id.view_pager_dashboard);
        pagerAdapter = new MainFragmentPagerAdapter(
                getActivity().getSupportFragmentManager()
        );
        viewPager.setAdapter(pagerAdapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        viewPager.setCurrentItem(pagerAdapter.getItemPosition(PagerAdapter.POSITION_NONE));
    }
}
