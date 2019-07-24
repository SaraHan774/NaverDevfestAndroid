package com.gahee.rss_v2.ui.pagerAdapters;

import android.content.Context;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.gahee.rss_v2.R;
import com.gahee.rss_v2.data.nasa.model.ChannelObj;
import com.gahee.rss_v2.data.nasa.tags.Channel;
import com.gahee.rss_v2.data.nasa.tags.Item;


public class NasaPagerAdapter extends PagerAdapter{



    private static final String TAG = "NasaPagerAdapter";

        private final Context mContext;
        private ChannelObj mChannelObj;

        public NasaPagerAdapter(Context context, ChannelObj channelObjs){
            Log.d(TAG, "feed pager adapter instantiating ... ");
            mContext = context;
            mChannelObj = channelObjs;
        }

        @Override
        public int getCount() {
            return mChannelObj.getmItemList() != null ? mChannelObj.getmItemList().size() : 0;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull final ViewGroup container, int position) {

            View view = LayoutInflater.from(mContext).inflate(R.layout.slider_below_nasa, container, false);

            Item item = mChannelObj.getmItemList().get(position);
            Log.d(TAG, "item check : " + item.getDescription());

            String source = item.getSource();
            String videoTitle = item.getTitle();
            String videoPubDate = item.getPubDate();
            String videoUrl = item.getEnclosure().getUrl();
            String pageLink = item.getLink();
            String videoDescription = item.getDescription();

            TextView tvVideoTitle = view.findViewById(R.id.tv_nasa_video_title);
            tvVideoTitle.setText(videoTitle);
            TextView tvVideoPubDate = view.findViewById(R.id.tv_nasa_video_pubDate);
            tvVideoPubDate.setText(videoPubDate);
            ImageButton imageButton = view.findViewById(R.id.img_btn_nasa_video_info);
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        onStartTransition(container, mContext);
                }
            });

            ViewPager nasaInnerViewPager = view.findViewById(R.id.view_pager_nasa_more_videos);
            NasaInnerPagerAdapter innerPagerAdapter = new NasaInnerPagerAdapter(mContext, mChannelObj);
            nasaInnerViewPager.setAdapter(innerPagerAdapter);


            Log.d(TAG, "instantiate item running ...." );
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            Log.d(TAG, "destroyItem");
            View view = (View) object;
            container.removeView(view);
        }



    private void onStartTransition(ViewGroup viewGroup, Context context){

        Scene endingScene = Scene.getSceneForLayout(
                (ViewGroup) viewGroup.findViewById(android.R.id.content),
                R.layout.slider_below_nasa_info,
                context
        );

        Transition transition = TransitionInflater.from(context)
                .inflateTransition(R.transition.to_info);

        TransitionManager.go(endingScene, transition);
    }


    }

