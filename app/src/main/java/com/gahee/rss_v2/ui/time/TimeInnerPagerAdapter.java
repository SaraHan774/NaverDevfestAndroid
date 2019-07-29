package com.gahee.rss_v2.ui.time;

import android.content.Context;
import android.util.Log;
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
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class TimeInnerPagerAdapter extends PagerAdapter {

    private Context context;
    private TimeArticle timeArticles;

    public TimeInnerPagerAdapter(Context context, TimeArticle timeArticles){
        Log.d("TimeArticleViewModel", "inner pager adapter constructor running " + timeArticles);
        this.context = context;
        this.timeArticles = timeArticles;
    }


    @Override
    public int getCount() {
        int mediaContentSize = timeArticles.getContent().size();
        int youtubeLinkSize = timeArticles.getmYoutubeLink().size();
        return mediaContentSize;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.inner_time_slider, container, false);
        Log.d("TimeArticleViewModel", "instantiateItem: time pager adapter instantiate item running");

        YouTubePlayerView playerView = view.findViewById(R.id.youtube_player_view);
        playerView.setVisibility(View.GONE);

        ImageView imageView = view.findViewById(R.id.image_view_time_inner);
        imageView.setVisibility(View.VISIBLE);
        if(timeArticles.getContent().get(position) != null){
            if(timeArticles.getContent().get(position).getUrl() != null){
                Glide.with(context).load(timeArticles.getContent().get(position).getUrl())
                        .placeholder(R.drawable.scrim_gradient_to_above)
                        .error(R.drawable.ic_launcher_background)
                        .transition(GenericTransitionOptions.with(R.anim.grow_left)).into(imageView);
            }
            TextView textView = view.findViewById(R.id.tv_time_inner_content_title);
            if(timeArticles.getContent().get(position).getTitle() != null){
                textView.setText(timeArticles.getContent().get(position).getTitle());
            }
        }

        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        View view = (View) object;
        container.removeView(view);
    }
}
