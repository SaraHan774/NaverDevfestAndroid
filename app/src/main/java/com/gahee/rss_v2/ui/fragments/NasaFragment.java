package com.gahee.rss_v2.ui.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gahee.rss_v2.R;
import com.gahee.rss_v2.data.nasa.model.ChannelObj;
import com.gahee.rss_v2.data.nasa.tags.Item;
import com.gahee.rss_v2.remoteSource.RemoteViewModel;
import com.gahee.rss_v2.ui.NasaVideoViewModel;
import com.gahee.rss_v2.ui.pagerAdapters.NasaInnerPagerAdapter;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;

import static com.gahee.rss_v2.utils.Constants.CURRENT_WINDOW;
import static com.gahee.rss_v2.utils.Constants.MEDIA_URL;
import static com.gahee.rss_v2.utils.Constants.PLAYBACK_POSITION;
import static com.gahee.rss_v2.utils.Constants.PLAY_WHEN_READY;
import static com.gahee.rss_v2.utils.Constants.USER_AGENT;


public class NasaFragment extends Fragment{

    private static final String TAG = "NasaFragment";

    private NasaVideoViewModel nasaVideoViewModel;
    private String mediaURL = "";
    private PlayerView playerView;
    private SimpleExoPlayer simpleExoPlayer;
    private boolean playWhenReady = true;
    private int currentWindow = 0;
    private long playbackPosition = 0;
    private TextView tvVideoTitle;
    private TextView tvVideoPubDate;;
    private TextView tvVideoDescription;

    private RemoteViewModel remoteViewModel;
    private String initialMediaUrl = "";
    private ViewPager viewPager;


    public NasaFragment() {
        // Required empty public constructor
    }

    public static NasaFragment newInstance() {
        return new NasaFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "on Create () ");

        remoteViewModel = ViewModelProviders.of(this).get(RemoteViewModel.class);
        remoteViewModel.getChannelMutableLiveData().observe(getActivity(), new Observer<ArrayList<ChannelObj>>() {
            @Override
            public void onChanged(ArrayList<ChannelObj> channelObjs) {
                Log.d(TAG, "on changed from remote view model");
                    if(savedInstanceState == null){
                    initialMediaUrl = channelObjs.get(0).getmItemList().get(0).getEnclosure().getUrl();
                    setMediaURL(initialMediaUrl);
                    initializePlayer();
                }else{
                    releasePlayer();
                }
            }
        });

