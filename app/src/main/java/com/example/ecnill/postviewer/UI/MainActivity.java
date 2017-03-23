package com.example.ecnill.postviewer.UI;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.ecnill.postviewer.R;
import com.example.ecnill.postviewer.UI.Main.PostListFragment;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public final class MainActivity extends AppCompatActivity implements FragmentChangeListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
        if (savedInstanceState == null) {
            setContentView(R.layout.activity_main);
        }
        if (!getResources().getBoolean(R.bool.two_pane_mode)) {
            PostListFragment listFragment = new PostListFragment();
            replaceFragment(R.id.fragment_container, listFragment, false);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }

    @Override
    public void replaceFragment(int containerId, Fragment fragment, boolean addToBackStack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(containerId, fragment, fragment.getClass().getSimpleName());
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
        }
        fragmentTransaction.commit();
        getSupportFragmentManager().executePendingTransactions();
    }

    /**
     * Inject into Context calligraphy library for using custom fonts.
     */
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
