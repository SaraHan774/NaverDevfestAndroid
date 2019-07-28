package com.gahee.rss_v2.utils;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.gahee.rss_v2.R;

import java.util.Random;

public class MyAnimationUtils {

    public static int setRandomGrowAnimation(){
        int [] animations = {R.anim.grow, R.anim.grow_left};
        Random random = new Random();
        int randomNumber = random.nextInt(2);
        return animations[randomNumber];
    }


}