        if(savedInstanceState != null){
            mediaURL = savedInstanceState.getString(MEDIA_URL);
            playWhenReady = savedInstanceState.getBoolean(PLAY_WHEN_READY, false);
            playbackPosition = savedInstanceState.getLong(PLAYBACK_POSITION, 0);
            currentWindow = savedInstanceState.getInt(CURRENT_WINDOW, 0);

            Log.d(TAG, "playback position : " + playbackPosition);
          }
        Log.d(TAG, "on create at the bottom , playback position : " + playbackPosition );
    }

    private void setMediaURL(String mediaURL){
        this.mediaURL = mediaURL;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "on save instance state");
        outState.putString(MEDIA_URL, mediaURL);
        outState.putBoolean(PLAY_WHEN_READY, playWhenReady);
        outState.putLong(PLAYBACK_POSITION, playbackPosition);
        outState.putInt(CURRENT_WINDOW, currentWindow);

        Log.d(TAG, "on save instance state, outState :" + outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_nasa_host, container, false);
        playerView = view.findViewById(R.id.video_view);

        int orientation = this.getResources().getConfiguration().orientation;

        tvVideoTitle = view.findViewById(R.id.tv_nasa_video_title);
        tvVideoPubDate = view.findViewById(R.id.tv_nasa_video_pubDate);
        tvVideoDescription = view.findViewById(R.id.tv_nasa_video_description);

        if(orientation != Configuration.ORIENTATION_LANDSCAPE){
            viewPager = view.findViewById(R.id.view_pager_nasa_more_videos);
            viewPager.setPageMargin(16);
            viewPager.setOffscreenPageLimit(3);
        }

        remoteViewModel.getChannelMutableLiveData().observe(this, new Observer<ArrayList<ChannelObj>>() {
            @Override
            public void onChanged(ArrayList<ChannelObj> channelObjs){
                if(orientation != Configuration.ORIENTATION_LANDSCAPE){
                    Log.d(TAG, "setting item for the first time ... ");
                    PagerAdapter pagerAdapter = new NasaInnerPagerAdapter(getContext(),NasaFragment.this ,  channelObjs.get(0));
                    Log.d(TAG, "channel obj : " + channelObjs);
                    viewPager.setAdapter(pagerAdapter);

                    if(savedInstanceState == null){
                        tvVideoTitle.setText(channelObjs.get(0).getmItemList().get(0).getTitle());
                        tvVideoPubDate.setText(channelObjs.get(0).getmItemList().get(0).getPubDate());
                        tvVideoDescription.setText(channelObjs.get(0).getmItemList().get(0).getDescription());
                    }
                }
            }
        });

        nasaVideoViewModel = ViewModelProviders.of(this).get(NasaVideoViewModel.class);
        nasaVideoViewModel.getSelectedVideo().observe(this, new Observer<Item>() {
            @Override
            public void onChanged(Item item) {
                Log.d(TAG, "after setting selected item, item :  " + item);
                if(orientation != Configuration.ORIENTATION_LANDSCAPE){
                    tvVideoTitle.setText(item.getTitle());
                    tvVideoPubDate.setText(item.getPubDate());
                    tvVideoDescription.setText(item.getDescription());
                    setMediaURL(item.getEnclosure().getUrl());
                    retrieveCurrentPlayerState(false);
                    initializePlayer();
                }
                Log.d(TAG, item.getPubDate());
            }
        });

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart()");
        if(Util.SDK_INT > 23){
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        hideSystemUi();
        Log.d(TAG, "onResume()");
        if(Util.SDK_INT <= 23 || simpleExoPlayer == null) {
            initializePlayer();
        }
    }

    private boolean initializePlayer(){
        if(simpleExoPlayer != null){
            releasePlayer();
        }
        if(mediaURL.equals("")){
            Log.d(TAG, "no media url");
            Toast.makeText(getContext(), getString(R.string.no_video_warning), Toast.LENGTH_SHORT).show();
            return false;
        }else {
            Log.d(TAG, "initializing exo player");
            playerView.setVisibility(View.VISIBLE);
            simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(
                    getContext(),
                    new DefaultRenderersFactory(getContext()),
                    new DefaultTrackSelector(),
                    new DefaultLoadControl()
            );
            playerView.setPlayer(simpleExoPlayer);
            //playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);
            simpleExoPlayer.setPlayWhenReady(playWhenReady);
            simpleExoPlayer.seekTo(currentWindow, playbackPosition);

            Uri uri = Uri.parse(mediaURL);
            MediaSource mediaSource = buildMediaSource(uri);
            simpleExoPlayer.prepare(mediaSource, false, false);
            return true;
        }
    }

    private MediaSource buildMediaSource(Uri uri){
        DefaultDataSourceFactory defaultDataSourceFactory = createDataSourceFactory(getContext(), USER_AGENT, null);
        return new ProgressiveMediaSource.Factory(
                defaultDataSourceFactory)
                .createMediaSource(uri);
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause()");
        retrieveCurrentPlayerState(true);
        if(Util.SDK_INT <= 23){
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop()");
        if(Util.SDK_INT > 23 || simpleExoPlayer != null){
            releasePlayer();
        }
    }

    private void releasePlayer(){
        Log.d(TAG, "releasePlayer()");
        if(simpleExoPlayer != null){
            simpleExoPlayer.release();
            simpleExoPlayer = null;
        }
    }

    private void retrieveCurrentPlayerState(boolean savePlaybackPosition){
        if(simpleExoPlayer != null && savePlaybackPosition == true){
            playbackPosition = simpleExoPlayer.getCurrentPosition();
        }else{
            playbackPosition = 0;
         }
        currentWindow = simpleExoPlayer.getCurrentWindowIndex();
        playWhenReady = simpleExoPlayer.getPlayWhenReady();
    }

    public static DefaultDataSourceFactory createDataSourceFactory(Context context, String userAgent,
                                                                   TransferListener listener) {
        DefaultHttpDataSourceFactory httpDataSourceFactory = new DefaultHttpDataSourceFactory(
                USER_AGENT,
                null,
                DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS,
                DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS,
                true
        );

        DefaultDataSourceFactory defaultDataSourceFactory = new DefaultDataSourceFactory(
                context,
                listener,
                httpDataSourceFactory
        );
        return defaultDataSourceFactory;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "on destroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "on detach");
    }

}
