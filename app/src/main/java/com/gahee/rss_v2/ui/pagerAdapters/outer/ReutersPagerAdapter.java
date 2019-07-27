package com.gahee.rss_v2.ui.pagerAdapters.outer;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.gahee.rss_v2.ParsingUtils;
import com.gahee.rss_v2.R;
import com.gahee.rss_v2.data.reuters.model.ChannelObj;
import com.gahee.rss_v2.data.reuters.tags.Item;

import static com.gahee.rss_v2.utils.Constants.TAG_REUTERS_FRAME;

public class ReutersPagerAdapter extends PagerAdapter {

    private static final String TAG = "ReutersPagerAdapter";

    private Context mContext;
    private ChannelObj mChannelObj;

    public ReutersPagerAdapter(Context context, ChannelObj channelObjs){
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

        View view = LayoutInflater.from(mContext).inflate(R.layout.main_reuters_slider, container, false);
        final Item item = mChannelObj.getmItemList().get(position);
        Log.d(TAG, "item check : " + item.getDescription());

        TextView title = view.findViewById(R.id.tv_reuters_outer_title);
        TextView description = view.findViewById(R.id.tv_reuters_outer_description);

        title.setText(mChannelObj.getmItemList().get(position).getTitle());
        String articleDescription = mChannelObj.getmItemList().get(position).getDescription();

        String cleanDescription = ParsingUtils.removeHtmlTagsFromString(articleDescription);
        description.setText(cleanDescription);

        FrameLayout frameLayout = view.findViewById(R.id.reuters_outer_slider_container);
        frameLayout.setTag(TAG_REUTERS_FRAME + position);

        RelativeLayout relativeLayout = view.findViewById(R.id.reuters_outer_text_view_container);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relativeLayout.setBackground(mContext.getDrawable(R.drawable.transparent_background));
                if(relativeLayout.getBackground().equals(mContext.getDrawable(R.drawable.transparent_background))){
                    relativeLayout.setBackground(mContext.getDrawable(R.drawable.scrim_gradient_to_below));
                }
            }
        });

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

