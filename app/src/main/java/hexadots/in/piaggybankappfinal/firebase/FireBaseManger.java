package hexadots.in.piaggybankappfinal.firebase;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseReference.CompletionListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import hexadots.in.piaggybankappfinal.Constants;
import hexadots.in.piaggybankappfinal.PiggyBankApplication;
import hexadots.in.piaggybankappfinal.SharedPrefManger;
import hexadots.in.piaggybankappfinal.bean.PiggyBankData;
import hexadots.in.piaggybankappfinal.bean.PiggyStatusDetails;
import hexadots.in.piaggybankappfinal.interfaces.CheckUserListner;
import hexadots.in.piaggybankappfinal.interfaces.ForgotPinCallBack;
import hexadots.in.piaggybankappfinal.interfaces.LoginListener;
import hexadots.in.piaggybankappfinal.interfaces.PiggyDetailsCallBack;
import hexadots.in.piaggybankappfinal.interfaces.SavePiggyDetailsListner;

public class FireBaseManger {
    public static final String TAG = "FireBaseManger";

    public void addAccountLoginDetails(PiggyBankData piggyBankData, Intent intent, Activity activity, String mobileNumber) {
        final String str = mobileNumber;
        final Activity activity2 = activity;
        final PiggyBankData piggyBankData2 = piggyBankData;
        final Intent intent2 = intent;
        FirebaseDatabase.getInstance().getReference(Constants.FIRE_BASE_DATABASE_PATH).child("users").child(mobileNumber).setValue((Object) piggyBankData, new CompletionListener() {
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                SharedPrefManger.saveMobileNumber(str, activity2);
                ((PiggyBankApplication) activity2.getApplicationContext()).setPiggyBankData(piggyBankData2);
                String str = FireBaseManger.TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("onComplete: ");
                stringBuilder.append(databaseReference.toString());
                Log.d(str, stringBuilder.toString());
                activity2.startActivity(intent2);
                activity2.finish();
            }
        });
    }

    public void getParticularUserDetails(String mobileNumber, final Object listener) {
        FirebaseDatabase.getInstance().getReference(Constants.FIRE_BASE_DATABASE_PATH).child("users").child(mobileNumber).addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                StringBuilder dataIsTheir = new StringBuilder();
                dataIsTheir.append("");
                dataIsTheir.append(dataSnapshot.getValue());
                //dataIsTheir = dataIsTheir.toString();
                String str = FireBaseManger.TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("onDataChange: dataIsTheir ");
                stringBuilder.append(dataIsTheir);
                Log.d(str, stringBuilder.toString());
                str = FireBaseManger.TAG;
                stringBuilder = new StringBuilder();
                stringBuilder.append("onDataChange: listener ");
                stringBuilder.append(listener);
                Log.d(str, stringBuilder.toString());
                if (listener instanceof ForgotPinCallBack) {
                    ForgotPinCallBack forgotPinCallBack = (ForgotPinCallBack)listener;
                    if (dataIsTheir.equals("null")) {
                        forgotPinCallBack.onPin("");
                    } else {
                        forgotPinCallBack.onPin(((PiggyBankData) dataSnapshot.getValue(PiggyBankData.class)).getLoginPin());
                    }
                } else if (listener instanceof CheckUserListner) {
                    CheckUserListner checkUserListner = (CheckUserListner)listener;
                    Log.d(TAG, dataIsTheir+" onDataChange:===> "+checkUserListner);
                    Log.d(TAG, "onDataChange: "+(dataIsTheir.equals("null")));
                    Log.d(TAG, "onDataChange: "+dataIsTheir);

                    if ((""+dataIsTheir).equals("null"))
                    {
                        checkUserListner.onCheck(false);
                    }
                    else
                    {
                        checkUserListner.onCheck(true);
                    }
                } else if (listener instanceof LoginListener) {
                    LoginListener loginListener = (LoginListener)listener;
                    if (dataSnapshot.getValue(PiggyBankData.class) != null) {
                        loginListener.loginPiggyBank(true, (PiggyBankData) dataSnapshot.getValue(PiggyBankData.class));
                    } else {
                        loginListener.loginPiggyBank(false, null);
                    }
                }
            }

            public void onCancelled(DatabaseError databaseError) {
                Log.d(FireBaseManger.TAG, "onCancelled: Error Happend");
            }
        });
    }

    public void savePiggyBankData(final Context context, final PiggyBankData piggyBankData, final SavePiggyDetailsListner savePiggyDetailsListner) {
        FirebaseDatabase.getInstance().getReference(Constants.FIRE_BASE_DATABASE_PATH).child("users").child(SharedPrefManger.getMobileNumber(context)).setValue((Object) piggyBankData, new CompletionListener() {
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                Log.d(TAG, "onComplete: ==> "+piggyBankData.toString());
                ((PiggyBankApplication) context.getApplicationContext()).setPiggyBankData(piggyBankData);
                savePiggyDetailsListner.onSave(true);
            }
        });
    }

    public void getPiggyDetails(String deviceName, final PiggyDetailsCallBack piggyDetailsCallBack) {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Constants.FIRE_BASE_PIGGY_DETAILS_DATABASE_PATH);
        stringBuilder.append(deviceName);
        database.child(stringBuilder.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                String str = FireBaseManger.TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("onDataChange:--> ");
                stringBuilder.append(dataSnapshot.toString());
                Log.d(str, stringBuilder.toString());
                PiggyStatusDetails piggyStatusDetails = (PiggyStatusDetails) dataSnapshot.getValue(PiggyStatusDetails.class);
                if (dataSnapshot.toString() == null || piggyStatusDetails == null) {
                    piggyDetailsCallBack.getPiggyDetails(null);
                    return;
                }
                if (dataSnapshot.toString().contains("true")) {
                    piggyStatusDetails.setPiggyConnected(true);
                } else {
                    piggyStatusDetails.setPiggyConnected(false);
                }
                String str2 = FireBaseManger.TAG;
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("onDataChange:-- Data-->1 ");
                stringBuilder2.append(piggyStatusDetails.isPiggyConnected());
                Log.d(str2, stringBuilder2.toString());
                str2 = FireBaseManger.TAG;
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append("onDataChange: -- Data-->2");
                stringBuilder2.append(piggyStatusDetails.getConnectedUserId());
                Log.d(str2, stringBuilder2.toString());
                str2 = FireBaseManger.TAG;
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append("onDataChange: -- Data-->3");
                stringBuilder2.append(piggyStatusDetails.getLinkedAccountId());
                Log.d(str2, stringBuilder2.toString());
                piggyDetailsCallBack.getPiggyDetails(piggyStatusDetails);
            }

            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
