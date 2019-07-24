package com.gahee.rss_v2.ui.pagerAdapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.gahee.rss_v2.R;
import com.gahee.rss_v2.data.nasa.model.ChannelObj;
import com.gahee.rss_v2.data.nasa.tags.Item;
import com.google.android.exoplayer2.ui.PlayerView;

public class NasaInnerPagerAdapter extends PagerAdapter {

        private static final String TAG = "NasaPagerAdapter";
        private final Context mContext;
        private ChannelObj mChannelObj;

        public NasaInnerPagerAdapter(Context context, ChannelObj channelObjs){
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
        public Object instantiateItem(@NonNull ViewGroup container, int position) {

            View view = LayoutInflater.from(mContext).inflate(R.layout.nasa_inner_slider, container, false);

            Item item = mChannelObj.getmItemList().get(position);
            Log.d(TAG, "item check : " + item.getDescription());

            String source = item.getSource();
            String videoTitle = item.getTitle();
            String videoPubDate = item.getPubDate();
            String videoUrl = item.getEnclosure().getUrl();
            String pageLink = item.getLink();
            String videoDescription = item.getDescription();

            PlayerView playerView = view.findViewById(R.id.nasa_video_thumbnail);

            TextView tvVideoTitle = view.findViewById(R.id.tv_nasa_inner_video_title);
            tvVideoTitle.setText(videoTitle);

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


    }
