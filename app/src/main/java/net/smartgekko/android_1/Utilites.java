package net.smartgekko.android_1;

import android.content.Context;
import android.widget.Toast;

public class Utilites {
    public static void showAlert(Context context, String msg) {
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.show();
    }

    public static void showInfo(Context context, String msg) {
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        toast.show();
    }
}
