package hexadots.in.piaggybankappfinal.activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;
import hexadots.in.piaggybankappfinal.R;
import hexadots.in.piaggybankappfinal.Constants;
import hexadots.in.piaggybankappfinal.PiggyBankApplication;
import hexadots.in.piaggybankappfinal.bean.Account;
import hexadots.in.piaggybankappfinal.bean.PiggyDetails;
import hexadots.in.piaggybankappfinal.bean.TransactionsDetails;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class PiggyBankConfirmationUpadateActivity extends BaseActivity implements OnClickListener {
    TextView UpdateToToy;
    TextView buttonUpdateToToy;
    boolean isFirstTime;
    PiggyDetails piggyDetails;
    TextView textViewAmountTransfer;
    TextView textViewDateTransfer;
    TextView textViewDescription;
    TextView textViewExtraAmountGoal;
    TextView textViewFromAcc;
    TextView textViewFromAccNum;
    TextView textViewGaolAmount;
    TextView textViewGoalDate;
    TextView textViewGoalName;
    TextView textViewSendBalance;
    TextView textViewToAcc;
    TextView textViewToAccNum;
    TransactionsDetails transactionsDetails;

    /* renamed from: hexadots.in.piaggybankappfinal.activities.PiggyBankConfirmationUpadateActivity$1 */
    class C04001 implements OnClickListener {
        C04001() {
        }

        public void onClick(View v) {
            PiggyBankConfirmationUpadateActivity.this.transferConfirmation(true);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String confirmationScreen = getIntent().getStringExtra(Constants.CONST_CONFIRMATION);
        if (confirmationScreen.equals(Constants.CONST_TRANSFER_CONFIRMATION)) {
            setContentView(R.layout.transfer_confirmation);
            initTransferUiConfirmation();
        }
        if (confirmationScreen.equals(Constants.CONST_Goal_CONFIRMATION)) {
            setContentView(R.layout.goal_confirmation_details);
            initGoalUiConfirmation();
        }
        PiggyBankHomeNavDrawer.openPasscode = false;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator((int) R.drawable.ic_home_black_24dp);
    }

    public void initTransferUiConfirmation() {
        TextView textView;
        this.UpdateToToy = (TextView) findViewById(R.id.update_To_Toy_Transfer_Confirmation);
        this.UpdateToToy.setOnClickListener(this);
        this.textViewFromAccNum = (TextView) findViewById(R.id.accountNumber__from);
        this.textViewToAccNum = (TextView) findViewById(R.id.to_account_number);
        this.textViewDateTransfer = (TextView) findViewById(R.id.date_Transfer_Confirmation);
        this.textViewDescription = (TextView) findViewById(R.id.description_Transfer_Confirmation);
        this.textViewToAcc = (TextView) findViewById(R.id.to_account_Number_Transfer_Confirmation);
        this.textViewFromAcc = (TextView) findViewById(R.id.from_accountNumber__Transfer_Confirmation);
        this.textViewAmountTransfer = (TextView) findViewById(R.id.amount_Transfer_Confirmation);
        this.textViewSendBalance = (TextView) findViewById(R.id.sendBalance);
        this.textViewSendBalance.setOnClickListener(new C04001());
        //KonfettiView viewKonfetti = (KonfettiView) findViewById(R.id.viewKonfetti);

        int color1 = ContextCompat.getColor(this, R.color.Sparkle_1);
        int color2 = ContextCompat.getColor(this, R.color.Sparkle_2);
        int color3 = ContextCompat.getColor(this, R.color.Sparkle_3);

        //viewKonfetti.build().addColors(color1, color2, color3).setDirection(0.0d, 180.0d).setSpeed(0.0f, 10.0f).setTimeToLive(2100).setPosition(500.0f, -250.0f).addShapes(Shape.CIRCLE, Shape.CIRCLE, Shape.CIRCLE).addSizes(new Size(12, 5.0f)).streamFor(350, 2000);

        this.transactionsDetails = (TransactionsDetails) getIntent().getSerializableExtra(Constants.TRANSACTION_DATA);
        TextView textView2 = this.textViewDateTransfer;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(this.transactionsDetails.getDateAndTime());
        textView2.setText(stringBuilder.toString());
        textView2 = this.textViewDescription;
        stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(this.transactionsDetails.getDescription());
        textView2.setText(stringBuilder.toString());
        PiggyBankApplication piggyBankApplication = (PiggyBankApplication) getApplicationContext();
        Map<String, Account> accountMap = piggyBankApplication.getPiggyBankData().getAccounts();
        Iterator it = new ArrayList(accountMap.keySet()).iterator();
        while (it.hasNext()) {
            Account account = (Account) accountMap.get((String) it.next());
            if (account.getAccountType().equals(Constants.ACCOUNT_TYPE_CHILD)) {
                textView = this.textViewToAcc;
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("");
                stringBuilder2.append(account.getPiggyDetails().getPiggyName());
                textView.setText(stringBuilder2.toString());
                piggyBankApplication.setDeviceId(account.getPiggyDetails().getDeviceId());
                break;
            }
        }
        textView = this.textViewFromAccNum;
        StringBuilder stringBuilder3 = new StringBuilder();
        stringBuilder3.append("");
        StringBuilder stringBuilder4 = new StringBuilder();
        stringBuilder4.append("");
        stringBuilder4.append(this.transactionsDetails.getFromAccountNumber());
        stringBuilder3.append(Constants.convertAccountNumberWithSpaces(stringBuilder4.toString()));
        textView.setText(stringBuilder3.toString());
        textView = this.textViewToAccNum;
        stringBuilder3 = new StringBuilder();
        stringBuilder3.append("");
        stringBuilder4 = new StringBuilder();
        stringBuilder4.append("");
        stringBuilder4.append(this.transactionsDetails.getToAccountNumber());
        stringBuilder3.append(Constants.convertAccountNumberWithSpaces(stringBuilder4.toString()));
        textView.setText(stringBuilder3.toString());
        textView = this.textViewFromAcc;
        stringBuilder3 = new StringBuilder();
        stringBuilder3.append("");
        stringBuilder3.append(piggyBankApplication.getPiggyBankData().getName());
        textView.setText(stringBuilder3.toString());
        textView = this.textViewAmountTransfer;
        stringBuilder3 = new StringBuilder();
        stringBuilder3.append("");
        stringBuilder4 = new StringBuilder();
        stringBuilder4.append("");
        stringBuilder4.append(this.transactionsDetails.getAmount());
        stringBuilder3.append(Constants.getFormatedAmount(Constants.generateCurrencyString(stringBuilder4.toString())));
        textView.setText(stringBuilder3.toString());
    }

    public void initGoalUiConfirmation() {
        this.piggyDetails = (PiggyDetails) getIntent().getSerializableExtra(Constants.CONST_Goal_CONFIRMATION);
        this.buttonUpdateToToy = (TextView) findViewById(R.id.updateTo_Toy_Goal_Confirmation);
        this.buttonUpdateToToy.setOnClickListener(this);
        this.textViewExtraAmountGoal = (TextView) findViewById(R.id.extra_amount_Goal_confirmation);
        this.textViewGoalDate = (TextView) findViewById(R.id.goal_date_Goal_Confirmation);
        TextView textView = this.textViewGoalDate;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(this.piggyDetails.getGoalDate());
        textView.setText(stringBuilder.toString());
        this.textViewGaolAmount = (TextView) findViewById(R.id.amount_Goal_Confirmation);
        textView = this.textViewGaolAmount;
        stringBuilder = new StringBuilder();
        stringBuilder.append("");
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("");
        stringBuilder2.append(this.piggyDetails.getGoalAmount());
        stringBuilder.append(Constants.getFormatedAmount(Constants.generateCurrencyString(stringBuilder2.toString())));
        textView.setText(stringBuilder.toString());
        this.textViewGoalName = (TextView) findViewById(R.id.goal_Name_Goal_Confirmation);
        this.textViewGoalName.setText(this.piggyDetails.getGoalName());
        ((PiggyBankApplication) getApplicationContext()).setDeviceId(this.piggyDetails.getDeviceId());
    }

    public void transferConfirmation(boolean isFrom) {
        Intent intentTransferConfirmation = new Intent(this, PiggyBankShowSearchScreenTransferGoalNew.class);
        intentTransferConfirmation.putExtra(Constants.isFrom, Constants.isFromTransfer);
        String value = getIntent().getStringExtra(Constants.CONST_AMOUNT_TO_TRANSFER_KEY);
        StringBuilder stringBuilder = new StringBuilder();
        //stringBuilder.append("onClick: ");
        stringBuilder.append(this.transactionsDetails.getAmount());
        Log.d("PiggyBank", stringBuilder.toString());
        intentTransferConfirmation.putExtra(Constants.CONST_AMOUNT_TO_TRANSFER_KEY, value);
        intentTransferConfirmation.putExtra(Constants.CONST_PIGGY_BALANCE, getIntent().getStringExtra(Constants.CONST_PIGGY_BALANCE));
        intentTransferConfirmation.putExtra("UpdateBalance", isFrom);
        startActivity(intentTransferConfirmation);
        finish();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.updateTo_Toy_Goal_Confirmation:
                Intent intentGoalConfirmation = new Intent(this, PiggyBankShowSearchScreenTransferGoalNew.class);
                intentGoalConfirmation.putExtra(Constants.isFrom, Constants.isFromGoal);
                intentGoalConfirmation.putExtra(Constants.GOAL_NAME, this.piggyDetails.getGoalName());
                String str = Constants.CONST_AMOUNT_TO_TRANSFER_KEY;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("");
                stringBuilder.append(this.piggyDetails.getGoalAmount());
                intentGoalConfirmation.putExtra(str, stringBuilder.toString());
                startActivity(intentGoalConfirmation);
                finish();
                return;
            case R.id.update_To_Toy_Transfer_Confirmation:
                transferConfirmation(false);
                return;
            default:
                return;
        }
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

    public void showGifImage() {
        this.isFirstTime = true;
        final ImageView gifImage = (ImageView) findViewById(R.id.imageView);
        final VideoView videoView = (VideoView) findViewById(R.id.videoView);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("android.resource://");
        stringBuilder.append(getPackageName());
        stringBuilder.append("/");
        stringBuilder.append(R.raw.check_circle_success);
        videoView.setVideoURI(Uri.parse(stringBuilder.toString()));
        videoView.setOnCompletionListener(new OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                if (PiggyBankConfirmationUpadateActivity.this.isFirstTime) {
                    videoView.start();
                    PiggyBankConfirmationUpadateActivity.this.isFirstTime = false;
                }
            }
        });
        this.isFirstTime = true;
        videoView.start();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                gifImage.setVisibility(View.GONE);
            }
        }, 500);
    }
}
