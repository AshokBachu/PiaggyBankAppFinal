package hexadots.in.piaggybankappfinal.bean;

import android.bluetooth.BluetoothDevice;

public class BlueToothDeviceState {
    private BluetoothDevice bluetoothDevice;
    private boolean makeBlur;

    public boolean isMakeBlur() {
        return this.makeBlur;
    }

    public void setMakeBlur(boolean makeBlur) {
        this.makeBlur = makeBlur;
    }

    public BluetoothDevice getBluetoothDevice() {
        return this.bluetoothDevice;
    }

    public void setBluetoothDevice(BluetoothDevice bluetoothDevice) {
        this.bluetoothDevice = bluetoothDevice;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(this.bluetoothDevice.getAddress());
        return stringBuilder.toString();
    }
}
