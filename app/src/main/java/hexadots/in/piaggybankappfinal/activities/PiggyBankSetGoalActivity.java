package hexadots.in.piaggybankappfinal.activities;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import hexadots.in.piaggybankappfinal.Constants;
import hexadots.in.piaggybankappfinal.PiggyBankApplication;
import hexadots.in.piaggybankappfinal.R;
import hexadots.in.piaggybankappfinal.ToastUtils;
import hexadots.in.piaggybankappfinal.bean.Account;
import hexadots.in.piaggybankappfinal.bean.PiggyBankData;
import hexadots.in.piaggybankappfinal.bean.PiggyDetails;
import hexadots.in.piaggybankappfinal.firebase.FireBaseManger;
import hexadots.in.piaggybankappfinal.interfaces.SavePiggyDetailsListner;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

public class PiggyBankSetGoalActivity extends BaseActivity implements OnClickListener, SavePiggyDetailsListner {
    public static final String TAG = "SetGoal";
    private TextView editTextGoalDate;
    private EditText editTextSetGoalAmount;
    private EditText editTextSetGoalName;
    private Handler handler;
    boolean isFromOnPause;
    private PiggyBankApplication piggyBankApplication;
    private PiggyBankData piggyBankData;
    private PiggyDetails piggyDetails;
    private RelativeLayout progressBarLayout;
    Runnable runnable = new C04373();
    private String textGoal;
    private TextView textViewSetAGoal;

    /* renamed from: hexadots.in.piaggybankappfinal.activities.PiggyBankSetGoalActivity$1 */
    class C04351 implements OnClickListener {
        C04351() {
        }

        public void onClick(View view) {
        }
    }

    /* renamed from: hexadots.in.piaggybankappfinal.activities.PiggyBankSetGoalActivity$2 */
    class C04362 implements OnClickListener {
        C04362() {
        }

        public void onClick(View view) {
            ((InputMethodManager) PiggyBankSetGoalActivity.this.getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(view.getWindowToken(), 0);
            PiggyBankSetGoalActivity.this.showDatePickerDialog();
        }
    }

    /* renamed from: hexadots.in.piaggybankappfinal.activities.PiggyBankSetGoalActivity$3 */
    class C04373 implements Runnable {
        C04373() {
        }

        public void run() {
            new FireBaseManger().savePiggyBankData(PiggyBankSetGoalActivity.this, PiggyBankSetGoalActivity.this.piggyBankData, PiggyBankSetGoalActivity.this);
        }
    }

    /* renamed from: hexadots.in.piaggybankappfinal.activities.PiggyBankSetGoalActivity$4 */
    class C04384 implements OnDateSetListener {
        C04384() {
        }

        public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
            try {
                Calendar myCalendar = Calendar.getInstance();
                myCalendar.set(Calendar.YEAR, selectedyear);
                myCalendar.set(Calendar.MONTH, selectedmonth);
                myCalendar.set(Calendar.DATE, selectedday);
                PiggyBankSetGoalActivity.this.editTextGoalDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(myCalendar.getTime()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_piggy_bank_set_goal);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.textGoal = getIntent().getStringExtra("GoalText");
        this.piggyBankApplication = (PiggyBankApplication) getApplicationContext();
        this.piggyBankData = this.piggyBankApplication.getPiggyBankData();
        this.handler = new Handler();
        this.progressBarLayout = (RelativeLayout) findViewById(R.id.progressBarLayout);
        this.progressBarLayout.setOnClickListener(new C04351());
        this.progressBarLayout.setVisibility(View.GONE);
        this.editTextSetGoalAmount = (EditText) findViewById(R.id.goalAmount);
        this.editTextSetGoalAmount.requestFocus();
        getWindow().setSoftInputMode(4);
        this.editTextSetGoalName = (EditText) findViewById(R.id.goalName);
        this.textViewSetAGoal = (TextView) findViewById(R.id.setAGoal);
        this.editTextGoalDate = (TextView) findViewById(R.id.goalDate);
        this.editTextGoalDate.setInputType(0);
        this.editTextGoalDate.setOnClickListener(new C04362());
        ActionBar supportActionBar = getSupportActionBar();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(this.textGoal);
        supportActionBar.setTitle(stringBuilder.toString());
        this.textViewSetAGoal.setOnClickListener(this);
        assignValuesToUi();
        PiggyBankHomeNavDrawer.openPasscode = false;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 16908332 && !this.progressBarLayout.isShown()) {
            finish();
        }
        if (item.getItemId() == R.id.save) {
            setGoalPiggyDetails(item.getActionView());
        }
        return super.onOptionsItemSelected(item);
    }

    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
        this.isFromOnPause = true;
        if (this.handler != null) {
            this.handler.removeCallbacks(this.runnable);
            this.progressBarLayout.setVisibility(View.GONE);
        }
    }

