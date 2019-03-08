package hexadots.in.piaggybankappfinal.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff.Mode;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import hexadots.in.piaggybankappfinal.Constants;
import hexadots.in.piaggybankappfinal.PiggyBankApplication;
import hexadots.in.piaggybankappfinal.R;
import hexadots.in.piaggybankappfinal.SharedPrefManger;
import hexadots.in.piaggybankappfinal.ToastUtils;
import hexadots.in.piaggybankappfinal.bean.PiggyBankData;
import hexadots.in.piaggybankappfinal.firebase.FireBaseManger;
import hexadots.in.piaggybankappfinal.interfaces.LoginListener;

public class PiggyBankPassCodeActivity extends AppCompatActivity implements LoginListener {
    private static final String TAG = "LoginActivity";
    private String enteredPin = "";
    private ImageView enteredPinIV_1;
    private ImageView enteredPinIV_2;
    private ImageView enteredPinIV_3;
    private ImageView enteredPinIV_4;
    private TextView enteredPin_1;
    private TextView enteredPin_2;
    private TextView enteredPin_3;
    private TextView enteredPin_4;
    private Handler handler;
    private RelativeLayout relativeLayoutProgressBar;
    Runnable runnable = new C04256();
    private boolean showEnteredPin = false;

    /* renamed from: hexadots.in.piaggybankappfinal.activities.PiggyBankPassCodeActivity$1 */
    class C04201 implements OnClickListener {
        C04201() {
        }

        public void onClick(View view) {
        }
    }

    /* renamed from: hexadots.in.piaggybankappfinal.activities.PiggyBankPassCodeActivity$2 */
    class C04212 implements OnClickListener {
        C04212() {
        }

        public void onClick(View v) {
            String loginPin = ((PiggyBankApplication) PiggyBankPassCodeActivity.this.getApplicationContext()).getPiggyBankData().getLoginPin();
            View access$000 = PiggyBankPassCodeActivity.this.relativeLayoutProgressBar;
            Context context = PiggyBankPassCodeActivity.this;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Your pin is: ");
            stringBuilder.append(loginPin);
            ToastUtils.showToast(access$000, context, stringBuilder.toString());
        }
    }

    /* renamed from: hexadots.in.piaggybankappfinal.activities.PiggyBankPassCodeActivity$5 */
    class C04245 implements OnClickListener {
        C04245() {
        }

