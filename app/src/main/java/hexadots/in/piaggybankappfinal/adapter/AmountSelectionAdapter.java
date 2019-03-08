package hexadots.in.piaggybankappfinal.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import hexadots.in.piaggybankappfinal.R;
import hexadots.in.piaggybankappfinal.Constants;
import hexadots.in.piaggybankappfinal.bean.ShowAmountRight;
import hexadots.in.piaggybankappfinal.callbacks.CustomTransferCallBack;
import java.util.ArrayList;

public class AmountSelectionAdapter extends Adapter<AmountSelectionAdapter.MyViewHolder> {
    private Context context;
    private String currentAmount;
    private CustomTransferCallBack customTransferCallBack;
    private ArrayList<ShowAmountRight> showAmountRights;

    class MyViewHolder extends ViewHolder {
        ImageView imageViewRightMark;
        RelativeLayout selectBackground;
        TextView textViewAmount;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.selectBackground = (RelativeLayout) itemView.findViewById(R.id.selctBackground);
            this.imageViewRightMark = (ImageView) itemView.findViewById(R.id.imageViewRightMark);
            this.textViewAmount = (TextView) itemView.findViewById(R.id.amountText);
            itemView.setOnClickListener(new OnClickListener()
            {
                public void onClick(View v) {
                    AmountSelectionAdapter.this.currentAmount = ((ShowAmountRight) AmountSelectionAdapter.this.showAmountRights.get(MyViewHolder.this.getLayoutPosition())).getAmount();
                    if (Constants.CUSTOM_TRANSFER.equals(AmountSelectionAdapter.this.currentAmount)) {
                        AmountSelectionAdapter.this.customTransferCallBack.customTransfer(Constants.CUSTOM_TRANSFER);
                        return;
                    }
                    AmountSelectionAdapter.this.customTransferCallBack.customTransfer(AmountSelectionAdapter.this.currentAmount);
                    AmountSelectionAdapter.this.showAmountRights.set(MyViewHolder.this.getLayoutPosition(), new ShowAmountRight(((ShowAmountRight) AmountSelectionAdapter.this.showAmountRights.get(MyViewHolder.this.getLayoutPosition())).getAmount(), true));
                    AmountSelectionAdapter.this.currentAmount = ((ShowAmountRight) AmountSelectionAdapter.this.showAmountRights.get(MyViewHolder.this.getLayoutPosition())).getAmount();
                    AmountSelectionAdapter.this.notifyDataSetChanged();
                }
            });
        }
    }

    public AmountSelectionAdapter(Context context, ArrayList<ShowAmountRight> showAmountRights, CustomTransferCallBack customTransferCallBack) {
        this.context = context;
        this.showAmountRights = showAmountRights;
        this.customTransferCallBack = customTransferCallBack;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(((Activity) this.context).getLayoutInflater().inflate(R.layout.amount_selection_card_item, null, false));
    }

    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (Constants.CUSTOM_TRANSFER.equals(((ShowAmountRight) this.showAmountRights.get(position)).getAmount())) {
            holder.textViewAmount.setText(((ShowAmountRight) this.showAmountRights.get(position)).getAmount());
        } else {
            holder.textViewAmount.setText(((ShowAmountRight) this.showAmountRights.get(position)).getAmount());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(this.currentAmount);
        if (stringBuilder.toString().equals(((ShowAmountRight) this.showAmountRights.get(position)).getAmount())) {
            holder.imageViewRightMark.setVisibility(View.GONE);
            holder.textViewAmount.setTextColor(-1);
            holder.selectBackground.setBackgroundResource(R.drawable.selection_background);
            return;
        }
        holder.imageViewRightMark.setVisibility(View.GONE);
    }

    public int getItemCount() {
        return this.showAmountRights.size();
    }
}
