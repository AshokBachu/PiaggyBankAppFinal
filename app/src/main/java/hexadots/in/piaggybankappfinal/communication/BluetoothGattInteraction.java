package hexadots.in.piaggybankappfinal.communication;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.content.Context;
import android.util.Log;
import hexadots.in.piaggybankappfinal.PiggyBankApplication;

public class BluetoothGattInteraction {
    public static final String TAG = "GattInteraction";

    public void sendData(Context context, String sendText) {
        try {
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("sendData: ");
            stringBuilder.append(sendText);
            Log.d(str, stringBuilder.toString());
            PiggyBankApplication piggyBankApplication = (PiggyBankApplication) context.getApplicationContext();
            String str2 = TAG;
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("sendData: ");
            stringBuilder2.append(piggyBankApplication.isPiggyConnected());
            Log.d(str2, stringBuilder2.toString());
            BluetoothGatt bluetoothGatt = piggyBankApplication.getBluetoothGatt();
            BluetoothGattCharacteristic bluetoothGattCharacteristic = piggyBankApplication.getBluetoothGattCharacteristic();
            bluetoothGattCharacteristic.setValue(sendText.getBytes());
            bluetoothGattCharacteristic.setWriteType(2);
            bluetoothGatt.writeCharacteristic(bluetoothGattCharacteristic);
            bluetoothGatt.setCharacteristicNotification(bluetoothGattCharacteristic, true);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
