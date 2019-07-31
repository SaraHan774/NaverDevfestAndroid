package com.gahee.rss_v2.ui.pagerAdapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

import com.gahee.rss_v2.data.reuters.model.ArticleReuters;
import com.gahee.rss_v2.utils.StringUtils;
import com.gahee.rss_v2.R;
import com.gahee.rss_v2.data.reuters.model.ChannelReuters;
import com.gahee.rss_v2.data.reuters.tags.Item;
import com.gahee.rss_v2.utils.ProgressBarUtil;
import com.google.android.exoplayer2.ui.PlayerView;

import java.util.ArrayList;

import static com.gahee.rss_v2.utils.Constants.TAG_REUTERS_FRAME;

public class ReutersPagerAdapter extends PagerAdapter {

    private static final String TAG = "ReutersPagerAdapter";

    private final Context mContext;
    private final ArrayList<ArticleReuters> mArticleReuters;
    private boolean isFirstInstantiation = false;
    private final Bundle bundle = new Bundle();


    public ReutersPagerAdapter(Context context, ArrayList<ArticleReuters> articleReuters){
        Log.d(TAG, "feed pager adapter instantiating ... ");
        mContext = context;
        mArticleReuters = articleReuters;
    }

    private void saveInstantiationStatus(boolean isFirstInstantiation){
        this.isFirstInstantiation = isFirstInstantiation;
        bundle.putBoolean("key", isFirstInstantiation);
    }

    @Override
    public int getCount() {
        return mArticleReuters != null ? mArticleReuters.size() : 0;
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

        final ArticleReuters articleReuters = mArticleReuters.get(position);

        TextView title = view.findViewById(R.id.tv_reuters_outer_title);
        TextView description = view.findViewById(R.id.tv_reuters_outer_description);
        TextView pubDate = view.findViewById(R.id.tv_reuters_outer_pub_date);

        if(articleReuters != null) {

            title.setText(articleReuters.getmArticleTitle());
            String articleDescription = articleReuters.getmArticleDescription();

            String cleanDescription = StringUtils.removeHtmlTagsFromString(articleDescription);
            description.setText(cleanDescription);

            if(articleReuters.getmArticlePubDate() != null || !articleReuters.getmArticlePubDate().equals("")){
                pubDate.setText("");
            }else{
                pubDate.setText(articleReuters.getmArticlePubDate());
            }
        }

        FrameLayout frameLayout = view.findViewById(R.id.reuters_outer_slider_container);
        frameLayout.setTag(TAG_REUTERS_FRAME + position);

        PlayerView playerView = view.findViewById(R.id.reuters_outer_video_player);

        playerView.setOnClickListener(parentView -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(articleReuters.getmArticleLink()));
            mContext.startActivity(intent);
        });


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
        Log.d(TAG, "destroyItem: " + position);
        View view = (View) object;
        container.removeView(view);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return super.getItemPosition(object);
    }
}