        public void onClick(View v) {
            PiggyBankPassCodeActivity piggyBankPassCodeActivity;
            StringBuilder stringBuilder;
            switch (v.getId()) {
                case R.id.login_passcode_key_0:
                    piggyBankPassCodeActivity = PiggyBankPassCodeActivity.this;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(PiggyBankPassCodeActivity.this.enteredPin);
                    stringBuilder.append("0");
                    piggyBankPassCodeActivity.changePin(stringBuilder.toString());
                    return;
                case R.id.login_passcode_key_1:
                    String str = PiggyBankPassCodeActivity.TAG;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("onClick: ");
                    stringBuilder.append(PiggyBankPassCodeActivity.this.enteredPin);
                    Log.d(str, stringBuilder.toString());
                    piggyBankPassCodeActivity = PiggyBankPassCodeActivity.this;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(PiggyBankPassCodeActivity.this.enteredPin);
                    stringBuilder.append("1");
                    piggyBankPassCodeActivity.changePin(stringBuilder.toString());
                    return;
                case R.id.login_passcode_key_2:
                    piggyBankPassCodeActivity = PiggyBankPassCodeActivity.this;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(PiggyBankPassCodeActivity.this.enteredPin);
                    stringBuilder.append("2");
                    piggyBankPassCodeActivity.changePin(stringBuilder.toString());
                    return;
                case R.id.login_passcode_key_3:
                    piggyBankPassCodeActivity = PiggyBankPassCodeActivity.this;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(PiggyBankPassCodeActivity.this.enteredPin);
                    stringBuilder.append("3");
                    piggyBankPassCodeActivity.changePin(stringBuilder.toString());
                    return;
                case R.id.login_passcode_key_4:
                    piggyBankPassCodeActivity = PiggyBankPassCodeActivity.this;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(PiggyBankPassCodeActivity.this.enteredPin);
                    stringBuilder.append("4");
                    piggyBankPassCodeActivity.changePin(stringBuilder.toString());
                    return;
                case R.id.login_passcode_key_5:
                    piggyBankPassCodeActivity = PiggyBankPassCodeActivity.this;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(PiggyBankPassCodeActivity.this.enteredPin);
                    stringBuilder.append("5");
                    piggyBankPassCodeActivity.changePin(stringBuilder.toString());
                    return;
                case R.id.login_passcode_key_6:
                    piggyBankPassCodeActivity = PiggyBankPassCodeActivity.this;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(PiggyBankPassCodeActivity.this.enteredPin);
                    stringBuilder.append("6");
                    piggyBankPassCodeActivity.changePin(stringBuilder.toString());
                    return;
                case R.id.login_passcode_key_7:
                    piggyBankPassCodeActivity = PiggyBankPassCodeActivity.this;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(PiggyBankPassCodeActivity.this.enteredPin);
                    stringBuilder.append("7");
                    piggyBankPassCodeActivity.changePin(stringBuilder.toString());
                    return;
                case R.id.login_passcode_key_8:
                    piggyBankPassCodeActivity = PiggyBankPassCodeActivity.this;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(PiggyBankPassCodeActivity.this.enteredPin);
                    stringBuilder.append("8");
                    piggyBankPassCodeActivity.changePin(stringBuilder.toString());
                    return;
                case R.id.login_passcode_key_9:
                    piggyBankPassCodeActivity = PiggyBankPassCodeActivity.this;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(PiggyBankPassCodeActivity.this.enteredPin);
                    stringBuilder.append("9");
                    piggyBankPassCodeActivity.changePin(stringBuilder.toString());
                    return;
                case R.id.login_passcode_key_backspace:
                    if (!PiggyBankPassCodeActivity.this.enteredPin.isEmpty()) {
                        if (PiggyBankPassCodeActivity.this.enteredPin.length() > 1) {
                            PiggyBankPassCodeActivity.this.changePin(PiggyBankPassCodeActivity.this.enteredPin.substring(0, PiggyBankPassCodeActivity.this.enteredPin.length() - 1));
                            return;
                        } else {
                            PiggyBankPassCodeActivity.this.changePin("");
                            return;
                        }
                    }
                    return;
                case R.id.login_passcode_key_done:
                    PiggyBankPassCodeActivity.this.submitPin();
                    return;
                default:
                    return;
            }
        }
    }

    /* renamed from: hexadots.in.piaggybankappfinal.activities.PiggyBankPassCodeActivity$6 */
    class C04256 implements Runnable {
        C04256() {
        }

        public void run() {
            if (((PiggyBankApplication) PiggyBankPassCodeActivity.this.getApplicationContext()).getPiggyBankData() != null) {
                BaseActivity.disablePasscode(PiggyBankPassCodeActivity.this);
                if (BaseActivity.isFromSplash(PiggyBankPassCodeActivity.this.getIntent())) {
                    PiggyBankPassCodeActivity.this.startActivity(new Intent(PiggyBankPassCodeActivity.this, PiggyBankHomeNavDrawer.class));
                }
                PiggyBankPassCodeActivity.this.finish();
                return;
            }
            ToastUtils.showToast(PiggyBankPassCodeActivity.this.relativeLayoutProgressBar, PiggyBankPassCodeActivity.this, "Wrong Pin");
            PiggyBankPassCodeActivity.this.clearAll();
            PiggyBankPassCodeActivity.this.relativeLayoutProgressBar.setVisibility(View.GONE);
            PiggyBankPassCodeActivity.this.enteredPin = "";
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_piggy_bank_pass_code);
        getSupportActionBar().hide();
        initializeAll();
        PiggyBankHomeNavDrawer.openPasscode = false;
    }

