package com.gahee.rss_v2.ui.pagerAdapters.outer;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.PagerAdapter;

import com.gahee.rss_v2.MainActivity;
import com.gahee.rss_v2.ParsingUtils;
import com.gahee.rss_v2.R;
import com.gahee.rss_v2.data.time.model.TimeArticle;
import com.gahee.rss_v2.data.time.model.TimeChannel;
import com.gahee.rss_v2.ui.TimeVideoViewModel;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;

import static com.gahee.rss_v2.utils.Constants.TAG_TIME_FRAME;
import static com.gahee.rss_v2.utils.Constants.TAG_TIME_VIDEO;

public class TimePagerAdapter extends PagerAdapter {

    private static final String TAG = "TimePagerAdapter";
    public static final String YOUTUBE = "YoutubePlayerDebugging";

    private Context mContext;
    private ArrayList<TimeArticle> timeArticles;
    private YouTubePlayerView youTubePlayerView;
    private AbstractYouTubePlayerListener listener;
    private TimeVideoViewModel timeVideoViewModel;

    public TimePagerAdapter(Context context, ArrayList<TimeArticle> timeArticles, AbstractYouTubePlayerListener listener){
        this.mContext = context;
        this.timeArticles = timeArticles;
        this.listener = listener;
        timeVideoViewModel = ViewModelProviders.of((AppCompatActivity) mContext).get(TimeVideoViewModel.class);

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

        FrameLayout frameLayout = view.findViewById(R.id.frame_layout_time_outer_slider);
        frameLayout.setTag(TAG_TIME_FRAME + position);

        TextView title = view.findViewById(R.id.tv_time_outer_title);
        title.setText(timeArticles.get(position).getmArticletitle());

        TextView description = view.findViewById(R.id.tv_time_outer_description);
        description.setText(Html.fromHtml(timeArticles.get(position).getmArticleDescription()));

        youTubePlayerView = view.findViewById(R.id.youtube_player_view);
        youTubePlayerView.setTag(TAG_TIME_FRAME + TAG_TIME_VIDEO + position);



        timeVideoViewModel.setSelectedVideo(timeArticles.get(position));

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        View view = (View) object;
        youTubePlayerView.release();
        container.removeView(view);
    }

}
