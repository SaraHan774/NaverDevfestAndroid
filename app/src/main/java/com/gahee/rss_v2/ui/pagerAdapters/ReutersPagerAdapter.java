package com.gahee.rss_v2.ui.pagerAdapters;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.gahee.rss_v2.utils.StringUtils;
import com.gahee.rss_v2.R;
import com.gahee.rss_v2.data.reuters.model.ChannelReuters;
import com.gahee.rss_v2.data.reuters.tags.Item;
import com.gahee.rss_v2.utils.ProgressBarUtil;

import static com.gahee.rss_v2.utils.Constants.TAG_REUTERS_FRAME;

public class ReutersPagerAdapter extends PagerAdapter {

    private static final String TAG = "ReutersPagerAdapter";

    private final Context mContext;
    private final ChannelReuters mChannelReuters;
    private boolean isFirstInstantiation = false;
    private final Bundle bundle = new Bundle();
    private ProgressBarUtil progressBarUtil;


    public ReutersPagerAdapter(Context context, ChannelReuters channelObjs){
        Log.d(TAG, "feed pager adapter instantiating ... ");
        mContext = context;
        mChannelReuters = channelObjs;
    }

    private void saveInstantiationStatus(boolean isFirstInstantiation){
        this.isFirstInstantiation = isFirstInstantiation;
        bundle.putBoolean("key", isFirstInstantiation);
    }

    @Override
    public int getCount() {
        return mChannelReuters.getmItemList() != null ? mChannelReuters.getmItemList().size() : 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        if(position == 0 && !isFirstInstantiation){
            saveInstantiationStatus(true);
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.main_reuters_slider, container, false);

        final Item item = mChannelReuters.getmItemList().get(position);


        TextView title = view.findViewById(R.id.tv_reuters_outer_title);
        TextView description = view.findViewById(R.id.tv_reuters_outer_description);
        TextView pubDate = view.findViewById(R.id.tv_reuters_outer_pub_date);

        if(item != null) {

            title.setText(item.getTitle());
            String articleDescription = item.getDescription();

            String cleanDescription = StringUtils.removeHtmlTagsFromString(articleDescription);
            description.setText(cleanDescription);

            pubDate.setText(item.getPubDate());
        }

        FrameLayout frameLayout = view.findViewById(R.id.reuters_outer_slider_container);
        frameLayout.setTag(TAG_REUTERS_FRAME + position);

//        frameLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse(mChannelReuters.getmItemList().get(position).getLink()));
//                mContext.startActivity(intent);
//            }
//        });


        if(bundle.getBoolean("key")) {
            Animation fadeOut = AnimationUtils.loadAnimation(mContext, R.anim.description_fade_out);
            description.startAnimation(fadeOut);
            Animation slideToRight = AnimationUtils.loadAnimation(mContext, R.anim.title_slide_to_left);
            pubDate.startAnimation(fadeOut);

            fadeOut.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    description.setVisibility(View.GONE);
                    pubDate.setVisibility(View.GONE);
                    title.startAnimation(slideToRight);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            slideToRight.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) { }
                @Override
                public void onAnimationEnd(Animation animation) {
                    title.setVisibility(View.GONE);
                }
                @Override
                public void onAnimationRepeat(Animation animation) { }
            });
        }
        container.addView(view);
        return view;
    }



    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        Log.d(TAG, "destroyItem: reset pb before remove view call");

        Log.d(TAG, "destroyItem: " + position);
        View view = (View) object;
        container.removeView(view);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return super.getItemPosition(object);
    }
}

