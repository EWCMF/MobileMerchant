package com.android.example.mobilemerchant.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.example.mobilemerchant.R;
import com.android.example.mobilemerchant.data.DebtOwedToOthers;

import java.util.ArrayList;
import java.util.List;

public class DebtOwedAdapter extends RecyclerView.Adapter<DebtOwedAdapter.DebtHolder> {
    private List<DebtOwedToOthers> debtOwedToOthers = new ArrayList<>();
    private ItemClickListener clickListener;

    @Override
    public DebtHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.debt_list_item, parent, false);
        return new DebtHolder(view);
    }

    @Override
    public void onBindViewHolder(DebtHolder holder, int position) {
        DebtOwedToOthers current = debtOwedToOthers.get(position);
        holder.name.setText(current.getName());
        holder.amount.setText(current.getAmountOwed() + " " + current.getCurrencyName());
    }

    @Override
    public int getItemCount() {
        return debtOwedToOthers.size();
    }

    public void setDebtOwedToOthers(List<DebtOwedToOthers> debtOwedToOthers) {
        this.debtOwedToOthers = debtOwedToOthers;
        notifyDataSetChanged();
    }

    public void setClickListener(ItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    class DebtHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name;
        TextView amount;

        public DebtHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.listPersonName);
            amount = itemView.findViewById(R.id.listPersonAmount);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                clickListener.onClick(v, getAdapterPosition());
            }
        }
    }
}
