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

class TimeInnerPagerAdapter extends PagerAdapter {

    private final Context context;
    private int imageLength;
    private int videoLength;
    private final TimeArticle timeArticles;
    private static boolean isVideo = false;

    public static boolean isIsVideo() {
        return isVideo;
    }

    public TimeInnerPagerAdapter(Context context, TimeArticle timeArticles){
        Log.d("TimeArticleViewModel", "inner pager adapter constructor running " + timeArticles);
        this.context = context;
        this.timeArticles = timeArticles;
    }



    @Override
    public int getCount() {
        imageLength = timeArticles.getContent().size();
        videoLength = timeArticles.getmYoutubeLink().size();

        return imageLength + videoLength;
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Log.d("TimeArticleViewModel", "instantiateItem: time pager adapter instantiate item running");

        LayoutInflater layoutInflater =
                (LayoutInflater) container.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        int resourceId = 0;
        if(position < imageLength){
            resourceId = R.layout.inner_time_slider;
            isVideo = false;

        }else if(position >= imageLength && position < imageLength + videoLength){
            resourceId = R.layout.inner_time_slider_video;
            isVideo = true;

        }else{
            //???
        }
        View view = layoutInflater != null ? layoutInflater.inflate(resourceId, container, false) : null;

        if(isVideo){
            view.setTag("VIEW" + position);
            YouTubePlayerView playerView = view.findViewById(R.id.youtube_player_view);
            playerView.setTag("PAYER" + position);

        }else {
            ImageView imageView = view.findViewById(R.id.image_view_time_inner);
            imageView.setVisibility(View.VISIBLE);

            if (timeArticles.getContent().get(position) != null) {
                if (timeArticles.getContent().get(position).getUrl() != null) {
                    Glide.with(context).load(timeArticles.getContent().get(position).getUrl())
                            .placeholder(R.drawable.scrim_gradient_to_above)
                            .error(R.drawable.ic_launcher_background)
                            .transition(GenericTransitionOptions.with(R.anim.grow_left)).into(imageView);
                }
                TextView textView = view.findViewById(R.id.tv_time_inner_content_title);
                String contentTitle = timeArticles.getContent().get(position).getTitle();
                if (contentTitle != null) {
                    textView.setText(timeArticles.getContent().get(position).getTitle());
                } else {
                    textView.setPadding(0, 0, 0, 0);
                }
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
