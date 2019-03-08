package hexadots.in.piaggybankappfinal;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;

public class BluetoothUtils {
    public static boolean isBluetoothSupported(Context context) {
        if (BluetoothAdapter.getDefaultAdapter() != null) {
            return true;
        }
        ToastUtils.showToast(null, context, "Device does not support Bluetooth");
        return false;
    }

    public static boolean bluetoothButtonEnabled(Context context) {
        if (BluetoothAdapter.getDefaultAdapter().isEnabled()) {
            return true;
        }
        return false;
    }

    public static void enableBlutooth(Context context) {
        ((Activity) context).startActivityForResult(new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE"), 1);
    }
}