    protected void onResume() {
        super.onResume();
        this.isFromOnPause = false;
        Log.d(TAG, "onResume: ");
    }

    public void onBackPressed() {
        Log.d(TAG, "onBackPressed: ");
        if (!this.progressBarLayout.isShown()) {
            super.onBackPressed();
        }
    }

    public void assignValuesToUi() {
        Map<String, Account> accountMap = this.piggyBankData.getAccounts();
        Iterator it = new ArrayList(accountMap.keySet()).iterator();
        while (it.hasNext()) {
            Account account = (Account) accountMap.get((String) it.next());
            if (account.getAccountType().equals(Constants.ACCOUNT_TYPE_CHILD)) {
                this.piggyDetails = account.getPiggyDetails();
                if (this.piggyDetails.isGoalCreated() && !this.textGoal.toString().equals("Set New Goal")) {
                    TextView textView = this.editTextGoalDate;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("");
                    stringBuilder.append(this.piggyDetails.getGoalDate());
                    textView.setText(stringBuilder.toString());
                    EditText editText = this.editTextSetGoalAmount;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("");
                    stringBuilder.append(this.piggyDetails.getGoalAmount().intValue());
                    editText.setText(stringBuilder.toString());
                    editText = this.editTextSetGoalAmount;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("");
                    stringBuilder.append(this.piggyDetails.getGoalAmount().intValue());
                    editText.setSelection(stringBuilder.toString().length());
                    editText = this.editTextSetGoalName;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("");
                    stringBuilder.append(this.piggyDetails.getGoalName());
                    editText.setText(stringBuilder.toString());
                    this.piggyDetails.setGoalCreatedDate(new SimpleDateFormat("dd/MM/yyyy hh:mm aa").format(new Date()));
                    this.piggyDetails.setGoalStatus("0");
                }
            }
        }
    }

    public void setGoalPiggyDetails(View view) {
        if (this.piggyDetails.isGoalCreated()) {
            String str;
            StringBuilder stringBuilder;
            boolean dataIsChanged = false;
            String goalDate = this.editTextGoalDate.getText().toString();
            String goalAmount = this.editTextSetGoalAmount.getText().toString();
            String goalName = this.editTextSetGoalName.getText().toString();
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("");
            stringBuilder2.append(this.piggyDetails.getGoalAmount().intValue());
            if (!stringBuilder2.toString().trim().equals(goalAmount.trim())) {
                str = TAG;
                stringBuilder = new StringBuilder();
                stringBuilder.append(this.piggyDetails.getGoalAmount());
                stringBuilder.append("onClick: ");
                stringBuilder.append(goalAmount);
                Log.d(str, stringBuilder.toString());
                Log.d(TAG, "onClick: ");
                dataIsChanged = true;
            }
            if (!this.piggyDetails.getGoalName().equals(goalName)) {
                str = TAG;
                stringBuilder = new StringBuilder();
                stringBuilder.append(this.piggyDetails.getGoalName());
                stringBuilder.append("onClick: ");
                stringBuilder.append(goalName);
                Log.d(str, stringBuilder.toString());
                dataIsChanged = true;
            }
            if (dataIsChanged) {
                saveToServer(view);
            } else {
                ToastUtils.showToast(this.editTextSetGoalName, this, "Please update goal details");
            }
            return;
        }
        saveToServer(view);
    }

    public void onClick(View view) {
        if (view.getId() == R.id.setAGoal) {
            setGoalPiggyDetails(view);
        }
        if (view.getId() == R.id.goalDate) {
            ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(view.getWindowToken(), 0);
            showDatePickerDialog();
        }
    }

