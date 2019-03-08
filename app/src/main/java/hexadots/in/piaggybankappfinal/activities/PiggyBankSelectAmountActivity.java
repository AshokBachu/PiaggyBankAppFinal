package hexadots.in.piaggybankappfinal.activities;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.view.ViewPager.PageTransformer;
import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import hexadots.in.piaggybankappfinal.R;
import hexadots.in.piaggybankappfinal.Constants;
import hexadots.in.piaggybankappfinal.DialogUtils;
import hexadots.in.piaggybankappfinal.PassDataToPiggy;
import hexadots.in.piaggybankappfinal.communication.BluetoothGattInteraction;
import hexadots.in.piaggybankappfinal.communication.CommunicationProtoCalHelper;
import hexadots.in.piaggybankappfinal.interfaces.PiggyUpdatedListener;
import hexadots.in.piaggybankappfinal.viewpager.CardItem;
import hexadots.in.piaggybankappfinal.viewpager.ShowCoinsNotesAdapter;
import java.util.ArrayList;

public class PiggyBankSelectAmountActivity extends BaseActivity {
    public static final String TAG = "BankSelectAmount";
    public static int selectedPosition;
    private String accountBalance;
    private String amount;
    private ImageView arrowImage;
    private ProgressBar coinsLoading;
    Dialog dialog;
    private TextView errorTextView;
    private ImageView imageAmount;
    boolean isFromOnBackPressed;
    boolean isFromOnpause;
    private ViewPager mViewPager;
    private RelativeLayout maskScreenRl;
    PageTransformer pageTransformer = new PageTransformer() {
        public void transformPage(View page, float position) {
            if (position != 0.0f) {
                page.setScaleY((Math.abs(Math.abs(position) - 1.1f) / 1.98f) + 0.58f);
                return;
            }
            page.setScaleX(1.0f);
            page.setScaleY(1.0f);
        }
    };
    PassDataToPiggy passDataToPiggy;
    private RelativeLayout progressBarLayout;
    private RelativeLayout relativeLayoutAnimationPath;
    private TextView skipTextView;
    private TextView textViewAmountTobeSwipe;
    private TextView textViewSwipeAmount;
    private TextView textViewSwipedAmount;

    /* renamed from: hexadots.in.piaggybankappfinal.activities.PiggyBankSelectAmountActivity$2 */
    class C04262 implements OnClickListener {
        C04262() {
        }

        public void onClick(View v) {
        }
    }

    /* renamed from: hexadots.in.piaggybankappfinal.activities.PiggyBankSelectAmountActivity$3 */
    class C04273 implements OnClickListener {
        C04273() {
        }

        public void onClick(View view) {
        }
    }

    /* renamed from: hexadots.in.piaggybankappfinal.activities.PiggyBankSelectAmountActivity$5 */
    class C04295 implements OnClickListener {
        C04295() {
        }

        public void onClick(View view) {
        }
    }

    /* renamed from: hexadots.in.piaggybankappfinal.activities.PiggyBankSelectAmountActivity$6 */
    class C04306 implements DialogInterface.OnClickListener {
        C04306() {
        }

        public void onClick(DialogInterface dialog, int which) {
            BluetoothGattInteraction bluetoothGattInteraction = new BluetoothGattInteraction();
            double amountToUpdate = Double.parseDouble(PiggyBankSelectAmountActivity.this.accountBalance);
            PiggyBankSelectAmountActivity.this.progressBarLayout.setVisibility(View.VISIBLE);
            PiggyBankSelectAmountActivity.this.updateBalance();
        }
    }

