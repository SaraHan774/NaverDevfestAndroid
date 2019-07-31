package com.gahee.rss_v2.ui.time;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;
import com.gahee.rss_v2.R;
import com.gahee.rss_v2.data.time.model.TimeArticle;
import com.gahee.rss_v2.utils.StringUtils;

import java.util.ArrayList;

public class TimePagerAdapter extends PagerAdapter {

    private static final String TAG = "TimePagerAdapter";
    public static final String YOUTUBE = "YoutubePlayerDebugging";

    private final Context mContext;
    private final ArrayList<TimeArticle> timeArticles;
    private Animation in_first;
    private Animation in_second;
    private Animation in_third;



    public TimePagerAdapter(Context context, ArrayList<TimeArticle> timeArticles){
        this.mContext = context;
        this.timeArticles = timeArticles;
    }

    @Override
    public int getCount() {
        return timeArticles != null ? timeArticles.size() : 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.main_time_slider, container, false);

        in_first = AnimationUtils.loadAnimation(mContext, R.anim.sequential_in_1);
        in_second = AnimationUtils.loadAnimation(mContext, R.anim.sequential_in_2);
        in_third = AnimationUtils.loadAnimation(mContext, R.anim.sequential_in_3);

        TextView title = view.findViewById(R.id.tv_time_outer_title);
        title.setText(timeArticles.get(position).getmArticletitle());
        title.startAnimation(in_first);

        TextView pubDate = view.findViewById(R.id.tv_time_outer_pub_date);
        String tempPubDate = timeArticles.get(position).getmArticlePubDate();
        pubDate.setText(StringUtils.formatTIMEPubDateString(tempPubDate));
        pubDate.startAnimation(in_second);

        TextView description = view.findViewById(R.id.tv_time_outer_description);
        description.setText(Html.fromHtml(timeArticles.get(position).getmArticleDescription()));
        description.startAnimation(in_third);


        ImageView imageView = view.findViewById(R.id.img_time_outer_article_thumbnail);
        String thumbnailUrl = timeArticles.get(position).getmArticleThumbnail().getUrl();
        Glide.with(mContext).load(thumbnailUrl).transition(GenericTransitionOptions.with(R.anim.in_from_right))
                .placeholder(R.drawable.time_magazine_logo)
                .error(R.drawable.time_magazine_logo)
                .into(imageView);

        view.setOnClickListener(view1 -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(timeArticles.get(position).getmArticleLink()));
            mContext.startActivity(intent);
        });


        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        View view = (View) object;
        container.removeView(view);
    }



    @Override
    public int getItemPosition(@NonNull Object object) {
        return super.getItemPosition(object);
    }

}
