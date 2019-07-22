package com.gahee.rss_v2.myFeed;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.PagerAdapter;

import com.gahee.rss_v2.R;
import com.gahee.rss_v2.databinding.FeedSliderBinding;
import com.gahee.rss_v2.data.nasa.model.ChannelObj;
import com.gahee.rss_v2.data.nasa.tags.Item;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class FeedPagerAdapter extends PagerAdapter {

    private static final String TAG = "FeedPagerAdapter";

    private final Context mContext;
    private ChannelObj mChannelObj;

    public FeedPagerAdapter(Context context, ChannelObj channelObjs){
        Log.d(TAG, "feed pager adapter instantiating ... ");
        mContext = context;
        mChannelObj = channelObjs;
    }

    @Override
    public int getCount() {
        Log.d(TAG, "get count : " + mChannelObj.getmItemList().size());
        return mChannelObj.getmItemList() != null ? mChannelObj.getmItemList().size() : 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        FeedSliderBinding feedSliderBinding =
                DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.feed_slider,
                        container, false);

        Item item = mChannelObj.getmItemList().get(position);
        Log.d(TAG, "item check : " + item.getDescription());

        feedSliderBinding.tvArticleDescription.setText(item.getDescription());
        feedSliderBinding.tvArticleTitle.setText(item.getTitle());
        feedSliderBinding.tvPubdate.setText(item.getPubDate());

//        String imageUrl = item.getEnclosure().getUrl();
//        feedSliderBinding.setImageUrl(stripItokTokenFromImageUrl(imageUrl));
//        feedSliderBinding.setContext(mContext);

//        feedSliderBinding.setVideoUrl(item.getEnclosure().getUrl());

        Log.d(TAG, "instantiate item running ...." );
        container.addView(feedSliderBinding.getRoot());
        return feedSliderBinding.getRoot();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        Log.d(TAG, "destroyItem");
        View view = (View) object;
        container.removeView(view);
    }

    private String stripItokTokenFromImageUrl(String string){
        Pattern pattern = Pattern.compile("\\?itok");
        Matcher matcher = pattern.matcher(string);
        String cleanString = "";
        while(matcher.find()){
            int index = matcher.start();
            cleanString = string.substring(0, index);
        }
        Log.d(TAG, cleanString);
        return cleanString;
    }
}
