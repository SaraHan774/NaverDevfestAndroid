package com.gahee.rss_v2;

import android.os.Bundle;

import com.gahee.rss_v2.databinding.ActivityMainBinding;
import com.gahee.rss_v2.myFeed.FeedFragment;
import com.gahee.rss_v2.remoteData.RemoteViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding activityMainBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_main);

        activityMainBinding.navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        fragmentTransactionHelper(new HomeFragment());
        RemoteViewModel remoteViewModel = new RemoteViewModel();
        remoteViewModel.fetchDataFromRemote();

        remoteViewModel.fetchYTDataFromRemote();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragmentTransactionHelper(new HomeFragment());
                    return true;
                case R.id.navigation_dashboard:
                    fragmentTransactionHelper(new FeedFragment());
                    return true;
            }
            return false;
        }
    };

    private void fragmentTransactionHelper(final Fragment fragment)   {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_placeholder, fragment);
        transaction.commit();
    }

}
