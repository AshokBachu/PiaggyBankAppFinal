package hexadots.in.piaggybankappfinal.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import hexadots.in.piaggybankappfinal.R;
import hexadots.in.piaggybankappfinal.Constants;
import hexadots.in.piaggybankappfinal.PiggyBankApplication;
import hexadots.in.piaggybankappfinal.SharedPrefManger;
import hexadots.in.piaggybankappfinal.ToastUtils;
import hexadots.in.piaggybankappfinal.adapter.AmountSelectionAdapter;
import hexadots.in.piaggybankappfinal.bean.Account;
import hexadots.in.piaggybankappfinal.bean.PiggyBankData;
import hexadots.in.piaggybankappfinal.bean.ShowAmountRight;
import hexadots.in.piaggybankappfinal.bean.TransactionsDetails;
import hexadots.in.piaggybankappfinal.callbacks.CustomTransferCallBack;
import hexadots.in.piaggybankappfinal.firebase.FireBaseManger;
import hexadots.in.piaggybankappfinal.interfaces.SavePiggyDetailsListner;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class PiggyBankTransferAmountActivity extends BaseActivity implements OnClickListener, SavePiggyDetailsListner {
    private TextView accountBalanceTextView;
    Button buttonCustomTransafer;
    private double childAccountBalance;
    private EditText customAmountEdittext;
    private EditText editTextAmount;
    private EditText editTextDescription;
    Handler handler = new Handler();
    boolean isFromOnpause;
    RelativeLayout manuvalTransfer;
    private RelativeLayout progressBarRl;
    RecyclerView recyclerViewAmounts;
    RelativeLayout relativeLayoutRecyclerLayout;
    Runnable runnable = new C04777();
    private TextView skipTextView;
    private TextView textViewTransferToPiggyBank;
    private TransactionsDetails transaction;

    /* renamed from: hexadots.in.piaggybankappfinal.activities.PiggyBankTransferAmountActivity$2 */
    class C04722 implements OnTouchListener {
        C04722() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == 0) {
                PiggyBankTransferAmountActivity.this.showManuvalAmountEntery(false);
            }
            return false;
        }
    }

    /* renamed from: hexadots.in.piaggybankappfinal.activities.PiggyBankTransferAmountActivity$3 */
    class C04733 implements OnClickListener {
        C04733() {
        }

        public void onClick(View v) {
            PiggyBankTransferAmountActivity.this.showManuvalAmountEntery(false);
        }
    }

    /* renamed from: hexadots.in.piaggybankappfinal.activities.PiggyBankTransferAmountActivity$4 */
    class C04744 implements OnClickListener {
        C04744() {
        }

        public void onClick(View v) {
            PiggyBankTransferAmountActivity.this.textViewTransferToPiggyBank.performClick();
        }
    }

    /* renamed from: hexadots.in.piaggybankappfinal.activities.PiggyBankTransferAmountActivity$5 */
    class C04755 implements OnClickListener {
        C04755() {
        }

        public void onClick(View view) {
        }
    }

    /* renamed from: hexadots.in.piaggybankappfinal.activities.PiggyBankTransferAmountActivity$6 */
    class C04766 implements Runnable {
        C04766() {
        }

        public void run() {
            PiggyBankTransferAmountActivity.this.hideSoftKeyboard();
            PiggyBankTransferAmountActivity.this.editTextAmount.requestFocus();
            PiggyBankTransferAmountActivity.this.showOnPirticularEt(PiggyBankTransferAmountActivity.this.editTextAmount);
        }
    }

    /* renamed from: hexadots.in.piaggybankappfinal.activities.PiggyBankTransferAmountActivity$7 */
    class C04777 implements Runnable {
        C04777() {
        }

        public void run() {
            String str = Constants.isFromTransfer;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("run: ");
            stringBuilder.append(BaseActivity.getLoginScreenVisibility(PiggyBankTransferAmountActivity.this));
            Log.d(str, stringBuilder.toString());
            if (!PiggyBankTransferAmountActivity.this.isFromOnpause) {
                Intent confirmationActivity = new Intent(PiggyBankTransferAmountActivity.this, PiggyBankConfirmationUpadateActivity.class);
                confirmationActivity.putExtra(Constants.CONST_CONFIRMATION, Constants.CONST_TRANSFER_CONFIRMATION);
                confirmationActivity.putExtra(Constants.TRANSACTION_DATA, PiggyBankTransferAmountActivity.this.transaction);
                String str2 = Constants.CONST_PIGGY_BALANCE;
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("");
                stringBuilder2.append(PiggyBankTransferAmountActivity.this.childAccountBalance);
                confirmationActivity.putExtra(str2, stringBuilder2.toString());
                confirmationActivity.putExtra(Constants.CONST_AMOUNT_TO_TRANSFER_KEY, PiggyBankTransferAmountActivity.this.editTextAmount.getText().toString());
                Context context = PiggyBankTransferAmountActivity.this;
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append("");
                stringBuilder2.append(PiggyBankTransferAmountActivity.this.editTextAmount.getText().toString());
                SharedPrefManger.saveTransferdAmount(context, stringBuilder2.toString());
                PiggyBankTransferAmountActivity.this.startActivity(confirmationActivity);
                PiggyBankTransferAmountActivity.this.progressBarRl.setVisibility(View.GONE);
                PiggyBankTransferAmountActivity.this.finish();
            }
        }
    }

    /* renamed from: hexadots.in.piaggybankappfinal.activities.PiggyBankTransferAmountActivity$1 */
    class C07621 implements CustomTransferCallBack {
        C07621() {
        }

        public void customTransfer(String amount) {
            if (Constants.CUSTOM_TRANSFER.equals(amount)) {
                Log.d("TRANSFER", "customTransfer: ");
                PiggyBankTransferAmountActivity.this.showManuvalAmountEntery(false);
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("customTransfer:---> ");
            stringBuilder.append(amount);
            Log.d("TRANSFER", stringBuilder.toString());
            String am = amount.replaceAll("MUR", "").replaceAll(" ", "");
            EditText access$000 = PiggyBankTransferAmountActivity.this.editTextAmount;
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("");
            stringBuilder2.append(am);
            access$000.setText(stringBuilder2.toString());
            PiggyBankTransferAmountActivity.this.editTextDescription.setText(Constants.isFromTransfer);
            PiggyBankTransferAmountActivity.this.showManuvalAmountEntery(true);
        }
    }

    public void showDefaultData() {
        this.recyclerViewAmounts.setLayoutManager(new GridLayoutManager(this, 3));
        ArrayList<ShowAmountRight> amountStrings = new ArrayList();
        ShowAmountRight showAmountRight1 = new ShowAmountRight("1", false);
        ShowAmountRight showAmountRight2 = new ShowAmountRight("10", false);
        ShowAmountRight showAmountRight3 = new ShowAmountRight("20", false);
        ShowAmountRight showAmountRight4 = new ShowAmountRight("50", false);
        ShowAmountRight showAmountRight5 = new ShowAmountRight("100", false);
        ShowAmountRight showAmountRight6 = new ShowAmountRight("200", false);
        amountStrings.add(showAmountRight1);
        amountStrings.add(showAmountRight2);
        amountStrings.add(showAmountRight3);
        amountStrings.add(showAmountRight4);
        amountStrings.add(showAmountRight5);
        amountStrings.add(showAmountRight6);
        this.recyclerViewAmounts.setAdapter(new AmountSelectionAdapter(this, amountStrings, new C07621()));
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_piggy_bank_transfer_amount);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(Constants.isFromTransfer);
        this.customAmountEdittext = (EditText) findViewById(R.id.customAmountEdittext);
        this.customAmountEdittext.setOnTouchListener(new C04722());
        this.customAmountEdittext.setOnClickListener(new C04733());
        this.accountBalanceTextView = (TextView) findViewById(R.id.accountBalance);
        TextView textView = this.accountBalanceTextView;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Available Balance :MUR ");
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("");
        stringBuilder2.append(getParentAccount().getBalance());
        stringBuilder.append(Constants.getInidianFormat(stringBuilder2.toString()));
        textView.setText(stringBuilder.toString());
        this.textViewTransferToPiggyBank = (TextView) findViewById(R.id.transferToPiggyBank);
        this.textViewTransferToPiggyBank.setOnClickListener(this);
        this.editTextAmount = (EditText) findViewById(R.id.amount);
        this.manuvalTransfer = (RelativeLayout) findViewById(R.id.manuvalTransfer);
        this.relativeLayoutRecyclerLayout = (RelativeLayout) findViewById(R.id.recyclerViewLayout);
        this.recyclerViewAmounts = (RecyclerView) findViewById(R.id.recyclerViewOfAmounts);
        this.buttonCustomTransafer = (Button) findViewById(R.id.customTransafer);
        this.buttonCustomTransafer.setOnClickListener(new C04744());
        this.editTextDescription = (EditText) findViewById(R.id.description);
        this.progressBarRl = (RelativeLayout) findViewById(R.id.progressBarRl);
        this.progressBarRl.setOnClickListener(new C04755());
        this.progressBarRl.setVisibility(View.GONE);
        showRecyclerLayout();
        showDefaultData();
    }

    public void hideSoftKeyboard() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("hideSoftKeyboard:--> ");
        stringBuilder.append(getCurrentFocus());
        Log.d("hideSoftKeyboard", stringBuilder.toString());
        if (getCurrentFocus() != null) {
            ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    public void showRecyclerLayout() {
        this.relativeLayoutRecyclerLayout.setVisibility(View.VISIBLE);
        this.manuvalTransfer.setVisibility(View.GONE);
    }

    public void showManuvalAmountEntery(boolean shouldFocusOnDescription) {
        this.relativeLayoutRecyclerLayout.setVisibility(View.GONE);
        this.manuvalTransfer.setVisibility(View.VISIBLE);
        if (shouldFocusOnDescription) {
            this.editTextDescription.requestFocus();
            showOnPirticularEt(this.editTextDescription);
            return;
        }
        new Handler().postDelayed(new C04766(), 200);
    }

    public void showOnPirticularEt(EditText editText) {
        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).showSoftInput(editText, 1);
    }

    public Account getParentAccount() {
        Map<String, Account> stringAccountMap = ((PiggyBankApplication) getApplicationContext()).getPiggyBankData().getAccounts();
        ArrayList<String> stringArrayList = new ArrayList(stringAccountMap.keySet());
        for (int i = 0; i < stringArrayList.size(); i++) {
            Account account = (Account) stringAccountMap.get(stringArrayList.get(i));
            if (account.getAccountType().equals(Constants.ACCOUNT_TYPE_PARENT)) {
                return account;
            }
        }
        return null;
    }

    public void saveData(View view) {
        if (this.editTextAmount.getText().toString().isEmpty()) {
            ToastUtils.showToast(this.editTextDescription, this, "Please enter amount");
        } else if (this.editTextDescription.getText().toString().isEmpty()) {
            ToastUtils.showToast(this.editTextDescription, this, "Please enter description");
        } else if (this.editTextDescription.getText().toString().length() < 3) {
            ToastUtils.showToast(this.editTextDescription, this, "Please enter description minimum 3 characters");
        } else {
            double balance = getParentAccount().getBalance();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("");
            stringBuilder.append(this.editTextAmount.getText().toString());
            if (balance < Double.parseDouble(stringBuilder.toString())) {
                View view2 = this.editTextDescription;
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("please enter amount up to ");
                stringBuilder = new StringBuilder();
                stringBuilder.append("");
                stringBuilder.append(getParentAccount().getBalance());
                stringBuilder2.append(Constants.generateCurrencyString(stringBuilder.toString()));
                stringBuilder2.append(" ");
                ToastUtils.showToast(view2, this, stringBuilder2.toString());
                return;
            }
            ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(view.getWindowToken(), 0);
            this.editTextAmount.setCursorVisible(false);
            this.editTextDescription.setCursorVisible(false);
            if (Constants.isNetworkAvailable(this)) {
                this.progressBarRl.setVisibility(View.VISIBLE);
                storeTransactionData();
            } else {
                Snackbar.make(this.editTextAmount, (CharSequence) "Please connect to internet", -1).show();
            }
        }
    }

    public void onClick(View view) {
        if (view.getId() == R.id.transferToPiggyBank) {
            saveData(view);
        }
    }

    protected void onPause() {
        super.onPause();
        this.isFromOnpause = true;
        this.handler.removeCallbacks(this.runnable);
        this.progressBarRl.setVisibility(View.GONE);
    }

    protected void onResume() {
        super.onResume();
        this.isFromOnpause = false;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 16908332 && !this.progressBarRl.isShown()) {
            finish();
        }
        if (item.getItemId() == R.id.save) {
            saveData(item.getActionView());
        }
        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {
        if (!this.progressBarRl.isShown()) {
            super.onBackPressed();
        }
    }

    public void storeTransactionData() {
        PiggyBankTransferAmountActivity piggyBankTransferAmountActivity = this;
        PiggyBankApplication piggyBankApplication = (PiggyBankApplication) getApplicationContext();
        PiggyBankData piggyBankData = piggyBankApplication.getPiggyBankData();
        Map<String, Account> stringAccountMap = piggyBankData.getAccounts();
        Account mainAccount = null;
        Account childAccount = null;
        Set<String> stringSet = stringAccountMap.keySet();
        ArrayList<String> stringKeys = new ArrayList(stringSet);
        Iterator it = stringKeys.iterator();
        while (it.hasNext()) {
            Account account = (Account) stringAccountMap.get((String) it.next());
            if (account.getAccountType().equals(Constants.ACCOUNT_TYPE_CHILD)) {
                childAccount = account;
            }
            if (account.getAccountType().equals(Constants.ACCOUNT_TYPE_PARENT)) {
                mainAccount = account;
            }
        }
        piggyBankTransferAmountActivity.transaction = new TransactionsDetails();
        String dateToStr = new SimpleDateFormat("dd/MM/yyyy hh:mm aa").format(new Date());
        piggyBankTransferAmountActivity.transaction.setDateAndTime(dateToStr);
        String[] stringParts = dateToStr.split(" ");
        String[] dateSplit = stringParts[0].split("/");
        String[] hoursMinutes = stringParts[1].split(":");
        TransactionsDetails transactionsDetails = piggyBankTransferAmountActivity.transaction;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(dateSplit[2]);
        stringBuilder.append("");
        stringBuilder.append(dateSplit[1]);
        stringBuilder.append("");
        stringBuilder.append(dateSplit[0]);
        stringBuilder.append("");
        stringBuilder.append(hoursMinutes[0]);
        stringBuilder.append("");
        stringBuilder.append(hoursMinutes[1]);
        transactionsDetails.setDateAndTimeAsNumber(stringBuilder.toString());
        piggyBankTransferAmountActivity.transaction.setFromAccountId(mainAccount.getAccountId());
        piggyBankTransferAmountActivity.transaction.setFromAccountName(mainAccount.getAccountName());
        piggyBankTransferAmountActivity.transaction.setFromAccountNumber(mainAccount.getAccountNumber());
        piggyBankTransferAmountActivity.transaction.setToAccountNumber(childAccount.getAccountNumber());
        piggyBankTransferAmountActivity.transaction.setUpdatedOnPiggy(Boolean.valueOf(false));
        piggyBankTransferAmountActivity.transaction.setToAccountId(childAccount.getAccountId());
        piggyBankTransferAmountActivity.transaction.setToAccountName(childAccount.getAccountName());
        piggyBankTransferAmountActivity.transaction.setTransactionId(Constants.generateTransactionKey());
        piggyBankTransferAmountActivity.transaction.setDescription(piggyBankTransferAmountActivity.editTextDescription.getText().toString());
        piggyBankTransferAmountActivity.transaction.setAmount(Double.valueOf(Double.parseDouble(piggyBankTransferAmountActivity.editTextAmount.getText().toString())));
        StringBuilder mainAmount = new StringBuilder();
        mainAmount.append("");
        mainAmount.append(mainAccount.getBalance() - Double.parseDouble(piggyBankTransferAmountActivity.editTextAmount.getText().toString()));
        double maiAccountBalance = Double.parseDouble(mainAmount.toString());
        mainAccount.setBalance(maiAccountBalance);
        stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(childAccount.getBalance() + Double.parseDouble(piggyBankTransferAmountActivity.editTextAmount.getText().toString()));
        String childAmount = stringBuilder.toString();
        piggyBankTransferAmountActivity.childAccountBalance = Double.valueOf(Double.parseDouble(childAmount)).doubleValue();
        childAccount.setBalance(Double.parseDouble(childAmount));
        piggyBankData.addTransaction(piggyBankTransferAmountActivity.transaction);
        new FireBaseManger().savePiggyBankData(piggyBankTransferAmountActivity, piggyBankData, piggyBankTransferAmountActivity);
    }

    public void onSave(boolean isSaved) {
        if (isSaved) {
            this.handler.postDelayed(this.runnable, 1000);
        }
    }
}
