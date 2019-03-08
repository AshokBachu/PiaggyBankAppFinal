package hexadots.in.piaggybankappfinal.viewpager;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothAdapter.LeScanCallback;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import hexadots.in.piaggybankappfinal.BluetoothUtils;
import hexadots.in.piaggybankappfinal.R;
import hexadots.in.piaggybankappfinal.Constants;
import hexadots.in.piaggybankappfinal.PiggyBankApplication;
import hexadots.in.piaggybankappfinal.R;
import hexadots.in.piaggybankappfinal.ToastUtils;
import hexadots.in.piaggybankappfinal.activities.PiggyBankSetupDetailsActivity;
import hexadots.in.piaggybankappfinal.activities.PiggyBankShowToyDevicesActivity;
import hexadots.in.piaggybankappfinal.bean.BlueToothDeviceState;
import hexadots.in.piaggybankappfinal.communication.CommunicationProtoCalHelper;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class ViewPagerAdapter extends PagerAdapter {
    public static final String TAG = "ViewPager";
    private BluetoothAdapter bluetoothAdapter;
    private String bluetoothDeviceName;
    private ArrayList<BlueToothDeviceState> bluetoothDevices;
    private Context context;
    private LeScanCallback leScanCallback;
    private MenuItem menuItem;
    private RelativeLayout progressBar;
    private ViewPager viewPager;
    private ArrayList<View> views = new ArrayList();

    public ViewPagerAdapter(MenuItem menuItem, BluetoothAdapter bluetoothAdapter, LeScanCallback leScanCallback, ViewPager viewPager, ArrayList<BlueToothDeviceState> bluetoothDevices, Context context, RelativeLayout progressBar) {
        this.menuItem = menuItem;
        this.progressBar = progressBar;
        this.context = context;
        this.bluetoothDevices = bluetoothDevices;
        this.bluetoothAdapter = bluetoothAdapter;
        this.leScanCallback = leScanCallback;
    }

    public Object instantiateItem(ViewGroup container, final int position) {
        String str;
        View view = ((Activity) context).getLayoutInflater().inflate(R.layout.device_item_show, null, false);
        container.addView(view);
        this.views.add(position, view);
        final RelativeLayout relativeLayoutDeviceImage = (RelativeLayout) view.findViewById(R.id.deviceImage);
        RelativeLayout relativeLayoutTopCircle = (RelativeLayout) view.findViewById(R.id.topCircle);
        TextView textViewDeviceName = (TextView) view.findViewById(R.id.deviceName);
        if (((BlueToothDeviceState) this.bluetoothDevices.get(position)).isMakeBlur()) {
            relativeLayoutTopCircle.setBackgroundResource(R.drawable.blur_piggy_background);
        } else {
            relativeLayoutTopCircle.setBackgroundResource(R.drawable.background_round);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(((BlueToothDeviceState) this.bluetoothDevices.get(position)).getBluetoothDevice().getName());
        if (stringBuilder.toString().equals("null")) {
            str = "No name found";
        } else {
            stringBuilder = new StringBuilder();
            stringBuilder.append("");
            stringBuilder.append(((BlueToothDeviceState) this.bluetoothDevices.get(position)).getBluetoothDevice().getName());
            str = stringBuilder.toString();
        }
        this.bluetoothDeviceName = str;
        if (this.bluetoothDeviceName.trim().equals("BT05")) {
            this.bluetoothDeviceName = "KLYA-BLUE";
        }
        if (this.bluetoothDeviceName.trim().equals(Constants.DEVICE_NAME)) {
            this.bluetoothDeviceName = "KLYA-WHITE";
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(this.bluetoothDeviceName);
        textViewDeviceName.setText(stringBuilder.toString());
        Button connectButton = (Button) view.findViewById(R.id.connectButton);
        relativeLayoutDeviceImage.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (PiggyBankShowToyDevicesActivity.selectedPosition == position) {
                    ViewPagerAdapter.this.bluetoothAdapter.stopLeScan(ViewPagerAdapter.this.leScanCallback);
                    try {
                        if (!BluetoothUtils.isBluetoothSupported(ViewPagerAdapter.this.context) || !BluetoothUtils.bluetoothButtonEnabled(ViewPagerAdapter.this.context)) {
                            ToastUtils.showToast(ViewPagerAdapter.this.progressBar, ViewPagerAdapter.this.context, "Please enable Bluetooth to connect");
                        } else if (!((BlueToothDeviceState) ViewPagerAdapter.this.bluetoothDevices.get(position)).isMakeBlur()) {
                            ViewPagerAdapter.this.menuItem.setVisible(false);
                            ViewPagerAdapter.this.performClickAction(position, ((BlueToothDeviceState) ViewPagerAdapter.this.bluetoothDevices.get(position)).getBluetoothDevice());
                        }
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }
            }
        });
        connectButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view1) {
                relativeLayoutDeviceImage.performClick();
            }
        });
        return view;
    }

    public void performClickAction(int position, final BluetoothDevice bluetoothDevice) {
        this.progressBar.setVisibility(View.VISIBLE);

        bluetoothDevice.connectGatt(this.context, false, new BluetoothGattCallback() {
            public void onServicesDiscovered(BluetoothGatt gatt, int status) {

                super.onServicesDiscovered(gatt, status);
                if (status != 0) {
                    Log.d("ViewPager", "onServicesDiscovered: Error");
                } else if (!((PiggyBankShowToyDevicesActivity) ViewPagerAdapter.this.context).isFromOnBackPressed()) {
                    if (((PiggyBankShowToyDevicesActivity) ViewPagerAdapter.this.context).isFromOnpause()) {
                        gatt.disconnect();
                        ((Activity) ViewPagerAdapter.this.context).finish();
                        return;
                    }
                    Log.d("ViewPager", "onServicesDiscovered: ");
                    BluetoothGattCharacteristic characteristic = gatt.getService(UUID.fromString(Constants.SERVICE_UUID)).getCharacteristic(UUID.fromString(Constants.CHARACTERISTIC_TOY_UUID));
                    String format = new SimpleDateFormat("ddMMyyyyhhmm").format(new Date());
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("onServicesDiscovered: ");
                    stringBuilder.append(CommunicationProtoCalHelper.setGoal("BuyCar", "100", format));
                    Log.d("ViewPager", stringBuilder.toString());
                    characteristic.setValue(CommunicationProtoCalHelper.showUserConnected().getBytes());
                    characteristic.setWriteType(2);
                    gatt.writeCharacteristic(characteristic);
                    gatt.setCharacteristicNotification(characteristic, true);
                    BluetoothGattDescriptor descriptor = characteristic.getDescriptor(UUID.fromString(Constants.DESCRIPTOR_CONFIG_UUID));
                    descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                    gatt.writeDescriptor(descriptor);
                    PiggyBankApplication piggyBankApplication = (PiggyBankApplication) ViewPagerAdapter.this.context.getApplicationContext();
                    piggyBankApplication.setBluetoothDevice(bluetoothDevice);
                    piggyBankApplication.setBluetoothGatt(gatt);
                    piggyBankApplication.setPiggyConnected(true);
                    piggyBankApplication.setBluetoothGattCharacteristic(characteristic);
                    if (!((PiggyBankShowToyDevicesActivity) ViewPagerAdapter.this.context).isFromOnBackPressed()) {
                        Log.d("ViewPager", "onServicesDiscovered: 1");
                        if (!((PiggyBankShowToyDevicesActivity) ViewPagerAdapter.this.context).isFromOnpause()) {
                            Log.d("ViewPager", "onServicesDiscovered: 2");
                            ViewPagerAdapter.this.callNextActivity(bluetoothDevice);
                        }
                    }
                    ((Activity) ViewPagerAdapter.this.context).finish();
                }
            }

            public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
                super.onCharacteristicRead(gatt, characteristic, status);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("onCharacteristicRead: ");
                stringBuilder.append(characteristic.getUuid());
                Log.d("ViewPager", stringBuilder.toString());
                readToyCharacteristic(characteristic);
            }

            public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
                super.onCharacteristicChanged(gatt, characteristic);
                Log.d("ViewPager", "onCharacteristicChanged: ");
                readToyCharacteristic(characteristic);
            }

            private void readToyCharacteristic(BluetoothGattCharacteristic characteristic) {
                Log.d("ViewPager", "readToyCharacteristic1: 0000ffe1-0000-1000-8000-00805f9b34fb");
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("readToyCharacteristic2: ");
                stringBuilder.append(characteristic.getUuid());
                Log.d("ViewPager", stringBuilder.toString());
                if (Constants.CHARACTERISTIC_TOY_UUID.equals(characteristic.getUuid().toString())) {
                    byte[] data = characteristic.getValue();
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("readCounterCharacteristic: ");
                    stringBuilder2.append(new String(data));
                    Log.d("ViewPager", stringBuilder2.toString());
                }
            }

            public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
                super.onConnectionStateChange(gatt, status, newState);
                if (newState == 2) {
                    Log.d("ViewPager", "Connected");
                    gatt.discoverServices();
                } else if (newState == 0) {
                    Log.d("ViewPager", "onConnectionStateChange: Disconnected");
                }
            }

            public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
                super.onCharacteristicWrite(gatt, characteristic, status);
                Log.d("ViewPager", "onCharacteristicWrite: ");
                readToyCharacteristic(characteristic);
            }

            public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
                super.onDescriptorWrite(gatt, descriptor, status);
                Log.d("ViewPager", "onDescriptorWrite: ");
                if (Constants.DESCRIPTOR_CONFIG_UUID.equals(descriptor.getUuid())) {
                    gatt.readCharacteristic(gatt.getService(UUID.fromString(Constants.SERVICE_UUID)).getCharacteristic(UUID.fromString(Constants.CHARACTERISTIC_TOY_UUID)));
                }
            }
        }).connect();
    }

    public void callNextActivity(BluetoothDevice bluetoothDevice) {
        Intent intentSetUpActivity = new Intent(this.context, PiggyBankSetupDetailsActivity.class);
        intentSetUpActivity.putExtra(Constants.CONST_DEVICE_NAME, this.bluetoothDeviceName);
        intentSetUpActivity.putExtra(Constants.CONST_DEVICE_ADDRES, bluetoothDevice.getAddress());
        ((Activity) this.context).startActivity(intentSetUpActivity);
    }

    public int getCount() {
        return this.bluetoothDevices.size();
    }

    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