    public void saveToServer(View view) {
        String goalAmount = this.editTextSetGoalAmount.getText().toString();
        String goalName = this.editTextSetGoalName.getText().toString();
        String goalDate = this.editTextGoalDate.getText().toString();
        if (goalAmount.isEmpty()) {
            ToastUtils.showToast(editTextSetGoalName,this, "Goal Amount should not be empty");
        } else if (goalName.isEmpty()) {
            ToastUtils.showToast(editTextSetGoalName, this, "Goal Name should not be empty");
        } else if (goalName.length() < 3) {
            ToastUtils.showToast(editTextSetGoalName, this, "please enter Goal Name minimum 3 characters");
        } else {
            int i = 0;
            ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(view.getWindowToken(), 0);
            String goalName2;
            String goalDate2;
            if (Constants.isNetworkAvailable(this)) {
                editTextSetGoalName.setCursorVisible(false);
                editTextSetGoalAmount.setCursorVisible(false);
                Map<String, Account> accountMap = piggyBankData.getAccounts();
                Iterator it = new ArrayList(accountMap.keySet()).iterator();
                while (it.hasNext()) {
                    String goalAmount2;
                    Account account = (Account) accountMap.get((String) it.next());
                    if (!account.getAccountType().equals(Constants.ACCOUNT_TYPE_CHILD)) {
                        goalAmount2 = goalAmount;
                        goalName2 = goalName;
                        goalDate2 = goalDate;
                    } else if (Double.parseDouble(goalAmount) > account.getBalance()) {
                        progressBarLayout.setVisibility(i);
                        piggyDetails = account.getPiggyDetails();
                        piggyDetails.setGoalAmount(Double.valueOf(Double.parseDouble(goalAmount)));
                        piggyDetails.setGoalName(goalName);
                        piggyDetails.setGoalCreated(true);
                        piggyDetails.setGoalDate(goalDate);
                        String DateToStr = new SimpleDateFormat("dd/MM/yyyy hh:mm aa").format(new Date());
                        piggyDetails.setGoalCreatedDate(DateToStr);
                        piggyDetails.setGoalDate(DateToStr);
                        goalAmount2 = goalAmount;
                        piggyDetails.setGoalStatus("0");
                        goalName2 = goalName;
                        goalDate2 = goalDate;
                        handler.postDelayed(runnable, 1000);
                    } else {
                        goalAmount2 = goalAmount;
                        goalName2 = goalName;
                        goalDate2 = goalDate;
                        goalAmount = getString(R.string.piggy);
                        View view2 = editTextSetGoalName;
                        Context context = piggyBankApplication;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Please enter the goal amount more than the ");
                        stringBuilder.append(goalAmount);
                        stringBuilder.append(" balance (");
                        StringBuilder stringBuilder2 = new StringBuilder();
                        stringBuilder2.append("");
                        stringBuilder2.append(account.getBalance());
                        stringBuilder.append(Constants.getFormatedAmount(Constants.generateCurrencyString(stringBuilder2.toString())));
                        stringBuilder.append(")");
                        ToastUtils.showToast(view2, context, stringBuilder.toString());
                    }
                    goalAmount = goalAmount2;
                    goalName = goalName2;
                    goalDate = goalDate2;
                    i = 0;
                }
                goalName2 = goalName;
                goalDate2 = goalDate;
                View goalDate3 = view;
            } else {
                goalName2 = goalName;
                goalDate2 = goalDate;
                Snackbar.make(view, (CharSequence) "Please connect to internet", -1).show();
            }
        }
    }

    public void showDatePickerDialog() {
        Calendar mcurrentDate = Calendar.getInstance();
        DatePickerDialog mDatePicker = new DatePickerDialog(this, new C04384(), mcurrentDate.get(Calendar.YEAR), mcurrentDate.get(Calendar.MONTH), mcurrentDate.get(Calendar.DATE));
        mDatePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        mDatePicker.setTitle("Select date");
        mDatePicker.show();
    }

    public void onSave(boolean isSaved) {
        if (isSaved && !this.isFromOnPause) {
            Intent intentConfirmationUpdate = new Intent(this, PiggyBankConfirmationUpadateActivity.class);
            intentConfirmationUpdate.putExtra(Constants.CONST_CONFIRMATION, Constants.CONST_Goal_CONFIRMATION);
            intentConfirmationUpdate.putExtra(Constants.CONST_Goal_CONFIRMATION, this.piggyDetails);
            startActivity(intentConfirmationUpdate);
            finish();
        }
    }
}
