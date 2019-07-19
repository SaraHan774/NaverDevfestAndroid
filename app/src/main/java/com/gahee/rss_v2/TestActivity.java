package com.gahee.rss_v2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.gahee.rss_v2.retrofit.NasaAPI;
import com.gahee.rss_v2.retrofit.RetrofitInstanceBuilder;
import com.gahee.rss_v2.retrofit.model.ArticleObj;
import com.gahee.rss_v2.retrofit.model.ChannelObj;
import com.gahee.rss_v2.retrofit.tags.Channel;
import com.gahee.rss_v2.retrofit.tags.Item;
import com.gahee.rss_v2.retrofit.tags.Rss;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestActivity extends AppCompatActivity {

    private static final String TAG = "TestActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);




    }


}
