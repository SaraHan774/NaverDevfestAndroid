package com.gahee.rss_v2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import com.gahee.rss_v2.remoteSource.RemoteViewModel;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        RemoteViewModel remoteViewModel = ViewModelProviders.of(this).get(RemoteViewModel.class);
        remoteViewModel.fetchTimeDataFromRepo();

    }
}
