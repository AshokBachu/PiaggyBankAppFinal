package hexadots.in.piaggybankappfinal.activities;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothAdapter.LeScanCallback;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.skyfishjy.library.RippleBackground;
import hexadots.in.piaggybankappfinal.R;
import hexadots.in.piaggybankappfinal.CheckPermissions;
import hexadots.in.piaggybankappfinal.Constants;
import hexadots.in.piaggybankappfinal.PiggyBankApplication;
import hexadots.in.piaggybankappfinal.PiggyBankBluetoothManger;
import hexadots.in.piaggybankappfinal.ToastUtils;
import hexadots.in.piaggybankappfinal.bean.Account;
import hexadots.in.piaggybankappfinal.communication.BluetoothGattInteraction;
import hexadots.in.piaggybankappfinal.communication.CommunicationProtoCalHelper;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

public class PiggyBankShowSearchScreenTransferGoal extends AppCompatActivity {
    public static final String TAG = "PiggyBankShow";
    public static boolean mScanning = false;
    private String accountBalance;
    private String amountToTransfer;
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothDevice bluetoothDeviceToTransfer;
    private boolean callDevice;
    private TextView descriptionText;
    private String goalName;
    Handler handlerStopUi;
    private ImageView imageView;
    boolean isDeviceScanning = true;
    boolean isFromOnBackPressed;
    private boolean isGoalSet;
    private boolean isSetup;
    private boolean isTransfer;
    boolean isfromEnable;
    private TextView noDevicesFoundTv;
    private PiggyBankBluetoothManger piggyBankBluetoothManger;
    private String piggyDeviceId;
    private RippleBackground rippleBackground;
    LeScanCallback scanCallback = new C04493();
    private TextView searchForPiggysText;
    Runnable stopRunnable = new C04504();
    private boolean stopScanning;

    /* renamed from: hexadots.in.piaggybankappfinal.activities.PiggyBankShowSearchScreenTransferGoal$1 */
    class C04411 implements OnClickListener {
        C04411() {
        }

        public void onClick(View view) {
            PiggyBankShowSearchScreenTransferGoal.this.isfromEnable = false;
            if (!PiggyBankShowSearchScreenTransferGoal.mScanning) {
                PiggyBankShowSearchScreenTransferGoal.mScanning = true;
                PiggyBankShowSearchScreenTransferGoal.this.searchForPiggysText.setVisibility(0);
                PiggyBankShowSearchScreenTransferGoal.this.descriptionText.setVisibility(0);
                PiggyBankShowSearchScreenTransferGoal.this.noDevicesFoundTv.setVisibility(8);
                PiggyBankShowSearchScreenTransferGoal.this.getSupportActionBar().setTitle((CharSequence) "Searching...");
                PiggyBankShowSearchScreenTransferGoal.this.imageView.setImageResource(R.drawable.piggy_bank);
                Log.d("PiggyBankShow", "Searching Process ");
                PiggyBankShowSearchScreenTransferGoal.this.rippleBackground.startRippleAnimation();
                PiggyBankShowSearchScreenTransferGoal.this.checkNearDevicesScan();
                Log.d("PiggyBankShow", "Center image has clicked");
            }
        }
    }

    /* renamed from: hexadots.in.piaggybankappfinal.activities.PiggyBankShowSearchScreenTransferGoal$3 */
    class C04493 implements LeScanCallback {
        C04493() {
        }

