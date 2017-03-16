package com.example.ecnill.postviewer.Utils;

import android.support.v4.app.Fragment;

/**
 * Created by ecnill on 14.3.17.
 */

public interface FragmentChangeListener {

    void replaceFragment(int containerId, Fragment fragment, boolean addToBackStack);

}
