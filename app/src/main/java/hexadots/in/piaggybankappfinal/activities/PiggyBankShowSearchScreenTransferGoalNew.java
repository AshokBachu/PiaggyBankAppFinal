package hexadots.in.piaggybankappfinal.activities;

import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothAdapter.LeScanCallback;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog.Builder;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.skyfishjy.library.RippleBackground;
import hexadots.in.piaggybankappfinal.BluetoothUtils;
import hexadots.in.piaggybankappfinal.R;
import hexadots.in.piaggybankappfinal.CheckPermissions;
import hexadots.in.piaggybankappfinal.Constants;
import hexadots.in.piaggybankappfinal.DialogUtils;
import hexadots.in.piaggybankappfinal.PassDataToPiggy;
import hexadots.in.piaggybankappfinal.PiggyBankApplication;
import hexadots.in.piaggybankappfinal.PiggyBankBluetoothManger;
import hexadots.in.piaggybankappfinal.ToastUtils;
import hexadots.in.piaggybankappfinal.bean.Account;
import hexadots.in.piaggybankappfinal.bean.PiggyBankData;
import hexadots.in.piaggybankappfinal.bean.PiggyDetails;
import hexadots.in.piaggybankappfinal.bean.PiggyStatusDetails;
import hexadots.in.piaggybankappfinal.communication.BluetoothGattInteraction;
import hexadots.in.piaggybankappfinal.communication.CommunicationProtoCalHelper;
import hexadots.in.piaggybankappfinal.firebase.FireBaseManger;
import hexadots.in.piaggybankappfinal.interfaces.PiggyDetailsCallBack;
import hexadots.in.piaggybankappfinal.interfaces.PiggyUpdatedListener;
import hexadots.in.piaggybankappfinal.interfaces.SavePiggyDetailsListner;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

public class PiggyBankShowSearchScreenTransferGoalNew extends BaseActivity {
    public static final String TAG = "PiggyBankShow";
    public static boolean mScanning = false;
    private String accountBalance;
    private String amountToTransfer;
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothDevice bluetoothDeviceToTransfer;
    private boolean callDevice;
    private TextView descriptionText;
    Dialog dialog;
    private boolean disconnectFunctionality;
    private boolean enableAutoSeachFunctionality;
    private String goalAmount;
    private String goalName;
    private String goalNameTosend;
    Handler handlerStopUi;
    private ImageView imageView;
    boolean isDeviceScanning = true;
    boolean isFromDialog;
    boolean isFromOnBackPressed;
    boolean isFromOnPause;
    private boolean isFromReset;
    private boolean isGoalSet;
    private boolean isSetup;
    private boolean isTransfer;
    boolean isfromEnable;
    private TextView noDevicesFoundTv;
    PassDataToPiggy passDataToPiggy;
    private PiggyBankBluetoothManger piggyBankBluetoothManger;
    private String piggyDeviceId;
    private RippleBackground rippleBackground;
    LeScanCallback scanCallback = new C04608();
    private TextView searchForPiggysText;
    Runnable stopRunnable = new C04619();
    private boolean stopScanning;
    private boolean updateBalance;

    /* renamed from: hexadots.in.piaggybankappfinal.activities.PiggyBankShowSearchScreenTransferGoalNew$1 */
    class C04511 implements OnClickListener {
        C04511() {
        }

        public void onClick(View view) {
            if (BluetoothUtils.isBluetoothSupported(PiggyBankShowSearchScreenTransferGoalNew.this) && BluetoothUtils.bluetoothButtonEnabled(PiggyBankShowSearchScreenTransferGoalNew.this)) {
                PiggyBankShowSearchScreenTransferGoalNew.this.isfromEnable = false;
                if (!PiggyBankShowSearchScreenTransferGoalNew.mScanning) {
                    PiggyBankShowSearchScreenTransferGoalNew.mScanning = true;
                    PiggyBankShowSearchScreenTransferGoalNew.this.searchForPiggysText.setVisibility(View.VISIBLE);
                    PiggyBankShowSearchScreenTransferGoalNew.this.descriptionText.setVisibility(View.VISIBLE);
                    PiggyBankShowSearchScreenTransferGoalNew.this.noDevicesFoundTv.setVisibility(View.GONE);
                    PiggyBankShowSearchScreenTransferGoalNew.this.getSupportActionBar().setTitle((CharSequence) "Searching...");
                    PiggyBankShowSearchScreenTransferGoalNew.this.imageView.setImageResource(R.drawable.piggy_bank);
                    Log.d("PiggyBankShow", "Searching Process ");
                    PiggyBankShowSearchScreenTransferGoalNew.this.rippleBackground.startRippleAnimation();
                    PiggyBankShowSearchScreenTransferGoalNew.this.checkNearDevicesScan();
                    Log.d("PiggyBankShow", "Center image has clicked");
                    return;
                }
                return;
            }
            PiggyBankShowSearchScreenTransferGoalNew.this.enableBluetooth();
        }
    }

