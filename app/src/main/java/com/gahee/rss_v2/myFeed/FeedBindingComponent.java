package com.gahee.rss_v2.myFeed;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.gahee.rss_v2.R;

public class FeedBindingComponent implements androidx.databinding.DataBindingComponent {


    private static final String TAG = "FeedBindingComponent";

    @BindingAdapter(value = {"android:pagerAdapter"}, requireAll = false)
    public static void setViewPager(ViewPager viewPager, PagerAdapter pagerAdapter){
        viewPager.setAdapter(pagerAdapter);
    }


    @BindingAdapter(value = {"articleImage", "context"})
    public static void loadImage(ImageView view, String imageUrl, Context context) {
        Glide.with(context)
                .load(imageUrl)
                .error(R.drawable.ic_launcher_background)
                .placeholder(R.drawable.ic_home_black_24dp)
                .into(view);
    }

    //Glide debugging
    private static RequestListener<Bitmap> requestListener = new RequestListener<Bitmap>() {
        @Override
        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
            Log.d(TAG, "\n glide exception :  " + e.getMessage() + "\n target : " + target + " \n is first resource : " + isFirstResource);
            return false;
        }

        @Override
        public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
            // everything worked out, so probably nothing to do
            return false;
        }
    };


}