    public void onBackPressed() {
        if (BaseActivity.isFromSplash(getIntent())) {
            super.onBackPressed();
            Log.d(TAG, "onBackPressed: ");
            return;
        }
        BaseActivity.openLuncherScreen(this);
    }

    protected void onPause() {
        super.onPause();
        if (this.handler != null) {
            this.handler.removeCallbacks(this.runnable);
        }
    }

    private void initializeAll() {
        initializeEnteredPinViews();
        intializeShowHide();
        initializeKeys();
        this.relativeLayoutProgressBar = (RelativeLayout) findViewById(R.id.progressBarRL);
        this.relativeLayoutProgressBar.setOnClickListener(new C04201());
        ((TextView) findViewById(R.id.forgot_password_tv)).setOnClickListener(new C04212());
    }

    private void initializeEnteredPinViews() {
        this.enteredPin_1 = (TextView) findViewById(R.id.pinTV_1);
        this.enteredPin_2 = (TextView) findViewById(R.id.pinTV_2);
        this.enteredPin_3 = (TextView) findViewById(R.id.pinTV_3);
        this.enteredPin_4 = (TextView) findViewById(R.id.pinTV_4);
        this.enteredPinIV_1 = (ImageView) findViewById(R.id.circleIV_1);
        this.enteredPinIV_2 = (ImageView) findViewById(R.id.circleIV_2);
        this.enteredPinIV_3 = (ImageView) findViewById(R.id.circleIV_3);
        this.enteredPinIV_4 = (ImageView) findViewById(R.id.circleIV_4);
    }

