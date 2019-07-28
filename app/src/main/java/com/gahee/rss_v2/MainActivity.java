package com.gahee.rss_v2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gahee.rss_v2.data.reuters.model.ChannelObj;
import com.gahee.rss_v2.data.reuters.tags.Item;
import com.gahee.rss_v2.data.time.model.TimeChannel;
import com.gahee.rss_v2.data.wwf.model.WWFArticle;
import com.gahee.rss_v2.remoteSource.RemoteViewModel;
import com.gahee.rss_v2.ui.pagerAdapters.outer.ReutersPagerAdapter;
import com.gahee.rss_v2.ui.pagerAdapters.outer.TimePagerAdapter;
import com.gahee.rss_v2.ui.pagerAdapters.outer.WwfPagerAdapter;
import com.gahee.rss_v2.utils.ProgressBarUtil;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.gahee.rss_v2.utils.Constants.CURRENT_WINDOW;
import static com.gahee.rss_v2.utils.Constants.MEDIA_URL;
import static com.gahee.rss_v2.utils.Constants.NASA_SLIDER_INDEX;
import static com.gahee.rss_v2.utils.Constants.PLAYBACK_POSITION;
import static com.gahee.rss_v2.utils.Constants.PLAY_WHEN_READY;
import static com.gahee.rss_v2.utils.Constants.TAG_REUTERS_FRAME;
import static com.gahee.rss_v2.utils.Constants.TAG_WWF_FRAME;
import static com.gahee.rss_v2.utils.Constants.USER_AGENT;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ViewPager viewPagerReuters;
    private ViewPager viewPagerWWF;
    private ViewPager viewPagerTime;
    private List<Item> reutersItemList;
    private ArrayList<WWFArticle> wwfItemList;
    private ProgressBarUtil progressBarUtil = new ProgressBarUtil();
    private ProgressBar [] progressBars;
    private int index = 0;

    //simpleExoPlayer
    private SimpleExoPlayer simpleExoPlayer;
    private PlayerView playerView;
    private String mediaURL = "";
    private boolean playWhenReady = true;
    private int currentWindow = 0;
    private long playbackPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findProgressBarsById();

        RemoteViewModel remoteViewModel = ViewModelProviders.of(this).get(RemoteViewModel.class);
        if(savedInstanceState == null){
            remoteViewModel.fetchReutersDataFromRepo();
//            fragmentTransactionHelper(NasaFragment.newInstance());
        }else{
            index = savedInstanceState.getInt(NASA_SLIDER_INDEX);
            mediaURL = savedInstanceState.getString(MEDIA_URL);
            playWhenReady = savedInstanceState.getBoolean(PLAY_WHEN_READY, false);
            playbackPosition = savedInstanceState.getLong(PLAYBACK_POSITION, 0);
            currentWindow = savedInstanceState.getInt(CURRENT_WINDOW, 0);
        }
        remoteViewModel.fetchTimeDataFromRepo();
        remoteViewModel.fetchWWFDataFromRepo();


        remoteViewModel.getChannelMutableLiveData().observe(this, new Observer<ArrayList<ChannelObj>>() {
            @Override
            public void onChanged(ArrayList<ChannelObj> channelObjs) {
                reutersItemList = channelObjs.get(0).getmItemList();
                viewPagerReuters = findViewById(R.id.view_pager_reuters_outer);
                ReutersPagerAdapter pagerAdapter = new ReutersPagerAdapter(MainActivity.this,  channelObjs.get(0));
                viewPagerReuters.setAdapter(pagerAdapter);
                viewPagerReuters.addOnPageChangeListener(reutersViewPagerListener);

                FrameLayout frameLayout = (FrameLayout) viewPagerReuters.findViewWithTag(TAG_REUTERS_FRAME + 0);
                PlayerView playerView = frameLayout.findViewById(R.id.reuters_outer_video_player);
                MainActivity.this.playerView = playerView;
                setMediaURL(channelObjs.get(0).getmItemList().get(0).getGroup().getContent().getUrlVideo());
                initializePlayer();
                hideSystemUi();
            }
        });

        remoteViewModel.getWwfArticleLiveData().observe(this, new Observer<ArrayList<WWFArticle>>() {
            @Override
            public void onChanged(ArrayList<WWFArticle> wwfArticles) {
                wwfItemList = wwfArticles;
                viewPagerWWF = findViewById(R.id.view_pager_wwf_outer);
                WwfPagerAdapter pagerAdapter = new WwfPagerAdapter(MainActivity.this, wwfArticles);
                viewPagerWWF.setAdapter(pagerAdapter);
                Log.d(TAG, "setting wwf pager adapter " );
                viewPagerWWF.addOnPageChangeListener(wwfViewPagerListener);

            }
        });

//        Timer timer = new Timer();
//        timer.scheduleAtFixedRate(new SliderTimer(), 4000, 6000);

        remoteViewModel.getTimeChannelLiveData().observe(this, new Observer<ArrayList<TimeChannel>>() {
            @Override
            public void onChanged(ArrayList<TimeChannel> timeChannels) {
                viewPagerTime = findViewById(R.id.view_pager_time_outer);
                TimePagerAdapter pagerAdapter = new TimePagerAdapter(MainActivity.this, timeChannels.get(0));
                viewPagerTime.setAdapter(pagerAdapter);
                Log.d(TAG, " setting time pager adapter ... ");
                viewPagerTime.addOnPageChangeListener(timeViewPagerListener);
            }
        });

