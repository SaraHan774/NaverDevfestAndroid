package com.gahee.rss_v2.ui.pagerAdapters.outer;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.gahee.rss_v2.R;
import com.gahee.rss_v2.data.time.model.TimeChannel;

import java.util.List;

public class TimePagerAdapter extends PagerAdapter {

    private static final String TAG = "TimePagerAdapter";

    private Context mContext;
    private TimeChannel timeChannel;

    public TimePagerAdapter(Context context, TimeChannel timeChannel){
        this.mContext = context;
        this.timeChannel = timeChannel;
    }

    @Override
    public int getCount() {
        return timeChannel.getmChannelItems() != null ? timeChannel.getmChannelItems().size() : 0;
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
        title.setText(timeChannel.getmChannelItems().get(position).getArticleTitle());

        TextView description = view.findViewById(R.id.tv_time_outer_description);
        description.setText(Html.fromHtml(timeChannel.getmChannelItems().get(position).getArticleDesc()));

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        View view = (View) object;
        container.removeView(view);
    }

}
