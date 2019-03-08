package hexadots.in.piaggybankappfinal.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff.Mode;
import android.os.Bundle;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
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
import hexadots.in.piaggybankappfinal.PiggyBankApplication;
import hexadots.in.piaggybankappfinal.R;
import hexadots.in.piaggybankappfinal.SharedPrefManger;
import hexadots.in.piaggybankappfinal.ToastUtils;
import hexadots.in.piaggybankappfinal.bean.PiggyBankData;
import hexadots.in.piaggybankappfinal.firebase.FireBaseManger;
import hexadots.in.piaggybankappfinal.interfaces.ForgotPinCallBack;
import hexadots.in.piaggybankappfinal.interfaces.LoginListener;

public class PiggyBankLoginActivity extends AppCompatActivity implements LoginListener {
    EditText editTextLoginPinEt;
    EditText editTextMobileNumber;
    FireBaseManger fireBaseManger;
    LinearLayout forgotPin;
    ProgressBar progressBarLogin;
    RelativeLayout relativeLayoutProgressBarLL;

    /* renamed from: hexadots.in.piaggybankappfinal.activities.PiggyBankLoginActivity$1 */
    class C04121 implements OnClickListener {
        C04121() {
        }

        public void onClick(View view) {
        }
    }

    /* renamed from: hexadots.in.piaggybankappfinal.activities.PiggyBankLoginActivity$2 */
    class C04132 implements OnClickListener {
        C04132() {
        }

        public void onClick(View v) {
            if (Constants.isNetworkAvailable(PiggyBankLoginActivity.this)) {
                PiggyBankLoginActivity.this.showPin();
            } else {
                ToastUtils.showToast(PiggyBankLoginActivity.this.editTextMobileNumber, PiggyBankLoginActivity.this, "Please connect to internet");
            }
        }
    }

    /* renamed from: hexadots.in.piaggybankappfinal.activities.PiggyBankLoginActivity$3 */
    class C04143 implements OnClickListener {
        C04143() {
        }

        public void onClick(View view) {
            PiggyBankLoginActivity.this.finish();
        }
    }

    /* renamed from: hexadots.in.piaggybankappfinal.activities.PiggyBankLoginActivity$4 */
    class C04154 implements OnClickListener {
        C04154() {
        }

        public void onClick(View view) {
            PiggyBankLoginActivity.this.startActivity(new Intent(PiggyBankLoginActivity.this, PiggyBankSignUpActivity.class));
            PiggyBankLoginActivity.this.finish();
        }
    }

    /* renamed from: hexadots.in.piaggybankappfinal.activities.PiggyBankLoginActivity$5 */
    class C04165 implements OnClickListener {
        C04165() {
        }

        public void onClick(View view) {
            PiggyBankLoginActivity.this.validateUiElements();
        }
    }

    /* renamed from: hexadots.in.piaggybankappfinal.activities.PiggyBankLoginActivity$7 */
    class C04177 implements DialogInterface.OnClickListener {
        C04177() {
        }

        public void onClick(DialogInterface dialog, int which) {
        }
    }

