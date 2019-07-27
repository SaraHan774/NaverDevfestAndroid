package com.gahee.rss_v2.ui.pagerAdapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.PagerAdapter;

import com.gahee.rss_v2.R;
import com.gahee.rss_v2.data.reuters.model.ChannelObj;
import com.gahee.rss_v2.data.reuters.tags.Item;
import com.gahee.rss_v2.ui.NasaVideoViewModel;
import com.gahee.rss_v2.ui.fragments.NasaFragment;

public class NasaInnerPagerAdapter extends PagerAdapter {

        private static final String TAG = "ReutersPagerAdapter";
        private final Context mContext;
        private ChannelObj mChannelObj;
        private NasaVideoViewModel nasaVideoViewModel;
        private NasaFragment nasaFragment;

        public NasaInnerPagerAdapter(Context context, NasaFragment fragment, ChannelObj channelObjs){
            Log.d(TAG, "feed pager adapter instantiating ... ");
            mContext = context;
            mChannelObj = channelObjs;
            nasaFragment = fragment;
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
            final Item item = mChannelObj.getmItemList().get(position);
            Log.d(TAG, "item check : " + item.getDescription());

            String videoTitle = item.getTitle();
            String videoUrl = item.getGroup().getContent().getUrlVideo();

            nasaVideoViewModel = ViewModelProviders.of(nasaFragment).get(NasaVideoViewModel.class);

            TextView tvVideoTitle = view.findViewById(R.id.tv_nasa_inner_video_title);
            tvVideoTitle.setText(videoTitle);
            ImageView imgVideoImage = view.findViewById(R.id.nasa_video_thumbnail);
            imgVideoImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "set selected video ... item : " + item);
                    nasaVideoViewModel.setSelectedVideo(item);
                }
            });

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

        @Override
        public int getItemPosition(@NonNull Object object) {
            return super.getItemPosition(object);
        }
}