        public void onLeScan(final BluetoothDevice bluetoothDevice, int i, byte[] bytes) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(bluetoothDevice.getName());
            stringBuilder.append("<--onLeScan-->");
            stringBuilder.append(bluetoothDevice.getAddress());
            Log.d("PiggyBankShow", stringBuilder.toString());
            if (!PiggyBankShowSearchScreenTransferGoal.this.stopScanning) {
                PiggyBankShowSearchScreenTransferGoal.this.stopScanning = true;
                PiggyBankShowSearchScreenTransferGoal.this.handlerStopUi = new Handler();
                PiggyBankShowSearchScreenTransferGoal.this.handlerStopUi.postDelayed(PiggyBankShowSearchScreenTransferGoal.this.stopRunnable, Constants.SCAN_PERIOD);
            }
            Handler handler = new Handler();
            if (PiggyBankShowSearchScreenTransferGoal.this.piggyDeviceId.equals(bluetoothDevice.getAddress()) && PiggyBankShowSearchScreenTransferGoal.this.isDeviceScanning) {
                PiggyBankShowSearchScreenTransferGoal.this.handlerStopUi.removeCallbacks(PiggyBankShowSearchScreenTransferGoal.this.stopRunnable);
                PiggyBankShowSearchScreenTransferGoal.this.isDeviceScanning = false;
                handler.postDelayed(new Runnable() {
                    public void run() {
                        Log.d("PiggyBankShow", "onLeScan: Device Found ");
                        PiggyBankShowSearchScreenTransferGoal.this.bluetoothAdapter.stopLeScan(PiggyBankShowSearchScreenTransferGoal.this.scanCallback);
                        PiggyBankApplication piggyBankApplication = (PiggyBankApplication) PiggyBankShowSearchScreenTransferGoal.this.getApplicationContext();
                        if (piggyBankApplication.isPiggyConnected()) {
                            PiggyBankShowSearchScreenTransferGoal.this.bluetoothDeviceToTransfer = piggyBankApplication.getBluetoothDevice();
                        } else {
                            PiggyBankShowSearchScreenTransferGoal.this.bluetoothDeviceToTransfer = bluetoothDevice;
                        }
                        PiggyBankShowSearchScreenTransferGoal.this.checkNear();
                    }
                }, 3000);
            }
        }
    }

    /* renamed from: hexadots.in.piaggybankappfinal.activities.PiggyBankShowSearchScreenTransferGoal$4 */
    class C04504 implements Runnable {
        C04504() {
        }

        public void run() {
            PiggyBankShowSearchScreenTransferGoal.this.showUiDeviceDisconnected();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PiggyBankHomeNavDrawer.openPasscode = false;
        setContentView((int) R.layout.activity_piggy_bank_searching_devices);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initSearchScreen();
    }

    protected void onResume() {
        super.onResume();
        if (PiggyBankHomeNavDrawer.openPasscode) {
            if (mScanning && this.bluetoothAdapter != null) {
                this.bluetoothAdapter.stopLeScan(this.scanCallback);
            }
            finish();
            return;
        }
        enableBluetooth();
    }

    public void initSearchScreen() {
        String fromWhere = getIntent().getStringExtra(Constants.isFrom);
        this.accountBalance = getIntent().getStringExtra(Constants.CONST_PIGGY_BALANCE);
        this.amountToTransfer = getIntent().getStringExtra(Constants.CONST_AMOUNT_TO_TRANSFER_KEY);
        this.searchForPiggysText = (TextView) findViewById(R.id.searchForPiggysText);
        this.descriptionText = (TextView) findViewById(R.id.description_text);
        PiggyBankHomeNavDrawer.openPasscode = false;
        if (fromWhere.equals(Constants.isFromGoal)) {
            this.isGoalSet = true;
            this.isTransfer = false;
            this.isSetup = false;
            this.goalName = getIntent().getStringExtra(Constants.GOAL_NAME);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("");
            stringBuilder.append(getIntent().getStringExtra(Constants.CONST_AMOUNT_TO_TRANSFER_KEY));
            this.amountToTransfer = stringBuilder.toString();
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("onCreate: ");
            stringBuilder2.append(this.amountToTransfer);
            Log.d("PiggyBankShow", stringBuilder2.toString());
            Log.d("PiggyBankShow", "onCreate: goal");
            StringBuilder htmlString = new StringBuilder();
            htmlString.append("<font color='#9f3046'>");
            htmlString.append(getDeviceName());
            htmlString.append("</font>");
            //htmlString = htmlString.toString();
            TextView textView = this.searchForPiggysText;
            StringBuilder stringBuilder3 = new StringBuilder();
            stringBuilder3.append("Searching for ");
            stringBuilder3.append(htmlString);
            stringBuilder3.append(" Piggy");
            textView.setText(Html.fromHtml(stringBuilder3.toString()));
        }
        if (fromWhere.equals(Constants.isFromTransfer)) {
            this.isGoalSet = false;
            this.isTransfer = true;
            this.isSetup = false;
            Log.d("PiggyBankShow", "onCreate: transfer");
            StringBuilder htmlString2 = new StringBuilder();
            htmlString2.append("<font color='#9f3046'>");
            htmlString2.append(getDeviceName());
            htmlString2.append("</font>");
            //htmlString2 = htmlString2.toString();
            TextView textView2 = this.searchForPiggysText;
            StringBuilder stringBuilder4 = new StringBuilder();
            stringBuilder4.append("Searching for ");
            stringBuilder4.append(htmlString2);
            stringBuilder4.append(" Piggy");
            textView2.setText(Html.fromHtml(stringBuilder4.toString()));
        }
        this.noDevicesFoundTv = (TextView) findViewById(R.id.nodevicesFoundTv);
        this.rippleBackground = (RippleBackground) findViewById(R.id.content);
        this.imageView = (ImageView) findViewById(R.id.centerImage);
        PiggyBankApplication piggyBankApplication = (PiggyBankApplication) getApplicationContext();
        this.imageView.setOnClickListener(new C04411());
    }

    public void checkNearDevicesScan() {
        this.bluetoothAdapter = new PiggyBankBluetoothManger(this).getBluetoothAdapter();
        this.bluetoothAdapter.startLeScan(this.scanCallback);
    }

    protected void onDestroy() {
        super.onDestroy();
        mScanning = false;
    }

    protected void onPause() {
        super.onPause();
        if (this.isFromOnBackPressed) {
            PiggyBankHomeNavDrawer.openPasscode = false;
        } else if (!this.isfromEnable) {
            this.isfromEnable = false;
            PiggyBankHomeNavDrawer.openPasscode = true;
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
        this.isFromOnBackPressed = true;
        if (this.bluetoothAdapter != null) {
            this.bluetoothAdapter.stopLeScan(this.scanCallback);
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 16908332) {
            this.isFromOnBackPressed = true;
            if (this.bluetoothAdapter != null) {
                this.bluetoothAdapter.stopLeScan(this.scanCallback);
            }
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void enableBluetooth() {
        this.isfromEnable = true;
        this.piggyBankBluetoothManger = new PiggyBankBluetoothManger(this);
        if (this.piggyBankBluetoothManger.hasBluetoothFeature() && this.piggyBankBluetoothManger.isBluetoothSupported()) {
            BluetoothAdapter bluetoothAdapter = this.piggyBankBluetoothManger.getBluetoothAdapter();
            if (this.piggyBankBluetoothManger.isBluetoothEnable(bluetoothAdapter)) {
                checkPermission();
            } else {
                this.piggyBankBluetoothManger.enableBluetooth(bluetoothAdapter);
                this.isfromEnable = true;
            }
            return;
        }
        ToastUtils.showToast(this.descriptionText, this, "Bluetooth functionality does not support this Device");
    }

    public void checkPermission() {
        CheckPermissions checkPermissions = new CheckPermissions(this);
        if (checkPermissions.checkPermission()) {
            this.imageView.performClick();
            return;
        }
        this.isfromEnable = true;
        checkPermissions.showAlertDialog(this);
    }

    public String getDeviceName() {
        Map<String, Account> accountHashMap = ((PiggyBankApplication) getApplicationContext()).getPiggyBankData().getAccounts();
        String piggyDeviceName = "";
        Iterator it = new ArrayList(accountHashMap.keySet()).iterator();
        while (it.hasNext()) {
            Account account = (Account) accountHashMap.get((String) it.next());
            if (account.getAccountType().equals(Constants.ACCOUNT_TYPE_CHILD)) {
                piggyDeviceName = account.getPiggyDetails().getPiggyName();
                this.piggyDeviceId = account.getPiggyDetails().getDeviceId();
                return piggyDeviceName;
            }
        }
        return piggyDeviceName;
    }

    public void checkNear() {
        final PiggyBankApplication piggyBankApplication = (PiggyBankApplication) getApplicationContext();
        this.bluetoothDeviceToTransfer.connectGatt(this, false, new BluetoothGattCallback() {

            /* renamed from: hexadots.in.piaggybankappfinal.activities.PiggyBankShowSearchScreenTransferGoal$2$2 */
            class C04442 implements Runnable {
                C04442() {
                }

                public void run() {
                    PiggyBankShowSearchScreenTransferGoal.this.showUiDeviceDisconnected();
                }
            }

            /* renamed from: hexadots.in.piaggybankappfinal.activities.PiggyBankShowSearchScreenTransferGoal$2$3 */
            class C04463 implements Runnable {

                /* renamed from: hexadots.in.piaggybankappfinal.activities.PiggyBankShowSearchScreenTransferGoal$2$3$1 */
                class C04451 implements Runnable {
                    C04451() {
                    }

                    public void run() {
                        if (PiggyBankShowSearchScreenTransferGoal.this.isTransfer) {
                            String amount = PiggyBankShowSearchScreenTransferGoal.this.getIntent().getStringExtra(Constants.CONST_AMOUNT_TO_TRANSFER_KEY);
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("onConnectionStateChange: isTransfer");
                            stringBuilder.append(amount);
                            Log.d("PiggyBankShow", stringBuilder.toString());
                            Intent intent = new Intent(PiggyBankShowSearchScreenTransferGoal.this, PiggyBankSelectAmountActivity.class);
                            intent.putExtra(Constants.CONST_AMOUNT_TO_TRANSFER_KEY, amount);
                            PiggyBankShowSearchScreenTransferGoal.this.startActivity(intent);
                            PiggyBankShowSearchScreenTransferGoal.this.finish();
                        }
                        if (PiggyBankShowSearchScreenTransferGoal.this.isGoalSet) {
                            Log.d("PiggyBankShow", "onConnectionStateChange: isGoalSet");
                            PiggyBankShowSearchScreenTransferGoal.this.startActivity(new Intent(PiggyBankShowSearchScreenTransferGoal.this, PiggyBankTransferMoneySuccessScreen.class));
                            PiggyBankShowSearchScreenTransferGoal.this.finish();
                        }
                    }
                }

                C04463() {
                }

                public void run() {
                    new Handler().postDelayed(new C04451(), 100);
                }
            }

            public void onConnectionStateChange(final BluetoothGatt gatt, int status, int newState) {
                super.onConnectionStateChange(gatt, status, newState);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(status);
                stringBuilder.append("<--onConnectionStateChange: newState-->");
                stringBuilder.append(newState);
                Log.d("PiggyBankShow", stringBuilder.toString());
                if (newState == 2) {
                    Log.d("PiggyBankShow", "onConnectionStateChange: Connected");
                    if (piggyBankApplication.isPiggyConnected()) {
                        PiggyBankShowSearchScreenTransferGoal.this.callNewActivity();
                    } else {
                        PiggyBankShowSearchScreenTransferGoal.this.runOnUiThread(new Runnable() {

                            /* renamed from: hexadots.in.piaggybankappfinal.activities.PiggyBankShowSearchScreenTransferGoal$2$1$1 */
                            class C04421 implements Runnable {
                                C04421() {
                                }

                                public void run() {
                                    gatt.discoverServices();
                                }
                            }

                            public void run() {
                                new Handler().postDelayed(new C04421(), 1000);
                            }
                        });
                    }
                }
                if (newState == 0) {
                    Log.d("PiggyBankShow", "onConnectionStateChange: DisConnected");
                    PiggyBankShowSearchScreenTransferGoal.this.callDevice = true;
                    piggyBankApplication.setPiggyConnected(false);
                    PiggyBankShowSearchScreenTransferGoal.this.runOnUiThread(new Thread(new C04442()));
                }
            }

            public void onServicesDiscovered(BluetoothGatt gatt, int status) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("onServicesDiscovered: ");
                stringBuilder.append(status);
                Log.d("PiggyBankShow", stringBuilder.toString());
                super.onServicesDiscovered(gatt, status);
                if (status != 0) {
                    Log.d("PiggyBankShow", "onServicesDiscovered: Error");
                    return;
                }
                Log.d("PiggyBankShow", "onServicesDiscovered: ");
                BluetoothGattCharacteristic characteristic = gatt.getService(UUID.fromString(Constants.SERVICE_UUID)).getCharacteristic(UUID.fromString(Constants.CHARACTERISTIC_TOY_UUID));
                String format = new SimpleDateFormat("ddMMyyyyhhmm").format(new Date());
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("onServicesDiscovered: ");
                stringBuilder2.append(CommunicationProtoCalHelper.setGoal("BuyCar", "100", format));
                Log.d("PiggyBankShow", stringBuilder2.toString());
                PiggyBankApplication piggyBankApplication = (PiggyBankApplication) PiggyBankShowSearchScreenTransferGoal.this.getApplicationContext();
                piggyBankApplication.setBluetoothDevice(PiggyBankShowSearchScreenTransferGoal.this.bluetoothDeviceToTransfer);

                piggyBankApplication.setBluetoothGatt(gatt);

                piggyBankApplication.setPiggyConnected(true);
                piggyBankApplication.setBluetoothGattCharacteristic(characteristic);
                if (PiggyBankShowSearchScreenTransferGoal.this.isTransfer) {
                    double amountToUpdate = Double.parseDouble(PiggyBankShowSearchScreenTransferGoal.this.accountBalance);
                    StringBuilder stringBuilder3 = new StringBuilder();
                    stringBuilder3.append("");
                    stringBuilder3.append(amountToUpdate);
                    characteristic.setValue(CommunicationProtoCalHelper.transferToPiggy(stringBuilder3.toString(), new SimpleDateFormat("ddMMyyyyHHmm").format(new Date())));
                } else {
                    String formatDate = new SimpleDateFormat("ddMMyyyyHHmm").format(new Date());
                    StringBuilder stringBuilder4 = new StringBuilder();
                    stringBuilder4.append("onServicesDiscovered: ");
                    stringBuilder4.append(PiggyBankShowSearchScreenTransferGoal.this.amountToTransfer);
                    Log.d("PiggyBankShow", stringBuilder4.toString());
                    Log.d("PiggyBankShow", "onServicesDiscovered: Goal Checking");
                    stringBuilder4 = new StringBuilder();
                    stringBuilder4.append("onServicesDiscovered: ");
                    stringBuilder4.append(CommunicationProtoCalHelper.setGoal(PiggyBankShowSearchScreenTransferGoal.this.goalName, PiggyBankShowSearchScreenTransferGoal.this.amountToTransfer, formatDate));
                    Log.d("PiggyBankShow", stringBuilder4.toString());
                    characteristic.setValue(CommunicationProtoCalHelper.setGoal(PiggyBankShowSearchScreenTransferGoal.this.goalName, PiggyBankShowSearchScreenTransferGoal.this.amountToTransfer, formatDate));
                }
                characteristic.setWriteType(2);
                gatt.writeCharacteristic(characteristic);
                gatt.setCharacteristicNotification(characteristic, true);
                BluetoothGattDescriptor descriptor = characteristic.getDescriptor(UUID.fromString(Constants.DESCRIPTOR_CONFIG_UUID));
                descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                gatt.writeDescriptor(descriptor);
                PiggyBankShowSearchScreenTransferGoal.this.runOnUiThread(new C04463());
            }
        }).connect();
    }

    public void callNewActivity() {
        if (this.isTransfer) {
            String amount = getIntent().getStringExtra(Constants.CONST_AMOUNT_TO_TRANSFER_KEY);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onConnectionStateChange: isTransfer");
            stringBuilder.append(amount);
            Log.d("PiggyBankShow", stringBuilder.toString());
            BluetoothGattInteraction bluetoothGattInteraction = new BluetoothGattInteraction();
            double amountToUpdate = Double.parseDouble(this.accountBalance);
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("");
            stringBuilder2.append(amountToUpdate);
            bluetoothGattInteraction.sendData(this, CommunicationProtoCalHelper.transferToPiggy(stringBuilder2.toString(), new SimpleDateFormat("ddMMyyyyHHmm").format(new Date())));
            Intent intent = new Intent(this, PiggyBankSelectAmountActivity.class);
            intent.putExtra(Constants.CONST_AMOUNT_TO_TRANSFER_KEY, amount);
            startActivity(intent);
            finish();
        }
        if (this.isGoalSet) {
            Log.d("PiggyBankShow", "onConnectionStateChange: isGoalSet");
            BluetoothGattInteraction bluetoothGattInteraction2 = new BluetoothGattInteraction();
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ddMMyyyyHHmm");
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("onScan: GoalAmount");
            stringBuilder2.append(this.amountToTransfer);
            Log.d("PiggyBankShow", stringBuilder2.toString());
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append("onScan: ");
            stringBuilder2.append(CommunicationProtoCalHelper.setGoal(this.goalName, this.amountToTransfer, simpleDateFormat.format(date)));
            Log.d("PiggyBankShow", stringBuilder2.toString());
            bluetoothGattInteraction2.sendData(this, CommunicationProtoCalHelper.setGoal(this.goalName, this.amountToTransfer, simpleDateFormat.format(date)));
            startActivity(new Intent(this, PiggyBankTransferMoneySuccessScreen.class));
            finish();
        }
    }

    public void showUiDeviceDisconnected() {
        mScanning = false;
        this.stopScanning = false;
        this.isDeviceScanning = true;
        this.searchForPiggysText.setVisibility(View.GONE);
        this.descriptionText.setVisibility(View.GONE);
        getSupportActionBar().setTitle((CharSequence) "Piggy Bank");
        this.rippleBackground.stopRippleAnimation();
        this.imageView.setImageResource(R.drawable.ic_refresh_black_24dp);
        this.noDevicesFoundTv.setVisibility(View.VISIBLE);
    }
}
