package com.gahee.rss_v2.ui.pagerAdapters.outer;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.gahee.rss_v2.StringUtils;
import com.gahee.rss_v2.R;
import com.gahee.rss_v2.data.reuters.model.ChannelObj;
import com.gahee.rss_v2.data.reuters.tags.Item;
import com.gahee.rss_v2.utils.ProgressBarUtil;
import com.gahee.rss_v2.utils.SliderIndexViewModel;
import com.google.android.exoplayer2.ui.PlayerView;

import static com.gahee.rss_v2.utils.Constants.TAG_REUTERS_FRAME;

public class ReutersPagerAdapter extends PagerAdapter {

    private static final String TAG = "ReutersPagerAdapter";

    private Context mContext;
    private ChannelObj mChannelObj;
    private boolean isFirstInstantiation = false;
    private Bundle bundle = new Bundle();
    private ProgressBarUtil progressBarUtil;


    public ReutersPagerAdapter(Context context, ChannelObj channelObjs){
        Log.d(TAG, "feed pager adapter instantiating ... ");
        mContext = context;
        mChannelObj = channelObjs;
    }

    private void saveInstantiationStatus(boolean isFirstInstantiation){
        this.isFirstInstantiation = isFirstInstantiation;
        bundle.putBoolean("key", isFirstInstantiation);
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
        if(position == 0 && !isFirstInstantiation){
            saveInstantiationStatus(true);
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.main_reuters_slider, container, false);

        final Item item = mChannelObj.getmItemList().get(position);

        TextView title = view.findViewById(R.id.tv_reuters_outer_title);
        TextView description = view.findViewById(R.id.tv_reuters_outer_description);

        title.setText(mChannelObj.getmItemList().get(position).getTitle());
        String articleDescription = mChannelObj.getmItemList().get(position).getDescription();

        String cleanDescription = StringUtils.removeHtmlTagsFromString(articleDescription);
        description.setText(cleanDescription);

        FrameLayout frameLayout = view.findViewById(R.id.reuters_outer_slider_container);
        frameLayout.setTag(TAG_REUTERS_FRAME + position);

//        Log.d(TAG, "instantiateItem: position  "+ position);
//        progressBarUtil.resetProgressBarToUserSelection(position);
        //이렇게 넘겨주면 뷰 페이저가 한번에 한개씩만 초기화 하는 게 아니기 때문에 이상해짐 ...
        //현재 포지션을 넘겨받아서 main 에서 업데이트 시키기 ... ?


//        RelativeLayout relativeLayout = view.findViewById(R.id.reuters_outer_text_view_container);
//        relativeLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(relativeLayout.getBackground().equals(mContext.getDrawable(R.drawable.transparent_background))){
//                    relativeLayout.setBackground(mContext.getDrawable(R.drawable.scrim_gradient_to_below));
//                }else{
//                    relativeLayout.setBackground(mContext.getDrawable(R.drawable.transparent_background));
//                }
//                Log.d(TAG, "on click listener ");
//            }
//        });

        if(bundle.getBoolean("key")) {
            Animation fadeOut = AnimationUtils.loadAnimation(mContext, R.anim.description_fade_out);
            description.startAnimation(fadeOut);
            Animation slideToRight = AnimationUtils.loadAnimation(mContext, R.anim.title_slide_to_left);

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