    private void intializeShowHide() {
        final TextView showEnteredPinTV = (TextView) findViewById(R.id.show_entered_pin_tv);
        final TextView hideEnteredPinTV = (TextView) findViewById(R.id.hide_entered_pin_tv);
        showEnteredPinTV.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                PiggyBankPassCodeActivity.this.showEnteredPin = true;
                PiggyBankPassCodeActivity.this.onClickShowHide(showEnteredPinTV, hideEnteredPinTV);
            }
        });
        hideEnteredPinTV.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                PiggyBankPassCodeActivity.this.showEnteredPin = false;
                PiggyBankPassCodeActivity.this.onClickShowHide(showEnteredPinTV, hideEnteredPinTV);
            }
        });
        onClickShowHide(showEnteredPinTV, hideEnteredPinTV);
    }

    private void onClickShowHide(TextView showEnteredPinTV, TextView hideEnteredPinTV) {
        if (this.showEnteredPin) {
            showEnteredPinTV.setVisibility(View.INVISIBLE);
            hideEnteredPinTV.setVisibility(View.VISIBLE);
        } else {
            showEnteredPinTV.setVisibility(View.VISIBLE);
            hideEnteredPinTV.setVisibility(View.INVISIBLE);
        }
        changePinView();
    }

    private void initializeKeys() {
        changePin("");
        TextView key1 = (TextView) findViewById(R.id.login_passcode_key_1);
        TextView key2 = (TextView) findViewById(R.id.login_passcode_key_2);
        TextView key3 = (TextView) findViewById(R.id.login_passcode_key_3);
        TextView key4 = (TextView) findViewById(R.id.login_passcode_key_4);
        TextView key5 = (TextView) findViewById(R.id.login_passcode_key_5);
        TextView key6 = (TextView) findViewById(R.id.login_passcode_key_6);
        TextView key7 = (TextView) findViewById(R.id.login_passcode_key_7);
        TextView key8 = (TextView) findViewById(R.id.login_passcode_key_8);
        TextView key9 = (TextView) findViewById(R.id.login_passcode_key_9);
        TextView key0 = (TextView) findViewById(R.id.login_passcode_key_0);
        ImageView keyBackspace = (ImageView) findViewById(R.id.login_passcode_key_backspace);
        ImageView keyDone = (ImageView) findViewById(R.id.login_passcode_key_done);
        OnClickListener keysClickListener = new C04245();
        key1.setOnClickListener(keysClickListener);
        key2.setOnClickListener(keysClickListener);
        key3.setOnClickListener(keysClickListener);
        key4.setOnClickListener(keysClickListener);
        key5.setOnClickListener(keysClickListener);
        key6.setOnClickListener(keysClickListener);
        key7.setOnClickListener(keysClickListener);
        key8.setOnClickListener(keysClickListener);
        key9.setOnClickListener(keysClickListener);
        key0.setOnClickListener(keysClickListener);
        keyBackspace.setOnClickListener(keysClickListener);
        keyDone.setOnClickListener(keysClickListener);
    }

    private void submitPin() {
        if (this.enteredPin == null) {
            Log.e(TAG, "submitPin: entered pin is null");
        } else if (this.enteredPin.length() != 4) {
            ToastUtils.showToast(this.relativeLayoutProgressBar, this, "Enter Valid 4 Digits Passcode");
        } else if (!Constants.isNetworkAvailable(this)) {
            ToastUtils.showToast(this.relativeLayoutProgressBar, this, "Network is not available");
            clearAll();
            this.enteredPin = "";
        } else if (Constants.isNetworkAvailable(this) && BaseActivity.isFromSplash(getIntent())) {
            new FireBaseManger().getParticularUserDetails(SharedPrefManger.getMobileNumber(this), this);
            this.relativeLayoutProgressBar.setVisibility(View.VISIBLE);
            //this.progressBar = (ProgressBar) findViewById(R.id.showProgressBar);
            this.relativeLayoutProgressBar.setVisibility(View.VISIBLE);
        } else {
            this.relativeLayoutProgressBar.setVisibility(View.VISIBLE);
            //progressBar = (ProgressBar) findViewById(R.id.showProgressBar);
            this.relativeLayoutProgressBar.setVisibility(View.VISIBLE);
            //progressBar.getIndeterminateDrawable().setColorFilter(-1, Mode.SRC_IN);
            validateData(((PiggyBankApplication) getApplicationContext()).getPiggyBankData());
        }
    }

    protected void onStop() {
        super.onStop();
    }

    private void changePin(String pin) {
        this.enteredPin = pin;
        changePinView();
    }

    private void changePinView() {
        if (this.enteredPin == null) {
            Log.e(TAG, "changePin: entered pin is null");
            return;
        }
        clearAll();
        TextView textView;
        StringBuilder stringBuilder;
        StringBuilder stringBuilder2;
        StringBuilder stringBuilder3;
        switch (this.enteredPin.length()) {
            case 1:
                if (!this.showEnteredPin) {
                    this.enteredPinIV_1.setVisibility(View.VISIBLE);
                    break;
                }
                textView = this.enteredPin_1;
                stringBuilder = new StringBuilder();
                stringBuilder.append(this.enteredPin.charAt(0));
                stringBuilder.append("");
                textView.setText(stringBuilder.toString());
                this.enteredPin_1.setVisibility(View.VISIBLE);
                break;
            case 2:
                if (!this.showEnteredPin) {
                    this.enteredPinIV_1.setVisibility(View.VISIBLE);
                    this.enteredPinIV_2.setVisibility(View.VISIBLE);
                    break;
                }
                textView = this.enteredPin_1;
                stringBuilder = new StringBuilder();
                stringBuilder.append(this.enteredPin.charAt(0));
                stringBuilder.append("");
                textView.setText(stringBuilder.toString());
                this.enteredPin_1.setVisibility(View.VISIBLE);
                textView = this.enteredPin_2;
                stringBuilder = new StringBuilder();
                stringBuilder.append(this.enteredPin.charAt(1));
                stringBuilder.append("");
                textView.setText(stringBuilder.toString());
                this.enteredPin_2.setVisibility(View.VISIBLE);
                break;
            case 3:
                if (!this.showEnteredPin) {
                    this.enteredPinIV_1.setVisibility(View.VISIBLE);
                    this.enteredPinIV_2.setVisibility(View.VISIBLE);
                    this.enteredPinIV_3.setVisibility(View.VISIBLE);
                    break;
                }
                textView = this.enteredPin_1;
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(this.enteredPin.charAt(0));
                stringBuilder2.append("");
                textView.setText(stringBuilder2.toString());
                this.enteredPin_1.setVisibility(View.VISIBLE);
                textView = this.enteredPin_2;
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(this.enteredPin.charAt(1));
                stringBuilder2.append("");
                textView.setText(stringBuilder2.toString());
                this.enteredPin_2.setVisibility(View.VISIBLE);
                textView = this.enteredPin_3;
                stringBuilder3 = new StringBuilder();
                stringBuilder3.append(this.enteredPin.charAt(2));
                stringBuilder3.append("");
                textView.setText(stringBuilder3.toString());
                this.enteredPin_3.setVisibility(View.VISIBLE);
                break;
            case 4:
                if (this.showEnteredPin) {
                    textView = this.enteredPin_1;
                    stringBuilder2 = new StringBuilder();
                    stringBuilder2.append(this.enteredPin.charAt(0));
                    stringBuilder2.append("");
                    textView.setText(stringBuilder2.toString());
                    this.enteredPin_1.setVisibility(View.VISIBLE);
                    textView = this.enteredPin_2;
                    stringBuilder2 = new StringBuilder();
                    stringBuilder2.append(this.enteredPin.charAt(1));
                    stringBuilder2.append("");
                    textView.setText(stringBuilder2.toString());
                    this.enteredPin_2.setVisibility(View.VISIBLE);
                    textView = this.enteredPin_3;
                    stringBuilder3 = new StringBuilder();
                    stringBuilder3.append(this.enteredPin.charAt(2));
                    stringBuilder3.append("");
                    textView.setText(stringBuilder3.toString());
                    this.enteredPin_3.setVisibility(View.VISIBLE);
                    textView = this.enteredPin_4;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(this.enteredPin.charAt(3));
                    stringBuilder.append("");
                    textView.setText(stringBuilder.toString());
                    this.enteredPin_4.setVisibility(View.VISIBLE);
                } else {
                    this.enteredPinIV_1.setVisibility(View.VISIBLE);
                    this.enteredPinIV_2.setVisibility(View.VISIBLE);
                    this.enteredPinIV_3.setVisibility(View.VISIBLE);
                    this.enteredPinIV_4.setVisibility(View.VISIBLE);
                }
                submitPin();
                break;
            default:
                break;
        }
    }

    private void clearAll() {
        this.enteredPinIV_1.setVisibility(View.INVISIBLE);
        this.enteredPinIV_2.setVisibility(View.INVISIBLE);
        this.enteredPinIV_3.setVisibility(View.INVISIBLE);
        this.enteredPinIV_4.setVisibility(View.INVISIBLE);
        this.enteredPin_1.setVisibility(View.INVISIBLE);
        this.enteredPin_2.setVisibility(View.INVISIBLE);
        this.enteredPin_3.setVisibility(View.INVISIBLE);
        this.enteredPin_4.setVisibility(View.INVISIBLE);
    }

    public void loginPiggyBank(boolean isLogin, PiggyBankData piggyBankData) {
        if (isLogin) {
            ((PiggyBankApplication) getApplicationContext()).setPiggyBankData(piggyBankData);
            validateData(piggyBankData);
        }
    }

    public void validateData(PiggyBankData piggyBankData) {
        if (piggyBankData.getLoginPin().equals(this.enteredPin)) {
            this.handler = new Handler();
            this.handler.postDelayed(this.runnable, 1000);
            return;
        }
        ToastUtils.showToast(this.relativeLayoutProgressBar, this, "Invalid Pin");
        clearAll();
        this.relativeLayoutProgressBar.setVisibility(View.GONE);
        this.enteredPin = "";
    }
}
