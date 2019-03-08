package hexadots.in.piaggybankappfinal.activities;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import hexadots.in.piaggybankappfinal.R;
import hexadots.in.piaggybankappfinal.Constants;
import hexadots.in.piaggybankappfinal.DialogUtils;
import hexadots.in.piaggybankappfinal.PassDataToPiggy;
import hexadots.in.piaggybankappfinal.PiggyBankApplication;
import hexadots.in.piaggybankappfinal.ToastUtils;
import hexadots.in.piaggybankappfinal.bean.Account;
import hexadots.in.piaggybankappfinal.bean.PiggyBankData;
import hexadots.in.piaggybankappfinal.bean.PiggyDetails;
import hexadots.in.piaggybankappfinal.communication.BluetoothGattInteraction;
import hexadots.in.piaggybankappfinal.firebase.FireBaseManger;
import hexadots.in.piaggybankappfinal.interfaces.PiggyUpdatedListener;
import hexadots.in.piaggybankappfinal.interfaces.SavePiggyDetailsListner;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PiggyBankConfirmationActivity extends BaseActivity implements SavePiggyDetailsListner {
    private Account account;
    private String deviceAddress;
    private String deviceName;
    Dialog dialog;
    PassDataToPiggy passDataToPiggy;
    private PiggyDetails piggyDetails;
    private String piggyName;
    private RelativeLayout progressBarRL;
    private TextView textViewBalance;
    private TextView textViewDate;
    private TextView textViewFinishSetUpProcess;
    private TextView textViewLinkedAcc;
    private TextView textViewPiggyDeviceName;
    private TextView textViewPiggyName;

    /* renamed from: hexadots.in.piaggybankappfinal.activities.PiggyBankConfirmationActivity$1 */
    class C03981 implements OnClickListener {
        C03981() {
        }

        public void onClick(View view) {
        }
    }

    /* renamed from: hexadots.in.piaggybankappfinal.activities.PiggyBankConfirmationActivity$2 */
    class C03992 implements OnClickListener {
        C03992() {
        }

        public void onClick(View view) {
            if (Constants.isNetworkAvailable(PiggyBankConfirmationActivity.this)) {
                PiggyBankConfirmationActivity.this.storeIntoFirebase();
            } else {
                ToastUtils.showToast(PiggyBankConfirmationActivity.this.textViewBalance, PiggyBankConfirmationActivity.this, "Please connect to internet");
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piggy_bank_confirmation);
        this.progressBarRL = (RelativeLayout) findViewById(R.id.progressBarRL);
        this.progressBarRL.setOnClickListener(new C03981());
        this.deviceName = getIntent().getStringExtra(Constants.CONST_DEVICE_NAME);
        this.deviceAddress = getIntent().getStringExtra(Constants.CONST_DEVICE_ADDRES);
        this.piggyName = getIntent().getStringExtra(Constants.PIGGY_NMAE);
        this.textViewPiggyName = (TextView) findViewById(R.id.piggyBankName);
        this.textViewLinkedAcc = (TextView) findViewById(R.id.linkedAccountNumber);
        this.textViewBalance = (TextView) findViewById(R.id.balance);
        this.textViewDate = (TextView) findViewById(R.id.date);
        this.textViewPiggyDeviceName = (TextView) findViewById(R.id.piggyDeviceName);
        TextView textView = this.textViewPiggyDeviceName;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(this.deviceName);
        textView.setText(stringBuilder.toString());
        this.textViewFinishSetUpProcess = (TextView) findViewById(R.id.finish);
        this.account = getAccount();
        this.piggyDetails = this.account.getPiggyDetails();
        textView = this.textViewPiggyName;
        stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(this.piggyName);
        textView.setText(stringBuilder.toString());
        textView = this.textViewLinkedAcc;
        stringBuilder = new StringBuilder();
        stringBuilder.append("");
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("");
        stringBuilder2.append(this.account.getAccountNumber());
        stringBuilder.append(Constants.convertAccountNumberWithSpaces(stringBuilder2.toString()));
        textView.setText(stringBuilder.toString());
        StringBuilder stringBuilder3 = new StringBuilder();
        stringBuilder3.append("");
        stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(this.account.getBalance());
        stringBuilder3.append(Constants.generateCurrencyString(stringBuilder.toString()));
        String amount = Constants.getFormatedAmount(stringBuilder3.toString());
        TextView textView2 = this.textViewBalance;
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append("");
        stringBuilder2.append(amount);
        textView2.setText(stringBuilder2.toString());
        try {
            this.textViewDate.setText(this.piggyDetails.getPiggyLastConnectedDateAndTime());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        this.textViewFinishSetUpProcess.setOnClickListener(new C03992());
    }

    protected void onPause() {
        super.onPause();
        if (this.passDataToPiggy != null) {
            this.passDataToPiggy.removeHandler();
        }
    }

    protected void onResume() {
        super.onResume();
    }

    public void onBackPressed() {
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 16908332) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void onSave(boolean isSaved) {
        if (isSaved) {
            this.progressBarRL.setVisibility(View.GONE);
            this.dialog = DialogUtils.showDialogUtils(this, "Updating KLYA ", 29000);
            updateToPiggy(this.dialog);
        }
    }

    public void updateToPiggy(final Dialog dialog) {
        this.passDataToPiggy = new PassDataToPiggy();
        BluetoothGattInteraction bluetoothGattInteraction = new BluetoothGattInteraction();
        PassDataToPiggy passDataToPiggy = this.passDataToPiggy;
        String str = this.piggyName;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(this.account.getAccountNumber());
        String stringBuilder2 = stringBuilder.toString();
        String goalName = this.piggyDetails.getGoalName();
        stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(this.piggyDetails.getGoalAmount());
        String stringBuilder3 = stringBuilder.toString();
        stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(this.account.getBalance());
        passDataToPiggy.updateName(bluetoothGattInteraction, str, stringBuilder2, this, goalName, stringBuilder3, stringBuilder.toString(), new PiggyUpdatedListener() {
            public void onPiggyUpdated() {
                PiggyBankConfirmationActivity.this.progressBarRL.setVisibility(View.GONE);
                if (PiggyBankConfirmationActivity.this.isDestroyed()) {
                    ToastUtils.showToast(PiggyBankConfirmationActivity.this.textViewBalance, PiggyBankConfirmationActivity.this, "Is Destroyed");
                } else if (PiggyBankConfirmationActivity.this.isFinishing()) {
                    ToastUtils.showToast(PiggyBankConfirmationActivity.this.textViewBalance, PiggyBankConfirmationActivity.this, "Is Finnishing");
                } else {
                    dialog.dismiss();
                    PiggyBankConfirmationActivity.this.finish();
                }
            }
        });
    }

    public boolean isPiggyDetailsAreTheir() {
        PiggyBankData piggyBankData = ((PiggyBankApplication) getApplicationContext()).getPiggyBankData();
        Account account = (Account) piggyBankData.getAccounts().get(getIntent().getStringExtra(Constants.ATTACHED_ACCOUNT_NUMBER));
        Log.d("Piggy", "isPiggyDetailsAreTheir: ");
        if (!(account == null || account.getPiggyDetails() == null || account.getPiggyDetails().getPiggyName() == null || account.getPiggyDetails().getPiggyName().isEmpty())) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("isPiggyDetailsAreTheir1: ");
            stringBuilder.append(account.getPiggyDetails().getPiggyName());
            Log.d("Piggy", stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append("isPiggyDetailsAreTheir2: ");
            stringBuilder.append(account.getPiggyDetails().getPiggyName().isEmpty());
            Log.d("Piggy", stringBuilder.toString());
            if (!(account.getPiggyDetails().getPiggyName() == null || account.getPiggyDetails().getPiggyName().isEmpty())) {
                return true;
            }
        }
        return false;
    }

    public Account getAccount() {
        PiggyBankData piggyBankData = ((PiggyBankApplication) getApplicationContext()).getPiggyBankData();
        return (Account) piggyBankData.getAccounts().get(getIntent().getStringExtra(Constants.ATTACHED_ACCOUNT_NUMBER));
    }

    public void storeIntoFirebase() {
        this.progressBarRL.setVisibility(View.VISIBLE);
        PiggyBankData piggyBankData = getNewPiggyValues();
        if (piggyBankData != null) {
            new FireBaseManger().savePiggyBankData(this, piggyBankData, this);
        }
    }

    public PiggyBankData getNewPiggyValues() {
        if (isPiggyDetailsAreTheir()) {
            this.piggyDetails = getAccount().getPiggyDetails();
        } else {
            this.piggyDetails = new PiggyDetails();
        }
        this.piggyDetails.setDeviceId(this.deviceAddress);
        this.piggyDetails.setDeviceName(this.deviceName);
        Account account = getAccount();
        String dateToStr = new SimpleDateFormat("dd/MM/yyyy hh:mm aa").format(new Date());
        this.piggyDetails.setPiggyAttached(true);
        this.piggyDetails.setPiggyLastConnectedDateAndTime(dateToStr);
        this.piggyDetails.setPiggyName(this.piggyName);
        PiggyBankData piggyBankData = ((PiggyBankApplication) getApplicationContext()).getPiggyBankData();
        if (!isGoalSet()) {
            this.piggyDetails.setGoalName(Constants.isFromGoal);
            this.piggyDetails.setGoalAmount(Double.valueOf(Double.parseDouble("9999.00")));
            String DateToStr1 = new SimpleDateFormat("dd/MM/yyyy hh:mm aa").format(new Date());
            this.piggyDetails.setGoalDate(DateToStr1);
            this.piggyDetails.setGoalCreatedDate(DateToStr1);
            this.piggyDetails.setGoalStatus("0");
            this.piggyDetails.setGoalCreated(true);
        }
        account.setPiggyDetails(this.piggyDetails);
        return piggyBankData;
    }

    public boolean isGoalSet() {
        if (this.piggyDetails.getGoalName() == null || this.piggyDetails.getGoalName().isEmpty()) {
            return false;
        }
        return true;
    }
}
