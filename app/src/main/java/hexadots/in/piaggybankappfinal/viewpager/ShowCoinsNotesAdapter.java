package hexadots.in.piaggybankappfinal.viewpager;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import hexadots.in.piaggybankappfinal.R;
import hexadots.in.piaggybankappfinal.Constants;
import hexadots.in.piaggybankappfinal.DialogUtils;
import hexadots.in.piaggybankappfinal.PassDataToPiggy;
import hexadots.in.piaggybankappfinal.ToastUtils;
import hexadots.in.piaggybankappfinal.activities.PiggyBankHomeNavDrawer;
import hexadots.in.piaggybankappfinal.activities.PiggyBankSelectAmountActivity;
import hexadots.in.piaggybankappfinal.activities.PiggyBankTransferMoneySuccessScreen;
import hexadots.in.piaggybankappfinal.communication.BluetoothGattInteraction;
import hexadots.in.piaggybankappfinal.interfaces.PiggyUpdatedListener;
import java.util.ArrayList;
import java.util.List;

public class ShowCoinsNotesAdapter extends PagerAdapter {
    public static final String TAG = "ViewPager";
    private String accountBalance;
    private Double amountToSwipe;
    private ProgressBar coinsLoading;
    private Context context;
    private ImageView imageAmount;
    private ImageView imageAmountArrowAnim;
    private ImageView imageView;
    private List<CardItem> mData = new ArrayList();
    private RelativeLayout maskRel;
    private MediaPlayer mp;
    private RelativeLayout progressBar;
    private RelativeLayout progressBarLayout;
    private Double[] swipedAmount = new Double[1];
    private TextView textViewAmountTobeSwipe;
    private TextView textViewError;
    private TextView textViewSwipeAmount;
    private ViewPager viewPager;

    private class GestureListener extends SimpleOnGestureListener {
        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        /* renamed from: hexadots.in.piaggybankappfinal.viewpager.ShowCoinsNotesAdapter$GestureListener$3 */
        class C04853 extends AsyncTask {
            ArrayList<CardItem> cardItems;

            /* renamed from: hexadots.in.piaggybankappfinal.viewpager.ShowCoinsNotesAdapter$GestureListener$3$1 */
            class C04841 implements Runnable {
                C04841() {
                }

                public void run() {
                    try {
                        ShowCoinsNotesAdapter.this.viewPager.beginFakeDrag();
                        ShowCoinsNotesAdapter.this.viewPager.fakeDragBy(0.0f);
                        ShowCoinsNotesAdapter.this.viewPager.endFakeDrag();
                    } catch (Throwable throwable) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("run: ");
                        stringBuilder.append(throwable.getMessage());
                        Log.d("ViewPager", stringBuilder.toString());
                        throwable.printStackTrace();
                    }
                }
            }

            C04853() {
            }

            protected void onPreExecute() {
                super.onPreExecute();
                ShowCoinsNotesAdapter.this.coinsLoading.setVisibility(View.VISIBLE);
            }

