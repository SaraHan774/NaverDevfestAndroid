package com.gahee.rss_v2.ui.pagerAdapters.outer;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.gahee.rss_v2.R;
import com.gahee.rss_v2.data.nasa.model.ChannelObj;
import com.gahee.rss_v2.data.nasa.tags.Item;

public class NasaPagerAdapter extends PagerAdapter {

    private static final String TAG = "NasaPagerAdapter";

    private Context mContext;
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
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.nasa_outer_slider, container, false);
        final Item item = mChannelObj.getmItemList().get(position);
        Log.d(TAG, "item check : " + item.getDescription());

        TextView title = view.findViewById(R.id.tv_nasa_outer_title);
        TextView description = view.findViewById(R.id.tv_nasa_outer_description);

        title.setText(mChannelObj.getmItemList().get(position).getTitle());
        description.setText(mChannelObj.getmItemList().get(position).getDescription());

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

