package hexadots.in.piaggybankappfinal.activities;

import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import hexadots.in.piaggybankappfinal.R;
import hexadots.in.piaggybankappfinal.Constants;
import hexadots.in.piaggybankappfinal.PiggyBankApplication;
import hexadots.in.piaggybankappfinal.SharedPrefManger;
import hexadots.in.piaggybankappfinal.bean.PiggyBankData;
import hexadots.in.piaggybankappfinal.firebase.FireBaseManger;
import hexadots.in.piaggybankappfinal.interfaces.LoginListener;

public class PiggyBankSplashActivity extends AppCompatActivity implements LoginListener {
    private static final String TAG = "Splash";
    private Handler handler;
    Intent intentToShowActivity;
    boolean isFromOnBackPressed;
    boolean isFromOnPause;
    private Runnable runnable = new C04711();

    /* renamed from: hexadots.in.piaggybankappfinal.activities.PiggyBankSplashActivity$1 */
    class C04711 implements Runnable {
        C04711() {
        }

        public void run() {
            if (!PiggyBankSplashActivity.this.isFromOnBackPressed && !PiggyBankSplashActivity.this.isFromOnPause) {
                PiggyBankSplashActivity.this.startActivity(PiggyBankSplashActivity.this.intentToShowActivity);
                PiggyBankSplashActivity.this.finish();
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piggy_bank_splash);
        if (VERSION.SDK_INT < 16) {
            getWindow().setFlags(1024, 1024);
            return;
        }
        getWindow().getDecorView().setSystemUiVisibility(4);
        getSupportActionBar().hide();
    }

    protected void onResume() {
        super.onResume();
        this.isFromOnPause = false;
        this.handler = new Handler();
        if (Constants.isNetworkAvailable(this)) {
            Log.d(TAG, "onResume: hjvv443324");
            if (SharedPrefManger.getMobileNumber(this).isEmpty()) {
                callSignUpIntent();
                this.handler.postDelayed(this.runnable, 2000);
                return;
            }
            new FireBaseManger().getParticularUserDetails(SharedPrefManger.getMobileNumber(this), this);
        } else if (SharedPrefManger.getMobileNumber(this).isEmpty()) {
            callSignUpIntent();
            this.handler.postDelayed(this.runnable, 2000);
        } else {
            callPassCodeIntent();
            this.handler.postDelayed(this.runnable, 2000);
        }
    }

    protected void onPause() {
        super.onPause();
        this.isFromOnPause = true;
        this.handler.removeCallbacks(this.runnable);
    }

    public void loginPiggyBank(boolean isLogin, PiggyBankData piggyBankData) {
        if (isLogin) {
            ((PiggyBankApplication) getApplicationContext()).setPiggyBankData(piggyBankData);
            callPassCodeIntent();
        } else {
            SharedPrefManger.saveMobileNumber("", this);
            callSignUpIntent();
        }
        this.handler.postDelayed(this.runnable, 100);
    }

    public void callSignUpIntent() {
        this.intentToShowActivity = new Intent(this, PiggyBankLoginActivity.class);
    }

    public void callPassCodeIntent() {
        this.intentToShowActivity = new Intent(this, PiggyBankPassCodeActivity.class);
        BaseActivity.getIntentWithSplashValue(this.intentToShowActivity);
    }

    public void onBackPressed() {
        super.onBackPressed();
        this.isFromOnBackPressed = true;
    }
}