            protected Object doInBackground(Object[] objects) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("");
                stringBuilder.append(ShowCoinsNotesAdapter.this.amountToSwipe.doubleValue() - ShowCoinsNotesAdapter.this.swipedAmount[0].doubleValue());
                this.cardItems = PiggyBankSelectAmountActivity.makeDenominations(stringBuilder.toString());
                return null;
            }

            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                ShowCoinsNotesAdapter.this.coinsLoading.setVisibility(View.GONE);
                ShowCoinsNotesAdapter.this.mData.addAll(this.cardItems);
                ShowCoinsNotesAdapter.this.notifyDataSetChanged();
                new Handler().postDelayed(new C04841(), 0);
            }
        }

        public boolean onDown(MotionEvent e) {
            return true;
        }

        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            try {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > 100.0f && Math.abs(velocityX) > 100.0f) {
                        if (diffX > 0.0f) {
                            onSwipeRight();
                        } else {
                            onSwipeLeft();
                        }
                        result = true;
                    }
                } else if (Math.abs(diffY) > 100.0f && Math.abs(velocityY) > 100.0f) {
                    if (diffY > 0.0f) {
                        onSwipeBottom();
                    } else {
                        onSwipeTop();
                    }
                    result = true;
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return result;
        }

        public void onSwipeRight() {
            Log.d("ViewPager", "onSwipeRight: ");
        }

        public void onSwipeLeft() {
            Log.d("ViewPager", "onSwipeLeft: ");
        }

        public void slideUpAnimation(final int position) {
            Animation slide = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 0.0f, 1, -5.0f);
            slide.setDuration(1500);
            slide.setFillAfter(true);
            slide.setFillEnabled(true);
            ShowCoinsNotesAdapter.this.imageAmount.startAnimation(slide);
            ShowCoinsNotesAdapter.this.imageAmount.animate();
            slide.setAnimationListener(new AnimationListener() {

                /* renamed from: hexadots.in.piaggybankappfinal.viewpager.ShowCoinsNotesAdapter$GestureListener$1$1 */
                class C04821 implements Runnable {
                    C04821() {
                    }

                    public void run() {
                        try {
                            ShowCoinsNotesAdapter.this.viewPager.beginFakeDrag();
                            ShowCoinsNotesAdapter.this.viewPager.fakeDragBy(0.0f);
                            ShowCoinsNotesAdapter.this.viewPager.endFakeDrag();
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("run: ");
                            stringBuilder.append(throwable.getMessage());
                            Log.d("ViewPager", stringBuilder.toString());
                        }
                    }
                }

                public void onAnimationStart(Animation animation) {
                    ShowCoinsNotesAdapter.this.maskRel.setVisibility(View.VISIBLE);
                    ShowCoinsNotesAdapter.this.imageAmount.setImageResource(((CardItem) ShowCoinsNotesAdapter.this.mData.get(position)).getImageResource());
                    ((CardItem) ShowCoinsNotesAdapter.this.mData.get(position)).setVisibility(false);
                    ShowCoinsNotesAdapter.this.viewPager.getAdapter().notifyDataSetChanged();
                    ShowCoinsNotesAdapter.this.coinsLoading.setVisibility(View.VISIBLE);
                    new Handler().postDelayed(new C04821(), 0);
                }

                public void onAnimationRepeat(Animation animation) {
                }

                public void onAnimationEnd(Animation animation) {
                    Log.d("Move", "onAnimationEnd: ");
                    ShowCoinsNotesAdapter.this.maskRel.setVisibility(View.GONE);
                    ShowCoinsNotesAdapter.this.playCoinsAudio(((CardItem) ShowCoinsNotesAdapter.this.mData.get(position)).isPlayCoin());
                    ShowCoinsNotesAdapter.this.imageAmount.setVisibility(View.INVISIBLE);
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("onAnimationEnd: ");
                    stringBuilder.append(ShowCoinsNotesAdapter.this.amountToSwipe);
                    Log.d("Move", stringBuilder.toString());
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("onAnimationEnd: ");
                    stringBuilder.append(ShowCoinsNotesAdapter.this.swipedAmount[0]);
                    Log.d("Move", stringBuilder.toString());
                    TextView access$700 = ShowCoinsNotesAdapter.this.textViewSwipeAmount;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("");
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("");
                    stringBuilder2.append(ShowCoinsNotesAdapter.this.swipedAmount[0]);
                    stringBuilder.append(Constants.getFormatedAmount(Constants.generateCurrencyString(stringBuilder2.toString())));
                    access$700.setText(stringBuilder.toString());
                    access$700 = ShowCoinsNotesAdapter.this.textViewAmountTobeSwipe;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("");
                    stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("");
                    stringBuilder2.append(ShowCoinsNotesAdapter.this.amountToSwipe.doubleValue() - ShowCoinsNotesAdapter.this.swipedAmount[0].doubleValue());
                    stringBuilder.append(Constants.getFormatedAmount(Constants.generateCurrencyString(stringBuilder2.toString())));
                    access$700.setText(stringBuilder.toString());
                    String charSequence = ShowCoinsNotesAdapter.this.textViewSwipeAmount.getText().toString();
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("");
                    stringBuilder.append(ShowCoinsNotesAdapter.this.amountToSwipe);
                    if (charSequence.equals(Constants.getFormatedAmount(Constants.generateCurrencyString(stringBuilder.toString())))) {
                        ShowCoinsNotesAdapter.this.coinsLoading.setVisibility(View.GONE);
                        GestureListener.this.sendAmountShowSuccess();
                    } else {
                        ((CardItem) ShowCoinsNotesAdapter.this.mData.get(position)).setVisibility(true);
                        ShowCoinsNotesAdapter.this.mData.clear();
                        GestureListener.this.loadDataAndSet();
                    }
                    ShowCoinsNotesAdapter.this.imageAmount.clearAnimation();
                    animation.cancel();
                    animation.reset();
                }
            });
        }

        public void updateBalance() {
            final Dialog dialog = DialogUtils.showDialogUtils(ShowCoinsNotesAdapter.this.context, "Updating KLYA ", 19);
            View access$1000 = ShowCoinsNotesAdapter.this.imageAmountArrowAnim;
            Context access$900 = ShowCoinsNotesAdapter.this.context;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("");
            stringBuilder.append(dialog.isShowing());
            ToastUtils.showToast(access$1000, access$900, stringBuilder.toString());
            new PassDataToPiggy().updateBalance(new BluetoothGattInteraction(), ShowCoinsNotesAdapter.this.context, ShowCoinsNotesAdapter.this.accountBalance, new PiggyUpdatedListener() {
                public void onPiggyUpdated() {
                    ShowCoinsNotesAdapter.this.progressBarLayout.setVisibility(View.GONE);
                    dialog.dismiss();
                    PiggyBankHomeNavDrawer.openPasscode = false;
                    ((Activity) ShowCoinsNotesAdapter.this.context).finish();
                    ((Activity) ShowCoinsNotesAdapter.this.context).startActivity(new Intent(ShowCoinsNotesAdapter.this.context, PiggyBankTransferMoneySuccessScreen.class));
                }
            });
        }

        public void sendAmountShowSuccess() {
            ShowCoinsNotesAdapter.this.progressBarLayout.setVisibility(0);
            ShowCoinsNotesAdapter.this.imageAmountArrowAnim.clearAnimation();
            ShowCoinsNotesAdapter.this.imageAmountArrowAnim.setVisibility(View.GONE);
            ((PiggyBankSelectAmountActivity) ShowCoinsNotesAdapter.this.context).updateBalance();
        }

        public void loadDataAndSet() {
            new C04853().execute(new Object[0]);
        }

        public void onSwipeTop() {
            StringBuilder stringBuilder;
            int position = ShowCoinsNotesAdapter.this.viewPager.getCurrentItem();
            if (position == PiggyBankSelectAmountActivity.selectedPosition) {
                int length;
                String cents;
                StringBuilder stringBuilder2;
                String ruppesFormat;
                Double alreadySwiped;
                Log.d("ViewPager", "onSwipeTop: ");
                StringBuilder stringBuilder3 = new StringBuilder();
                stringBuilder3.append("Amount to Swipe ");
                stringBuilder3.append(ShowCoinsNotesAdapter.this.amountToSwipe);
                Log.d("ViewPager", stringBuilder3.toString());
                stringBuilder3 = new StringBuilder();
                stringBuilder3.append("onSwipeTop: swipedAmount");
                stringBuilder3.append(ShowCoinsNotesAdapter.this.swipedAmount[0]);
                Log.d("ViewPager", stringBuilder3.toString());
                Log.d("Move", "onAnimationStart: ");
                Log.d("ViewPager", "Selected Money: ");
                Double aDoubleAmount = Double.valueOf("0");
                int i = 1;
                if (((CardItem) ShowCoinsNotesAdapter.this.mData.get(position)).getmTitleResource().contains("CENT")) {
                    int amount = Integer.parseInt(((CardItem) ShowCoinsNotesAdapter.this.mData.get(position)).getmTitleResource().replaceAll(" ", "").replaceAll("MUR", "").replaceAll("CENT", "").trim());
                    StringBuilder stringBuilder4 = new StringBuilder();
                    stringBuilder4.append("");
                    stringBuilder4.append(amount);
                    stringBuilder4.append("");
                    length = stringBuilder4.toString().length();
                    if (length == 1) {
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("onAnimationEnd: length1 ");
                        stringBuilder.append(amount);
                        Log.d("ViewPager", stringBuilder.toString());
                        //cents = new StringBuilder();
                        //cents.append();
                        //cents.append(amount);
                        
                        cents="0.0"+amount;
                        aDoubleAmount = Double.valueOf(Double.parseDouble(cents.toString()));
                    }
                    if (length == 2) {
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("onAnimationEnd: length2 ");
                        stringBuilder.append(amount);
                        Log.d("ViewPager", stringBuilder.toString());
                        stringBuilder2 = new StringBuilder();
                        stringBuilder2.append("0.");
                        stringBuilder2.append(amount);
                        aDoubleAmount = Double.valueOf(Double.parseDouble(stringBuilder2.toString()));
                    }
                }
                if (((CardItem) ShowCoinsNotesAdapter.this.mData.get(position)).getmTitleResource().contains("MUR")) {
                    aDoubleAmount = Double.valueOf(Double.parseDouble(((CardItem) ShowCoinsNotesAdapter.this.mData.get(position)).getmTitleResource().replaceAll(" ", "").replaceAll("MUR", "").replaceAll("CENT", "").trim()));
                }
                try {
                    ruppesFormat = ShowCoinsNotesAdapter.this.textViewSwipeAmount.getText().toString().replaceAll("MUR", "").trim();
                    stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("onAnimationEnd: ");
                    stringBuilder2.append(ruppesFormat);
                    Log.d("ViewPager", stringBuilder2.toString());
                    if (ruppesFormat.equals("null")) {
                        stringBuilder2 = new StringBuilder();
                        stringBuilder2.append("onCreate: ");
                        stringBuilder2.append(ruppesFormat);
                        Log.d("ViewPager", stringBuilder2.toString());
                        ruppesFormat = "0.0";
                    }
                } catch (Exception e) {
                    ruppesFormat = "0.0";
                }
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append("onAnimationEnd1: ");
                stringBuilder2.append(ruppesFormat);
                Log.d("ViewPager", stringBuilder2.toString());
                ruppesFormat = ruppesFormat.replaceAll(" ", "").trim();
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append("onSwipeTop2: ");
                stringBuilder2.append(ruppesFormat);
                Log.d("ViewPager", stringBuilder2.toString());
                ruppesFormat = Constants.getInidianFormat(ruppesFormat.replaceAll("MUR", ""));
                try {
                    String formatedString = "";
                    stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("");
                    stringBuilder2.append(ruppesFormat.charAt(0));
                    if (stringBuilder2.toString().equals(" ")) {
                        ruppesFormat = ruppesFormat.substring(1, ruppesFormat.length());
                        //StringBuilder cents = new StringBuilder();
                        //cents.append("");
                        //cents.append(ruppesFormat);
                        cents=""+ruppesFormat;
                        cents = cents.toString();
                        for (length = 1; length < ruppesFormat.length(); length++) {
                            stringBuilder = new StringBuilder();
                            stringBuilder.append("");
                            stringBuilder.append(ruppesFormat.charAt(length));
                            if (!stringBuilder.toString().equals(",")) {
                                stringBuilder = new StringBuilder();
                                stringBuilder.append(cents);
                                stringBuilder.append(ruppesFormat.charAt(length));
                                //cents = stringBuilder.toString();
                            }
                        }
                        formatedString = cents;
                    }
                    alreadySwiped = Double.valueOf(Double.parseDouble(""+formatedString));
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                    cents = "";
                    while (i < ruppesFormat.length()) {
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("");
                        stringBuilder.append(ruppesFormat.charAt(i));
                        if (!stringBuilder.toString().equals(",")) {
                            stringBuilder = new StringBuilder();
                            stringBuilder.append(cents);
                            stringBuilder.append(ruppesFormat.charAt(i));
                            cents = stringBuilder.toString();
                        }
                        i++;
                    }
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("onSwipeTop: FormatedString...");
                    stringBuilder.append(cents);
                    Log.d("ViewPager", stringBuilder.toString());
                    alreadySwiped = Double.valueOf(Double.parseDouble(cents));
                }
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append("alreadySwiped: ");
                stringBuilder2.append(alreadySwiped);
                Log.d("ViewPager", stringBuilder2.toString());
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append("aDoubleAmount: ");
                stringBuilder2.append(aDoubleAmount);
                Log.d("ViewPager", stringBuilder2.toString());
                ShowCoinsNotesAdapter.this.swipedAmount[0] = Double.valueOf(aDoubleAmount.doubleValue() + alreadySwiped.doubleValue());
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append("Has to Swipe Amount:");
                stringBuilder2.append(ShowCoinsNotesAdapter.this.amountToSwipe);
                Log.d("ViewPager", stringBuilder2.toString());
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append("Swipe Amount");
                stringBuilder2.append(ShowCoinsNotesAdapter.this.swipedAmount[0]);
                Log.d("ViewPager", stringBuilder2.toString());
                if (ShowCoinsNotesAdapter.this.amountToSwipe.doubleValue() >= ShowCoinsNotesAdapter.this.swipedAmount[0].doubleValue()) {
                    ShowCoinsNotesAdapter.this.textViewError.setVisibility(View.INVISIBLE);
                    slideUpAnimation(position);
                    return;
                }
                ShowCoinsNotesAdapter.this.textViewError.setVisibility(View.VISIBLE);
            }
        }

        public void onSwipeBottom() {
            Log.d("ViewPager", "onSwipeBottom: ");
        }
    }

    class OnSwipeTouchListener implements OnTouchListener {
        public static final String TAG = "OnSwipe";
        private final GestureDetector gestureDetector;

        public OnSwipeTouchListener(Context ctx) {
            this.gestureDetector = new GestureDetector(ctx, new GestureListener());
        }

        public boolean onTouch(View v, MotionEvent event) {
            return this.gestureDetector.onTouchEvent(event);
        }
    }

    public ShowCoinsNotesAdapter(RelativeLayout maskRel, String accountBalance, ProgressBar coinsLoading, ImageView imageAmountArrowAnim, Double amountToSwipe, ImageView imageView, TextView textViewSwipeAmount, RelativeLayout progressBarLayout, TextView textViewError, TextView textViewAmountTobeSwipe, ArrayList<CardItem> cardItems, Context context, ViewPager viewPager) {
        this.maskRel = maskRel;
        this.accountBalance = accountBalance;
        this.imageAmountArrowAnim = imageAmountArrowAnim;
        this.textViewSwipeAmount = textViewSwipeAmount;
        this.progressBarLayout = progressBarLayout;
        this.textViewAmountTobeSwipe = textViewAmountTobeSwipe;
        this.viewPager = viewPager;
        this.imageAmount = imageView;
        this.textViewError = textViewError;
        this.amountToSwipe = amountToSwipe;
        this.context = context;
        this.mData = cardItems;
        this.coinsLoading = coinsLoading;
    }

    public void playCoinsAudio(boolean playCoin) {
        if (this.mp != null) {
            this.mp.reset();
            this.mp.release();
        }
        if (playCoin) {
            this.mp = MediaPlayer.create(this.context, Constants.COIN_SOUND);
        } else {
            this.mp = MediaPlayer.create(this.context, Constants.NOTE_SOUND);
        }
        this.mp.start();
    }

    public float getPageWidth(int position) {
        return 0.98f;
    }

    public Object instantiateItem(ViewGroup container, int position) {
        int i = 0;
        View view = ((Activity) this.context).getLayoutInflater().inflate(R.layout.notes_item, null, false);
        ImageView imageViewNote = (ImageView) view.findViewById(R.id.noteImage);
        CardItem model = (CardItem) this.mData.get(position);
        ((RelativeLayout) view.findViewById(R.id.relativeLayout)).setOnTouchListener(new OnSwipeTouchListener(this.context));
        imageViewNote.setImageResource(model.getImageResource());
        if (!model.canVisible()) {
            i = 4;
        }
        imageViewNote.setVisibility(i);
        container.addView(view);
        return view;
    }

    public int getCount() {
        return this.mData.size();
    }

    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("destroyItem: ");
        stringBuilder.append(position);
        Log.d("ViewPager", stringBuilder.toString());
        container.removeView((View) object);
    }

    public int getItemPosition(Object object) {
        return -2;
    }
}
