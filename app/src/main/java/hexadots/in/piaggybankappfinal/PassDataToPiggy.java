package hexadots.in.piaggybankappfinal;

import android.content.Context;
import android.os.Handler;
import hexadots.in.piaggybankappfinal.communication.BluetoothGattInteraction;
import hexadots.in.piaggybankappfinal.communication.CommunicationProtoCalHelper;
import hexadots.in.piaggybankappfinal.interfaces.PiggyUpdatedListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PassDataToPiggy {
    Handler handler;
    Runnable runnable;

    public void updateBalance(BluetoothGattInteraction bluetoothGattInteraction, Context context, String blance, PiggyUpdatedListener piggyUpdatedListener) {
        final Date date = new Date();
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ddMMyyyyHHmm");
        bluetoothGattInteraction.sendData(context, CommunicationProtoCalHelper.showProcessing());
        final BluetoothGattInteraction bluetoothGattInteraction2 = bluetoothGattInteraction;
        final Context context2 = context;
        final String str = blance;
        final PiggyUpdatedListener piggyUpdatedListener2 = piggyUpdatedListener;
        this.runnable = new Runnable() {

            /* renamed from: hexadots.in.piaggybankappfinal.PassDataToPiggy$1$1 */
            class C03871 implements Runnable {

                /* renamed from: hexadots.in.piaggybankappfinal.PassDataToPiggy$1$1$1 */
                class C03861 implements Runnable {

                    /* renamed from: hexadots.in.piaggybankappfinal.PassDataToPiggy$1$1$1$1 */
                    class C03851 implements Runnable {
                        C03851() {
                        }

                        public void run() {
                            piggyUpdatedListener2.onPiggyUpdated();
                        }
                    }

                    C03861() {
                    }

                    public void run() {
                        bluetoothGattInteraction2.sendData(context2, CommunicationProtoCalHelper.showUpdatedBalance());
                        PassDataToPiggy.this.handler.postDelayed(new C03851(), 3000);
                    }
                }

                C03871() {
                }

                public void run() {
                    bluetoothGattInteraction2.sendData(context2, CommunicationProtoCalHelper.transferToPiggy("0.00", simpleDateFormat.format(date)));
                    PassDataToPiggy.this.handler.postDelayed(new C03861(), 5000);
                }
            }

            public void run() {
                BluetoothGattInteraction bluetoothGattInteraction = bluetoothGattInteraction2;
                Context context = context2;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("");
                stringBuilder.append(str);
                bluetoothGattInteraction.sendData(context, CommunicationProtoCalHelper.passBalanceCurrencyName(stringBuilder.toString(), "€", simpleDateFormat.format(date)));
                PassDataToPiggy.this.handler.postDelayed(new C03871(), 8000);
            }
        };
        this.handler = new Handler();
        this.handler.postDelayed(this.runnable, 3000);
    }

    public void updateName(BluetoothGattInteraction bluetoothGattInteraction, String piggyName, String accountNumber, Context context, String golaName, String goalAmount, String blance, PiggyUpdatedListener piggyUpdatedListener) {
        String DateToStr = new SimpleDateFormat("ddMMyyyyHHmm").format(new Date());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(accountNumber);
        BluetoothGattInteraction bluetoothGattInteraction2 = bluetoothGattInteraction;
        Context context2 = context;
        bluetoothGattInteraction2.sendData(context2, CommunicationProtoCalHelper.passAccountNumberPiggName(stringBuilder.toString(), piggyName, DateToStr));
        final BluetoothGattInteraction bluetoothGattInteraction3 = bluetoothGattInteraction2;
        final Context context3 = context2;
        final String str = golaName;
        final String str2 = goalAmount;
        final String str3 = DateToStr;
        final String str4 = blance;
        final PiggyUpdatedListener piggyUpdatedListener2 = piggyUpdatedListener;
        Runnable runnable = new Runnable() {

            /* renamed from: hexadots.in.piaggybankappfinal.PassDataToPiggy$2$1 */
            class C03891 implements Runnable {
                C03891() {
                }

                public void run() {
                    PassDataToPiggy.this.updateBalance(bluetoothGattInteraction3, context3, str4, piggyUpdatedListener2);
                }
            }

            public void run() {
                BluetoothGattInteraction bluetoothGattInteraction = bluetoothGattInteraction3;
                Context context = context3;
                //String str = str;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("");
                stringBuilder.append(str2);
                bluetoothGattInteraction.sendData(context, CommunicationProtoCalHelper.setGoal(str, stringBuilder.toString(), str3));
                PassDataToPiggy.this.handler.postDelayed(new C03891(), 5000);
            }
        };
        this.handler = new Handler();
        this.handler.postDelayed(runnable, 5000);
    }

    public void synkKlya(BluetoothGattInteraction bluetoothGattInteraction, Context context, String goalName, String goalAmount, String blance, PiggyUpdatedListener piggyUpdatedListener) {
        final Date date = new Date();
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ddMMyyyyHHmm");
        BluetoothGattInteraction bluetoothGattInteraction2 = bluetoothGattInteraction;
        Context context2 = context;
        bluetoothGattInteraction2.sendData(context2, CommunicationProtoCalHelper.showProcessing());
        final BluetoothGattInteraction bluetoothGattInteraction3 = bluetoothGattInteraction2;
        final Context context3 = context2;
        final String str = blance;
        final String str2 = goalName;
        final String str3 = goalAmount;
        final PiggyUpdatedListener piggyUpdatedListener2 = piggyUpdatedListener;

        Runnable runnable = new Runnable()
        {

            /* renamed from: hexadots.in.piaggybankappfinal.PassDataToPiggy$3$1 */
            class C03941 implements Runnable {

                /* renamed from: hexadots.in.piaggybankappfinal.PassDataToPiggy$3$1$1 */
                class C03931 implements Runnable {

                    /* renamed from: hexadots.in.piaggybankappfinal.PassDataToPiggy$3$1$1$1 */
                    class C03921 implements Runnable {

                        /* renamed from: hexadots.in.piaggybankappfinal.PassDataToPiggy$3$1$1$1$1 */
                        class C03911 implements Runnable {
                            C03911() {
                            }

                            public void run() {
                                piggyUpdatedListener2.onPiggyUpdated();
                            }
                        }

                        C03921() {
                        }

                        public void run() {
                            bluetoothGattInteraction3.sendData(context3, CommunicationProtoCalHelper.setGoal(str2, str3, simpleDateFormat.format(date)));
                            PassDataToPiggy.this.handler.postDelayed(new C03911(), 100);
                        }
                    }

                    C03931() {
                    }

                    public void run() {
                        bluetoothGattInteraction3.sendData(context3, CommunicationProtoCalHelper.showUpdatedBalance());
                        PassDataToPiggy.this.handler.postDelayed(new C03921(), 8000);
                    }
                }

                C03941() {
                }

                public void run() {
                    bluetoothGattInteraction3.sendData(context3, CommunicationProtoCalHelper.transferToPiggy("0.00", simpleDateFormat.format(date)));
                    PassDataToPiggy.this.handler.postDelayed(new C03931(), 5000);
                }
            }

            public void run() {
                BluetoothGattInteraction bluetoothGattInteraction = bluetoothGattInteraction3;
                Context context = context3;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("");
                stringBuilder.append(str);
                bluetoothGattInteraction.sendData(context, CommunicationProtoCalHelper.passBalanceCurrencyName(stringBuilder.toString(), "€", simpleDateFormat.format(date)));
                PassDataToPiggy.this.handler.postDelayed(new C03941(), 8000);
            }
        };
        this.handler = new Handler();
        this.handler.postDelayed(runnable, 3000);
    }

    public void removeHandler() {
    }
}