    /* renamed from: hexadots.in.piaggybankappfinal.activities.PiggyBankLoginActivity$8 */
    class C04188 implements DialogInterface.OnClickListener {
        C04188() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
        }
    }

    /* renamed from: hexadots.in.piaggybankappfinal.activities.PiggyBankLoginActivity$9 */
    class C04199 implements DialogInterface.OnClickListener {
        C04199() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
        }
    }

    /* renamed from: hexadots.in.piaggybankappfinal.activities.PiggyBankLoginActivity$6 */
    class C07506 implements ForgotPinCallBack {
        C07506() {
        }

        public void onPin(String showPin) {
            PiggyBankLoginActivity.this.relativeLayoutProgressBarLL.setVisibility(View.GONE);
            if (showPin.equals("")) {
                PiggyBankLoginActivity.this.showAlertDialog("Not Registered ", "Mobile number is not registered with us, click Sign up to register");
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Your Login Pin is ");
            stringBuilder.append(showPin);
            PiggyBankLoginActivity.this.showPasswordInAlertDialog("Pin", stringBuilder.toString());
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_piggy_bank_login);
        getSupportActionBar().hide();
        initUIElements();
    }

    public void initUIElements() {
        this.relativeLayoutProgressBarLL = (RelativeLayout) findViewById(R.id.progressBarRL);
        this.relativeLayoutProgressBarLL.setVisibility(View.GONE);
        this.relativeLayoutProgressBarLL.setOnClickListener(new C04121());
        this.progressBarLogin = (ProgressBar) findViewById(R.id.progressBarInLogin);
        this.editTextMobileNumber = (EditText) findViewById(R.id.mobile_number_et);
        this.editTextLoginPinEt = (EditText) findViewById(R.id.login_pin_et);
        this.forgotPin = (LinearLayout) findViewById(R.id.forgotPin);
        this.forgotPin.setOnClickListener(new C04132());
        TextView signUpProceedTv = (TextView) findViewById(R.id.sign_up_proceed_tv);
        ((TextView) findViewById(R.id.sig_up_cancel_tv)).setOnClickListener(new C04143());
        ((LinearLayout) findViewById(R.id.sign_up_ll)).setOnClickListener(new C04154());
        signUpProceedTv.setOnClickListener(new C04165());
        this.fireBaseManger = new FireBaseManger();
    }

    public void showPin() {
        if (this.editTextMobileNumber.getText().toString().isEmpty()) {
            this.editTextMobileNumber.setError("Please enter 10-digit mobile number");
            this.editTextMobileNumber.requestFocus();
        } else if (this.editTextMobileNumber.getText().toString().length() != 10) {
            this.editTextMobileNumber.setError("Please enter 10-digit mobile number");
            this.editTextMobileNumber.requestFocus();
        } else {
            this.relativeLayoutProgressBarLL.setVisibility(View.VISIBLE);
            this.progressBarLogin.getIndeterminateDrawable().setColorFilter(-1, Mode.SRC_IN);
            ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(this.editTextMobileNumber.getWindowToken(), 0);
            this.fireBaseManger.getParticularUserDetails(this.editTextMobileNumber.getText().toString(), new C07506());
        }
    }

    public void showAlertDialog(String title, String message) {
        Builder builder = new Builder(this);
        builder.setTitle((CharSequence) title);
        builder.setMessage((CharSequence) message);
        builder.setPositiveButton((CharSequence) "Ok", new C04177());
        builder.create().show();
    }

    public void showPasswordInAlertDialog(String title, String message) {
        Builder builder = new Builder(this);
        builder.setTitle((CharSequence) title);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(message);
        builder.setMessage(stringBuilder.toString());
        builder.setPositiveButton((CharSequence) "Ok", new C04188());
        builder.setNegativeButton((CharSequence) "Cancel", new C04199());
        builder.create().show();
    }

    public void validateUiElements() {
        String login = this.editTextLoginPinEt.getText().toString();
        String mobileNumber = this.editTextMobileNumber.getText().toString();
        boolean detailsValid = true;
        if (mobileNumber.isEmpty() || mobileNumber.length() < 10) {
            detailsValid = false;
            this.editTextMobileNumber.setError("Please Enter 10 Digit Mobile Number");
        }
        if (login.isEmpty() || login.length() < 4) {
            detailsValid = false;
            this.editTextLoginPinEt.setError("Please Enter 4 Digit Login Pin");
        }
        if (!detailsValid) {
            return;
        }
        if (Constants.isNetworkAvailable(this)) {
            this.fireBaseManger.getParticularUserDetails(mobileNumber, this);
            this.relativeLayoutProgressBarLL.setVisibility(View.VISIBLE
            );
            this.progressBarLogin.getIndeterminateDrawable().setColorFilter(-1, Mode.SRC_IN);
            ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(this.editTextMobileNumber.getWindowToken(), 0);
            return;
        }
        ToastUtils.showToast(this.editTextMobileNumber, this, "Please connect to internet");
    }

    public void loginPiggyBank(boolean isLogin, PiggyBankData piggyBankData) {
        if (isLogin) {
            this.relativeLayoutProgressBarLL.setVisibility(View.GONE);
            this.progressBarLogin.setVisibility(View.GONE);
            String loginPin = piggyBankData.getLoginPin();
            String mobileNumber = piggyBankData.getMobileNumber();
            if (!loginPin.equals(this.editTextLoginPinEt.getText().toString())) {
                this.editTextLoginPinEt.setError("Wrong Login Pin");
                return;
            } else if (mobileNumber.equals(this.editTextMobileNumber.getText().toString())) {
                ((PiggyBankApplication) getApplicationContext()).setPiggyBankData(piggyBankData);
                SharedPrefManger.saveMobileNumber(this.editTextMobileNumber.getText().toString(), this);
                startActivity(new Intent(this, PiggyBankHomeNavDrawer.class));
                finish();
                return;
            } else {
                this.editTextLoginPinEt.setError("Wrong Mobile Number");
                return;
            }
        }
        showAlertDialog("Not Registered ", "Mobile number is not registered with us,click Sign up to register");
        this.relativeLayoutProgressBarLL.setVisibility(View.GONE);
        this.progressBarLogin.setVisibility(View.GONE);
    }
}
