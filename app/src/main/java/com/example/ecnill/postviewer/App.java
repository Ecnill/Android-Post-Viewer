package com.example.ecnill.postviewer;

import android.app.Application;

import com.example.ecnill.postviewer.Utils.Connectivity;

/**
 * Created by ecnill on 23.3.17.
 */

public final class App extends Application {

    private final Connectivity connectivity = new Connectivity(this);

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public boolean isNetworkAvailable() {
        return connectivity.isNetworkAvailable();
    }

}
