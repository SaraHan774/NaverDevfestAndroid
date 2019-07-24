package com.gahee.rss_v2.ui.fragments;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.transition.Scene;
import androidx.transition.Transition;
import androidx.transition.TransitionInflater;
import androidx.transition.TransitionManager;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.gahee.rss_v2.R;
import com.gahee.rss_v2.data.nasa.model.ChannelObj;
import com.gahee.rss_v2.data.nasa.tags.Item;
import com.gahee.rss_v2.remoteSource.RemoteViewModel;
import com.gahee.rss_v2.ui.NasaVideoViewModel;
import com.gahee.rss_v2.ui.pagerAdapters.NasaInnerPagerAdapter;

import java.util.ArrayList;

public class NasaInnerFragment extends Fragment {

    private static final String TAG = "NasaInnerFragment";
    private TextView tvVideoTitle;
    private TextView tvVideoPubDate;;
    private TextView tvVideoDescription;
    private NasaVideoViewModel nasaVideoViewModel;
    private Scene scene;
    private Scene lessScene;
    private Transition transition;

    public NasaInnerFragment() {
        Log.d(TAG, "creating instance of Nasa Inner Fragment");
    }

    public static NasaInnerFragment newInstance() {
        NasaInnerFragment fragment = new NasaInnerFragment();
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private static boolean isInfoClicked = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_nasa_below, container, false);
        tvVideoTitle = view.findViewById(R.id.tv_nasa_video_title);
        tvVideoPubDate = view.findViewById(R.id.tv_nasa_video_pubDate);
        tvVideoDescription = view.findViewById(R.id.tv_nasa_video_description);
        ImageButton imageButton = view.findViewById(R.id.img_info_button_nasa_below);
//        scene = Scene.getSceneForLayout(
//                container, R.layout.fragment_nasa_below_expanded, getContext()
//        );
//        lessScene = Scene.getSceneForLayout(container, R.layout.fragment_nasa_below, getContext());
//        transition = TransitionInflater.from(getContext())
//                .inflateTransition(R.transition.to_info);
//        if(!isInfoClicked){
//            imageButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    TransitionManager.go(scene, transition);
//                    tvVideoDescription = view.findViewById(R.id.tv_nasa_video_description);
//                }
//            });
//        }else{
//            imageButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    TransitionManager.go(lessScene);
//                    tvVideoDescription = null;
//                }
//            });
//        }

//        NestedScrollView scrollView = (NestedScrollView) view.findViewById (R.id.nested_scroll_view_nasa);
//        scrollView.setFillViewport (true);

        final ViewPager viewPager = view.findViewById(R.id.view_pager_nasa_more_videos);
        viewPager.setPageMargin(16);
        viewPager.setOffscreenPageLimit(3);

        RemoteViewModel remoteViewModel = ViewModelProviders.of(this).get(RemoteViewModel.class);
        remoteViewModel.getChannelMutableLiveData().observe(this, new Observer<ArrayList<ChannelObj>>() {
            @Override
            public void onChanged(ArrayList<ChannelObj> channelObjs){
                PagerAdapter pagerAdapter = new NasaInnerPagerAdapter(getContext(),NasaInnerFragment.this ,  channelObjs.get(0));
                viewPager.setAdapter(pagerAdapter);

            }
        });

        nasaVideoViewModel = ViewModelProviders.of(this).get(NasaVideoViewModel.class);
        nasaVideoViewModel.getSelectedVideo().observe(this, new Observer<Item>() {
            @Override
            public void onChanged(Item item) {
                Log.d(TAG, "after setting selected item, item :  " + item);
                tvVideoTitle.setText(item.getTitle());
                tvVideoPubDate.setText(item.getPubDate());
                tvVideoDescription.setText(item.getDescription());

                Log.d(TAG, item.getPubDate());
            }
        });



        return view;
    }






}
