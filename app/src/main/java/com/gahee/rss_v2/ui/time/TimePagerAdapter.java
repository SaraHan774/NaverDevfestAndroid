package com.gahee.rss_v2.ui.time;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;
import com.gahee.rss_v2.R;
import com.gahee.rss_v2.data.time.model.TimeArticle;
import com.gahee.rss_v2.ui.TimeArticleViewModel;
import com.gahee.rss_v2.utils.StringUtils;

import java.util.ArrayList;

public class TimePagerAdapter extends PagerAdapter {

    private static final String TAG = "TimePagerAdapter";
    public static final String YOUTUBE = "YoutubePlayerDebugging";

    private Context mContext;
    private ArrayList<TimeArticle> timeArticles;
    private TimeArticleViewModel timeArticleViewModel;


    public TimePagerAdapter(Context context, ArrayList<TimeArticle> timeArticles){
        this.mContext = context;
        this.timeArticles = timeArticles;
        timeArticleViewModel = new TimeArticleViewModel();
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

        TextView title = view.findViewById(R.id.tv_time_outer_title);
        title.setText(timeArticles.get(position).getmArticletitle());

        TextView description = view.findViewById(R.id.tv_time_outer_description);
        description.setText(Html.fromHtml(timeArticles.get(position).getmArticleDescription()));

        TextView pubDate = view.findViewById(R.id.tv_time_outer_pub_date);
        String tempPubDate = timeArticles.get(position).getmArticlePubDate();
        pubDate.setText(StringUtils.formatPubDateString(tempPubDate));

        ImageView imageView = view.findViewById(R.id.img_time_outer_article_thumbnail);
        String thumbnailUrl = timeArticles.get(position).getmArticleThumbnail().getUrl();
        Glide.with(mContext).load(thumbnailUrl).transition(GenericTransitionOptions.with(R.anim.in_from_right))
                .placeholder(R.drawable.scrim_gradient_to_above)
                .error(R.drawable.ic_launcher_background)
                .into(imageView);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        View view = (View) object;
//        youTubePlayerView.release();
        container.removeView(view);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return super.getItemPosition(object);
    }
}