    /* renamed from: hexadots.in.piaggybankappfinal.activities.PiggyBankShowSearchScreenTransferGoalNew$2 */
    class C04532 implements Runnable {

        /* renamed from: hexadots.in.piaggybankappfinal.activities.PiggyBankShowSearchScreenTransferGoalNew$2$1 */
        class C04521 implements Runnable {
            C04521() {
            }

            public void run() {
                if (PiggyBankShowSearchScreenTransferGoalNew.this.isFromOnBackPressed) {
                    PiggyBankShowSearchScreenTransferGoalNew.this.finish();
                } else if (PiggyBankShowSearchScreenTransferGoalNew.this.isFromOnPause) {
                    PiggyBankShowSearchScreenTransferGoalNew.this.finish();
                } else {
                    if (!(!PiggyBankShowSearchScreenTransferGoalNew.this.isTransfer || PiggyBankShowSearchScreenTransferGoalNew.this.updateBalance || PiggyBankShowSearchScreenTransferGoalNew.this.isFromOnBackPressed || PiggyBankShowSearchScreenTransferGoalNew.this.disconnectFunctionality || PiggyBankShowSearchScreenTransferGoalNew.this.isFromOnPause)) {
                        String amount = PiggyBankShowSearchScreenTransferGoalNew.this.getIntent().getStringExtra(Constants.CONST_AMOUNT_TO_TRANSFER_KEY);
                        PiggyBankShowSearchScreenTransferGoalNew.this.accountBalance = PiggyBankShowSearchScreenTransferGoalNew.this.getIntent().getStringExtra(Constants.CONST_PIGGY_BALANCE);
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("onConnectionStateChange: isTransfer");
                        stringBuilder.append(amount);
                        Log.d("PiggyBankShow", stringBuilder.toString());
                        Intent intent = new Intent(PiggyBankShowSearchScreenTransferGoalNew.this, PiggyBankSelectAmountActivity.class);
                        intent.putExtra(Constants.CONST_AMOUNT_TO_TRANSFER_KEY, amount);
                        intent.putExtra(Constants.CONST_PIGGY_BALANCE, PiggyBankShowSearchScreenTransferGoalNew.this.accountBalance);
                        PiggyBankShowSearchScreenTransferGoalNew.this.startActivity(intent);
                        PiggyBankShowSearchScreenTransferGoalNew.this.finish();
                    }
                    if (PiggyBankShowSearchScreenTransferGoalNew.this.isGoalSet) {
                        if (PiggyBankShowSearchScreenTransferGoalNew.this.isFromOnBackPressed) {
                            PiggyBankShowSearchScreenTransferGoalNew.this.finish();
                        } else if (PiggyBankShowSearchScreenTransferGoalNew.this.isFromOnPause) {
                            PiggyBankShowSearchScreenTransferGoalNew.this.finish();
                        } else if (!(PiggyBankShowSearchScreenTransferGoalNew.this.isFromOnBackPressed || PiggyBankShowSearchScreenTransferGoalNew.this.isFromOnPause)) {
                            Log.d("PiggyBankShow", "onConnectionStateChange: isGoalSet");
                            PiggyBankShowSearchScreenTransferGoalNew.this.startActivity(new Intent(PiggyBankShowSearchScreenTransferGoalNew.this, PiggyBankTransferMoneySuccessScreen.class));
                            PiggyBankShowSearchScreenTransferGoalNew.this.finish();
                        }
                    }
                }
            }
        }

        C04532() {
        }

        public void run() {
            new Handler().postDelayed(new C04521(), 100);
        }
    }

    /* renamed from: hexadots.in.piaggybankappfinal.activities.PiggyBankShowSearchScreenTransferGoalNew$8 */
    class C04608 implements LeScanCallback {
        C04608() {
        }

