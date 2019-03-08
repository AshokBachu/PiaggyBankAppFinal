package hexadots.in.piaggybankappfinal;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

public class CheckPermissions extends Activity {
    public static final int PERMISSION_REQUEST_CODE = 1001;
    private Activity activity;
    AlertDialog alertDialog;

    /* renamed from: hexadots.in.piaggybankappfinal.CheckPermissions$1 */
    class C03811 implements OnClickListener {
        C03811() {
        }

        public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.dismiss();
            CheckPermissions.this.requestPermission();
        }
    }

    public CheckPermissions(Activity activity) {
        this.activity = activity;
    }

    public boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(this.activity, "android.permission.ACCESS_FINE_LOCATION") == 0) {
            return true;
        }
        return false;
    }

    public void requestPermission() {
        ActivityCompat.requestPermissions(this.activity, new String[]{"android.permission.ACCESS_FINE_LOCATION"}, 1001);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1001) {
            if (grantResults.length > 0) {
                int i = grantResults[0];
            }
        }
    }

    public void showAlertDialog(final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Enable Location");
        builder.setMessage("Location allows us to find near by KLYA devices. ");
        builder.setIcon(R.drawable.ic_location_on_black_24dp);
        builder.setPositiveButton("Ok", new C03811());
        builder.setNegativeButton("Cancel", new OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                activity.finish();
            }
        });
        builder.setCancelable(false);
        this.alertDialog = builder.create();
        this.alertDialog.show();
    }
}
