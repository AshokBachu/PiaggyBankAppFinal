package hexadots.in.piaggybankappfinal;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class DialogUtils {
    public static Dialog showDialogUtils(Context context, String message, int milliseconds) {
        Dialog dialog = new Dialog(context);
        View view = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.update_value_progress_bar, null, false);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("showDialogUtils: ");
        stringBuilder.append(milliseconds / 1000);
        Log.d("Check", stringBuilder.toString());
        showProgressBar(dialog, message, view, milliseconds / 1000);
        dialog.setContentView(view);
        dialog.setCancelable(false);
        try {
            dialog.show();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return dialog;
    }

    public static void showProgressBar(Dialog dialog, String synkingData, View view, int milliseconds) {
        final int[] progressBarValue = new int[]{0};
        final Handler handler = new Handler();
        ProgressBar Progressbar = (ProgressBar) view.findViewById(R.id.progressBar1);
        TextView ShowText = (TextView) view.findViewById(R.id.textView1);
        Progressbar.setMax(milliseconds);
        final int i = milliseconds;
        final Dialog dialog2 = dialog;
        final ProgressBar progressBar = Progressbar;
        final TextView textView = ShowText;
        final String str = synkingData;
        new Thread(new Runnable() {

            /* renamed from: hexadots.in.piaggybankappfinal.DialogUtils$1$1 */
            class C03831 implements Runnable {
                C03831() {
                }

                public void run() {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(progressBarValue[0]);
                    stringBuilder.append("run: ");
                    stringBuilder.append(progressBar.getMax());
                    Log.d("DATA", stringBuilder.toString());
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("run: ");
                    stringBuilder.append(progressBarValue[0] / progressBar.getMax());
                    Log.d("DATA", stringBuilder.toString());
                    double res = (((double) progressBarValue[0]) / ((double) progressBar.getMax())) * 100.0d;
                    progressBar.setProgress(progressBarValue[0]);
                    StringBuilder htmlString = new StringBuilder();
                    htmlString.append("<font color='#9f3046' size='30' > <b>");
                    htmlString.append((int) res);
                    htmlString.append("% </b></font>");
                    //htmlString = htmlString.toString();
                    //TextView textView = textView;
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append(str);
                    stringBuilder2.append(" ");
                    stringBuilder2.append(htmlString);
                    textView.setText(Html.fromHtml(stringBuilder2.toString()));
                }
            }

            public void run() {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("run:-----> ");
                stringBuilder.append(i / 1000);
                Log.d("", stringBuilder.toString());
                if (progressBarValue[0] == i) {
                    dialog2.dismiss();
                } else if (progressBarValue[0] > i) {
                    dialog2.dismiss();
                } else {
                    while (progressBarValue[0] < i) {
                        int[] iArr = progressBarValue;
                        iArr[0] = iArr[0] + 1;
                        handler.post(new C03831());
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }
}
