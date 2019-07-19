package com.gahee.rss_v2;

import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.PagerAdapter;
import com.gahee.rss_v2.databinding.FeedSliderBinding;
import com.gahee.rss_v2.retrofit.model.ChannelObj;
import com.gahee.rss_v2.retrofit.tags.Item;


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


        Log.d(TAG, "instantiate item running ...." );
        container.addView(feedSliderBinding.getRoot());
//        Log.d(TAG, "get root () :  " + feedSliderBinding.getRoot());
        return feedSliderBinding.getRoot();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        Log.d(TAG, "destroyItem");
        View view = (View) object;
        container.removeView(view);
    }
}
