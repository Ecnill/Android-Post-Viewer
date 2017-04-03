package com.example.ecnill.postviewer;

import android.app.Application;

import com.example.ecnill.postviewer.network.NetworkService;
import com.example.ecnill.postviewer.utils.Connectivity;

import lombok.Getter;

/**
 * Created by ecnill on 23.3.17.
 */

public final class App extends Application {

    @Getter private final NetworkService networkService = new NetworkService();
    private final Connectivity connectivity = new Connectivity(this);

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public boolean isNetworkAvailable() {
        return connectivity.isNetworkAvailable();
    }

}
