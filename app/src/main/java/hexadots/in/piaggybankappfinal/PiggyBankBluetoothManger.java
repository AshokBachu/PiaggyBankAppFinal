package hexadots.in.piaggybankappfinal;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Intent;

public class PiggyBankBluetoothManger {
    private Activity activity;

    public PiggyBankBluetoothManger(Activity activity) {
        this.activity = activity;
    }

    public BluetoothAdapter getBluetoothAdapter() {
        return ((BluetoothManager) this.activity.getSystemService("bluetooth")).getAdapter();
    }

    public boolean hasBluetoothFeature() {
        return this.activity.getPackageManager().hasSystemFeature("android.hardware.bluetooth_le");
    }

    public boolean isBluetoothSupported() {
        return getBluetoothAdapter() != null;
    }

    public boolean isBluetoothEnable(BluetoothAdapter bluetoothAdapter) {
        return bluetoothAdapter.isEnabled();
    }

    public void enableBluetooth(BluetoothAdapter bluetoothAdapter) {
        this.activity.startActivityForResult(new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE"), 1);
    }
}
