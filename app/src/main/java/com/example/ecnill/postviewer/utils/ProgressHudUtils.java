package com.example.ecnill.postviewer.utils;

import android.app.Activity;

import com.example.ecnill.postviewer.R;
import com.kaopiz.kprogresshud.KProgressHUD;

import static com.kaopiz.kprogresshud.KProgressHUD.create;

/**
 * Created by ecnill on 23.3.17.
 */

public abstract class ProgressHudUtils {

    public static KProgressHUD createProgressIdentifier(Activity activity){
        KProgressHUD progressHUD = create(activity);
        progressHUD.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(activity.getResources().getString(R.string.load_message))
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);
        return progressHUD;
    }
}
