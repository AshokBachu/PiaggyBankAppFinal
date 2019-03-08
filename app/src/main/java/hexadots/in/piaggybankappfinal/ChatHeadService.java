package hexadots.in.piaggybankappfinal;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

public class ChatHeadService extends Service {
    private View mChatHeadView;
    private WindowManager mWindowManager;

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
        this.mChatHeadView = LayoutInflater.from(this).inflate(R.layout.notes_item, null);
        LayoutParams layoutParams = new LayoutParams(-2, -2, 2002, 8, -3);
        layoutParams.gravity = 51;
        layoutParams.x = 0;
        layoutParams.y = 100;
        this.mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        this.mWindowManager.addView(this.mChatHeadView, layoutParams);
    }

    public void onDestroy() {
        super.onDestroy();
        if (this.mChatHeadView != null) {
            this.mWindowManager.removeView(this.mChatHeadView);
        }
    }
}
