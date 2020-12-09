package com.fyp.goodsmanagenmentsystem;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    boolean doubleBackToExitPressedOnce=false;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    boolean isdarkmodeon=false;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            sharedPreferences
                    =getSharedPreferences(
                    "sharedPrefs", MODE_PRIVATE);
            editor
                    = sharedPreferences.edit();
            isdarkmodeon
                    = sharedPreferences
                    .getBoolean(
                            "isDarkModeOn", false);
            editor
                    = sharedPreferences.edit();
            isdarkmodeon
                    = sharedPreferences
                    .getBoolean(
                            "isDarkModeOn", false);
            // When user reopens the app
            // after applying dark/light mode
            if (isdarkmodeon) {
                AppCompatDelegate
                        .setDefaultNightMode(
                                AppCompatDelegate
                                        .MODE_NIGHT_YES);
            } else {
                AppCompatDelegate
                        .setDefaultNightMode(
                                AppCompatDelegate
                                        .MODE_NIGHT_NO);
            }
            initViews();
        }
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
//    private boolean loadFragment(Fragment fragment) {
//        //switching fragment
//        if (fragment != null) {
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.fragment_container, fragment)
//                    .commit();
//            return true;
//        }
//        return false;
//    }
public void changeFragment(Fragment fragment, String tagFragmentName) {

    FragmentManager mFragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

    Fragment currentFragment = mFragmentManager.getPrimaryNavigationFragment();
    if (currentFragment != null) {
        fragmentTransaction.hide(currentFragment);
    }

    Fragment fragmentTemp = mFragmentManager.findFragmentByTag(tagFragmentName);
    if (fragmentTemp == null) {
        fragmentTemp = fragment;
        fragmentTransaction.add(R.id.fragment_container, fragmentTemp, tagFragmentName);
    } else {
        fragmentTransaction.show(fragmentTemp);
    }
    fragmentTransaction.setPrimaryNavigationFragment(fragmentTemp);
    fragmentTransaction.setReorderingAllowed(true);
    fragmentTransaction.commitNowAllowingStateLoss();
}
    private void initViews() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.navigation_home:
                                changeFragment(new HomeFragment(), HomeFragment.class
                                        .getSimpleName());
                              //  toggleViews(true, "");
                                break;
                            case R.id.navigation_search:
                                changeFragment(new SearchFragment(), SearchFragment.class
                                        .getSimpleName());
                                //toggleViews(false, "Favorites");
                                break;
                            case R.id.navigation_account:
                                changeFragment(new AccountFragment(), AccountFragment.class.getSimpleName());
                               // toggleViews(false, "Venues");
                                break;
                            case R.id.navigation_mydeal:
                                changeFragment(new NewDealFragment(), NewDealFragment.class
                                        .getSimpleName());
                                //toggleViews(false, "Profile");
                                break;
                            case R.id.navigation_notifications:
                                changeFragment(new NotificationsFragment(), NotificationsFragment.class
                                        .getSimpleName());
                                //toggleViews(false, "Profile");
                                break;
                        }
                        return true;
                    }
                });

        //Manually displaying the first fragment - one time only
        changeFragment(new HomeFragment(), HomeFragment.class
                .getSimpleName());

    }
}