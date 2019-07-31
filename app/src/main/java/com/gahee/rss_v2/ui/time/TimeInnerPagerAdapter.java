package com.gahee.rss_v2.ui.time;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.gahee.rss_v2.R;
import com.gahee.rss_v2.data.time.model.TimeArticle;

import static com.gahee.rss_v2.utils.Constants.YOUTUBE_WATCH_BASE_URL;

public class TimeInnerPagerAdapter extends PagerAdapter {

    private static final String TAG = "TimeInnerPagerAdapter";

    private final Context context;
    private int imageLength;
    private int videoThumbnailLength;
    private final TimeArticle timeArticles;



    public TimeInnerPagerAdapter(Context context, TimeArticle timeArticles){
        Log.d("TimeArticleViewModel", "inner pager adapter constructor running " + timeArticles);
        this.context = context;
        this.timeArticles = timeArticles;
    }



    @Override
    public int getCount() {
        imageLength = timeArticles.getContent().size();
        videoThumbnailLength = timeArticles.getmYoutubeThumbnailLinks().size();

        return imageLength + videoThumbnailLength;
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Log.d("TimeArticleViewModel", "instantiateItem: time pager adapter instantiate item running");

        LayoutInflater layoutInflater =
                (LayoutInflater) container.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater != null ? layoutInflater.inflate(R.layout.inner_time_slider, container, false) : null;

        TextView currentPage = view.findViewById(R.id.tv_article_page_number_current);
        TextView totalPage = view.findViewById(R.id.tv_article_page_number_total);

        ImageView imageView = view.findViewById(R.id.image_view_time_inner);
        TextView textView = view.findViewById(R.id.tv_time_inner_content_title);

            if(position < imageLength) {
                if (timeArticles.getContent().get(position) != null) {
                    if (timeArticles.getContent().get(position).getUrl() != null) {
                        Glide.with(context).load(timeArticles.getContent().get(position).getUrl())
                                .placeholder(R.drawable.time_magazine_logo)
                                .error(R.drawable.time_magazine_logo)
                                .transition(GenericTransitionOptions.with(R.anim.grow_left)).into(imageView);


                        String contentTitle = timeArticles.getContent().get(position).getTitle();
                        if (contentTitle != null) {
                            textView.setText(Html.fromHtml(contentTitle));
                        } else {
                            textView.setPadding(0, 0, 0, 0);
                        }
                    }
                }
            }else if(position >= imageLength && position < videoThumbnailLength + imageLength){
                Log.d("GLIDE", imageLength + " / " + videoThumbnailLength);
                Glide.with(context).load(timeArticles.getmYoutubeThumbnailLinks().get(position - imageLength))
                        .placeholder(R.drawable.scrim_gradient_to_above)
                        .error(R.drawable.ic_launcher_foreground)
                        .addListener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                Log.d(TAG, "onLoadFailed: "  + e.getMessage() + " \n " + e.getCauses());
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                return false;
                            }
                        })
                        .transition(GenericTransitionOptions.with(R.anim.grow_left))
                        .into(imageView);

                ImageButton imageButton = view.findViewById(R.id.custom_fab_play_youtube);
                imageButton.setVisibility(View.VISIBLE);
                //image button is visible but user has to click on the view itself to get onClick event.
                view.setOnClickListener(view1 ->
                        watchYoutubeVideo(context, timeArticles.getmYoutubeLinkIds().get(position - imageLength)));

                textView.setPadding(0, 0, 0, 0);
            }

            currentPage.setText(String.valueOf(position + 1));
            totalPage.setText(String.valueOf(videoThumbnailLength + imageLength));

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

    public static void watchYoutubeVideo(Context context, String videoId){
        Intent launchYoutubeApp = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + videoId));
        Intent launchOnBrowser = new Intent(Intent.ACTION_VIEW,
                Uri.parse(YOUTUBE_WATCH_BASE_URL + videoId));
        try {
            context.startActivity(launchYoutubeApp);
        } catch (
                ActivityNotFoundException ex) {
            context.startActivity(launchOnBrowser);
        }
    }

}
