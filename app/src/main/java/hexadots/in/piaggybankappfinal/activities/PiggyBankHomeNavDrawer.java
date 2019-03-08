package hexadots.in.piaggybankappfinal.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import hexadots.in.piaggybankappfinal.BluetoothUtils;
import hexadots.in.piaggybankappfinal.R;
import hexadots.in.piaggybankappfinal.ChatHeadService;
import hexadots.in.piaggybankappfinal.Constants;
import hexadots.in.piaggybankappfinal.PiggyBankApplication;
import hexadots.in.piaggybankappfinal.ToastUtils;
import hexadots.in.piaggybankappfinal.bean.Account;
import hexadots.in.piaggybankappfinal.bean.PiggyBankData;
import hexadots.in.piaggybankappfinal.bean.PiggyDetails;
import hexadots.in.piaggybankappfinal.firebase.FireBaseManger;
import hexadots.in.piaggybankappfinal.interfaces.SavePiggyDetailsListner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class PiggyBankHomeNavDrawer extends BaseActivity implements OnNavigationItemSelectedListener {
    public static final String TAG = "PiggyBankHome";
    public static boolean isDeviceDisconnected;
    public static boolean openPasscode;
    public static String toyDeviceName;
    private Account account;
    private TextView accountNumTextView;
    private TextView addNewPiggyTextView;
    private String childAccountKey;
    boolean defaultColorsApplied;
    private RelativeLayout goalLayout;
    private TextView goalTextView;
    private ImageView imageViewGoal;
    private boolean isFromAddNewKlya;
    private boolean isFromBluetoothPermission;
    private boolean isFromSetupKlya;
    private boolean isFromSyncKlya;
    private boolean isFromTransferKlya;
    private boolean isFromUpdateGoalKlya;
    private boolean isProgressIsLoading;
    DrawerLayout mdraweDrawerLayout;
    private String parentAccountKey;
    private LinearLayout piggyBackground;
    private double piggyBalance;
    private PiggyDetails piggyDetails;
    private RelativeLayout progressBarRL;
    private RelativeLayout relativeLayoutGoalAchieved;
    private RelativeLayout relativeLayoutMiddleLayout;
    private RelativeLayout relativeLayoutTopMask;
    private Button setUp;
    private SeekBar showGoal;
    private TextView textViewAccountBalance;
    private TextView textViewConnectionStatus;
    private TextView textViewGoalAmount;
    private TextView textViewGoalName;
    private TextView textViewPiggyBalance;
    private TextView textViewPiggyDetailsPiggyName;
    private LinearLayout textViewSetGoal;
    private LinearLayout textViewSetUpBluetooth;
    private LinearLayout textViewTransfer;
    private TextView textViewUserName;
    private TextView text_seekbar;
    private TextView updateBlance;

    /* renamed from: hexadots.in.piaggybankappfinal.activities.PiggyBankHomeNavDrawer$1 */
    class C04031 implements OnClickListener {
        C04031() {
        }

        public void onClick(View v) {
            DrawerLayout drawer = (DrawerLayout) PiggyBankHomeNavDrawer.this.findViewById(R.id.drawer_layout);
            if (!drawer.isDrawerOpen((int) GravityCompat.START)) {
                drawer.openDrawer((int) GravityCompat.START);
            }
        }
    }

    /* renamed from: hexadots.in.piaggybankappfinal.activities.PiggyBankHomeNavDrawer$2 */
    class C04042 implements DialogInterface.OnClickListener {
        C04042() {
        }

        public void onClick(DialogInterface dialog, int which) {
            PiggyBankHomeNavDrawer.this.callDisconnectFunctionality(false, false, false);
        }
    }

    /* renamed from: hexadots.in.piaggybankappfinal.activities.PiggyBankHomeNavDrawer$3 */
    class C04053 implements DialogInterface.OnClickListener {
        C04053() {
        }

        public void onClick(DialogInterface dialog, int which) {
        }
    }

    /* renamed from: hexadots.in.piaggybankappfinal.activities.PiggyBankHomeNavDrawer$4 */
    class C04064 implements DialogInterface.OnClickListener {
        C04064() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    }

    /* renamed from: hexadots.in.piaggybankappfinal.activities.PiggyBankHomeNavDrawer$5 */
    class C04075 implements OnClickListener {
        C04075() {
        }

        public void onClick(View v) {
            if (!BluetoothUtils.isBluetoothSupported(PiggyBankHomeNavDrawer.this) || BluetoothUtils.bluetoothButtonEnabled(PiggyBankHomeNavDrawer.this)) {
                PiggyBankHomeNavDrawer.this.startAddNewPiggy();
                return;
            }
            PiggyBankHomeNavDrawer.this.isFromAddNewKlya = true;
            PiggyBankHomeNavDrawer.this.isFromSyncKlya = false;
            PiggyBankHomeNavDrawer.this.isFromTransferKlya = false;
            PiggyBankHomeNavDrawer.this.isFromUpdateGoalKlya = false;
            PiggyBankHomeNavDrawer.this.isFromSetupKlya = false;
            BluetoothUtils.enableBlutooth(PiggyBankHomeNavDrawer.this);
        }
    }

    /* renamed from: hexadots.in.piaggybankappfinal.activities.PiggyBankHomeNavDrawer$6 */
    class C04086 implements OnClickListener {
        C04086() {
        }

        public void onClick(View v) {
            if (!BluetoothUtils.isBluetoothSupported(PiggyBankHomeNavDrawer.this) || BluetoothUtils.bluetoothButtonEnabled(PiggyBankHomeNavDrawer.this)) {
                PiggyBankHomeNavDrawer.this.callUpdateBalance();
                return;
            }
            Log.d(PiggyBankHomeNavDrawer.TAG, "onClick: 1");
            PiggyBankHomeNavDrawer.this.isFromAddNewKlya = false;
            PiggyBankHomeNavDrawer.this.isFromSyncKlya = true;
            PiggyBankHomeNavDrawer.this.isFromTransferKlya = false;
            PiggyBankHomeNavDrawer.this.isFromUpdateGoalKlya = false;
            PiggyBankHomeNavDrawer.this.isFromSetupKlya = false;
            BluetoothUtils.enableBlutooth(PiggyBankHomeNavDrawer.this);
        }
    }

    /* renamed from: hexadots.in.piaggybankappfinal.activities.PiggyBankHomeNavDrawer$7 */
    class C04097 implements OnClickListener {
        C04097() {
        }

        public void onClick(View view) {
            PiggyBankHomeNavDrawer.this.startActivity(new Intent(PiggyBankHomeNavDrawer.this, PiggyBankShowToyDevicesActivity.class));
        }
    }

    /* renamed from: hexadots.in.piaggybankappfinal.activities.PiggyBankHomeNavDrawer$8 */
    class C04108 implements OnClickListener {
        C04108() {
        }

        public void onClick(View view) {
            if (!BluetoothUtils.isBluetoothSupported(PiggyBankHomeNavDrawer.this) || BluetoothUtils.bluetoothButtonEnabled(PiggyBankHomeNavDrawer.this)) {
                PiggyBankHomeNavDrawer.this.startSetUp();
                return;
            }
            PiggyBankHomeNavDrawer.this.isFromAddNewKlya = false;
            PiggyBankHomeNavDrawer.this.isFromSyncKlya = false;
            PiggyBankHomeNavDrawer.this.isFromTransferKlya = false;
            PiggyBankHomeNavDrawer.this.isFromUpdateGoalKlya = false;
            PiggyBankHomeNavDrawer.this.isFromSetupKlya = true;
            BluetoothUtils.enableBlutooth(PiggyBankHomeNavDrawer.this);
        }
    }

    /* renamed from: hexadots.in.piaggybankappfinal.activities.PiggyBankHomeNavDrawer$9 */
    class C04119 implements OnClickListener {
        C04119() {
        }

        public void onClick(View view) {
            if (!BluetoothUtils.isBluetoothSupported(PiggyBankHomeNavDrawer.this) || BluetoothUtils.bluetoothButtonEnabled(PiggyBankHomeNavDrawer.this)) {
                PiggyBankHomeNavDrawer.this.startTransfer();
                return;
            }
            PiggyBankHomeNavDrawer.this.isFromAddNewKlya = false;
            PiggyBankHomeNavDrawer.this.isFromSyncKlya = false;
            PiggyBankHomeNavDrawer.this.isFromTransferKlya = true;
            PiggyBankHomeNavDrawer.this.isFromUpdateGoalKlya = false;
            PiggyBankHomeNavDrawer.this.isFromSetupKlya = false;
            BluetoothUtils.enableBlutooth(PiggyBankHomeNavDrawer.this);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piggy_bank_home_nav_drawer);
        isDeviceDisconnected = false;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ((TextView) toolbar.findViewById(R.id.toolbar_title)).setText(getString(R.string.app_name));
        getSupportActionBar().setTitle((CharSequence) "");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        this.mdraweDrawerLayout = drawer;
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        ((NavigationView) findViewById(R.id.nav_view)).setNavigationItemSelectedListener(this);
        ((ImageView) findViewById(R.id.navOpenCloseIcon)).setOnClickListener(new C04031());
        initUiElements();

        //todoo
        /*Intent intent = new Intent(this, ChatHeadService.class);
        if (VERSION.SDK_INT >= 26) {
            startForegroundService(intent);
        }*/
    }

    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen((int) GravityCompat.START)) {
            drawer.closeDrawer((int) GravityCompat.START);
        } else if (!this.isProgressIsLoading) {
            super.onBackPressed();
        }
    }

    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id != R.id.nav_home) {
            if (id == R.id.disconnect) {
                checkData();
            }
        }
        ((DrawerLayout) findViewById(R.id.drawer_layout)).closeDrawer((int) GravityCompat.START);
        return true;
    }

    public void showDisconnectAlertDialog() {
        Builder builder = new Builder(this);
        builder.setTitle(Constants.DISCONNECT_FUN);
        builder.setMessage((CharSequence) "Disconnect will reset current KLYA. Are you sure..?");
        builder.setPositiveButton((CharSequence) "ok", new C04042());
        builder.setNegativeButton((CharSequence) "Cancel", new C04053());
        builder.create().show();
    }

    public void checkData() {
        if (!Constants.isNetworkAvailable(this)) {
            ToastUtils.showToast(this.accountNumTextView, this, "Please connect to internet");
        } else if (this.piggyDetails == null) {
            ToastUtils.showToast(this.accountNumTextView, this, "Please setup KLYA to disconnect");
        } else if (this.piggyDetails.isPiggyAttached()) {
            showDisconnectAlertDialog();
        } else {
            ToastUtils.showToast(this.accountNumTextView, this, "KLYA device already disconnected");
        }
    }

    protected void onResume() {
        super.onResume();
        if (this.isFromBluetoothPermission) {
            this.isFromBluetoothPermission = false;
            return;
        }
        PiggyBankApplication piggyBankApplication = (PiggyBankApplication) getApplicationContext();
        if (piggyBankApplication.isPiggyConnected()) {
            piggyBankApplication.setPiggyConnected(false);
            Log.d(TAG, "onResume: piggy isconnected");
            ColorDrawable colorDrawable = new ColorDrawable();
            colorDrawable.setColor(-1);
            this.piggyBackground.setBackground(colorDrawable);
            piggyBankApplication.getBluetoothGatt().disconnect();
        }
        try {
            setUpUI();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (isDeviceDisconnected) {
            isDeviceDisconnected = false;
            disconnectedDeviceDialog("Disconnected", "is already connected to another mobile. Please disconnect from that mobile device and then select ADD NEW KLYA");
        }
    }

    public void disconnectedDeviceDialog(String title, String message) {
        Builder builder = new Builder(this);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(title);
        builder.setTitle(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append(toyDeviceName);
        stringBuilder.append(" ");
        stringBuilder.append(message);
        builder.setMessage(stringBuilder.toString());
        builder.setPositiveButton((CharSequence) "ok", new C04064());
        builder.create().show();
    }

    public void initUiElements() {
        this.progressBarRL = (RelativeLayout) findViewById(R.id.progressBarRL);
        this.progressBarRL.setVisibility(View.GONE);
        this.imageViewGoal = (ImageView) findViewById(R.id.imageViewGoal);
        this.addNewPiggyTextView = (TextView) findViewById(R.id.addNewPiggy);
        this.addNewPiggyTextView.setOnClickListener(new C04075());
        this.updateBlance = (TextView) findViewById(R.id.updateBlance);
        this.updateBlance.setOnClickListener(new C04086());
        this.piggyBackground = (LinearLayout) findViewById(R.id.piggyBackground);
        this.goalLayout = (RelativeLayout) findViewById(R.id.goalLayout);
        this.relativeLayoutMiddleLayout = (RelativeLayout) findViewById(R.id.middleLayout);
        this.setUp = (Button) findViewById(R.id.setUp);
        this.relativeLayoutGoalAchieved = (RelativeLayout) findViewById(R.id.goalAchieved);
        this.relativeLayoutGoalAchieved.setVisibility(View.GONE);
        this.setUp.setOnClickListener(new C04097());
        this.showGoal = (SeekBar) findViewById(R.id.showGoal);
        this.text_seekbar = (TextView) findViewById(R.id.textViewSeek);
        this.accountNumTextView = (TextView) findViewById(R.id.accountNum);
        this.textViewPiggyBalance = (TextView) findViewById(R.id.PiggyBalance);
        this.textViewGoalName = (TextView) findViewById(R.id.goalName);
        this.textViewPiggyDetailsPiggyName = (TextView) findViewById(R.id.piggyDetailsPiggName);
        this.goalTextView = (TextView) findViewById(R.id.goalText);
        this.textViewUserName = (TextView) findViewById(R.id.userName);
        this.textViewAccountBalance = (TextView) findViewById(R.id.accountBalence);
        this.textViewGoalAmount = (TextView) findViewById(R.id.goalAmount);
        this.textViewTransfer = (LinearLayout) findViewById(R.id.transfer);
        this.textViewSetUpBluetooth = (LinearLayout) findViewById(R.id.setUpBlutooth);
        this.textViewSetGoal = (LinearLayout) findViewById(R.id.setGoal);
        this.textViewConnectionStatus = (TextView) findViewById(R.id.connectionStatus);
        this.relativeLayoutTopMask = (RelativeLayout) findViewById(R.id.topMask);
        this.relativeLayoutTopMask.setVisibility(View.VISIBLE);
        this.relativeLayoutMiddleLayout.setVisibility(View.GONE);
        this.textViewSetUpBluetooth.setOnClickListener(new C04108());
        this.textViewTransfer.setOnClickListener(new C04119());
        this.textViewSetGoal.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (!BluetoothUtils.isBluetoothSupported(PiggyBankHomeNavDrawer.this) || BluetoothUtils.bluetoothButtonEnabled(PiggyBankHomeNavDrawer.this)) {
                    PiggyBankHomeNavDrawer.this.startGoal();
                    return;
                }
                PiggyBankHomeNavDrawer.this.isFromAddNewKlya = false;
                PiggyBankHomeNavDrawer.this.isFromSyncKlya = false;
                PiggyBankHomeNavDrawer.this.isFromTransferKlya = false;
                PiggyBankHomeNavDrawer.this.isFromUpdateGoalKlya = true;
                PiggyBankHomeNavDrawer.this.isFromSetupKlya = false;
                BluetoothUtils.enableBlutooth(PiggyBankHomeNavDrawer.this);
            }
        });
    }

    public void startAddNewPiggy() {
        PiggyBankApplication piggyBankApplication = (PiggyBankApplication) getApplicationContext();
        if (this.piggyDetails == null || !this.piggyDetails.isPiggyAttached()) {
            startActivity(new Intent(this, PiggyBankShowToyDevicesActivity.class));
        } else {
            showDiscoonectAlertDialog();
        }
    }

    public void startSetUp() {
        startActivity(new Intent(this, PiggyBankShowToyDevicesActivity.class));
    }

    public boolean checkInsufficientFunds() {
        double parentBalance = getParentAccount().getBalance();
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("checkInsufficientFunds: ");
        stringBuilder.append(parentBalance);
        Log.d(str, stringBuilder.toString());
        str = TAG;
        stringBuilder = new StringBuilder();
        stringBuilder.append("checkInsufficientFunds: ");
        stringBuilder.append(parentBalance == 0.0d);
        Log.d(str, stringBuilder.toString());
        if (parentBalance == 0.0d) {
            return true;
        }
        return false;
    }

    public void startTransfer() {
        if (checkInsufficientFunds()) {
            showInsufficientBalanceAlert();
        } else {
            startActivity(new Intent(this, PiggyBankTransferAmountActivity.class));
        }
    }

    public void startGoal() {
        if (checkInsufficientFunds()) {
            showInsufficientBalanceAlert();
            return;
        }
        Intent intentPiggyBankSearchNear = new Intent(this, PiggyBankSetGoalActivity.class);
        intentPiggyBankSearchNear.putExtra("GoalText", this.goalTextView.getText().toString());
        startActivity(intentPiggyBankSearchNear);
    }

    public void callUpdateBalance() {
        Log.d(TAG, "callUpdateBalance: 1");
        Log.d(TAG, "callUpdateBalance: 2");
        Intent intentTransferConfirmation = new Intent(this, PiggyBankShowSearchScreenTransferGoalNew.class);
        intentTransferConfirmation.putExtra(Constants.isFrom, Constants.isFromTransfer);
        String str = Constants.CONST_PIGGY_BALANCE;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(this.piggyBalance);
        intentTransferConfirmation.putExtra(str, stringBuilder.toString());
        str = TAG;
        stringBuilder = new StringBuilder();
        stringBuilder.append("callUpdateBalance:1 ");
        stringBuilder.append(this.piggyBalance);
        Log.d(str, stringBuilder.toString());
        intentTransferConfirmation.putExtra("UpdateBalance", true);
        Log.d(TAG, "callUpdateBalance: "+this.account);

        if (!(this.account.getPiggyDetails() == null || !this.account.getPiggyDetails().isGoalCreated() || this.account.getPiggyDetails().getGoalName() == null || this.account.getPiggyDetails().getGoalName().isEmpty())) {
            str = Constants.GOAL_NAME;
            stringBuilder = new StringBuilder();
            stringBuilder.append("");
            stringBuilder.append(this.account.getPiggyDetails().getGoalName());
            intentTransferConfirmation.putExtra(str, stringBuilder.toString());
            str = Constants.GOALAMOUNT;
            stringBuilder = new StringBuilder();
            stringBuilder.append("");
            stringBuilder.append(this.account.getPiggyDetails().getGoalAmount());
            intentTransferConfirmation.putExtra(str, stringBuilder.toString());
        }
        startActivity(intentTransferConfirmation);
    }

    /*public void setUpUI() {
        Map<String, Account> map;
        Set<String> set;
        ArrayList<String> arrayList;
        PiggyBankApplication piggyBankApplication = (PiggyBankApplication) getApplicationContext();
        PiggyBankData piggyBankData = piggyBankApplication.getPiggyBankData();
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("setUpUI:---> ");
        stringBuilder.append(piggyBankData.getName().trim());
        Log.d(str, stringBuilder.toString());
        TextView textView = this.textViewUserName;
        stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(piggyBankData.getName().trim());
        textView.setText(stringBuilder.toString());
        Map<String, Account> accountHashMap = piggyBankData.getAccounts();
        Set<String> strings = accountHashMap.keySet();
        ArrayList<String> stringKeys = new ArrayList(strings);
        Iterator it;
        for (Iterator it2 = stringKeys.iterator(); it2.hasNext(); it2 = it) {
            PiggyBankApplication piggyBankApplication2;
            StringBuilder stringBuilder2;
            Account account = (Account) accountHashMap.get((String) it2.next());
            if (account.getAccountType().equals(Constants.ACCOUNT_TYPE_CHILD)) {
                TextView textView2 = accountNumTextView;
                StringBuilder stringBuilder3 = new StringBuilder();
                stringBuilder3.append("");
                stringBuilder3.append(account.getAccountNumber());
                textView2.setText(Constants.convertAccountNumberWithSpaces(stringBuilder3.toString()));
                stringBuilder3 = new StringBuilder();
                stringBuilder3.append("setUpUI: ");
                stringBuilder3.append(account.getBalance());
                Log.d("PiggyHome", stringBuilder3.toString());
                String str2 = TAG;
                stringBuilder3 = new StringBuilder();
                stringBuilder3.append("setUpUI:--->2 ");
                stringBuilder3.append(piggyBankData.getName().trim());
                Log.d(str2, stringBuilder3.toString());
                textView2 = textViewUserName;
                stringBuilder3 = new StringBuilder();
                stringBuilder3.append("");
                stringBuilder3.append(piggyBankData.getName().trim());
                textView2.setText(stringBuilder3.toString());
                piggyDetails = account.getPiggyDetails();
                account = account;
                if (piggyDetails != null) {
                    if (piggyDetails.isGoalCreated()) {
                        goalTextView.setText("Update Goal");
                        imageViewGoal.setImageResource(R.drawable.update_goal);
                        TextView textView3 = textViewGoalAmount;
                        StringBuilder stringBuilder4 = new StringBuilder();
                        stringBuilder4.append("");
                        StringBuilder stringBuilder5 = new StringBuilder();
                        stringBuilder5.append("");
                        stringBuilder5.append(piggyDetails.getGoalAmount());
                        stringBuilder4.append(Constants.generateCurrencyString(stringBuilder5.toString()));
                        textView3.setText(Constants.getFormatedAmount(stringBuilder4.toString()));
                        TextView textView4 = textViewGoalName;
                        StringBuilder stringBuilder6 = new StringBuilder();
                        stringBuilder6.append("");
                        stringBuilder6.append(piggyDetails.getGoalName());
                        textView4.setText(stringBuilder6.toString());
                        stringBuilder5 = new StringBuilder();
                        stringBuilder5.append("");
                        stringBuilder5.append(piggyDetails.getGoalAmount());
                        double goalAmount = Double.parseDouble(stringBuilder5.toString());
                        int childAmount = (int) account.getBalance();
                        int goal = (int) goalAmount;
                        piggyBankApplication2 = piggyBankApplication;
                        map = accountHashMap;
                        stringBuilder2 = new StringBuilder();
                        set = strings;
                        stringBuilder2.append("setUpUI: ");
                        stringBuilder2.append(childAmount);
                        Log.d("Home", stringBuilder2.toString());
                        stringBuilder2 = new StringBuilder();
                        stringBuilder2.append("setUpUI: ");
                        stringBuilder2.append(goal);
                        Log.d("Home", stringBuilder2.toString());
                        piggyBankApplication = textViewUserName;
                        stringBuilder2 = new StringBuilder();
                        stringBuilder2.append("");
                        stringBuilder2.append(piggyBankData.getName().trim());
                        piggyBankApplication.setText(stringBuilder2.toString());
                        if (childAmount >= goal) {
                            Log.d(TAG, "setUpUI: If Condition");
                            showGoal.setMax(100);
                            showGoal.setProgress(100);
                            arrayList = stringKeys;
                            text_seekbar.setX((showGoal.getX() + ((float) (((showGoal.getWidth() - (showGoal.getThumbOffset() * 2)) * 87) / showGoal.getMax()))) + ((float) (showGoal.getThumbOffset() / 2)));
                            text_seekbar.setText("100 %");
                            showGoal.setVisibility(View.INVISIBLE);
                            text_seekbar.setVisibility(View.INVISIBLE);
                            relativeLayoutGoalAchieved.setVisibility(View.VISIBLE);
                            goalTextView.setText("Set New Goal");
                            imageViewGoal.setImageResource(R.drawable.update_goal);
                            it = it2;
                        } else {
                            arrayList = stringKeys;
                            Log.d(TAG, "setUpUI: Else Condition");
                            it = it2;
                            piggyBankApplication = (int) ((((double) childAmount) / ((double) goal)) * 100.0d);
                            showGoal.setMax(100);
                            showGoal.setProgress(piggyBankApplication);
                            new Handler().postDelayed(new Runnable() {
                                public void run() {
                                    if (piggyBankApplication > 88) {
                                        PiggyBankHomeNavDrawer.this.text_seekbar.setX((PiggyBankHomeNavDrawer.this.showGoal.getX() + ((float) (((PiggyBankHomeNavDrawer.this.showGoal.getWidth() - (PiggyBankHomeNavDrawer.this.showGoal.getThumbOffset() * 2)) * 87) / PiggyBankHomeNavDrawer.this.showGoal.getMax()))) + ((float) (PiggyBankHomeNavDrawer.this.showGoal.getThumbOffset() / 2)));
                                        TextView access$600 = PiggyBankHomeNavDrawer.this.text_seekbar;
                                        StringBuilder stringBuilder = new StringBuilder();
                                        stringBuilder.append("");
                                        stringBuilder.append(piggyBankApplication);
                                        stringBuilder.append(" %");
                                        access$600.setText(stringBuilder.toString());
                                        return;
                                    }
                                    PiggyBankHomeNavDrawer.this.text_seekbar.setX((PiggyBankHomeNavDrawer.this.showGoal.getX() + ((float) ((piggyBankApplication * (PiggyBankHomeNavDrawer.this.showGoal.getWidth() - (PiggyBankHomeNavDrawer.this.showGoal.getThumbOffset() * 2))) / PiggyBankHomeNavDrawer.this.showGoal.getMax()))) + ((float) (PiggyBankHomeNavDrawer.this.showGoal.getThumbOffset() / 2)));
                                    access$600 = PiggyBankHomeNavDrawer.this.text_seekbar;
                                    StringBuilder stringBuilder = new StringBuilder();
                                    stringBuilder.append("");
                                    stringBuilder.append(piggyBankApplication);
                                    stringBuilder.append(" %");
                                    access$600.setText(stringBuilder.toString());
                                }
                            }, 1000);
                            showGoal.setVisibility(View.VISIBLE);
                            text_seekbar.setVisibility(View.VISIBLE);
                            relativeLayoutGoalAchieved.setVisibility(View.GONE);
                            str = TAG;
                            stringBuilder = new StringBuilder();
                            stringBuilder.append("setUpUI: X value");
                            stringBuilder.append(text_seekbar.getX());
                            Log.d(str, stringBuilder.toString());
                        }
                        showGoal.setEnabled(false);
                        showGoal.setProgressDrawable(ContextCompat.getDrawable(this, R.drawable.progress));
                    } else {
                        piggyBankApplication2 = piggyBankApplication;
                        map = accountHashMap;
                        set = strings;
                        arrayList = stringKeys;
                        it = it2;
                        text_seekbar.setVisibility(View.GONE);
                        showGoal.setProgressDrawable(ContextCompat.getDrawable(this, R.drawable.background_fill));
                    }
                    TextView textView5;
                    StringBuilder stringBuilder7;
                    StringBuilder stringBuilder8;
                    ColorDrawable colorDrawable;
                    if (piggyDetails.isPiggyAttached() != null) {
                        textView5 = textViewPiggyDetailsPiggyName;
                        stringBuilder7 = new StringBuilder();
                        stringBuilder7.append("");
                        stringBuilder7.append(piggyDetails.getPiggyName().trim());
                        textView5.setText(stringBuilder7.toString());
                        piggyBalance = account.getBalance();
                        textView5 = textViewPiggyBalance;
                        stringBuilder7 = new StringBuilder();
                        stringBuilder7.append("");
                        stringBuilder8 = new StringBuilder();
                        stringBuilder8.append("");
                        stringBuilder8.append(account.getBalance());
                        stringBuilder7.append(Constants.generateCurrencyString(stringBuilder8.toString()));
                        textView5.setText(Constants.getFormatedAmount(stringBuilder7.toString()));
                        relativeLayoutTopMask.setVisibility(View.GONE);
                        relativeLayoutMiddleLayout.setVisibility(View.VISIBLE);
                        textViewConnectionStatus.setText("Connected");
                        updateBlance.setVisibility(View.VISIBLE);
                        colorDrawable = new ColorDrawable();
                        colorDrawable.setColor(-1);
                        piggyBackground.setBackground(colorDrawable);
                        if (!defaultColorsApplied) {
                            showColors();
                        }
                    } else if (piggyDetails == null || piggyDetails.getPiggyName().isEmpty()) {
                        textView = textViewUserName;
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("");
                        stringBuilder.append(piggyBankData.getName().trim());
                        textView.setText(stringBuilder.toString());
                        relativeLayoutTopMask.setVisibility(View.VISIBLE);
                        relativeLayoutMiddleLayout.setVisibility(View.GONE);
                        updateBlance.setVisibility(View.GONE);
                        accountHashMap = new ColorDrawable();
                        accountHashMap.setColor(getResources().getColor(R.color.gray_color));
                        piggyBackground.setBackground(accountHashMap);
                    } else {
                        textView5 = textViewPiggyDetailsPiggyName;
                        stringBuilder7 = new StringBuilder();
                        stringBuilder7.append("");
                        stringBuilder7.append(piggyDetails.getPiggyName().trim());
                        textView5.setText(stringBuilder7.toString());
                        piggyBalance = account.getBalance();
                        textView5 = textViewPiggyBalance;
                        stringBuilder7 = new StringBuilder();
                        stringBuilder7.append("");
                        stringBuilder8 = new StringBuilder();
                        stringBuilder8.append("");
                        stringBuilder8.append(account.getBalance());
                        stringBuilder7.append(Constants.generateCurrencyString(stringBuilder8.toString()));
                        textView5.setText(Constants.getFormatedAmount(stringBuilder7.toString()));
                        relativeLayoutTopMask.setVisibility(View.GONE);
                        relativeLayoutMiddleLayout.setVisibility(View.VISIBLE);
                        textViewConnectionStatus.setText("Disconnected");
                        defaultColorsApplied = false;
                        updateBlance.setVisibility(View.VISIBLE);
                        colorDrawable = new ColorDrawable();
                        colorDrawable.setColor(-1);
                        piggyBackground.setBackground(colorDrawable);
                        updateBlance.getBackground().setAlpha(100);
                        updateBlance.setEnabled(false);
                        updateBlance.setTextColor(getResources().getColor(R.color.colorPrimary));
                        textViewTransfer.getBackground().setAlpha(255);
                        textViewTransfer.setEnabled(false);
                        textViewSetGoal.getBackground().setAlpha(255);
                        textViewSetGoal.setEnabled(false);
                        ColorDrawable color = new ColorDrawable();
                        color.setColor(getResources().getColor(R.color.progress_bar_layout_color1));
                        textViewTransfer.setBackground(color);
                        textViewSetGoal.setBackground(color);
                    }
                } else {
                    piggyBankApplication2 = piggyBankApplication;
                    map = accountHashMap;
                    set = strings;
                    arrayList = stringKeys;
                    it = it2;
                    piggyBankApplication = textViewUserName;
                    stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("");
                    stringBuilder2.append(piggyBankData.getName().trim());
                    piggyBankApplication.setText(stringBuilder2.toString());
                    relativeLayoutTopMask.setVisibility(View.VISIBLE);
                    relativeLayoutMiddleLayout.setVisibility(View.GONE);
                    updateBlance.setVisibility(View.GONE);
                    piggyBankApplication = new ColorDrawable();
                    piggyBankApplication.setColor(getResources().getColor(R.color.backgroundColor));
                    piggyBackground.setBackground(piggyBankApplication);
                }
            } else {
                piggyBankApplication2 = piggyBankApplication;
                map = accountHashMap;
                set = strings;
                arrayList = stringKeys;
                it = it2;
            }
            if (account.getAccountType().equals(Constants.ACCOUNT_TYPE_PARENT)) {
                TextView textView6 = textViewAccountBalance;
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append("");
                stringBuilder = new StringBuilder();
                stringBuilder.append("");
                stringBuilder.append(account.getBalance());
                stringBuilder2.append(Constants.generateCurrencyString(stringBuilder.toString()));
                textView6.setText(Constants.getFormatedAmount(stringBuilder2.toString()));
            }
            piggyBankApplication = piggyBankApplication2;
            accountHashMap = map;
            strings = set;
            stringKeys = arrayList;
        }
        map = accountHashMap;
        set = strings;
        arrayList = stringKeys;
        if (goalTextView.getText().toString().equals("Set Goal")) {
            goalLayout.setVisibility(View.GONE);
        } else {
            goalLayout.setVisibility(View.VISIBLE);
        }
    }*/
    public void setUpUI() {
        Map<String, Account> map;
        Set<String> set;
        ArrayList<String> arrayList;
        PiggyBankApplication piggyBankApplication = (PiggyBankApplication) getApplicationContext();
        PiggyBankData piggyBankData = piggyBankApplication.getPiggyBankData();

        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        //stringBuilder.append("setUpUI:---> ");
        stringBuilder.append(piggyBankData.getName().trim());
        Log.d(str, stringBuilder.toString());
        TextView textView = this.textViewUserName;
        stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(piggyBankData.getName().trim());
        textView.setText(stringBuilder.toString());
        Map<String, Account> accountHashMap = piggyBankData.getAccounts();
        Set<String> strings = accountHashMap.keySet();
        ArrayList<String> stringKeys = new ArrayList(strings);
        Iterator it;
        for (Iterator it2 = stringKeys.iterator(); it2.hasNext(); it2 = it) {
            PiggyBankApplication piggyBankApplication2;
            StringBuilder stringBuilder2;
            Account account = (Account) accountHashMap.get((String) it2.next());
            if (account.getAccountType().equals(Constants.ACCOUNT_TYPE_CHILD)) {
                TextView textView2 = accountNumTextView;
                StringBuilder stringBuilder3 = new StringBuilder();
                stringBuilder3.append("");
                stringBuilder3.append(account.getAccountNumber());
                textView2.setText(Constants.convertAccountNumberWithSpaces(stringBuilder3.toString()));
                stringBuilder3 = new StringBuilder();
                stringBuilder3.append("setUpUI: ");
                stringBuilder3.append(account.getBalance());
                Log.d("PiggyHome", stringBuilder3.toString());
                String str2 = TAG;
                stringBuilder3 = new StringBuilder();
                stringBuilder3.append("setUpUI:--->2 ");
                stringBuilder3.append(piggyBankData.getName().trim());
                Log.d(str2, stringBuilder3.toString());
                textView2 = textViewUserName;
                stringBuilder3 = new StringBuilder();
                stringBuilder3.append("");
                stringBuilder3.append(piggyBankData.getName().trim());
                textView2.setText(stringBuilder3.toString());
                piggyDetails = account.getPiggyDetails();
                this.account = account;

                if (piggyDetails != null) {
                    if (piggyDetails.isGoalCreated()) {
                        goalTextView.setText("Update Goal");
                        imageViewGoal.setImageResource(R.drawable.update_goal);
                        TextView textView3 = textViewGoalAmount;
                        StringBuilder stringBuilder4 = new StringBuilder();
                        stringBuilder4.append("");
                        StringBuilder stringBuilder5 = new StringBuilder();
                        stringBuilder5.append("");
                        stringBuilder5.append(piggyDetails.getGoalAmount());
                        stringBuilder4.append(Constants.generateCurrencyString(stringBuilder5.toString()));
                        textView3.setText(Constants.getFormatedAmount(stringBuilder4.toString()));
                        TextView textView4 = textViewGoalName;
                        StringBuilder stringBuilder6 = new StringBuilder();
                        stringBuilder6.append("");
                        stringBuilder6.append(piggyDetails.getGoalName());
                        textView4.setText(stringBuilder6.toString());
                        stringBuilder5 = new StringBuilder();
                        stringBuilder5.append("");
                        stringBuilder5.append(piggyDetails.getGoalAmount());
                        double goalAmount = Double.parseDouble(stringBuilder5.toString());
                        int childAmount = (int) account.getBalance();
                        int goal = (int) goalAmount;
                        piggyBankApplication2 = piggyBankApplication;
                        map = accountHashMap;
                        stringBuilder2 = new StringBuilder();
                        set = strings;
                        stringBuilder2.append("setUpUI: ");
                        stringBuilder2.append(childAmount);
                        Log.d("Home", stringBuilder2.toString());
                        stringBuilder2 = new StringBuilder();
                        stringBuilder2.append("setUpUI: ");
                        stringBuilder2.append(goal);
                        Log.d("Home", stringBuilder2.toString());
                        //piggyBankApplication = textViewUserName;
                        stringBuilder2 = new StringBuilder();
                        stringBuilder2.append("");
                        stringBuilder2.append(piggyBankData.getName().trim());
                        textViewUserName.setText(stringBuilder2.toString());
                        if (childAmount >= goal) {
                            Log.d(TAG, "setUpUI: If Condition");
                            showGoal.setMax(100);
                            showGoal.setProgress(100);
                            arrayList = stringKeys;
                            text_seekbar.setX((showGoal.getX() + ((float) (((showGoal.getWidth() - (showGoal.getThumbOffset() * 2)) * 87) / showGoal.getMax()))) + ((float) (showGoal.getThumbOffset() / 2)));
                            text_seekbar.setText("100 %");
                            showGoal.setVisibility(View.INVISIBLE);
                            text_seekbar.setVisibility(View.INVISIBLE);
                            relativeLayoutGoalAchieved.setVisibility(View.VISIBLE);
                            goalTextView.setText("Set New Goal");
                            imageViewGoal.setImageResource(R.drawable.update_goal);
                            it = it2;
                        } else {
                            arrayList = stringKeys;
                            Log.d(TAG, "setUpUI: Else Condition");
                            it = it2;
                            final int valueInt = (int) ((((double) childAmount) / ((double) goal)) * 100.0d);
                            showGoal.setMax(100);
                            showGoal.setProgress(valueInt);
                            new Handler().postDelayed(new Runnable() {
                                public void run() {
                                    if (valueInt > 88) {
                                        PiggyBankHomeNavDrawer.this.text_seekbar.setX((PiggyBankHomeNavDrawer.this.showGoal.getX() + ((float) (((PiggyBankHomeNavDrawer.this.showGoal.getWidth() - (PiggyBankHomeNavDrawer.this.showGoal.getThumbOffset() * 2)) * 87) / PiggyBankHomeNavDrawer.this.showGoal.getMax()))) + ((float) (PiggyBankHomeNavDrawer.this.showGoal.getThumbOffset() / 2)));
                                        TextView access$600 = PiggyBankHomeNavDrawer.this.text_seekbar;
                                        StringBuilder stringBuilder = new StringBuilder();
                                        stringBuilder.append("");
                                        stringBuilder.append(valueInt);
                                        stringBuilder.append(" %");
                                        PiggyBankHomeNavDrawer.this.text_seekbar.setText(""+stringBuilder.toString());
                                        //access$600.setText(stringBuilder.toString());
                                        return;
                                    }
                                    PiggyBankHomeNavDrawer.this.text_seekbar.setX((PiggyBankHomeNavDrawer.this.showGoal.getX() + ((float) ((valueInt * (PiggyBankHomeNavDrawer.this.showGoal.getWidth() - (PiggyBankHomeNavDrawer.this.showGoal.getThumbOffset() * 2))) / PiggyBankHomeNavDrawer.this.showGoal.getMax()))) + ((float) (PiggyBankHomeNavDrawer.this.showGoal.getThumbOffset() / 2)));
                                    //access$600 = PiggyBankHomeNavDrawer.this.text_seekbar;
                                    StringBuilder stringBuilder = new StringBuilder();
                                    stringBuilder.append("");
                                    stringBuilder.append(valueInt);
                                    stringBuilder.append(" %");
                                    PiggyBankHomeNavDrawer.this.text_seekbar.setText(""+stringBuilder.toString());
                                    //access$600.setText(stringBuilder.toString());

                                }
                            }, 1000);
                            showGoal.setVisibility(View.VISIBLE);
                            text_seekbar.setVisibility(View.VISIBLE);
                            relativeLayoutGoalAchieved.setVisibility(View.GONE);
                            str = TAG;
                            stringBuilder = new StringBuilder();
                            stringBuilder.append("setUpUI: X value");
                            stringBuilder.append(text_seekbar.getX());
                            Log.d(str, stringBuilder.toString());
                        }
                        showGoal.setEnabled(false);
                        showGoal.setProgressDrawable(ContextCompat.getDrawable(this, R.drawable.progress));
                    } else {
                        piggyBankApplication2 = piggyBankApplication;
                        map = accountHashMap;
                        set = strings;
                        arrayList = stringKeys;
                        it = it2;
                        text_seekbar.setVisibility(View.GONE);
                        showGoal.setProgressDrawable(ContextCompat.getDrawable(this, R.drawable.background_fill));
                    }
                    TextView textView5;
                    StringBuilder stringBuilder7;
                    StringBuilder stringBuilder8;
                    ColorDrawable colorDrawable;
                    if (piggyDetails!=null&&piggyDetails.isPiggyAttached()) {
                        textView5 = textViewPiggyDetailsPiggyName;
                        stringBuilder7 = new StringBuilder();
                        stringBuilder7.append("");
                        stringBuilder7.append(piggyDetails.getPiggyName().trim());
                        textView5.setText(stringBuilder7.toString());
                        piggyBalance = account.getBalance();
                        textView5 = textViewPiggyBalance;
                        stringBuilder7 = new StringBuilder();
                        stringBuilder7.append("");
                        stringBuilder8 = new StringBuilder();
                        stringBuilder8.append("");
                        stringBuilder8.append(account.getBalance());
                        stringBuilder7.append(Constants.generateCurrencyString(stringBuilder8.toString()));
                        textView5.setText(Constants.getFormatedAmount(stringBuilder7.toString()));
                        relativeLayoutTopMask.setVisibility(View.GONE);
                        relativeLayoutMiddleLayout.setVisibility(View.VISIBLE);
                        textViewConnectionStatus.setText("Connected");
                        updateBlance.setVisibility(View.VISIBLE);
                        colorDrawable = new ColorDrawable();
                        colorDrawable.setColor(-1);
                        piggyBackground.setBackground(colorDrawable);
                        if (!defaultColorsApplied) {
                            showColors();
                        }
                    } else if (piggyDetails == null || piggyDetails.getPiggyName().isEmpty()) {
                        textView = textViewUserName;
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("");
                        stringBuilder.append(piggyBankData.getName().trim());
                        textView.setText(stringBuilder.toString());
                        relativeLayoutTopMask.setVisibility(View.VISIBLE);
                        relativeLayoutMiddleLayout.setVisibility(View.GONE);
                        updateBlance.setVisibility(View.GONE);
                        ColorDrawable colorDrawable1 = new ColorDrawable();
                        colorDrawable1.setColor(getResources().getColor(R.color.gray_color));
                        piggyBackground.setBackground(colorDrawable1);
                    } else {
                        textView5 = textViewPiggyDetailsPiggyName;
                        stringBuilder7 = new StringBuilder();
                        stringBuilder7.append("");
                        stringBuilder7.append(piggyDetails.getPiggyName().trim());
                        textView5.setText(stringBuilder7.toString());
                        piggyBalance = account.getBalance();
                        textView5 = textViewPiggyBalance;
                        stringBuilder7 = new StringBuilder();
                        stringBuilder7.append("");
                        stringBuilder8 = new StringBuilder();
                        stringBuilder8.append("");
                        stringBuilder8.append(account.getBalance());
                        stringBuilder7.append(Constants.generateCurrencyString(stringBuilder8.toString()));
                        textView5.setText(Constants.getFormatedAmount(stringBuilder7.toString()));
                        relativeLayoutTopMask.setVisibility(View.GONE);
                        relativeLayoutMiddleLayout.setVisibility(View.VISIBLE);
                        textViewConnectionStatus.setText("Disconnected");
                        defaultColorsApplied = false;
                        updateBlance.setVisibility(View.VISIBLE);
                        colorDrawable = new ColorDrawable();
                        colorDrawable.setColor(-1);
                        piggyBackground.setBackground(colorDrawable);
                        updateBlance.getBackground().setAlpha(100);
                        updateBlance.setEnabled(false);
                        updateBlance.setTextColor(getResources().getColor(R.color.colorPrimary));
                        textViewTransfer.getBackground().setAlpha(255);
                        textViewTransfer.setEnabled(false);
                        textViewSetGoal.getBackground().setAlpha(255);
                        textViewSetGoal.setEnabled(false);
                        ColorDrawable color = new ColorDrawable();
                        color.setColor(getResources().getColor(R.color.progress_bar_layout_color1));
                        textViewTransfer.setBackground(color);
                        textViewSetGoal.setBackground(color);
                    }
                } else {
                    piggyBankApplication2 = piggyBankApplication;
                    map = accountHashMap;
                    set = strings;
                    arrayList = stringKeys;
                    it = it2;
                    //piggyBankApplication = textViewUserName;
                    stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("");
                    stringBuilder2.append(piggyBankData.getName().trim());
                    textViewUserName.setText(stringBuilder2.toString());
                    relativeLayoutTopMask.setVisibility(View.VISIBLE);
                    relativeLayoutMiddleLayout.setVisibility(View.GONE);
                    updateBlance.setVisibility(View.GONE);
                    //piggyBankApplication = new ColorDrawable();
                    ColorDrawable colorDrawable = new ColorDrawable();
                    colorDrawable.setColor(getResources().getColor(R.color.backgroundColor));
                    piggyBackground.setBackground(colorDrawable);
                }
            } else {
                piggyBankApplication2 = piggyBankApplication;
                map = accountHashMap;
                set = strings;
                arrayList = stringKeys;
                it = it2;
            }
            if (account.getAccountType().equals(Constants.ACCOUNT_TYPE_PARENT)) {
                TextView textView6 = textViewAccountBalance;
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append("");
                stringBuilder = new StringBuilder();
                stringBuilder.append("");
                stringBuilder.append(account.getBalance());
                stringBuilder2.append(Constants.generateCurrencyString(stringBuilder.toString()));
                textView6.setText(Constants.getFormatedAmount(stringBuilder2.toString()));
            }
            piggyBankApplication = piggyBankApplication2;
            accountHashMap = map;
            strings = set;
            stringKeys = arrayList;
        }
        map = accountHashMap;
        set = strings;
        arrayList = stringKeys;
        if (goalTextView.getText().toString().equals("Set Goal")) {
            goalLayout.setVisibility(View.GONE);
        } else {
            goalLayout.setVisibility(View.VISIBLE);
        }
    }

    public void showColors() {
        this.defaultColorsApplied = true;
        this.updateBlance.setBackgroundResource(R.drawable.background_synk);
        this.updateBlance.setTextColor(getResources().getColor(R.color.text_color_white));
        this.updateBlance.setEnabled(true);
        this.textViewTransfer.getBackground().setAlpha(0);
        this.textViewTransfer.setEnabled(true);
        this.textViewSetGoal.getBackground().setAlpha(0);
        this.textViewSetGoal.setEnabled(true);
        this.textViewTransfer.setBackgroundResource(R.drawable.button_background);
        this.textViewSetGoal.setBackgroundResource(R.drawable.button_background);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (resultCode != 0) {
            if (this.isFromAddNewKlya) {
                startAddNewPiggy();
            }
            if (this.isFromSyncKlya) {
                Log.d(TAG, "onActivityResult: 0");
                callUpdateBalance();
            }
            if (this.isFromTransferKlya) {
                startTransfer();
            }
            if (this.isFromUpdateGoalKlya) {
                startGoal();
            }
            if (this.isFromSetupKlya) {
                startSetUp();
            }
        }
        this.isFromBluetoothPermission = true;
    }

    public void showDiscoonectAlertDialog() {
        Builder builder = new Builder(this);
        builder.setTitle((CharSequence) "Confirm");
        builder.setMessage((CharSequence) "Please disconnect from current device before adding new KLYA");
        builder.setPositiveButton(Constants.DISCONNECT_FUN, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (Constants.isNetworkAvailable(PiggyBankHomeNavDrawer.this)) {
                    PiggyBankHomeNavDrawer.this.callDisconnectFunctionality(true, false, false);
                } else {
                    ToastUtils.showToast(PiggyBankHomeNavDrawer.this.showGoal, PiggyBankHomeNavDrawer.this, "Please connect to internet");
                }
            }
        });
        builder.setNegativeButton((CharSequence) "Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    public void callDisconnectFunctionality(boolean enableAutoSeachFunctionality, boolean makeBlur, boolean isfromReset) {
        callDisconnectFun(enableAutoSeachFunctionality, isfromReset);
    }

    public void callDisconnectFun(boolean enableAutoSearching, boolean isfromRest) {
        Intent intentTransferConfirmation = new Intent(this, PiggyBankShowSearchScreenTransferGoalNew.class);
        intentTransferConfirmation.putExtra(Constants.isFrom, Constants.isFromTransfer);
        intentTransferConfirmation.putExtra(Constants.DISCONNECT_FUN, true);
        intentTransferConfirmation.putExtra(Constants.ENABLE_AUTO_SEARCH_FUN, enableAutoSearching);
        intentTransferConfirmation.putExtra(Constants.IS_FROM_RESET, isfromRest);
        startActivity(intentTransferConfirmation);
    }

    public void showInsufficientBalanceAlert() {
        Builder builder = new Builder(this);
        builder.setTitle((CharSequence) "Reset");
        builder.setMessage((CharSequence) "Insufficient balance you can't perform any action, Please reset data to continue");
        builder.setPositiveButton((CharSequence) "Reset", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (Constants.isNetworkAvailable(PiggyBankHomeNavDrawer.this)) {
                    PiggyBankHomeNavDrawer.this.storePiggyDataIntoFirebase();
                } else {
                    ToastUtils.showToast(PiggyBankHomeNavDrawer.this.showGoal, PiggyBankHomeNavDrawer.this, "Please connect to internet");
                }
            }
        });
        builder.setNegativeButton((CharSequence) "Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.create().show();
    }

    public void storePiggyDataIntoFirebase() {
        this.progressBarRL.setVisibility(View.VISIBLE);
        this.isProgressIsLoading = true;
        this.mdraweDrawerLayout.setDrawerLockMode(1);
        PiggyBankData piggyBankData = ((PiggyBankApplication) getApplicationContext()).getPiggyBankData();
        Map<String, Account> stringAccountMap = new HashMap();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(getChildAccountKey());
        stringAccountMap.put(stringBuilder.toString(), getChildAccount());
        Account accountParent = getParentAccount();
        Account accountChild = getChildAccount();
        accountParent.setBalance(9999.0d);
        accountChild.setBalance(0.0d);
        PiggyDetails piggyDetails = accountChild.getPiggyDetails();
        piggyDetails.setGoalName(Constants.isFromGoal);
        piggyDetails.setGoalAmount(Double.valueOf(9999.0d));
        accountChild.setPiggyDetails(piggyDetails);
        String str = TAG;
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("storePiggyDataIntoFirebase Parent Account Key: ");
        stringBuilder2.append(getParentAccountKey());
        Log.d(str, stringBuilder2.toString());
        str = TAG;
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append("storePiggyDataIntoFirebase Child Account Key: ");
        stringBuilder2.append(getChildAccountKey());
        Log.d(str, stringBuilder2.toString());
        StringBuilder stringBuilder3 = new StringBuilder();
        stringBuilder3.append("");
        stringBuilder3.append(getParentAccountKey());
        stringAccountMap.put(stringBuilder3.toString(), getParentAccount());
        piggyBankData.setAccounts(stringAccountMap);
        new FireBaseManger().savePiggyBankData(this, piggyBankData, new SavePiggyDetailsListner() {
            public void onSave(boolean isSaved) {
                PiggyBankHomeNavDrawer.this.progressBarRL.setVisibility(View.GONE);
                PiggyBankHomeNavDrawer.this.mdraweDrawerLayout.setDrawerLockMode(0);
                PiggyBankHomeNavDrawer.this.isProgressIsLoading = false;
                PiggyBankHomeNavDrawer.this.callDisconnectFunctionality(false, true, true);
            }
        });
    }

    public String getParentAccountKey() {
        return this.parentAccountKey;
    }

    public String getChildAccountKey() {
        return this.childAccountKey;
    }

    public Account getParentAccount() {
        Map<String, Account> stringAccountMap = ((PiggyBankApplication) getApplicationContext()).getPiggyBankData().getAccounts();
        ArrayList<String> stringArrayList = new ArrayList(stringAccountMap.keySet());
        for (int i = 0; i < stringArrayList.size(); i++) {
            Account account = (Account) stringAccountMap.get(stringArrayList.get(i));
            if (account.getAccountType().equals(Constants.ACCOUNT_TYPE_PARENT)) {
                this.parentAccountKey = (String) stringArrayList.get(i);
                return account;
            }
        }
        return null;
    }

    public Account getChildAccount() {
        Map<String, Account> stringAccountMap = ((PiggyBankApplication) getApplicationContext()).getPiggyBankData().getAccounts();
        ArrayList<String> stringArrayList = new ArrayList(stringAccountMap.keySet());
        for (int i = 0; i < stringArrayList.size(); i++) {
            Account account = (Account) stringAccountMap.get(stringArrayList.get(i));
            if (account.getAccountType().equals(Constants.ACCOUNT_TYPE_CHILD)) {
                this.childAccountKey = (String) stringArrayList.get(i);
                return account;
            }
        }
        return null;
    }
}
