package hexadots.in.piaggybankappfinal;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import hexadots.in.piaggybankappfinal.bean.Account;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.text.Format;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class Constants {
    public static final String ACCOUNT_TYPE_CHILD = "child";
    public static final String ACCOUNT_TYPE_PARENT = "parent";
    public static final String ATTACHED_ACCOUNT_NUMBER = "ATTCHED_ACC";
    public static final String CHARACTERISTIC_TOY_UUID = "0000ffe1-0000-1000-8000-00805f9b34fb";
    public static final String CHILDREN_ACCOUNT_NAME = "Children Account";
    public static int COIN_SOUND = R.raw.coin_drop;
    public static final String CONST_AMOUNT_TO_TRANSFER_KEY = "Amount";
    public static final String CONST_CONFIRMATION = "Confirmation";
    public static final String CONST_DEVICE_ADDRES = "DeviceAddress";
    public static final String CONST_DEVICE_NAME = "DeviceName";
    public static final String CONST_Goal_CONFIRMATION = "GoalConfirmation";
    public static final String CONST_MOBILE_NUMBER = "MobileNumber";
    public static final String CONST_PIGGY_BALANCE = "PiggyBalance";
    public static final String CONST_TRANSFER_CONFIRMATION = "TransferConfirmation";
    public static final String CUSTOM_TRANSFER = "Custom Transfer";
    public static final String DESCRIPTOR_CONFIG_UUID = "00002902-0000-1000-8000-00805f9b34fb";
    public static final String DEVICE_NAME = "MLT-BT05";
    public static final String DISCONNECT_FUN = "Disconnect";
    public static final String ENABLE_AUTO_SEARCH_FUN = "AutoSearchFun";
    public static final String[] EurosAmount = new String[]{"MUR 1", "MUR 10", "MUR 20", "MUR 50", "MUR 100", "MUR 200", "MUR 500", "MUR 1000"};
    public static final String FIRE_BASE_DATABASE_PATH = "PiggyBank/HD_KlyaDemo";
    public static final String FIRE_BASE_PIGGY_DETAILS_DATABASE_PATH = "PiggyBank/HD_KlyaDemo/piggyDevices/";
    public static final String GOALAMOUNT = "GoalAmount";
    public static final String GOAL_NAME = "GoalName";
    public static final String IS_FROM_RESET = "FromReset";
    public static final String MESSAGE = "Message";
    public static int NOTE_SOUND = R.raw.note_sound;
    public static final double PARENT_ACCOUNT_BALANCE = 9999.0d;
    public static final String PIGGY_DETAILS_PATH = "https://hdchatpay.firebaseio.com/PiggyBank/NBPiggybankDemo/piggyDevices";
    public static final String PIGGY_NMAE = "PIGGY NAME";
    public static final int REQUEST_ENABLE_BT = 1;
    public static final String SAVINGS_ACCOUNT_NAME = "Savings Account";
    public static final long SCAN_PERIOD = 30000;
    public static final String SERVICE_UUID = "0000ffe0-0000-1000-8000-00805f9b34fb";
    public static final String TAG = "Constants";
    public static final String TRANSACTION_DATA = "Transaction";
    public static final String TRANSFER_AMOUNT = "Transfer Amount";
    public static final double[] doublesEuros = new double[]{1.0d, 10.0d, 20.0d, 50.0d, 100.0d, 200.0d, 500.0d, 1000.0d};
    public static final int[] drwableEuros = new int[]{R.drawable.one_rupee_mur, R.drawable.ten_ruppe_mur, R.drawable.twnty_ruppee_mur, R.drawable.fifty_ruppe_mur, R.drawable.hundred_ruppe_mur, R.drawable.two_hundred_ruppee_mur, R.drawable.fivehundred_ruppees_mur, R.drawable.thousand_ruppes_mur};
    public static final String isFrom = "FromWhere";
    public static final String isFromGoal = "Goal";
    public static final String isFromSetUp = "SetUp";
    public static final String isFromTransfer = "Transfer";

    public static boolean isNetworkAvailable(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static String generateAccountNumber() {
        String accountNumber = "";
        for (int i = 0; i < 16; i++) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(accountNumber);
            stringBuilder.append(new Random().nextInt(9));
            accountNumber = stringBuilder.toString();
        }
        return accountNumber;
    }

    public static String generateAccountKey() {
        String randomAccountKey = "";
        String abcds = "abcdefghijklmnopqrstuvwxyz";
        Random random = new Random();
        for (int i = 0; i < 7; i++) {
            int generatedChar = random.nextInt(26);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(randomAccountKey);
            stringBuilder.append(abcds.charAt(generatedChar));
            randomAccountKey = stringBuilder.toString();
        }
        StringBuilder number = new StringBuilder();
        number.append("");
        number.append(new Random().nextInt(9));
        number.append("");
        number.append(new Random().nextInt(9));
        number.append("");
        number.append(new Random().nextInt(9));
        number.append("");
        number.append(new Random().nextInt(9));
        //number = number.toString();
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(randomAccountKey);
        stringBuilder2.append(number);
        stringBuilder2.append("_");
        stringBuilder2.append(System.currentTimeMillis());
        return stringBuilder2.toString();
    }

    public static String generateTransactionKey() {
        String abcds = "abcdefghijklmnopqrstuvwxyz";
        Random random = new Random();
        StringBuilder data = new StringBuilder();
        data.append("");
        data.append(abcds.charAt(random.nextInt(26)));
        data.append("");
        data.append(abcds.charAt(random.nextInt(26)));
        data.append("");
        data.append(abcds.charAt(random.nextInt(26)));
        data.append("");
        data.append(abcds.charAt(random.nextInt(26)));
        //data = data.toString();
        StringBuilder digits = new StringBuilder();
        digits.append("");
        digits.append(random.nextInt(9));
        digits.append("");
        digits.append(random.nextInt(9));
        digits.append("");
        digits.append(random.nextInt(9));
        digits.append("");
        digits.append(random.nextInt(9));
        digits.append("");
        digits.append(random.nextInt(9));
        //digits = digits.toString();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("tr");
        stringBuilder.append(data);
        stringBuilder.append(digits);
        stringBuilder.append("_");
        stringBuilder.append(System.currentTimeMillis());
        return stringBuilder.toString();
    }

    public static String getCurrencySymbol() {
        return NumberFormat.getCurrencyInstance(new Locale("PT", "pt")).getCurrency().getSymbol();
    }

    public static String generateCurrencyString(String currencyString) {
        NumberFormat portugesFormat = NumberFormat.getCurrencyInstance(new Locale("en", "mu"));
        if (Double.valueOf(Double.parseDouble(currencyString)).doubleValue() > 0.0d) {
            PrintStream printStream = System.out;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("portugal: ");
            stringBuilder.append(portugesFormat.format(Double.parseDouble(currencyString)));
            printStream.println(stringBuilder.toString());
            return portugesFormat.format(Double.parseDouble(currencyString));
        }
        portugesFormat.setMaximumFractionDigits(2);
        portugesFormat.setMinimumFractionDigits(0);
        return portugesFormat.format(Double.parseDouble(currencyString));
    }

    public static String getFormatedAmount(String amount) {
        Format format = NumberFormat.getCurrencyInstance(new Locale("en", "mu"));
        String replacedAmount = convertPortugalToRuppes(amount);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("MUR ");
        stringBuilder.append(format.format(new BigDecimal(replacedAmount)).toLowerCase().replaceAll("₹", "").replaceAll("rs", ""));
        return stringBuilder.toString();
    }

    public static String getInidianFormat(String amount) {
        return NumberFormat.getCurrencyInstance(new Locale("en", "in")).format(new BigDecimal(amount.replaceAll(",", ""))).toLowerCase().replaceAll("₹", "").replaceAll("rs", "").replaceAll(" ", "");
    }

    public static String convertPortugalToRuppes(String portugalFormat) {
        BigDecimal bigDecimal = null;
        try {
            bigDecimal = new BigDecimal(NumberFormat.getCurrencyInstance(new Locale("en", "mu")).parse(portugalFormat).toString());
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("convertPortugalToRuppes: ");
            stringBuilder.append(bigDecimal);
            Log.d(str, stringBuilder.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("");
        stringBuilder2.append(bigDecimal);
        return stringBuilder2.toString();
    }

    public static String convertAccountNumberWithSpaces(String accountNumber) {
        StringBuilder str = new StringBuilder();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("convertAccountNumberWithSpaces: ");
        stringBuilder.append(accountNumber);
        //Log.d(str, stringBuilder.toString());
        try {
            str = new StringBuilder();
            str.append(accountNumber.substring(0, 4));
            str.append(" ");
            str.append(accountNumber.substring(4, 8));
            str.append(" ");
            str.append(accountNumber.substring(8, 12));
            str.append(" ");
            str.append(accountNumber.substring(12, 16));
            return str.toString();
        } catch (Throwable e) {
            e.printStackTrace();
            return accountNumber;
        }
    }

    public static String getAccountKey(Context context) {
        HashMap<String, Account> accountHashMap = (HashMap) ((PiggyBankApplication) context.getApplicationContext()).getPiggyBankData().getAccounts();
        ArrayList<String> stringArrayList = new ArrayList(accountHashMap.keySet());
        for (int i = 0; i < stringArrayList.size(); i++) {
            if (((Account) accountHashMap.get(stringArrayList.get(i))).getAccountType().equals(ACCOUNT_TYPE_CHILD)) {
                return (String) stringArrayList.get(i);
            }
        }
        return "";
    }

    public static Account getChildAccount(Context context) {
        Map<String, Account> stringAccountMap = ((PiggyBankApplication) context.getApplicationContext()).getPiggyBankData().getAccounts();
        ArrayList<String> stringArrayList = new ArrayList(stringAccountMap.keySet());
        for (int i = 0; i < stringArrayList.size(); i++) {
            Account account = (Account) stringAccountMap.get(stringArrayList.get(i));
            if (account.getAccountType().equals(ACCOUNT_TYPE_CHILD)) {
                return account;
            }
        }
        return null;
    }
}