        public void onLeScan(final BluetoothDevice bluetoothDevice, int i, byte[] bytes) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(bluetoothDevice.getName());
            stringBuilder.append("<--onLeScan-->");
            stringBuilder.append(bluetoothDevice.getAddress());
            Log.d("PiggyBankShow", stringBuilder.toString());
            if (!PiggyBankShowSearchScreenTransferGoalNew.this.stopScanning) {
                PiggyBankShowSearchScreenTransferGoalNew.this.stopScanning = true;
                PiggyBankShowSearchScreenTransferGoalNew.this.handlerStopUi = new Handler();
                PiggyBankShowSearchScreenTransferGoalNew.this.handlerStopUi.postDelayed(PiggyBankShowSearchScreenTransferGoalNew.this.stopRunnable, Constants.SCAN_PERIOD);
            }
            Handler handler = new Handler();
            if (PiggyBankShowSearchScreenTransferGoalNew.this.piggyDeviceId.equals(bluetoothDevice.getAddress()) && PiggyBankShowSearchScreenTransferGoalNew.this.isDeviceScanning) {
                PiggyBankShowSearchScreenTransferGoalNew.this.handlerStopUi.removeCallbacks(PiggyBankShowSearchScreenTransferGoalNew.this.stopRunnable);
                PiggyBankShowSearchScreenTransferGoalNew.this.isDeviceScanning = false;
                handler.postDelayed(new Runnable() {
                    public void run() {
                        Log.d("PiggyBankShow", "onLeScan: Device Found ");
                        PiggyBankShowSearchScreenTransferGoalNew.this.bluetoothAdapter.stopLeScan(PiggyBankShowSearchScreenTransferGoalNew.this.scanCallback);
                        PiggyBankApplication piggyBankApplication = (PiggyBankApplication) PiggyBankShowSearchScreenTransferGoalNew.this.getApplicationContext();
                        if (piggyBankApplication.isPiggyConnected()) {
                            PiggyBankShowSearchScreenTransferGoalNew.this.bluetoothDeviceToTransfer = piggyBankApplication.getBluetoothDevice();
                            if (piggyBankApplication.getBluetoothGatt() != null) {
                                piggyBankApplication.getBluetoothGatt().disconnect();
                            }
                        } else {
                            PiggyBankShowSearchScreenTransferGoalNew.this.bluetoothDeviceToTransfer = bluetoothDevice;
                        }
                        PiggyBankShowSearchScreenTransferGoalNew.this.checkNear();
                    }
                }, 3000);
            }
        }
    }

    /* renamed from: hexadots.in.piaggybankappfinal.activities.PiggyBankShowSearchScreenTransferGoalNew$9 */
    class C04619 implements Runnable {
        C04619() {
        }

        public void run() {
            PiggyBankShowSearchScreenTransferGoalNew.this.showUiDeviceDisconnected();
        }
    }

    /* renamed from: hexadots.in.piaggybankappfinal.activities.PiggyBankShowSearchScreenTransferGoalNew$5 */
    class C07565 implements SavePiggyDetailsListner {
        C07565() {
        }

        public void onSave(boolean isSaved) {
            if (PiggyBankShowSearchScreenTransferGoalNew.this.disconnectFunctionality && PiggyBankShowSearchScreenTransferGoalNew.this.enableAutoSeachFunctionality) {
                PiggyBankShowSearchScreenTransferGoalNew.this.showAlertDialog("Disconnected Successfully,Searching for new KLYA", "Alert!", false);
            } else {
                PiggyBankShowSearchScreenTransferGoalNew.this.showAlertDialog("Disconnected Successfully", "Alert!", false);
            }
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
        this.isFromOnPause = false;
        Log.d("PiggyBankShow", "onResume: ");
        if (this.bluetoothAdapter == null) {
            enableBluetooth();
        }
    }

    public void initSearchScreen() {
        this.isFromReset = getIntent().getBooleanExtra(Constants.IS_FROM_RESET, false);
        this.disconnectFunctionality = getIntent().getBooleanExtra(Constants.DISCONNECT_FUN, false);
        this.enableAutoSeachFunctionality = getIntent().getBooleanExtra(Constants.ENABLE_AUTO_SEARCH_FUN, false);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("initSearchScreen enableAutoSeach: ");
        stringBuilder.append(this.enableAutoSeachFunctionality);
        Log.d("PiggyBankShow", stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append("initSearchScreen disconnectFun: ");
        stringBuilder.append(this.disconnectFunctionality);
        Log.d("PiggyBankShow", stringBuilder.toString());
        this.goalNameTosend = getIntent().getStringExtra(Constants.GOAL_NAME);
        this.goalAmount = getIntent().getStringExtra(Constants.GOALAMOUNT);
        this.updateBalance = getIntent().getBooleanExtra("UpdateBalance", false);
        stringBuilder = new StringBuilder();
        stringBuilder.append("initSearchScreen: ");
        stringBuilder.append(this.updateBalance);
        Log.d("PiggyBankShow", stringBuilder.toString());
        String fromWhere = getIntent().getStringExtra(Constants.isFrom);
        this.accountBalance = getIntent().getStringExtra(Constants.CONST_PIGGY_BALANCE);
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("callUpdateBalance2: ");
        stringBuilder2.append(this.accountBalance);
        Log.d("PiggyBankShow", stringBuilder2.toString());
        this.amountToTransfer = getIntent().getStringExtra(Constants.CONST_AMOUNT_TO_TRANSFER_KEY);
        this.searchForPiggysText = (TextView) findViewById(R.id.searchForPiggysText);
        this.descriptionText = (TextView) findViewById(R.id.description_text);
        PiggyBankHomeNavDrawer.openPasscode = false;
        if (fromWhere.equals(Constants.isFromGoal)) {
            this.isGoalSet = true;
            this.isTransfer = false;
            this.isSetup = false;
            this.goalName = getIntent().getStringExtra(Constants.GOAL_NAME);
            stringBuilder = new StringBuilder();
            stringBuilder.append("");
            stringBuilder.append(getIntent().getStringExtra(Constants.CONST_AMOUNT_TO_TRANSFER_KEY));
            this.amountToTransfer = stringBuilder.toString();
            StringBuilder stringBuilder3 = new StringBuilder();
            stringBuilder3.append("onCreate: ");
            stringBuilder3.append(this.amountToTransfer);
            Log.d("PiggyBankShow", stringBuilder3.toString());
            Log.d("PiggyBankShow", "onCreate: goal");
            StringBuilder htmlString = new StringBuilder();
            htmlString.append("<font color='#9f3046' size='30' > <b>");
            htmlString.append(getDeviceName());
            htmlString.append(" </b></font>");
            //htmlString = htmlString.toString();
            String piggy = getString(R.string.piggy);
            TextView textView = this.searchForPiggysText;
            StringBuilder stringBuilder4 = new StringBuilder();
            stringBuilder4.append("Searching for ");
            stringBuilder4.append(htmlString);
            stringBuilder4.append(" ");
            stringBuilder4.append(piggy);
            textView.setText(Html.fromHtml(stringBuilder4.toString()));
        }
        if (fromWhere.equals(Constants.isFromTransfer)) {
            this.isGoalSet = false;
            this.isTransfer = true;
            this.isSetup = false;
            Log.d("PiggyBankShow", "onCreate: transfer");
            String htmlString = getString(R.string.piggy);
            StringBuilder htmlString2 = new StringBuilder();
            htmlString2.append("<font color='#9f3046' size='30' ><b>");
            htmlString2.append(getDeviceName());
            htmlString2.append("</b></font>");
            //htmlString2 = htmlString2.toString();
            TextView textView2 = this.searchForPiggysText;
            StringBuilder stringBuilder5 = new StringBuilder();
            stringBuilder5.append("Searching for ");
            stringBuilder5.append(htmlString2);
            stringBuilder5.append(" ");
            stringBuilder5.append(htmlString);
            textView2.setText(Html.fromHtml(stringBuilder5.toString()));
        }
        this.noDevicesFoundTv = (TextView) findViewById(R.id.nodevicesFoundTv);
        this.rippleBackground = (RippleBackground) findViewById(R.id.content);
        this.imageView = (ImageView) findViewById(R.id.centerImage);
        PiggyBankApplication piggyBankApplication = (PiggyBankApplication) getApplicationContext();
        this.imageView.setOnClickListener(new C04511());
    }

    public void checkNearDevicesScan() {
        this.bluetoothAdapter = new PiggyBankBluetoothManger(this).getBluetoothAdapter();
        this.bluetoothAdapter.startLeScan(this.scanCallback);
    }

    protected void onDestroy() {
        super.onDestroy();
        Log.d("PiggyBankShow", "onDestroy: ");
        mScanning = false;
    }

    protected void onStop() {
        super.onStop();
        Log.d("PiggyBankShow", "onStop: ");
    }

    protected void onPause() {
        super.onPause();
        Log.d("PiggyBankShow", "onPause: ");
        this.isFromOnPause = true;
    }

    public void onBackPressed() {
        this.isFromOnBackPressed = true;
        if (this.bluetoothAdapter != null) {
            this.bluetoothAdapter.stopLeScan(this.scanCallback);
        }
        super.onBackPressed();
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
        ToastUtils.showToast(this.noDevicesFoundTv, this, "Bluetooth functionality does not support this Device");
    }

    public void checkPermission() {
        CheckPermissions checkPermissions = new CheckPermissions(this);
        if (checkPermissions.checkPermission()) {
            enablLocation();
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

    public void discoveredToy(BluetoothGatt gatt, int status) {
        if (status != 0) {
            Log.d("PiggyBankShow", "onServicesDiscovered: Error");
            return;
        }
        Log.d("PiggyBankShow", "onServicesDiscovered: ");
        BluetoothGattCharacteristic characteristic = gatt.getService(UUID.fromString(Constants.SERVICE_UUID)).getCharacteristic(UUID.fromString(Constants.CHARACTERISTIC_TOY_UUID));
        String format = new SimpleDateFormat("ddMMyyyyhhmm").format(new Date());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("onServicesDiscovered: ");
        stringBuilder.append(CommunicationProtoCalHelper.setGoal("BuyCar", "100", format));
        Log.d("PiggyBankShow", stringBuilder.toString());
        PiggyBankApplication piggyBankApplication = (PiggyBankApplication) getApplicationContext();
        piggyBankApplication.setBluetoothDevice(this.bluetoothDeviceToTransfer);

        piggyBankApplication.setBluetoothGatt(gatt);

        piggyBankApplication.setPiggyConnected(true);
        piggyBankApplication.setBluetoothGattCharacteristic(characteristic);
        if (this.isTransfer) {
            transferDataToToy();
        } else {
            String formatDate = new SimpleDateFormat("ddMMyyyyHHmm").format(new Date());
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("onServicesDiscovered: ");
            stringBuilder2.append(this.amountToTransfer);
            Log.d("PiggyBankShow", stringBuilder2.toString());
            Log.d("PiggyBankShow", "onServicesDiscovered: Goal Checking");
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append("onServicesDiscovered: ");
            stringBuilder2.append(CommunicationProtoCalHelper.setGoal(this.goalName, this.amountToTransfer, formatDate));
            Log.d("PiggyBankShow", stringBuilder2.toString());
            characteristic.setValue(CommunicationProtoCalHelper.setGoal(this.goalName, this.amountToTransfer, formatDate));
        }
        characteristic.setWriteType(2);
        gatt.writeCharacteristic(characteristic);
        gatt.setCharacteristicNotification(characteristic, true);
        BluetoothGattDescriptor descriptor = characteristic.getDescriptor(UUID.fromString(Constants.DESCRIPTOR_CONFIG_UUID));
        descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
        gatt.writeDescriptor(descriptor);
        callRunnableData();
    }

    public void transferDataToToy() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("callNewActivity: ");
        stringBuilder.append(BaseActivity.getLoginScreenVisibility(this));
        Log.d("PiggyBankShow", stringBuilder.toString());
        if (this.updateBalance) {
            if (!this.isFromOnBackPressed) {
                SyncDataToToy(new BluetoothGattInteraction());
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("run:Data ");
                stringBuilder2.append(this.accountBalance);
                Log.d("PiggyBankShow", stringBuilder2.toString());
            } else {
                return;
            }
        }
        if (this.disconnectFunctionality && !this.isFromOnBackPressed) {
            BluetoothGattInteraction bluetoothGattInteraction = new BluetoothGattInteraction();
            if (this.isFromReset) {
                executeDisconnectFunctinality(bluetoothGattInteraction, "Data reset in progress ", "Resetting...");
            } else {
                executeDisconnectFunctinality(bluetoothGattInteraction, "Disconnecting KLYA ", "Disconnecting...");
            }
        }
    }

    public void callRunnableData() {
        runOnUiThread(new C04532());
    }

    public void checkNear() {
        final PiggyBankApplication piggyBankApplication = (PiggyBankApplication) getApplicationContext();
        this.bluetoothDeviceToTransfer.connectGatt(this, false, new BluetoothGattCallback() {

            /* renamed from: hexadots.in.piaggybankappfinal.activities.PiggyBankShowSearchScreenTransferGoalNew$3$1 */
            class C04541 implements Runnable {
                C04541() {
                }

                public void run() {
                    PiggyBankShowSearchScreenTransferGoalNew.this.showUiDeviceDisconnected();
                }
            }

            public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
                super.onConnectionStateChange(gatt, status, newState);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(status);
                stringBuilder.append("<--onConnectionStateChange: newState-->");
                stringBuilder.append(newState);
                Log.d("PiggyBankShow", stringBuilder.toString());
                if (newState == 2) {
                    Log.d("PiggyBankShow", "onConnectionStateChange: Connected");
                    if (piggyBankApplication.isPiggyConnected()) {
                        PiggyBankShowSearchScreenTransferGoalNew.this.callNewActivity();
                    } else {
                        gatt.discoverServices();
                    }
                }
                if (newState == 0) {
                    Log.d("PiggyBankShow", "onConnectionStateChange: DisConnected");
                    PiggyBankShowSearchScreenTransferGoalNew.this.callDevice = true;
                    piggyBankApplication.setPiggyConnected(false);
                    PiggyBankShowSearchScreenTransferGoalNew.this.runOnUiThread(new Thread(new C04541()));
                }
            }

            public void onServicesDiscovered(final BluetoothGatt gatt, final int status) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("onServicesDiscovered: ");
                stringBuilder.append(status);
                Log.d("PiggyBankShow", stringBuilder.toString());
                super.onServicesDiscovered(gatt, status);
                FireBaseManger fireBaseManger = new FireBaseManger();
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("onServicesDiscovered:--> ");
                stringBuilder2.append(PiggyBankShowSearchScreenTransferGoalNew.this.getToyname());
                Log.d("PiggyBankShow", stringBuilder2.toString());
                stringBuilder = new StringBuilder();
                stringBuilder.append("");
                Log.d(TAG, "onServicesDiscovered:===> "+PiggyBankShowSearchScreenTransferGoalNew.this.getToyname());
                stringBuilder.append(PiggyBankShowSearchScreenTransferGoalNew.this.getToyname());
                fireBaseManger.getPiggyDetails(stringBuilder.toString(), new PiggyDetailsCallBack() {
                    /* renamed from: hexadots.in.piaggybankappfinal.activities.PiggyBankShowSearchScreenTransferGoalNew$3$2$1 */
                    class C07531 implements SavePiggyDetailsListner {
                        C07531() {
                        }

                        public void onSave(boolean isSaved)
                        {
                            PiggyBankHomeNavDrawer.toyDeviceName = PiggyBankShowSearchScreenTransferGoalNew.this.getToyname();
                            PiggyBankHomeNavDrawer.isDeviceDisconnected = true;

                            PiggyBankShowSearchScreenTransferGoalNew.this.disconnect();

                            PiggyBankShowSearchScreenTransferGoalNew.this.finish();

                        }
                    }

                    public void getPiggyDetails(PiggyStatusDetails piggyStatusDetails) {
                        if (!piggyStatusDetails.isPiggyConnected() || !piggyStatusDetails.getLinkedAccountId().equals(Constants.getAccountKey(PiggyBankShowSearchScreenTransferGoalNew.this))) {
                            new FireBaseManger().savePiggyBankData(PiggyBankShowSearchScreenTransferGoalNew.this, PiggyBankShowSearchScreenTransferGoalNew.this.makePiggyToDisconnectState(), new C07531());
                        } else if (PiggyBankShowSearchScreenTransferGoalNew.this.disconnectFunctionality) {
                            PiggyBankShowSearchScreenTransferGoalNew.this.disconnectFun(false, gatt, status);
                        } else if (PiggyBankShowSearchScreenTransferGoalNew.this.isFromReset) {
                            PiggyBankShowSearchScreenTransferGoalNew.this.disconnectFun(true, gatt, status);
                        } else {
                            PiggyBankShowSearchScreenTransferGoalNew.this.discoveredToy(gatt, status);
                        }
                    }
                });
            }
        }).connect();
    }

    public void disconnectFun(boolean isFromDisconnectedOrReset, BluetoothGatt bluetoothGatt, int status) {
        discoveredToy(bluetoothGatt, status);
    }

    public PiggyBankData makePiggyToDisconnectState() {
        PiggyBankData piggyBankData = ((PiggyBankApplication) getApplicationContext()).getPiggyBankData();
        Account account = (Account) ((HashMap) piggyBankData.getAccounts()).get(Constants.getAccountKey(this));
        PiggyDetails piggyDetails = account.getPiggyDetails();
        piggyDetails.setPiggyAttached(false);
        account.setPiggyDetails(piggyDetails);
        return piggyBankData;
    }

    public String getToyname()
    {
        return ((Account) ((HashMap) ((PiggyBankApplication) getApplicationContext()).getPiggyBankData().getAccounts()).get(Constants.getAccountKey(this))).getPiggyDetails().getDeviceName();
    }

    public void setBalanceSetUpUi() {
        this.rippleBackground.setVisibility(View.INVISIBLE);
        getSupportActionBar().setTitle((CharSequence) "Updating...");
        this.searchForPiggysText.setVisibility(View.INVISIBLE);
    }

    public void seDisconnectUi() {
        this.rippleBackground.setVisibility(View.INVISIBLE);
        getSupportActionBar().setTitle((CharSequence) "Disconnecting...");
        this.searchForPiggysText.setVisibility(View.INVISIBLE);
    }

    public void executeDisconnectFunctinality(final BluetoothGattInteraction bluetoothGattInteraction, final String messageName, final String actionBarTitle) {
        runOnUiThread(new Runnable() {

            /* renamed from: hexadots.in.piaggybankappfinal.activities.PiggyBankShowSearchScreenTransferGoalNew$4$1 */
            class C07551 implements PiggyUpdatedListener {
                C07551() {
                }

                public void onPiggyUpdated() {

                    PiggyBankShowSearchScreenTransferGoalNew.this.disconnect();

                    PiggyBankShowSearchScreenTransferGoalNew.this.dialog.dismiss();
                    if (PiggyBankShowSearchScreenTransferGoalNew.this.isFromReset) {
                        PiggyBankShowSearchScreenTransferGoalNew.this.showAlertDialog("Data reset successfully", "Alert!", false);
                    } else if (PiggyBankShowSearchScreenTransferGoalNew.this.disconnectFunctionality && PiggyBankShowSearchScreenTransferGoalNew.this.enableAutoSeachFunctionality) {
                        PiggyBankShowSearchScreenTransferGoalNew.this.callDisconnectFun();
                    } else {
                        PiggyBankShowSearchScreenTransferGoalNew.this.callDisconnectFun();
                    }
                }
            }

            public void run() {
                PiggyBankShowSearchScreenTransferGoalNew.this.seDisconnectUi();
                PiggyBankShowSearchScreenTransferGoalNew piggyBankShowSearchScreenTransferGoalNew = PiggyBankShowSearchScreenTransferGoalNew.this;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("");
                stringBuilder.append(actionBarTitle);
                piggyBankShowSearchScreenTransferGoalNew.setActionBarTitle(stringBuilder.toString());
                PiggyBankShowSearchScreenTransferGoalNew.this.dialog = DialogUtils.showDialogUtils(PiggyBankShowSearchScreenTransferGoalNew.this, messageName, 29000);
                PiggyBankShowSearchScreenTransferGoalNew.this.passDataToPiggy = new PassDataToPiggy();
                PiggyBankShowSearchScreenTransferGoalNew.this.passDataToPiggy.updateName(bluetoothGattInteraction, "KLYA", "0000000000000000", PiggyBankShowSearchScreenTransferGoalNew.this, Constants.isFromGoal, "9999.00", "0.00", new C07551());
            }
        });
    }

    public void callDisconnectFun() {
        PiggyBankApplication piggyBankApplication = (PiggyBankApplication) getApplicationContext();
        PiggyDetails piggyDetails = Constants.getChildAccount(this).getPiggyDetails();
        if (this.isFromReset) {
            finish();
            return;
        }
        if (this.disconnectFunctionality) {
            piggyDetails.setPiggyAttached(false);
        }
        PiggyBankData piggyBankData = piggyBankApplication.getPiggyBankData();
        Constants.getChildAccount(this).setPiggyDetails(piggyDetails);
        new FireBaseManger().savePiggyBankData(this, piggyBankApplication.getPiggyBankData(), new C07565());
    }

    public void disconnect()
    {
        ((PiggyBankApplication) getApplicationContext()).getBluetoothGatt().disconnect();
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle((CharSequence) title);
    }

    public void SyncDataToToy(final BluetoothGattInteraction bluetoothGattInteraction) {
        runOnUiThread(new Runnable() {

            /* renamed from: hexadots.in.piaggybankappfinal.activities.PiggyBankShowSearchScreenTransferGoalNew$6$1 */
            class C07571 implements PiggyUpdatedListener {
                C07571() {
                }

                public void onPiggyUpdated() {
                    PiggyBankShowSearchScreenTransferGoalNew.this.dialog.dismiss();
                    PiggyBankShowSearchScreenTransferGoalNew.this.showAlertDialog("Sync Completed", "Alert!", true);
                }
            }

            public void run() {
                PiggyBankShowSearchScreenTransferGoalNew.this.setBalanceSetUpUi();
                PiggyBankShowSearchScreenTransferGoalNew.this.dialog = DialogUtils.showDialogUtils(PiggyBankShowSearchScreenTransferGoalNew.this, "Syncing KLYA ", 30000);
                PiggyBankShowSearchScreenTransferGoalNew.this.setActionBarTitle("Syncing...");
                PiggyBankShowSearchScreenTransferGoalNew.this.passDataToPiggy = new PassDataToPiggy();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("run:1 ");
                stringBuilder.append(PiggyBankShowSearchScreenTransferGoalNew.this.goalNameTosend);
                Log.d("PiggyBankShow", stringBuilder.toString());
                stringBuilder = new StringBuilder();
                stringBuilder.append("run:2 ");
                stringBuilder.append(PiggyBankShowSearchScreenTransferGoalNew.this.goalAmount);
                Log.d("PiggyBankShow", stringBuilder.toString());
                stringBuilder = new StringBuilder();
                stringBuilder.append("run:3 ");
                stringBuilder.append(PiggyBankShowSearchScreenTransferGoalNew.this.accountBalance);
                Log.d("PiggyBankShow", stringBuilder.toString());
                PiggyBankShowSearchScreenTransferGoalNew.this.passDataToPiggy.synkKlya(bluetoothGattInteraction, PiggyBankShowSearchScreenTransferGoalNew.this, PiggyBankShowSearchScreenTransferGoalNew.this.goalNameTosend, PiggyBankShowSearchScreenTransferGoalNew.this.goalAmount, PiggyBankShowSearchScreenTransferGoalNew.this.accountBalance, new C07571());
            }
        });
    }

    public void setUpdateBalance(final BluetoothGattInteraction bluetoothGattInteraction) {
        runOnUiThread(new Runnable() {

            /* renamed from: hexadots.in.piaggybankappfinal.activities.PiggyBankShowSearchScreenTransferGoalNew$7$1 */
            class C07581 implements PiggyUpdatedListener {
                C07581() {
                }

                public void onPiggyUpdated() {
                    PiggyBankShowSearchScreenTransferGoalNew.this.dialog.dismiss();
                    PiggyBankShowSearchScreenTransferGoalNew.this.showAlertDialog("Updated successfully", "Alert!", true);
                }
            }

            public void run() {
                PiggyBankShowSearchScreenTransferGoalNew.this.setBalanceSetUpUi();
                PiggyBankShowSearchScreenTransferGoalNew.this.dialog = DialogUtils.showDialogUtils(PiggyBankShowSearchScreenTransferGoalNew.this, "Syncing KLYA ", 19000);
                PassDataToPiggy passDataToPiggy = new PassDataToPiggy();
                BluetoothGattInteraction bluetoothGattInteraction = new BluetoothGattInteraction();
                Context context = PiggyBankShowSearchScreenTransferGoalNew.this;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("");
                stringBuilder.append(PiggyBankShowSearchScreenTransferGoalNew.this.accountBalance);
                passDataToPiggy.updateBalance(bluetoothGattInteraction, context, stringBuilder.toString(), new C07581());
            }
        });
    }

    public void showAlertDialog(String message, String title, boolean isFromUpdate) {
        ToastUtils.showToast(this.noDevicesFoundTv, this, message);
        if (isFromUpdate || !this.enableAutoSeachFunctionality) {
            finish();
            return;
        }
        startActivity(new Intent(this, PiggyBankShowToyDevicesActivity.class));
        finish();
    }

    public void callNewActivity() {
        if (this.isTransfer) {
            if (BaseActivity.getLoginScreenVisibility(this)) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("callNewActivity: ");
                stringBuilder.append(BaseActivity.getLoginScreenVisibility(this));
                Log.d("PiggyBankShow", stringBuilder.toString());
                String amount = getIntent().getStringExtra(Constants.CONST_AMOUNT_TO_TRANSFER_KEY);
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("onConnectionStateChange: isTransfer");
                stringBuilder2.append(amount);
                Log.d("PiggyBankShow", stringBuilder2.toString());

                BluetoothGattInteraction bluetoothGattInteraction = new BluetoothGattInteraction();
                double amountToUpdate = Double.parseDouble(this.accountBalance);

                Intent intent = new Intent(this, PiggyBankSelectAmountActivity.class);
                intent.putExtra(Constants.CONST_AMOUNT_TO_TRANSFER_KEY, amount);
                intent.putExtra(Constants.CONST_PIGGY_BALANCE, this.accountBalance);
                startActivity(intent);
            }
            finish();
        }
        if (this.isGoalSet) {
            if (BaseActivity.getLoginScreenVisibility(this)) {
                Log.d("PiggyBankShow", "onConnectionStateChange: isGoalSet");
                BluetoothGattInteraction bluetoothGattInteraction2 = new BluetoothGattInteraction();
                Date date = new Date();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ddMMyyyyHHmm");
                StringBuilder stringBuilder3 = new StringBuilder();
                stringBuilder3.append("onScan: GoalAmount");
                stringBuilder3.append(this.amountToTransfer);
                Log.d("PiggyBankShow", stringBuilder3.toString());
                stringBuilder3 = new StringBuilder();
                stringBuilder3.append("onScan: ");
                stringBuilder3.append(CommunicationProtoCalHelper.setGoal(this.goalName, this.amountToTransfer, simpleDateFormat.format(date)));
                Log.d("PiggyBankShow", stringBuilder3.toString());
                bluetoothGattInteraction2.sendData(this, CommunicationProtoCalHelper.setGoal(this.goalName, this.amountToTransfer, simpleDateFormat.format(date)));
                startActivity(new Intent(this, PiggyBankTransferMoneySuccessScreen.class));
            }
            finish();
        }
    }

    public void showUiDeviceDisconnected() {
        mScanning = false;
        this.stopScanning = false;
        this.isDeviceScanning = true;
        this.searchForPiggysText.setVisibility(View.GONE);
        this.descriptionText.setVisibility(View.GONE);
        getSupportActionBar().setTitle((CharSequence) "MCB KLYA");
        this.rippleBackground.stopRippleAnimation();
        this.imageView.setImageResource(R.drawable.ic_refresh_black_24dp);
        this.noDevicesFoundTv.setVisibility(View.VISIBLE);
        if (this.bluetoothAdapter != null) {
            this.bluetoothAdapter.stopLeScan(this.scanCallback);
        }
    }

    public void enablLocation() {
        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (lm.isProviderEnabled("gps")) {
            if (lm.isProviderEnabled("network")) {
                this.imageView.performClick();
                return;
            }
        }
        Builder builder = new Builder(this);
        builder.setTitle(getResources().getString(R.string.location_services_title));
        builder.setMessage(getResources().getString(R.string.location_services_message));
        builder.setPositiveButton((CharSequence) "enable", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                PiggyBankShowSearchScreenTransferGoalNew.this.startActivity(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"));
            }
        });
        builder.setNegativeButton((CharSequence) "Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                PiggyBankShowSearchScreenTransferGoalNew.this.finish();
            }
        });
        Dialog alertDialogLoaction = builder.create();
        alertDialogLoaction.setCancelable(false);
        alertDialogLoaction.show();
    }
}
