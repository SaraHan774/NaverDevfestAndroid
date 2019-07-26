package com.gahee.rss_v2.ui.pagerAdapters.outer;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.gahee.rss_v2.R;
import com.gahee.rss_v2.data.wwf.model.WWFArticle;
import com.gahee.rss_v2.data.wwf.model.WWFChannel;
import com.gahee.rss_v2.data.wwf.tags.Item;

import java.util.ArrayList;

public class WwfPagerAdapter extends PagerAdapter {
    private static final String TAG = "NasaPagerAdapter";

    private Context mContext;
    private ArrayList<WWFArticle> wwfArticle;

    public WwfPagerAdapter(Context context, ArrayList<WWFArticle> article){
        Log.d(TAG, "feed pager adapter instantiating ... ");
        mContext = context;
        wwfArticle = article;
    }

    @Override
    public int getCount() {
        return wwfArticle != null ? wwfArticle.size() : 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.wwf_outer_slider, container, false);
        final WWFArticle article = wwfArticle.get(position);
//        Log.d(TAG, "item check : " + article.getDescription());

        TextView title = view.findViewById(R.id.tv_wwf_outer_title);
        TextView description = view.findViewById(R.id.tv_wwf_outer_description);
        ImageView imageView = view.findViewById(R.id.img_wwf_outer_slider);

        title.setText(article.getTitle());
        description.setText(Html.fromHtml(article.getDescription()));

        Glide.with(mContext).load(article.getExtractedMediaLinks().get(0)).placeholder(R.drawable.scrim_gradient_to_above)
                .error(R.drawable.ic_launcher_background)
                .into(imageView);

        container.addView(view);
        return view;
    }



    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        Log.d(TAG, "destroyItem");
        View view = (View) object;
        container.removeView(view);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return super.getItemPosition(object);
    }
}
