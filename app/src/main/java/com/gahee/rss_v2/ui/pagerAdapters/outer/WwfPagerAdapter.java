package com.gahee.rss_v2.ui.pagerAdapters.outer;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.gahee.rss_v2.MainActivity;
import com.gahee.rss_v2.R;
import com.gahee.rss_v2.data.wwf.model.WWFArticle;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.gahee.rss_v2.utils.Constants.TAG_WWF_FRAME;

public class WwfPagerAdapter extends PagerAdapter {
    private static final String TAG = "wwfPagerAdapter";

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

        View view = LayoutInflater.from(mContext).inflate(R.layout.main_wwf_slider, container, false);
        final WWFArticle article = wwfArticle.get(position);
//        Log.d(TAG, "item check : " + article.getDescription());

        FrameLayout frameLayout = view.findViewById(R.id.wwf_outer_slider_container);
        frameLayout.setTag(TAG_WWF_FRAME + position);

        TextView title = view.findViewById(R.id.tv_wwf_outer_title);
        TextView description = view.findViewById(R.id.tv_wwf_outer_description);

        title.setText(article.getTitle());

        Animation fadeIn = AnimationUtils.loadAnimation(mContext, R.anim.description_fade_in);
        description.setText(article.getDescription());
        description.startAnimation(fadeIn);

        Log.d(TAG, "instantiate Item " + position);

        ImageSwitcher imageSwitcher = view.findViewById(R.id.image_switcher_wwf_outer_slider);
        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(mContext);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setLayoutParams(
                        new ImageSwitcher.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT
                        )
                );
                return imageView;
            }
        });

        if(article.getExtractedMediaLinks().size() > 0){
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new ImageSliderTimer(article.getExtractedMediaLinks(), imageSwitcher), 0, 2500);
        }
        container.addView(view);
        return view;
    }



    private class ImageSliderTimer extends TimerTask{
        List<String> medias;
        ImageSwitcher imageSwitcher;
        int index = 0;

        public ImageSliderTimer(List<String> medias, ImageSwitcher imageSwitcher){
            this.medias = medias;
            this.imageSwitcher = imageSwitcher;
        }

        @Override
        public void run() {
            ((MainActivity) mContext).runOnUiThread(
                    new Runnable() {
                        @Override
                        public void run() {
                            if(index < medias.size()){
                                Glide.with(mContext).load(medias.get(index++))
                                        .transition(DrawableTransitionOptions.withCrossFade(800))
                                        .placeholder(R.drawable.scrim_gradient_to_above)
                                        .error(R.drawable.ic_launcher_background)
                                        .into((ImageView) imageSwitcher.getCurrentView());

                            }else{
                                index = 0;
                                Glide.with(mContext).load(medias.get(index++))
                                        .transition(DrawableTransitionOptions.withCrossFade(800))
                                        .placeholder(R.drawable.scrim_gradient_to_above)
                                        .error(R.drawable.ic_launcher_background)
                                        .into((ImageView) imageSwitcher.getCurrentView());

                            }
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
