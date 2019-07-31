package com.gahee.rss_v2.ui.pagerAdapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;
import com.gahee.rss_v2.ui.activity.MainActivity;
import com.gahee.rss_v2.R;
import com.gahee.rss_v2.data.wwf.model.WWFArticle;
import com.gahee.rss_v2.utils.MyAnimationUtils;
import com.gahee.rss_v2.utils.StringUtils;


import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.gahee.rss_v2.utils.Constants.TAG_WWF_FRAME;

public class WwfPagerAdapter extends PagerAdapter {
    private static final String TAG = "wwfPagerAdapter";

    private final Context mContext;
    private final ArrayList<WWFArticle> wwfArticle;

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

        View view = LayoutInflater.from(mContext).inflate(R.layout.main_wwf_slider, container, false);
        final WWFArticle article = wwfArticle.get(position);

        FrameLayout frameLayout = view.findViewById(R.id.wwf_outer_slider_container);
        frameLayout.setTag(TAG_WWF_FRAME + position);

        TextView title = view.findViewById(R.id.tv_wwf_outer_title);
        Animation titleSlideInAnimation = AnimationUtils.loadAnimation(mContext, R.anim.wwf_title_slide_in);
        titleSlideInAnimation.setInterpolator(new BounceInterpolator());
        title.setText(article.getTitle());
        title.startAnimation(titleSlideInAnimation);

        TextView pubDate = view.findViewById(R.id.tv_wwf_outer_pubdate);
        pubDate.setText(StringUtils.formatWWFPubDateString(wwfArticle.get(position).getPubDate()));

        TextView description = view.findViewById(R.id.tv_wwf_outer_description);
        Animation fadeIn = AnimationUtils.loadAnimation(mContext, R.anim.description_fade_in);
        description.setText(article.getDescription());
        description.startAnimation(fadeIn);

        Log.d(TAG, "instantiate Item " + position);

        view.setOnClickListener(view1 -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(wwfArticle.get(position).getGuid()));
            mContext.startActivity(intent);
        });


        ImageSwitcher imageSwitcher = view.findViewById(R.id.image_switcher_wwf_outer_slider);

        imageSwitcher.setFactory(() -> {
            ImageView imageView = new ImageView(mContext);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(
                    new ImageSwitcher.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                    )
            );
            return imageView;
        });

        if(article.getExtractedMediaLinks().size() > 0){
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new ImageSliderTimer(article.getExtractedMediaLinks(), imageSwitcher), 0, 4000);
        }


        container.addView(view);
        return view;
    }


    private class ImageSliderTimer extends TimerTask{
        final List<String> imageLinks;
        final ImageSwitcher imageSwitcher;
        int index = 0;

        ImageSliderTimer(List<String> medias, ImageSwitcher imageSwitcher){
            this.imageLinks = medias;
            this.imageSwitcher = imageSwitcher;
        }

        @Override
        public void run() {
            ((MainActivity) mContext).runOnUiThread(
                    () -> {

                        if(index < imageLinks.size()){
                            Glide.with(mContext).load(imageLinks.get(index++))
                                    .transition(GenericTransitionOptions.with(MyAnimationUtils.setRandomGrowAnimation()))
                                    .placeholder(R.drawable.scrim_gradient_up_and_down)
                                    .error(R.drawable.scrim_gradient_up_and_down)
                                    .into((ImageView) imageSwitcher.getCurrentView());

                        }else{
                            index = 0;
                            Glide.with(mContext).load(imageLinks.get(index++))
                                    .transition(GenericTransitionOptions.with(MyAnimationUtils.setRandomGrowAnimation()))
                                    .placeholder(R.drawable.wwf_logo)
                                    .error(R.drawable.wwf_logo)
                                    .into((ImageView) imageSwitcher.getCurrentView());

                        }
                    }
            );
        }
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
