package hexadots.in.piaggybankappfinal.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff.Mode;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import hexadots.in.piaggybankappfinal.R;
import hexadots.in.piaggybankappfinal.Constants;
import hexadots.in.piaggybankappfinal.ToastUtils;
import hexadots.in.piaggybankappfinal.bean.Account;
import hexadots.in.piaggybankappfinal.bean.PiggyBankData;
import hexadots.in.piaggybankappfinal.firebase.FireBaseManger;
import hexadots.in.piaggybankappfinal.interfaces.CheckUserListner;
import java.util.HashMap;

public class PiggyBankSignUpActivity extends AppCompatActivity implements OnClickListener, CheckUserListner {
    public static final String TAG = "SignUpActivity";
    EditText editTextConfirmLoginPinEt;
    EditText editTextConfirmTransactionPinEt;
    EditText editTextLoginPinEt;
    EditText editTextMobileNumber;
    EditText editTextName;
    EditText editTextTransactionPinEt;
    private FireBaseManger fireBaseManger;
    LinearLayout linearLayoutLogin;
    LinearLayout linearLayoutStep1;
    LinearLayout linearLayoutStep2;
    LinearLayout linearLayoutStep3;
    private RelativeLayout progressBarRL;
    private ProgressBar progressBarWeel;
    TextView textViewSignUpCancel;
    TextView textViewSignUpProceed;

    /* renamed from: hexadots.in.piaggybankappfinal.activities.PiggyBankSignUpActivity$1 */
    class C04691 implements Runnable {
        C04691() {
        }

        public void run() {
            Intent intentOpenPiggyBankHomeScreen = new Intent(PiggyBankSignUpActivity.this, PiggyBankHomeNavDrawer.class);
            PiggyBankData piggyBankData = new PiggyBankData();
            piggyBankData.setLoginPin(PiggyBankSignUpActivity.this.editTextLoginPinEt.getText().toString());
            piggyBankData.setMobileNumber(PiggyBankSignUpActivity.this.editTextMobileNumber.getText().toString());
            Account accountDetailsChild = new Account();
            accountDetailsChild.setAccountName(Constants.CHILDREN_ACCOUNT_NAME);
            accountDetailsChild.setAccountType(Constants.ACCOUNT_TYPE_CHILD);
            accountDetailsChild.setBalance(0.0d);
            accountDetailsChild.setAccountNumber(Long.valueOf(Long.parseLong(Constants.generateAccountNumber())));
            accountDetailsChild.setAccountId(Constants.generateAccountKey());
            Account accountDetailsSavings = new Account();
            accountDetailsSavings.setAccountName(Constants.SAVINGS_ACCOUNT_NAME);
            accountDetailsSavings.setAccountType(Constants.ACCOUNT_TYPE_PARENT);
            accountDetailsSavings.setBalance(9999.0d);
            accountDetailsSavings.setAccountNumber(Long.valueOf(Long.parseLong(Constants.generateAccountNumber())));
            accountDetailsSavings.setAccountId(Constants.generateAccountKey());
            HashMap<String, Account> accountHashMap = new HashMap();
            accountHashMap.put(accountDetailsChild.getAccountId(), accountDetailsChild);
            accountHashMap.put(accountDetailsSavings.getAccountId(), accountDetailsSavings);
            piggyBankData.setAccounts(accountHashMap);
            piggyBankData.setName(PiggyBankSignUpActivity.this.editTextName.getText().toString());
            piggyBankData.setLoginPin(PiggyBankSignUpActivity.this.editTextLoginPinEt.getText().toString());
            PiggyBankSignUpActivity.this.fireBaseManger.addAccountLoginDetails(piggyBankData, intentOpenPiggyBankHomeScreen, PiggyBankSignUpActivity.this, piggyBankData.getMobileNumber());
        }
    }

