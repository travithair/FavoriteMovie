package com.tugasmobile.searchmovieapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.tugasmobile.searchmovieapplication.fragment.FavoriteFragment;
import com.tugasmobile.searchmovieapplication.fragment.NowPlayingFragment;
import com.tugasmobile.searchmovieapplication.fragment.SearchFragment;
import com.tugasmobile.searchmovieapplication.fragment.UpComingFragment;
import com.tugasmobile.searchmovieapplication.notification.NotificationSetting;

public class HomeActivity extends AppCompatActivity implements
        NowPlayingFragment.OnFragmentInteractionListener,
        SearchFragment.OnFragmentInteractionListener,
        UpComingFragment.OnFragmentInteractionListener,
        FavoriteFragment.OnFragmentInteractionListener {

    private ActionBar toolbar;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment fragment;

            switch (item.getItemId()) {
                case R.id.navigation_now_playing:
                    toolbar.setTitle(getString(R.string.title_now_playing));
                    fragment = new NowPlayingFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_up_coming:
                    toolbar.setTitle(getString(R.string.title_up_coming));
                    fragment = new UpComingFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_search:
                    toolbar.setTitle(getString(R.string.title_search));
                    fragment = new SearchFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_favorite:
                    toolbar.setTitle(getString(R.string.favorit));
                    fragment = new FavoriteFragment();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        toolbar = getSupportActionBar();

        if (savedInstanceState == null) {
            toolbar.setTitle(getString(R.string.title_now_playing));
            NowPlayingFragment nowPlayingFragment = new NowPlayingFragment();
            loadFragment(nowPlayingFragment);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.setting) {
            Intent intent = new Intent(this, NotificationSetting.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
