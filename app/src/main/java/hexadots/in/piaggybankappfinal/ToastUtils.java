package hexadots.in.piaggybankappfinal;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

public class ToastUtils {
    public static void showToast(View view, Context context, String errorMessage) {
        Toast toast = Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT);
        toast.setGravity(49, 0, 500);
        toast.show();
    }

    public static void showDisplayMessage(View view, Context context, String errorMessage, boolean isfromToast, boolean isFromError) {
        if (isfromToast && view == null) {
            Toast toast = Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT);
            toast.setGravity(49, 0, 500);
            toast.show();
        }
        if (!isfromToast) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("");
            stringBuilder.append(errorMessage);
            Snackbar snackbar = Snackbar.make(view, stringBuilder.toString(), -1);
            snackbar.setActionTextColor(-1);
            snackbar.show();
            View sbView = snackbar.getView();
            if (isFromError) {
                sbView.setBackgroundColor(Color.parseColor("#FF0000"));
            } else {
                sbView.setBackgroundColor(Color.parseColor("#000000"));
            }
        }
    }
}
