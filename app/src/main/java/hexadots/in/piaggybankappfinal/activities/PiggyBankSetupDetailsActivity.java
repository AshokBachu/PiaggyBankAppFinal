package hexadots.in.piaggybankappfinal.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import hexadots.in.piaggybankappfinal.R;
import hexadots.in.piaggybankappfinal.Constants;
import hexadots.in.piaggybankappfinal.PiggyBankApplication;
import hexadots.in.piaggybankappfinal.ToastUtils;
import hexadots.in.piaggybankappfinal.bean.Account;
import hexadots.in.piaggybankappfinal.bean.PiggyBankData;
import hexadots.in.piaggybankappfinal.bean.PiggyDetails;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class PiggyBankSetupDetailsActivity extends BaseActivity implements OnClickListener {
    public static final String TAG = "PiggyBankSetup";
    private TextView PiggyBankNameTv;
    private String attachToacc;
    private String deviceAddress;
    private String deviceName;
    private EditText piggyBankNameEt;
    private PiggyDetails piggyDetails;
    RelativeLayout progressBarRL;
    private Spinner spinnerToShowAccounts;
    private Map<String, Account> stringAccountHashMap;
    ArrayList<String> stringArrayListKeys = new ArrayList();
    private TextView textViewContinue;

    /* renamed from: hexadots.in.piaggybankappfinal.activities.PiggyBankSetupDetailsActivity$1 */
    class C04391 implements OnClickListener {
        C04391() {
        }

        public void onClick(View view) {
        }
    }

    /* renamed from: hexadots.in.piaggybankappfinal.activities.PiggyBankSetupDetailsActivity$2 */
    class C04402 implements OnItemSelectedListener {
        C04402() {
        }

        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            String str = PiggyBankSetupDetailsActivity.TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onItemSelected: ");
            stringBuilder.append(i);
            Log.d(str, stringBuilder.toString());
            PiggyBankSetupDetailsActivity.this.attachToacc = (String) PiggyBankSetupDetailsActivity.this.stringArrayListKeys.get(i);
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_piggy_bank_setup_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.progressBarRL = (RelativeLayout) findViewById(R.id.progressBarRL);
        this.progressBarRL.setOnClickListener(new C04391());
        this.progressBarRL.setVisibility(8);
        this.deviceName = getIntent().getStringExtra(Constants.CONST_DEVICE_NAME);
        this.deviceAddress = getIntent().getStringExtra(Constants.CONST_DEVICE_ADDRES);
        this.spinnerToShowAccounts = (Spinner) findViewById(R.id.spinnerData);
        this.piggyBankNameEt = (EditText) findViewById(R.id.piggyBankNameEt);
        this.piggyBankNameEt.requestFocus();
        ArrayAdapter spinnerAdapter = new ArrayAdapter(this, 17367048, makeSpinnerEntries());
        spinnerAdapter.setDropDownViewResource(17367049);
        this.spinnerToShowAccounts.setAdapter(spinnerAdapter);
        this.textViewContinue = (TextView) findViewById(R.id.continuee);
        this.textViewContinue.setOnClickListener(this);
        getWindow().setSoftInputMode(4);
        this.PiggyBankNameTv = (TextView) findViewById(R.id.PiggyBankNameTv);
        this.PiggyBankNameTv.setText(this.deviceName);
        this.spinnerToShowAccounts.setOnItemSelectedListener(new C04402());
    }

    public ArrayList<String> makeSpinnerEntries() {
        ArrayList<String> entries = new ArrayList();
        PiggyBankData piggyBankData = ((PiggyBankApplication) getApplicationContext()).getPiggyBankData();
        this.stringAccountHashMap = piggyBankData.getAccounts();
        Iterator it = new ArrayList(this.stringAccountHashMap.keySet()).iterator();
        while (it.hasNext()) {
            String data = (String) it.next();
            Account account = (Account) piggyBankData.getAccounts().get(data);
            Long accountNum = account.getAccountNumber();
            if (account.getAccountType().equals(Constants.ACCOUNT_TYPE_CHILD)) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("");
                stringBuilder.append(accountNum);
                entries.add(stringBuilder.toString());
                this.stringArrayListKeys.add(data);
                if (!(account.getPiggyDetails() == null || account.getPiggyDetails().getPiggyName() == null || account.getPiggyDetails().getPiggyName().isEmpty())) {
                    this.piggyBankNameEt.setText(account.getPiggyDetails().getPiggyName());
                    this.piggyBankNameEt.setSelection(account.getPiggyDetails().getPiggyName().length());
                }
            }
        }
        return entries;
    }

    protected void onPause() {
        super.onPause();
    }

    protected void onResume() {
        super.onResume();
    }

    public void onBackPressed() {
        super.onBackPressed();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 16908332 && !this.progressBarRL.isShown()) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClick(View view) {
        if (view.getId() == R.id.continuee) {
            try {
                if (this.piggyBankNameEt.getText() != null && this.piggyBankNameEt.getText().toString().isEmpty()) {
                    ToastUtils.showToast(this.piggyBankNameEt, this, "Please enter KLYA name");
                } else if (this.piggyBankNameEt.getText().toString().length() < 3) {
                    ToastUtils.showToast(this.piggyBankNameEt, this, "Please enter KLYA name minimum 3 characters");
                } else {
                    Intent intent = new Intent(this, PiggyBankConfirmationActivity.class);
                    intent.putExtra(Constants.ATTACHED_ACCOUNT_NUMBER, this.attachToacc);
                    intent.putExtra(Constants.CONST_DEVICE_NAME, this.deviceName);
                    intent.putExtra(Constants.CONST_DEVICE_ADDRES, this.deviceAddress);
                    intent.putExtra(Constants.PIGGY_NMAE, this.piggyBankNameEt.getText().toString().trim());
                    startActivity(intent);
                    finish();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
