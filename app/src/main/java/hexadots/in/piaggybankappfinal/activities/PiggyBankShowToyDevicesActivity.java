package hexadots.in.piaggybankappfinal.activities;

import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothAdapter.LeScanCallback;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.view.ViewPager.PageTransformer;
import android.support.v7.app.AlertDialog.Builder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.skyfishjy.library.RippleBackground;
import hexadots.in.piaggybankappfinal.BluetoothUtils;
import hexadots.in.piaggybankappfinal.R;
import hexadots.in.piaggybankappfinal.CheckPermissions;
import hexadots.in.piaggybankappfinal.Constants;
import hexadots.in.piaggybankappfinal.PiggyBankBluetoothManger;
import hexadots.in.piaggybankappfinal.ToastUtils;
import hexadots.in.piaggybankappfinal.bean.BlueToothDeviceState;
import hexadots.in.piaggybankappfinal.bean.PiggyStatusDetails;
import hexadots.in.piaggybankappfinal.firebase.FireBaseManger;
import hexadots.in.piaggybankappfinal.interfaces.PiggyDetailsCallBack;
import hexadots.in.piaggybankappfinal.viewpager.ViewPagerAdapter;
import java.util.ArrayList;
import java.util.UUID;

public class PiggyBankShowToyDevicesActivity extends BaseActivity {
    public static final String TAG = "PiggyBankShow";
    public static int selectedPosition;
    private ArrayList<BlueToothDeviceState> blueToothDeviceStates = new ArrayList();
    public BluetoothAdapter bluetoothAdapter;
    private TextView descriptionText;
    private ImageView imageViewCenter;
    boolean isFromOnBackPressed;
    boolean isFromOnpause;
    boolean isScanningStartted;
    boolean isfromEnable;
    private boolean ismScanning;
    Handler largeHandler;
    private LinearLayout linearLayoutSearchContent;
    private boolean mScanning;
    private ViewPagerAdapter myViewPagerAdapter;
    private TextView noDevicesFoundTv;
    private PiggyBankBluetoothManger piggyBankBluetoothManger;
    private RelativeLayout progressBarRel;
    MenuItem register;
    private RippleBackground rippleBackground;
    LeScanCallback scanCallback = new C04667();
    private TextView searchForPiggysText;
    Handler shrtHandler;
    private TextView tapText;
    Runnable thirtySecremoveCallbacks = new C04689();
    Runnable threeremoveCallbacks = new C04678();
    private boolean viewPagerIsShowing;
    OnPageChangeListener viewPagerPageChangeListener = new C07604();
    private ViewPager viewPagerToShowDevices;

    /* renamed from: hexadots.in.piaggybankappfinal.activities.PiggyBankShowToyDevicesActivity$1 */
    class C04621 implements OnClickListener {
        C04621() {
        }

        public void onClick(View view) {
            if (!BluetoothUtils.isBluetoothSupported(PiggyBankShowToyDevicesActivity.this) || BluetoothUtils.bluetoothButtonEnabled(PiggyBankShowToyDevicesActivity.this)) {
                PiggyBankShowToyDevicesActivity.this.isfromEnable = false;
                if (!PiggyBankShowToyDevicesActivity.this.ismScanning) {
                    PiggyBankShowToyDevicesActivity.this.ismScanning = true;
                    PiggyBankShowToyDevicesActivity.this.scanDevices();
                    return;
                }
                return;
            }
            PiggyBankShowToyDevicesActivity.this.enableBluetooth();
        }
    }

    /* renamed from: hexadots.in.piaggybankappfinal.activities.PiggyBankShowToyDevicesActivity$2 */
    class C04632 implements OnClickListener {
        C04632() {
        }

        public void onClick(View view) {
        }
    }

    /* renamed from: hexadots.in.piaggybankappfinal.activities.PiggyBankShowToyDevicesActivity$5 */
    class C04645 implements DialogInterface.OnClickListener {
        C04645() {
        }

        public void onClick(DialogInterface dialogInterface, int i) {
            PiggyBankShowToyDevicesActivity.this.startActivity(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"));
        }
    }

    /* renamed from: hexadots.in.piaggybankappfinal.activities.PiggyBankShowToyDevicesActivity$6 */
    class C04656 implements DialogInterface.OnClickListener {
        C04656() {
        }

        public void onClick(DialogInterface dialogInterface, int i) {
            PiggyBankShowToyDevicesActivity.this.finish();
        }
    }

    /* renamed from: hexadots.in.piaggybankappfinal.activities.PiggyBankShowToyDevicesActivity$7 */
    class C04667 implements LeScanCallback {
        C04667() {
        }