    /* renamed from: hexadots.in.piaggybankappfinal.activities.PiggyBankSignUpActivity$2 */
    class C04702 implements DialogInterface.OnClickListener {
        C04702() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_piggy_bank_sign_up);
        getSupportActionBar().hide();
        initUiElements();
    }

    public void initUiElements() {
        this.linearLayoutStep1 = (LinearLayout) findViewById(R.id.step1);
        this.linearLayoutStep2 = (LinearLayout) findViewById(R.id.step2);
        this.linearLayoutStep3 = (LinearLayout) findViewById(R.id.step3);
        this.progressBarRL = (RelativeLayout) findViewById(R.id.progressBarRL);
        this.linearLayoutStep2.setVisibility(View.GONE);
        this.linearLayoutStep3.setVisibility(View.GONE);
        this.progressBarRL.setVisibility(View.GONE);
        this.progressBarWeel = (ProgressBar) findViewById(R.id.showProgressBar);
        this.textViewSignUpProceed = (TextView) findViewById(R.id.sign_up_proceed_tv);
        this.textViewSignUpCancel = (TextView) findViewById(R.id.sig_up_cancel_tv);
        this.editTextName = (EditText) findViewById(R.id.name_et);
        this.editTextMobileNumber = (EditText) findViewById(R.id.mobile_number_et);
        this.editTextLoginPinEt = (EditText) findViewById(R.id.login_pin_et);
        this.editTextConfirmLoginPinEt = (EditText) findViewById(R.id.confirm_login_pin_et);
        this.editTextTransactionPinEt = (EditText) findViewById(R.id.transaction_pin_et);
        this.editTextConfirmTransactionPinEt = (EditText) findViewById(R.id.confirm_transaction_pin_et);
        this.linearLayoutLogin = (LinearLayout) findViewById(R.id.login_ll);
        this.linearLayoutLogin.setOnClickListener(this);
        this.textViewSignUpProceed.setText("Next");
        this.textViewSignUpProceed.setTag("1");
        this.textViewSignUpProceed.setOnClickListener(this);
        this.textViewSignUpCancel.setOnClickListener(this);
        this.progressBarRL.setOnClickListener(this);
        this.fireBaseManger = new FireBaseManger();
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.login_ll) {
            startActivity(new Intent(this, PiggyBankLoginActivity.class));
            finish();
        } else if (id != R.id.sig_up_cancel_tv) {
            if (id == R.id.sign_up_proceed_tv) {
                ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(view.getWindowToken(), 0);
                validateInputs();
            }
        } else if (this.textViewSignUpCancel.getText().toString().equals("BACK")) {
            this.linearLayoutStep1.setVisibility(View.VISIBLE);
            this.linearLayoutStep2.setVisibility(View.GONE);
            this.editTextConfirmLoginPinEt.setText("");
            this.editTextLoginPinEt.setText("");
            this.textViewSignUpProceed.setText("Next");
            this.textViewSignUpProceed.setTag(Integer.valueOf(1));
            this.textViewSignUpCancel.setText("Cancel");
            this.progressBarRL.setVisibility(View.GONE);
        } else {
            finish();
        }
    }

    public void validateInputs() {
        boolean isStep1Valid;
        String name = this.editTextName.getText().toString().trim();
        String mobileNumber = this.editTextMobileNumber.getText().toString().trim();
        String loginPin = this.editTextLoginPinEt.getText().toString().trim();
        String confirmLoginPin = this.editTextConfirmLoginPinEt.getText().toString().trim();
        String transactionPin = this.editTextTransactionPinEt.getText().toString().trim();
        String confirmTransactionPin = this.editTextConfirmTransactionPinEt.getText().toString().trim();
        if (this.textViewSignUpProceed.getText().toString().equals("Next")) {
            isStep1Valid = true;
            if (name.isEmpty()) {
                this.editTextName.setError("Please Enter Name");
                isStep1Valid = false;
            }
            if (mobileNumber.isEmpty() || mobileNumber.length() < 10) {
                isStep1Valid = false;
                this.editTextMobileNumber.setError("Please Enter 10 Digit Mobile Number");
            }
            if (isStep1Valid) {
                if (Constants.isNetworkAvailable(this)) {
                    this.linearLayoutStep1.setVisibility(View.VISIBLE);
                    this.progressBarRL.setVisibility(View.VISIBLE);
                    this.progressBarWeel.setVisibility(View.VISIBLE);
                    this.fireBaseManger.getParticularUserDetails(this.editTextMobileNumber.getText().toString(), this);
                } else {
                    ToastUtils.showToast(this.linearLayoutStep2, this, "Please connect to internet");
                }
                return;
            }
        }
        if (this.textViewSignUpProceed.getText().toString().equals("Proceed")) {
            if (!loginPin.isEmpty()) {
                if (loginPin.length() >= 4) {
                    if (!confirmLoginPin.isEmpty()) {
                        if (confirmLoginPin.length() >= 4) {
                            if (!loginPin.isEmpty() && !confirmLoginPin.isEmpty() && !loginPin.equals(confirmLoginPin)) {
                                Log.d(TAG, "validateInputs: 3");
                                this.editTextLoginPinEt.setError("Login Pin is not matched with confirmation");
                                this.editTextLoginPinEt.requestFocus();
                                return;
                            } else if (true) {
                                if (Constants.isNetworkAvailable(this)) {
                                    storeDataInDataBase();
                                } else {
                                    ToastUtils.showToast(this.linearLayoutStep2, this, "Please connect to internet");
                                }
                                return;
                            }
                        }
                    }
                    Log.d(TAG, "validateInputs: 2");
                    this.editTextConfirmLoginPinEt.setError("Please Re-Enter 4 Digit Login Pin");
                    this.editTextConfirmLoginPinEt.requestFocus();
                    return;
                }
            }
            Log.d(TAG, "validateInputs: 1");
            this.editTextLoginPinEt.setError("Please Enter 4 Digit Login Pin");
            this.editTextLoginPinEt.requestFocus();
            return;
        }
        if (this.textViewSignUpProceed.getTag().equals("3")) {
            isStep1Valid = true;
            if (transactionPin.isEmpty() || transactionPin.length() < 6) {
                isStep1Valid = false;
                Log.d(TAG, "validateInputs: 4");
                this.editTextTransactionPinEt.setError("Please Enter 6 Digit Transaction Pin");
            }
            if (confirmTransactionPin.isEmpty() || confirmTransactionPin.length() < 6) {
                isStep1Valid = false;
                Log.d(TAG, "validateInputs: 5");
                this.editTextConfirmTransactionPinEt.setError("Please Re-Enter 6 Digit Transaction Pin");
            }
            if (!(transactionPin.isEmpty() || confirmTransactionPin.isEmpty() || transactionPin.equals(confirmTransactionPin))) {
                isStep1Valid = false;
                this.editTextTransactionPinEt.setError("Transaction Pin is not matched with confirmation");
            }
            if (isStep1Valid) {
            }
        }
    }

    public void storeDataInDataBase() {
        this.progressBarRL.setVisibility(View.VISIBLE);
        this.progressBarWeel.setVisibility(View.VISIBLE);
        this.progressBarWeel.getIndeterminateDrawable().setColorFilter(-1, Mode.SRC_IN);
        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(this.editTextConfirmTransactionPinEt.getWindowToken(), 0);
        Log.d(TAG, "onCheck: step3 ");
        new Handler().postDelayed(new C04691(), 2000);
    }

    public void onCheck(boolean userCheck) {
        if (userCheck)
        {
            this.progressBarRL.setVisibility(View.GONE);
            this.progressBarWeel.setVisibility(View.GONE);
            this.editTextName.setText("");
            this.editTextMobileNumber.setText("");
            showAlertDialog();
            return;
        }
        Log.d(TAG, "onCheck: Progress");
        this.progressBarRL.setVisibility(View.GONE);
        this.progressBarWeel.setVisibility(View.GONE);
        this.linearLayoutStep2.setVisibility(View.VISIBLE);
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        this.editTextLoginPinEt.requestFocus();
        imm.showSoftInput(this.editTextLoginPinEt, 1);
        this.linearLayoutStep1.setVisibility(View.GONE);
        this.linearLayoutStep3.setVisibility(View.GONE);
        this.editTextLoginPinEt.setError(null);
        this.editTextConfirmLoginPinEt.setError(null);
        this.textViewSignUpProceed.setTag("2");
        this.textViewSignUpProceed.setText("Proceed");
        this.textViewSignUpCancel.setText("BACK");
    }

    public void showAlertDialog() {
        Builder builder = new Builder(this);
        builder.setTitle((CharSequence) "Registered");
        builder.setMessage((CharSequence) "Mobile number is already registered with us. Please Login to continue");
        builder.setPositiveButton((CharSequence) "ok", new C04702());
        builder.create().show();
    }
}
