package hexadots.in.piaggybankappfinal.activities;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.VideoView;
import hexadots.in.piaggybankappfinal.R;

public class PiggyBankTransferMoneySuccessScreen extends BaseActivity {
    private Button finishButton;
    boolean isFirstTime;

    /* renamed from: hexadots.in.piaggybankappfinal.activities.PiggyBankTransferMoneySuccessScreen$1 */
    class C04781 implements OnClickListener {
        C04781() {
        }

        public void onClick(View view) {
            PiggyBankTransferMoneySuccessScreen.this.finish();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_piggy_bank_transfer_money_success_screen);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.finishButton = (Button) findViewById(R.id.finishButton);
        this.finishButton.setOnClickListener(new C04781());
    }

    public void showGifImage() {
        this.isFirstTime = true;
        final ImageView imageView = (ImageView) findViewById(R.id.imageView);
        final VideoView videoView = (VideoView) findViewById(R.id.videoView);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("android.resource://");
        stringBuilder.append(getPackageName());
        stringBuilder.append("/");
        stringBuilder.append(R.raw.check_circle_success);
        videoView.setVideoURI(Uri.parse(stringBuilder.toString()));
        videoView.setOnCompletionListener(new OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                if (PiggyBankTransferMoneySuccessScreen.this.isFirstTime) {
                    videoView.start();
                    PiggyBankTransferMoneySuccessScreen.this.isFirstTime = false;
                }
            }
        });
        this.isFirstTime = true;
        videoView.start();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                imageView.setVisibility(View.GONE);
            }
        }, 500);
    }

    protected void onPause() {
        super.onPause();
    }

    protected void onResume() {
        super.onResume();
        showGifImage();
    }

    public void onBackPressed() {
        super.onBackPressed();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 16908332) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
