package com.unsoldriceball.quietmusicplayer;

import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class QMP_Utility {
    public static void ShowMessage(View v, String msg) {
        final Snackbar SB = Snackbar.make(v, msg, 1200);
        SB.show();
    }
}
