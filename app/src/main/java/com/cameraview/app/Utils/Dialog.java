package com.cameraview.app.Utils;

import android.app.ProgressDialog;
import android.content.Context;

public class Dialog {
    public static ProgressDialog PROGRESS_DIALOG = null;

    public static void showdialog(Context context, String message)
    {
        PROGRESS_DIALOG = new ProgressDialog(context);
        PROGRESS_DIALOG.show();
        PROGRESS_DIALOG.setMessage(message);
    }
    public static void dissmissdialog() {
        PROGRESS_DIALOG.dismiss();

    }
}
