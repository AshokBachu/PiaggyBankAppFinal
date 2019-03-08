package hexadots.in.piaggybankappfinal;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class SharedPrefManger {
    public static void saveMobileNumber(String mobileNum, Context context) {
        Editor editor = context.getSharedPreferences("PiggyBankSharedPref", 0).edit();
        String str = Constants.CONST_MOBILE_NUMBER;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(mobileNum);
        editor.putString(str, stringBuilder.toString());
        editor.commit();
    }

    public static String getMobileNumber(Context context) {
        return context.getSharedPreferences("PiggyBankSharedPref", 0).getString(Constants.CONST_MOBILE_NUMBER, "");
    }

    public static void saveTransferdAmount(Context context, String amount) {
        String str = Constants.CONST_AMOUNT_TO_TRANSFER_KEY;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("saveTransferdAmount: ");
        stringBuilder.append(amount);
        Log.d(str, stringBuilder.toString());
        Editor editor = context.getSharedPreferences("PiggyBankSharedPref", 0).edit();
        String str2 = Constants.TRANSFER_AMOUNT;
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("");
        stringBuilder2.append(amount);
        editor.putString(str2, stringBuilder2.toString());
        editor.commit();
    }

    public static String getTransferdAmount(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("PiggyBankSharedPref", 0);
        String str = Constants.CONST_AMOUNT_TO_TRANSFER_KEY;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("getTransferdAmount: ");
        stringBuilder.append(sharedPreferences.getString(Constants.TRANSFER_AMOUNT, "0"));
        Log.d(str, stringBuilder.toString());
        return sharedPreferences.getString(Constants.TRANSFER_AMOUNT, "0");
    }
}
