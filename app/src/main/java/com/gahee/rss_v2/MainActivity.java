package com.gahee.rss_v2;

import android.os.Bundle;

import com.gahee.rss_v2.databinding.ActivityMainBinding;
import com.gahee.rss_v2.remoteSource.RemoteViewModel;
import com.gahee.rss_v2.ui.fragments.NasaFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding activityMainBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_main);

        RemoteViewModel remoteViewModel = new RemoteViewModel();
        if(savedInstanceState == null){
            remoteViewModel.fetchNasaDataFromRepo();
            fragmentTransactionHelper(NasaFragment.newInstance());
        }
//        remoteViewModel.fetchTimeDataFromRepo();
//        remoteViewModel.fetchWWFDataFromRepo();

    }


    private void fragmentTransactionHelper(final Fragment fragment)   {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_placeholder, fragment);
        transaction.commit();
    }

}
