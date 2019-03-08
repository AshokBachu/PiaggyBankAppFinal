package hexadots.in.piaggybankappfinal;

import android.app.Application;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.util.Log;

import hexadots.in.piaggybankappfinal.bean.PiggyBankData;
import java.util.ArrayList;

public class PiggyBankApplication extends Application
{

    public static final String TAG="BluetoothGatt";
    private BluetoothDevice bluetoothDevice;
    private ArrayList<BluetoothDevice> bluetoothDevicesList;
    private BluetoothGatt bluetoothGatt;
    private BluetoothGattCharacteristic bluetoothGattCharacteristic;
    private String deviceId;
    private boolean isPiggyConnected;
    private PiggyBankData piggyBankData;

    public boolean isPiggyConnected() {
        return this.isPiggyConnected;
    }

    public void setPiggyConnected(boolean piggyConnected) {
        this.isPiggyConnected = piggyConnected;
    }

    public BluetoothGatt getBluetoothGatt() {
        Log.d(TAG, "getBluetoothGatt: ");
        return this.bluetoothGatt;
    }

    public void setBluetoothGatt(BluetoothGatt bluetoothGatt) {
        Log.d(TAG, "setBluetoothGatt: ");
        this.bluetoothGatt = bluetoothGatt;
    }

    public BluetoothGattCharacteristic getBluetoothGattCharacteristic() {
        return this.bluetoothGattCharacteristic;
    }

    public void setBluetoothGattCharacteristic(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        this.bluetoothGattCharacteristic = bluetoothGattCharacteristic;
    }

    public BluetoothDevice getBluetoothDevice() {
        return this.bluetoothDevice;
    }

    public void setBluetoothDevice(BluetoothDevice bluetoothDevice) {
        this.bluetoothDevice = bluetoothDevice;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public ArrayList<BluetoothDevice> getBluetoothDevicesList() {
        return this.bluetoothDevicesList;
    }

    public void setBluetoothDevicesList(ArrayList<BluetoothDevice> bluetoothDevicesList) {
        this.bluetoothDevicesList = bluetoothDevicesList;
    }

    public PiggyBankData getPiggyBankData() {
        return this.piggyBankData;
    }

    public void setPiggyBankData(PiggyBankData piggyBankData) {
        this.piggyBankData = piggyBankData;
    }
}
