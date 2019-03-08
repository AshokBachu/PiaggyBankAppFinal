package hexadots.in.piaggybankappfinal.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    private static final String IS_FROM_SPLASH = "IsFromSplash";
    private static final long LOGIN_SCREEN_DEALY = 5000;
    private static final String VISIBILITY = "Visibility";
    private static final String VISIBILTY_SHAREDPREF_NAME = "VisibilityCheckFile";
    private static Context context;
    private static Handler handler;
    private static Runnable runnable;

    /* renamed from: hexadots.in.piaggybankappfinal.activities.BaseActivity$1 */
    static class C03971 implements Runnable {
        C03971() {
        }

        public void run() {
            BaseActivity.setLoginScreenVisibility(BaseActivity.context, true);
        }
    }

    public static Handler getHandler() {
        if (handler == null) {
            handler = new Handler();
        }
        return handler;
    }

    public static Runnable getRunnableThread() {
        if (runnable == null) {
            runnable = new C03971();
        }
        return runnable;
    }

    private static void setLoginScreenVisibility(Context context, boolean visibility) {
        getSharedPrf(context).edit().putBoolean(VISIBILITY, visibility).commit();
    }

    public static boolean getLoginScreenVisibility(Context context) {
        return getSharedPrf(context).getBoolean(VISIBILITY, false);
    }

    private static SharedPreferences getSharedPrf(Context context) {
        return context.getSharedPreferences(VISIBILTY_SHAREDPREF_NAME, 0);
    }

    private void enablePasscode() {
        getHandler().postDelayed(getRunnableThread(), LOGIN_SCREEN_DEALY);
    }

    private void disablePasscode() {
        getHandler().removeCallbacks(getRunnableThread());
        setLoginScreenVisibility(this, false);
    }

    public static void disablePasscode(Context context) {
        getHandler().removeCallbacks(getRunnableThread());
        setLoginScreenVisibility(context, false);
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        context = this;
        disablePasscode();
        super.onCreate(savedInstanceState);
    }

    public void showPasscodeActivity() {
        startActivity(new Intent(this, PiggyBankPassCodeActivity.class));
    }

    protected void onResume() {
        getHandler().removeCallbacks(getRunnableThread());
        if (getLoginScreenVisibility(this)) {
            disablePasscode();
            showPasscodeActivity();
        }
        super.onResume();
    }

    protected void onStop() {
        enablePasscode();
        super.onStop();
    }

    protected void onDestroy() {
        disablePasscode();
        super.onDestroy();
    }

    public void finish() {
        disablePasscode();
        super.finish();
    }

    public void onBackPressed() {
        disablePasscode();
        super.onBackPressed();
    }

    public static void openLuncherScreen(Context context) {
        Intent startMain = new Intent("android.intent.action.MAIN");
        startMain.addCategory("android.intent.category.HOME");
        startMain.setFlags(268435456);
        context.startActivity(startMain);
    }

    public static boolean isFromSplash(Intent intent) {
        if (intent == null) {
            return false;
        }
        return intent.getBooleanExtra(IS_FROM_SPLASH, false);
    }

    public static void getIntentWithSplashValue(Intent intent) {
        intent.putExtra(IS_FROM_SPLASH, true);
    }
}