//        new CloudAsync().execute();
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
        Log.d(TAG, "onResume()");
        if(Util.SDK_INT <= 23 || simpleExoPlayer == null) {
            initializePlayer();
        }
    }

    private MediaSource buildMediaSource(Uri uri){
        DefaultDataSourceFactory defaultDataSourceFactory = createDataSourceFactory(this, USER_AGENT, null);
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



    private ViewPager.OnPageChangeListener wwfViewPagerListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            Animation fadeIn = AnimationUtils.loadAnimation(MainActivity.this, R.anim.description_fade_in);

            FrameLayout frameLayout = (FrameLayout) viewPagerWWF.findViewWithTag(TAG_WWF_FRAME + position);
            TextView articleTitle = frameLayout.findViewById(R.id.tv_wwf_outer_description);
            articleTitle.startAnimation(fadeIn);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private ViewPager.OnPageChangeListener reutersViewPagerListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            releasePlayer();
            setMediaURL(reutersItemList.get(position).getGroup().getContent().getUrlVideo());
            playbackPosition = 0;

            FrameLayout frameLayout = (FrameLayout) viewPagerReuters.findViewWithTag(TAG_REUTERS_FRAME + position);
            PlayerView playerView = frameLayout.findViewById(R.id.reuters_outer_video_player);
            //play videos
            MainActivity.this.playerView = playerView;
            initializePlayer();

            if(simpleExoPlayer != null){
                Animation fadeOut = AnimationUtils.loadAnimation(MainActivity.this, R.anim.description_fade_out);
                TextView description = frameLayout.findViewById(R.id.tv_reuters_outer_description);

                TextView title = frameLayout.findViewById(R.id.tv_reuters_outer_title);
                Animation slideToRight = AnimationUtils.loadAnimation(MainActivity.this, R.anim.title_slide_to_left);

                if(description.getVisibility() == View.GONE || title.getVisibility() == View.GONE){
                    description.setVisibility(View.VISIBLE);
                    title.setVisibility(View.VISIBLE);
                }

                description.startAnimation(fadeOut);
                fadeOut.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }
                    @Override
                    public void onAnimationEnd(Animation animation) {
                            description.setVisibility(View.GONE);
                            title.startAnimation(slideToRight);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                slideToRight.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        title.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }

            Log.d(TAG, "player view init ... " + MainActivity.this.playerView);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };


    private class ReutersTimerTask extends TimerTask{
        FrameLayout frameLayout;

        public ReutersTimerTask(FrameLayout frameLayout){
            this.frameLayout = frameLayout;
        }

        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                }
            });
        }
    }

    private ViewPager.OnPageChangeListener timeViewPagerListener = new ViewPager.OnPageChangeListener() {
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

    private void setMediaURL(String mediaURL){

        this.mediaURL = mediaURL;

        Log.d(TAG, "media URL : " + this.mediaURL);
    }

    private boolean initializePlayer(){
        if(simpleExoPlayer != null){
            releasePlayer();
        }
        if(mediaURL.equals("")){
            Log.d(TAG, "no media url");
            Toast.makeText(this, getString(R.string.no_video_warning), Toast.LENGTH_SHORT).show();
            return false;
        }else {
            Log.d(TAG, "initializing exo player");
            playerView.setVisibility(View.VISIBLE);
            playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_ZOOM);
            simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(
                    this,
                    new DefaultRenderersFactory(this),
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



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(NASA_SLIDER_INDEX, index);

        outState.putString(MEDIA_URL, mediaURL);
        outState.putBoolean(PLAY_WHEN_READY, playWhenReady);
        outState.putLong(PLAYBACK_POSITION, playbackPosition);
        outState.putInt(CURRENT_WINDOW, currentWindow);
    }


    private void fragmentTransactionHelper(final Fragment fragment)   {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_placeholder_reuters, fragment);
        transaction.commit();
    }

    private class SliderTimer extends TimerTask{

        @Override
        public void run() {
            MainActivity.this.runOnUiThread(
                    new Runnable() {
                        @Override
                        public void run() {
                            if(viewPagerReuters.getCurrentItem() < reutersItemList.size() - 1){
                                if (index < 6) {
                                    progressBarUtil.setSliderProgress(progressBars[index++]);
                                } else {
                                    index = 0;
                                    progressBarUtil.refreshAllProgressBars(progressBars);
                                }
                                Log.d(TAG, "progress bar index : " + index);
                                viewPagerReuters.setCurrentItem(viewPagerReuters.getCurrentItem() + 1);
                            }else{
                                viewPagerReuters.setCurrentItem(0);
                            }
                        }
                    }
            );
        }
    }

    private void findProgressBarsById(){
        progressBars = new ProgressBar[]{
            findViewById(R.id.progress_bar_1),
            findViewById(R.id.progress_bar_2),
            findViewById(R.id.progress_bar_3),
            findViewById(R.id.progress_bar_4),
            findViewById(R.id.progress_bar_5),
            findViewById(R.id.progress_bar_6),
        };
    }




}