    /* renamed from: hexadots.in.piaggybankappfinal.activities.PiggyBankSelectAmountActivity$7 */
    class C04317 implements DialogInterface.OnClickListener {
        C04317() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
            PiggyBankSelectAmountActivity.this.isFromOnBackPressed = true;
        }
    }

    /* renamed from: hexadots.in.piaggybankappfinal.activities.PiggyBankSelectAmountActivity$8 */
    class C04348 implements DialogInterface.OnClickListener {
        C04348() {
        }

        public void onClick(DialogInterface dialog, int which) {
            PiggyBankSelectAmountActivity.this.progressBarLayout.setVisibility(0);
            final BluetoothGattInteraction bluetoothGattInteraction = new BluetoothGattInteraction();
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {

                /* renamed from: hexadots.in.piaggybankappfinal.activities.PiggyBankSelectAmountActivity$8$1$1 */
                class C04321 implements Runnable {
                    C04321() {
                    }

                    public void run() {
                        PiggyBankSelectAmountActivity.this.progressBarLayout.setVisibility(View.GONE);
                        PiggyBankSelectAmountActivity.this.finish();
                    }
                }

                public void run() {
                    bluetoothGattInteraction.sendData(PiggyBankSelectAmountActivity.this, CommunicationProtoCalHelper.showUpdatedBalance());
                    handler.postDelayed(new C04321(), 1000);
                }
            }, 1000);
        }
    }

    /* renamed from: hexadots.in.piaggybankappfinal.activities.PiggyBankSelectAmountActivity$1 */
    class C07511 implements OnPageChangeListener {
        C07511() {
        }

        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        public void onPageSelected(int position) {
            String str = PiggyBankSelectAmountActivity.TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onPageSelected: ");
            stringBuilder.append(position);
            Log.d(str, stringBuilder.toString());
            PiggyBankSelectAmountActivity.selectedPosition = position;
        }

        public void onPageScrollStateChanged(int state) {
        }
    }

    /* renamed from: hexadots.in.piaggybankappfinal.activities.PiggyBankSelectAmountActivity$9 */
    class C07529 implements PiggyUpdatedListener {
        C07529() {
        }

        public void onPiggyUpdated() {
            PiggyBankSelectAmountActivity.this.progressBarLayout.setVisibility(View.GONE);
            String str = PiggyBankSelectAmountActivity.TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onPiggyUpdated1: ");
            stringBuilder.append(BaseActivity.getLoginScreenVisibility(PiggyBankSelectAmountActivity.this));
            Log.d(str, stringBuilder.toString());
            PiggyBankSelectAmountActivity.this.dialog.dismiss();
            if (PiggyBankSelectAmountActivity.this.isFromOnpause) {
                PiggyBankSelectAmountActivity.this.finish();
                return;
            }
            str = PiggyBankSelectAmountActivity.TAG;
            stringBuilder = new StringBuilder();
            stringBuilder.append("onPiggyUpdated2: ");
            stringBuilder.append(BaseActivity.getLoginScreenVisibility(PiggyBankSelectAmountActivity.this));
            Log.d(str, stringBuilder.toString());
            PiggyBankSelectAmountActivity.this.startActivity(new Intent(PiggyBankSelectAmountActivity.this, PiggyBankTransferMoneySuccessScreen.class));
            PiggyBankSelectAmountActivity.this.finish();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_piggy_bank_select_amount);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        PiggyBankHomeNavDrawer.openPasscode = false;
        this.accountBalance = getIntent().getStringExtra(Constants.CONST_PIGGY_BALANCE);
        this.amount = getIntent().getStringExtra(Constants.CONST_AMOUNT_TO_TRANSFER_KEY);
        new BluetoothGattInteraction().sendData(this, CommunicationProtoCalHelper.showProcessing());
        this.coinsLoading = (ProgressBar) findViewById(R.id.coinsLoading);
        this.relativeLayoutAnimationPath = (RelativeLayout) findViewById(R.id.animationPath);
        this.arrowImage = (ImageView) findViewById(R.id.arrowImage);
        slideToAbove();
        this.textViewSwipedAmount = (TextView) findViewById(R.id.swipedAmount);
        this.imageAmount = (ImageView) findViewById(R.id.imageAmount);
        this.mViewPager = (ViewPager) findViewById(R.id.viewPager);
        this.mViewPager.addOnPageChangeListener(new C07511());
        this.progressBarLayout = (RelativeLayout) findViewById(R.id.progressBarLayout);
        this.maskScreenRl = (RelativeLayout) findViewById(R.id.maskScreen);
        this.maskScreenRl.setVisibility(View.GONE);
        this.maskScreenRl.setOnClickListener(new C04262());
        this.errorTextView = (TextView) findViewById(R.id.errorTextView);
        this.progressBarLayout.setOnClickListener(new C04273());
        this.progressBarLayout.setVisibility(View.GONE);
        this.textViewSwipeAmount = (TextView) findViewById(R.id.amountSwipe);
        this.textViewSwipeAmount.setText(Constants.getFormatedAmount(Constants.generateCurrencyString("0.00")));
        this.textViewAmountTobeSwipe = (TextView) findViewById(R.id.amountToUpdate);
        TextView textView = this.textViewSwipedAmount;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(Constants.getFormatedAmount(Constants.generateCurrencyString(this.amount)));
        textView.setText(stringBuilder.toString());
        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setPageTransformer(false, this.pageTransformer);
        new AsyncTask() {
            ArrayList<CardItem> cardItems;

            protected void onPreExecute() {
                super.onPreExecute();
                PiggyBankSelectAmountActivity.this.coinsLoading.setVisibility(View.VISIBLE);
            }

            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                PiggyBankSelectAmountActivity.this.coinsLoading.setVisibility(View.GONE);
                viewPager.setAdapter(new ShowCoinsNotesAdapter(PiggyBankSelectAmountActivity.this.maskScreenRl, PiggyBankSelectAmountActivity.this.accountBalance, PiggyBankSelectAmountActivity.this.coinsLoading, PiggyBankSelectAmountActivity.this.arrowImage, Double.valueOf(Double.parseDouble(PiggyBankSelectAmountActivity.this.amount)), PiggyBankSelectAmountActivity.this.imageAmount, PiggyBankSelectAmountActivity.this.textViewSwipeAmount, PiggyBankSelectAmountActivity.this.progressBarLayout, PiggyBankSelectAmountActivity.this.errorTextView, PiggyBankSelectAmountActivity.this.textViewSwipedAmount, this.cardItems, PiggyBankSelectAmountActivity.this, PiggyBankSelectAmountActivity.this.mViewPager));
            }

            protected Object doInBackground(Object[] objects) {
                this.cardItems = PiggyBankSelectAmountActivity.makeDenominations(PiggyBankSelectAmountActivity.this.amount);
                return null;
            }
        }.execute(new Object[0]);
        this.imageAmount.setOnClickListener(new C04295());
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.piggy_bank_home_nav_drawer, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void showAlertDialog() {
        Builder builder = new Builder(this);
        builder.setTitle("Skip Swipe");
        builder.setPositiveButton("Update now    ", new C04306());
        builder.setNegativeButton("Cancel     ", new C04317());
        builder.setNeutralButton("Update Later      ", new C04348());
        builder.create().show();
    }

    public void updateBalance() {
        this.dialog = DialogUtils.showDialogUtils(this, "Updating KLYA ", 19000);
        this.passDataToPiggy = new PassDataToPiggy();
        this.passDataToPiggy.updateBalance(new BluetoothGattInteraction(), this, this.accountBalance, new C07529());
    }

    public static ArrayList<CardItem> makeDenominations(String amount) {
        int amountData = (int) Double.parseDouble(amount);
        int finalizedAmount = amountData;
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("makeDenominations: ");
        stringBuilder.append(amountData);
        Log.d(str, stringBuilder.toString());
        int singleNum = amountData % 10;
        int i = 10;
        amountData /= 10;
        int tens = amountData % 10;
        amountData /= 10;
        int hundreds = amountData % 10;
        amountData /= 10;
        int thousands = amountData % 10;
        amountData /= 10;
        int tenthousands = amountData % 10;
        int oneLak = (amountData / 10) % 10;
        ArrayList<CardItem> cardItems = new ArrayList();
        boolean z = true;
        if (finalizedAmount == 1) {
            cardItems.add(new CardItem(Constants.EurosAmount[0], Constants.drwableEuros[0], true));
        }
        if (finalizedAmount == 10) {
            cardItems.add(new CardItem(Constants.EurosAmount[0], Constants.drwableEuros[0], true));
            cardItems.add(new CardItem(Constants.EurosAmount[1], Constants.drwableEuros[1], true));
            return cardItems;
        } else if (finalizedAmount == 20) {
            cardItems.add(new CardItem(Constants.EurosAmount[0], Constants.drwableEuros[0], true));
            cardItems.add(new CardItem(Constants.EurosAmount[1], Constants.drwableEuros[1], true));
            cardItems.add(new CardItem(Constants.EurosAmount[2], Constants.drwableEuros[2], true));
            return cardItems;
        } else if (finalizedAmount == 50) {
            cardItems.add(new CardItem(Constants.EurosAmount[1], Constants.drwableEuros[1], true));
            cardItems.add(new CardItem(Constants.EurosAmount[2], Constants.drwableEuros[2], true));
            cardItems.add(new CardItem(Constants.EurosAmount[3], Constants.drwableEuros[3], false));
            return cardItems;
        } else if (finalizedAmount == 100) {
            cardItems.add(new CardItem(Constants.EurosAmount[2], Constants.drwableEuros[2], true));
            cardItems.add(new CardItem(Constants.EurosAmount[3], Constants.drwableEuros[3], false));
            cardItems.add(new CardItem(Constants.EurosAmount[4], Constants.drwableEuros[4], false));
            return cardItems;
        } else if (finalizedAmount == Callback.DEFAULT_DRAG_ANIMATION_DURATION) {
            cardItems.add(new CardItem(Constants.EurosAmount[3], Constants.drwableEuros[3], false));
            cardItems.add(new CardItem(Constants.EurosAmount[4], Constants.drwableEuros[4], false));
            cardItems.add(new CardItem(Constants.EurosAmount[5], Constants.drwableEuros[5], true));
            return cardItems;
        } else if (finalizedAmount == 500) {
            cardItems.add(new CardItem(Constants.EurosAmount[4], Constants.drwableEuros[4], false));
            cardItems.add(new CardItem(Constants.EurosAmount[5], Constants.drwableEuros[5], false));
            cardItems.add(new CardItem(Constants.EurosAmount[6], Constants.drwableEuros[6], false));
            return cardItems;
        } else if (finalizedAmount == 1000) {
            cardItems.add(new CardItem(Constants.EurosAmount[5], Constants.drwableEuros[5], false));
            cardItems.add(new CardItem(Constants.EurosAmount[6], Constants.drwableEuros[6], false));
            cardItems.add(new CardItem(Constants.EurosAmount[7], Constants.drwableEuros[7], false));
            return cardItems;
        } else {
            int i2 = 0;
            while (i2 < Constants.drwableEuros.length) {
                String str2 = TAG;
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("makeDenominations: for loop .1 ");
                stringBuilder2.append(i2);
                Log.d(str2, stringBuilder2.toString());
                if (Double.parseDouble(amount) <= Constants.doublesEuros[i2]) {
                    break;
                }
                Log.d(TAG, "makeDenominations:  ");
                int am = Integer.parseInt(Constants.EurosAmount[i2].replace("MUR", "").trim());
                if (singleNum != 0) {
                    Log.d(TAG, "onCreate: single digits their");
                    if (!(am % 10 == 0 || cardItems.toString().contains(Constants.EurosAmount[i2]))) {
                        if (Constants.EurosAmount[i2].contains("1") && !cardItems.toString().contains(Constants.EurosAmount[i2])) {
                            cardItems.add(new CardItem(Constants.EurosAmount[i2], Constants.drwableEuros[i2], z));
                        }
                        if (Constants.EurosAmount[i2].contains("5") && !cardItems.toString().contains(Constants.EurosAmount[i2])) {
                            cardItems.add(new CardItem(Constants.EurosAmount[i2], Constants.drwableEuros[i2], z));
                        }
                    }
                }
                if (tens != 0) {
                    Log.d(TAG, "onCreate: tens are their");
                    if (!((am / 10) % i == 0 || cardItems.toString().contains(Constants.EurosAmount[i2]))) {
                        cardItems.add(new CardItem(Constants.EurosAmount[i2], Constants.drwableEuros[i2], false));
                    }
                }
                if (hundreds != 0) {
                    Log.d(TAG, "onCreate: HundredsAre their");
                    if (!((am / 100) % 100 == 0 || cardItems.toString().contains(Constants.EurosAmount[i2]))) {
                        cardItems.add(new CardItem(Constants.EurosAmount[i2], Constants.drwableEuros[i2], false));
                    }
                }
                if (!((oneLak == 0 && tenthousands == 0 && thousands == 0) || (am / 100) % 100 == 0 || cardItems.toString().contains(Constants.EurosAmount[i2]))) {
                    cardItems.add(new CardItem(Constants.EurosAmount[i2], Constants.drwableEuros[i2], false));
                }
                if (Double.parseDouble(amount) > 100000.0d) {
                    if ((am / 100) % 100 != 0 && !cardItems.toString().contains(Constants.EurosAmount[i2])) {
                        cardItems.add(new CardItem(Constants.EurosAmount[i2], Constants.drwableEuros[i2], false));
                    }
                }
                i2++;
                i = 10;
                z = true;
            }
            return cardItems;
        }
    }

    public void slideToAbove() {
        AnimationSet set = new AnimationSet(true);
        Animation slide = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 0.0f, 1, -5.0f);
        slide.setDuration(1500);
        slide.setFillAfter(true);
        slide.setFillEnabled(true);
        set.addAnimation(slide);
        this.arrowImage.startAnimation(set);
        slide.setRepeatCount(-1);
        slide.setAnimationListener(new AnimationListener() {
            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
            }
        });
    }

    protected void onPause() {
        super.onPause();
        this.isFromOnpause = true;
        if (this.passDataToPiggy != null) {
            this.passDataToPiggy.removeHandler();
        }
    }

    protected void onResume() {
        super.onResume();
        this.isFromOnpause = false;
    }

    public void onBackPressed() {
        if (this.dialog == null) {
            showAlertDialog();
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 16908332 && this.dialog == null) {
            showAlertDialog();
        }
        if (item.getItemId() == R.id.action_skip && this.dialog == null) {
            showAlertDialog();
        }
        return super.onOptionsItemSelected(item);
    }
}
