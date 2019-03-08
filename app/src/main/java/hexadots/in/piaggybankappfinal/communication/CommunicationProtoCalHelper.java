package hexadots.in.piaggybankappfinal.communication;

import android.util.Log;
import hexadots.in.piaggybankappfinal.Constants;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CommunicationProtoCalHelper {
    public static String passAccountNumberPiggName(String accountNumber, String piggyName, String DDMMYYYYHHMM) {
        DDMMYYYYHHMM = getDateFormat(DDMMYYYYHHMM);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("(");
        stringBuilder.append(accountNumber);
        stringBuilder.append(",");
        stringBuilder.append(piggyName);
        stringBuilder.append("@");
        stringBuilder.append(DDMMYYYYHHMM);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public static String passBalanceCurrencyName(String balance, String currencyName, String DDMMYYYYHHMM) {
        DDMMYYYYHHMM = getDateFormat(DDMMYYYYHHMM);
        int bal = (int) Double.parseDouble(balance);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        stringBuilder.append(bal);
        stringBuilder.append(",");
        stringBuilder.append(currencyName);
        stringBuilder.append("@");
        stringBuilder.append(DDMMYYYYHHMM);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public static String transferToPiggy(String amountTobeAdded, String DDMMYYYYHHMM) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("transferToPiggy: ");
        stringBuilder.append(amountTobeAdded);
        Log.d("mount", stringBuilder.toString());
        DDMMYYYYHHMM = getDateFormat(DDMMYYYYHHMM);
        int amount = (int) Double.parseDouble(amountTobeAdded);
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("{");
        stringBuilder2.append(amount);
        stringBuilder2.append("@");
        stringBuilder2.append(DDMMYYYYHHMM);
        stringBuilder2.append("}");
        return stringBuilder2.toString();
    }

    public static String setGoal(String goal, String goalAmount, String DDMMYYYYHHMM) {
        DDMMYYYYHHMM = getDateFormat(DDMMYYYYHHMM);
        String str = Constants.isFromGoal;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("setGoal: ");
        stringBuilder.append(DDMMYYYYHHMM);
        Log.d(str, stringBuilder.toString());
        int amunt = (int) Double.parseDouble(goalAmount);
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("<");
        stringBuilder2.append(goal);
        stringBuilder2.append(",");
        stringBuilder2.append(amunt);
        stringBuilder2.append("@");
        stringBuilder2.append(DDMMYYYYHHMM);
        stringBuilder2.append(">");
        return stringBuilder2.toString();
    }

    public static String showProcessing() {
        return "#";
    }

    public static String showUpdatedBalance() {
        return "*";
    }

    public static String showUserConnected() {
        return "&";
    }

    public static String getDateWithDDMMYYYYHHMMFormat()
    {
        return new SimpleDateFormat("DDMMYYYYkkmm").format(Calendar.getInstance().getTime());
    }

    public static String getDateFormat(String DDMMYYYYHHMM) {
        StringBuilder date = new StringBuilder();
        date.append("");
        date.append(DDMMYYYYHHMM.charAt(0));
        date.append(DDMMYYYYHHMM.charAt(1));
        //date = date.toString();
        StringBuilder month = new StringBuilder();
        month.append("");
        month.append(DDMMYYYYHHMM.charAt(2));
        month.append(DDMMYYYYHHMM.charAt(3));
        //month = month.toString();
        StringBuilder year = new StringBuilder();
        year.append("");
        year.append(DDMMYYYYHHMM.charAt(6));
        year.append(DDMMYYYYHHMM.charAt(7));
        //year = year.toString();
        StringBuilder hours = new StringBuilder();
        hours.append("");
        hours.append(DDMMYYYYHHMM.charAt(8));
        hours.append(DDMMYYYYHHMM.charAt(9));
        //hours = hours.toString();
        StringBuilder minutes = new StringBuilder();
        minutes.append("");
        minutes.append(DDMMYYYYHHMM.charAt(10));
        minutes.append(DDMMYYYYHHMM.charAt(11));
        //minutes = minutes.toString();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("getDateFormat: ");
        stringBuilder.append(date);
        stringBuilder.append("/");
        stringBuilder.append(month);
        stringBuilder.append("/");
        stringBuilder.append(year);
        stringBuilder.append(" ");
        stringBuilder.append(hours);
        stringBuilder.append(":");
        stringBuilder.append(minutes);
        Log.d("DATE", stringBuilder.toString());
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(date);
        stringBuilder2.append("/");
        stringBuilder2.append(month);
        stringBuilder2.append("/");
        stringBuilder2.append(year);
        stringBuilder2.append(" ");
        stringBuilder2.append(hours);
        stringBuilder2.append(":");
        stringBuilder2.append(minutes);
        return stringBuilder2.toString();
    }
}
