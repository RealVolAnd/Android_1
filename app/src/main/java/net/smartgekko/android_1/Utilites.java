package net.smartgekko.android_1;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class Utilites {
    public static void showAlert(Context context, String msg) {
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.show();
    }

    public static void showAlertSnack(View view, Context context, String msg) {
        Snackbar.make(
                view,
                msg,
                Snackbar.LENGTH_LONG
        ).show();
    }

    public static void showInfo(Context context, String msg) {
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        toast.show();
    }
}