        public void onLeScan(final BluetoothDevice bluetoothDevice, int i, byte[] bytes) {
            if (!PiggyBankShowToyDevicesActivity.this.isScanningStartted) {
                Log.d("PiggyBankShow", "onLeScan: ");
                PiggyBankShowToyDevicesActivity.this.isScanningStartted = true;
                PiggyBankShowToyDevicesActivity.this.shrtHandler = new Handler();
                PiggyBankShowToyDevicesActivity.this.shrtHandler.postDelayed(PiggyBankShowToyDevicesActivity.this.threeremoveCallbacks, 5000);
            }
            if (!PiggyBankShowToyDevicesActivity.this.blueToothDeviceStates.contains(bluetoothDevice)) {
                String bluetoothToyName = bluetoothDevice.getName();
                if (bluetoothDevice.getName().equals("BT05")) {
                    bluetoothToyName = "KLYA-BLUE";
                }
                if (bluetoothDevice.getName().equals(Constants.DEVICE_NAME)) {
                    bluetoothToyName = "KLYA-WHITE";
                }
                FireBaseManger fireBaseManger = new FireBaseManger();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("onLeScan:-----> ");
                stringBuilder.append(bluetoothToyName);
                Log.d("PiggyBankShow", stringBuilder.toString());
                fireBaseManger.getPiggyDetails(bluetoothToyName, new PiggyDetailsCallBack() {
                    public void getPiggyDetails(PiggyStatusDetails piggyStatusDetails) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("getPiggyDetails:---> ");
                        stringBuilder.append(PiggyBankShowToyDevicesActivity.this.blueToothDeviceStates.toString());
                        Log.d("PiggyBankShow", stringBuilder.toString());
                        if (!PiggyBankShowToyDevicesActivity.this.blueToothDeviceStates.toString().contains(bluetoothDevice.getAddress())) {
                            BlueToothDeviceState blueToothDeviceState = new BlueToothDeviceState();
                            blueToothDeviceState.setBluetoothDevice(bluetoothDevice);
                            if (piggyStatusDetails == null) {
                                blueToothDeviceState.setMakeBlur(false);
                            } else if (piggyStatusDetails.isPiggyConnected()) {
                                blueToothDeviceState.setMakeBlur(true);
                            } else {
                                blueToothDeviceState.setMakeBlur(false);
                            }
                            PiggyBankShowToyDevicesActivity.this.blueToothDeviceStates.add(blueToothDeviceState);
                            if (PiggyBankShowToyDevicesActivity.this.myViewPagerAdapter != null) {
                                PiggyBankShowToyDevicesActivity.this.myViewPagerAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
            }
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append(bluetoothDevice.getName());
            stringBuilder2.append("<--onLeScan-->");
            stringBuilder2.append(bluetoothDevice.getAddress());
            Log.d("PiggyBankShow", stringBuilder2.toString());
        }
    }

    /* renamed from: hexadots.in.piaggybankappfinal.activities.PiggyBankShowToyDevicesActivity$8 */
    class C04678 implements Runnable {
        C04678() {
        }

        public void run() {
            Log.d("PiggyBankShow", "run: three Sec");
            if (PiggyBankShowToyDevicesActivity.this.blueToothDeviceStates.size() > 0) {
                PiggyBankShowToyDevicesActivity.this.ismScanning = false;
                if (PiggyBankShowToyDevicesActivity.this.largeHandler != null) {
                    PiggyBankShowToyDevicesActivity.this.largeHandler.removeCallbacks(PiggyBankShowToyDevicesActivity.this.thirtySecremoveCallbacks);
                }
                PiggyBankShowToyDevicesActivity.this.setUpViewPagerDevices();
            }
        }
    }

    /* renamed from: hexadots.in.piaggybankappfinal.activities.PiggyBankShowToyDevicesActivity$9 */
    class C04689 implements Runnable {
        C04689() {
        }

        public void run() {
            Log.d("PiggyBankShow", "run: thirty Sec");
            if (PiggyBankShowToyDevicesActivity.this.blueToothDeviceStates.size() == 0) {
                PiggyBankShowToyDevicesActivity.this.ismScanning = false;
                if (PiggyBankShowToyDevicesActivity.this.shrtHandler != null) {
                    PiggyBankShowToyDevicesActivity.this.shrtHandler.removeCallbacks(PiggyBankShowToyDevicesActivity.this.threeremoveCallbacks);
                }
                PiggyBankShowToyDevicesActivity.this.bluetoothAdapter.stopLeScan(PiggyBankShowToyDevicesActivity.this.scanCallback);
                PiggyBankShowToyDevicesActivity.this.largeHandler.removeCallbacks(PiggyBankShowToyDevicesActivity.this.thirtySecremoveCallbacks);
                PiggyBankShowToyDevicesActivity.this.isScanningStartted = false;
                PiggyBankShowToyDevicesActivity.this.showUiDeviceDisconnected();
            }
        }
    }

    /* renamed from: hexadots.in.piaggybankappfinal.activities.PiggyBankShowToyDevicesActivity$3 */
    class C07593 implements PageTransformer {
        C07593() {
        }

        public void transformPage(View page, float position) {
            page.setScaleY((Math.abs(Math.abs(position) - 1.1f) / 2.0f) + 0.58f);
        }
    }

    /* renamed from: hexadots.in.piaggybankappfinal.activities.PiggyBankShowToyDevicesActivity$4 */
    class C07604 implements OnPageChangeListener {
        C07604() {
        }

        public void onPageSelected(int position) {
            PiggyBankShowToyDevicesActivity.selectedPosition = PiggyBankShowToyDevicesActivity.this.viewPagerToShowDevices.getCurrentItem();
        }

        public void onPageScrolled(int position, float positionOffset, int positionOff) {
        }

        public void onPageScrollStateChanged(int arg0) {
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_show_devices);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        PiggyBankHomeNavDrawer.openPasscode = false;
        this.ismScanning = false;
        initUiElements();
    }

    protected void onStop() {
        super.onStop();
    }

    protected void onPause() {
        super.onPause();
        this.isFromOnpause = true;
    }

    public boolean isFromOnBackPressed() {
        return this.isFromOnBackPressed;
    }

    public boolean isFromOnpause() {
        return this.isFromOnpause;
    }

    public void onBackPressed() {
        super.onBackPressed();
        this.isFromOnBackPressed = true;
        if (this.bluetoothAdapter != null) {
            this.bluetoothAdapter.stopLeScan(this.scanCallback);
        }
    }

    public void initUiElements() {
        this.searchForPiggysText = (TextView) findViewById(R.id.searchForPiggysText);
        this.descriptionText = (TextView) findViewById(R.id.description_text);
        this.noDevicesFoundTv = (TextView) findViewById(R.id.nodevicesFoundTv);
        this.viewPagerToShowDevices = (ViewPager) findViewById(R.id.viewPager);
        this.tapText = (TextView) findViewById(R.id.tapText);
        this.progressBarRel = (RelativeLayout) findViewById(R.id.progressBarRel);
        this.linearLayoutSearchContent = (LinearLayout) findViewById(R.id.searchContent);
        this.linearLayoutSearchContent.setVisibility(View.GONE);
        this.imageViewCenter = (ImageView) findViewById(R.id.centerImage);
        this.imageViewCenter.setOnClickListener(new C04621());
    }

    public void setUpViewPagerDevices() {
        this.register.setVisible(true);
        getSupportActionBar().setTitle((CharSequence) "Choose KLYA");
        this.linearLayoutSearchContent.setVisibility(View.GONE);
        this.viewPagerToShowDevices.setVisibility(View.VISIBLE);
        this.tapText.setVisibility(View.VISIBLE);
        this.progressBarRel.setOnClickListener(new C04632());
        assignAdapterToViewPager();
    }

    public void showUiDeviceDisconnected() {
        this.register.setVisible(false);
        this.viewPagerToShowDevices.setVisibility(View.GONE);
        this.descriptionText.setVisibility(View.GONE);
        this.searchForPiggysText.setVisibility(View.GONE);
        getSupportActionBar().setTitle((CharSequence) "MCB KLYA");
        this.rippleBackground.stopRippleAnimation();
        this.imageViewCenter.setImageResource(R.drawable.ic_refresh_black_24dp);
        this.noDevicesFoundTv.setVisibility(View.VISIBLE);
        if (this.bluetoothAdapter != null) {
            this.bluetoothAdapter.stopLeScan(this.scanCallback);
        }
    }

    public void scanDevices() {
        if (this.register != null) {
            this.register.setVisible(false);
        }
        this.descriptionText.setVisibility(View.VISIBLE);
        this.searchForPiggysText.setVisibility(View.VISIBLE);
        this.linearLayoutSearchContent.setVisibility(View.VISIBLE);
        this.tapText.setVisibility(View.GONE);
        this.viewPagerToShowDevices.setVisibility(View.GONE);
        this.rippleBackground = (RippleBackground) findViewById(R.id.content);
        this.noDevicesFoundTv.setVisibility(View.GONE);
        getSupportActionBar().setTitle((CharSequence) "Searching...");
        this.imageViewCenter.setImageResource(R.drawable.piggy_bank);
        this.rippleBackground.startRippleAnimation();
        this.largeHandler = new Handler();
        this.largeHandler.postDelayed(this.thirtySecremoveCallbacks, Constants.SCAN_PERIOD);
        if (this.bluetoothAdapter != null) {
            UUID uuid = UUID.fromString(Constants.SERVICE_UUID);
            this.bluetoothAdapter.startLeScan(new UUID[]{uuid}, this.scanCallback);
            return;
        }
        this.bluetoothAdapter = new PiggyBankBluetoothManger(this).getBluetoothAdapter();
        UUID uuid2 = UUID.fromString(Constants.SERVICE_UUID);
        this.bluetoothAdapter.startLeScan(new UUID[]{uuid2}, this.scanCallback);
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.refresh, menu);
        this.register = menu.findItem(R.id.refresh);
        this.register.setVisible(false);
        return super.onPrepareOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.refresh && !this.ismScanning) {
            this.ismScanning = true;
            if (this.largeHandler != null) {
                this.largeHandler.removeCallbacks(this.thirtySecremoveCallbacks);
            }
            if (this.shrtHandler != null) {
                this.shrtHandler.removeCallbacks(this.threeremoveCallbacks);
            }
            this.bluetoothAdapter.stopLeScan(this.scanCallback);
            this.isScanningStartted = false;
            this.blueToothDeviceStates.clear();
            scanDevices();
        }
        if (item.getItemId() == 16908332) {
            this.isFromOnBackPressed = true;
            if (this.bluetoothAdapter != null) {
                this.bluetoothAdapter.stopLeScan(this.scanCallback);
            }
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void assignAdapterToViewPager() {
        this.viewPagerToShowDevices.setPageTransformer(false, new C07593());
        this.myViewPagerAdapter = new ViewPagerAdapter(this.register, this.bluetoothAdapter, this.scanCallback, this.viewPagerToShowDevices, this.blueToothDeviceStates, this, this.progressBarRel);
        this.viewPagerToShowDevices.setClipToPadding(false);
        this.viewPagerToShowDevices.setAdapter(this.myViewPagerAdapter);
        this.viewPagerToShowDevices.addOnPageChangeListener(this.viewPagerPageChangeListener);
    }

    protected void onResume() {
        super.onResume();
        this.isFromOnpause = false;
        if (this.bluetoothAdapter == null && !this.viewPagerIsShowing) {
            enableBluetooth();
        }
        this.isFromOnpause = false;
    }

    public void enableBluetooth() {
        this.isfromEnable = true;
        this.piggyBankBluetoothManger = new PiggyBankBluetoothManger(this);
        if (this.piggyBankBluetoothManger.hasBluetoothFeature() && this.piggyBankBluetoothManger.isBluetoothSupported()) {
            BluetoothAdapter bluetoothAdapter = this.piggyBankBluetoothManger.getBluetoothAdapter();
            if (this.piggyBankBluetoothManger.isBluetoothEnable(bluetoothAdapter)) {
                this.isfromEnable = true;
                checkPermission();
            } else {
                this.isfromEnable = true;
                this.piggyBankBluetoothManger.enableBluetooth(bluetoothAdapter);
            }
            return;
        }
        ToastUtils.showToast(this.imageViewCenter, this, "Bluetooth functionality does not support this Device");
    }

    public void checkPermission() {
        CheckPermissions checkPermissions = new CheckPermissions(this);
        if (checkPermissions.checkPermission()) {
            this.isfromEnable = true;
            this.linearLayoutSearchContent.setVisibility(View.VISIBLE);
            enablLocation();
            return;
        }
        this.isfromEnable = true;
        checkPermissions.showAlertDialog(this);
    }

    public void enablLocation() {
        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (lm.isProviderEnabled("gps")) {
            if (lm.isProviderEnabled("network")) {
                this.imageViewCenter.performClick();
                return;
            }
        }
        Builder builder = new Builder(this);
        builder.setTitle(getResources().getString(R.string.location_services_title));
        builder.setMessage(getResources().getString(R.string.location_services_message));
        builder.setPositiveButton((CharSequence) "enable", new C04645());
        builder.setNegativeButton((CharSequence) "Cancel", new C04656());
        Dialog alertDialogLoaction = builder.create();
        alertDialogLoaction.setCancelable(false);
        alertDialogLoaction.show();
    }
}
