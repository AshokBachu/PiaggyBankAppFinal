package hexadots.in.piaggybankappfinal.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import hexadots.in.piaggybankappfinal.R;
import hexadots.in.piaggybankappfinal.adapter.AmountSelectionAdapter;
import hexadots.in.piaggybankappfinal.bean.ShowAmountRight;
import hexadots.in.piaggybankappfinal.callbacks.CustomTransferCallBack;
import java.util.ArrayList;

public class AmountSelectionActivity extends AppCompatActivity {

    /* renamed from: hexadots.in.piaggybankappfinal.activities.AmountSelectionActivity$1 */
    class C07481 implements CustomTransferCallBack {
        C07481() {
        }

        public void customTransfer(String amount) {
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_amount_selection);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, 0, false));
        ArrayList<ShowAmountRight> amountStrings = new ArrayList();
        ShowAmountRight showAmountRight1 = new ShowAmountRight("5", false);
        ShowAmountRight showAmountRight2 = new ShowAmountRight("10", false);
        ShowAmountRight showAmountRight3 = new ShowAmountRight("20", false);
        ShowAmountRight showAmountRight4 = new ShowAmountRight("50", false);
        ShowAmountRight showAmountRight5 = new ShowAmountRight("100", false);
        ShowAmountRight showAmountRight6 = new ShowAmountRight("200", false);
        ShowAmountRight showAmountRight7 = new ShowAmountRight("500", false);
        amountStrings.add(showAmountRight1);
        amountStrings.add(showAmountRight2);
        amountStrings.add(showAmountRight3);
        amountStrings.add(showAmountRight4);
        amountStrings.add(showAmountRight5);
        amountStrings.add(showAmountRight6);
        amountStrings.add(showAmountRight7);
        recyclerView.setAdapter(new AmountSelectionAdapter(this, amountStrings, new C07481()));
    }
}
