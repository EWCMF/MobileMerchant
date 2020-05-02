package com.android.example.mobilemerchant.view;

import android.app.Activity;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.example.mobilemerchant.R;
import com.android.example.mobilemerchant.data.DebtOwedItem;
import com.android.example.mobilemerchant.data.DebtOwedToOthers;
import com.android.example.mobilemerchant.data.DebtOwedToYou;

import java.util.ArrayList;
import java.util.List;

public class DebtOwedAdapter extends RecyclerView.Adapter<DebtOwedAdapter.DebtHolder> {
    private List<DebtOwedToOthers> debtOwedToOthers = new ArrayList<>();
    private List<DebtOwedToYou> debtOwedToYous = new ArrayList<>();
    private boolean toOthers;
    private Activity activity;

    DebtOwedAdapter(boolean toOthers, Activity activity) {
        this.toOthers = toOthers;
        this.activity = activity;
    }

    @Override
    public DebtHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.debt_list_item, parent, false);
        return new DebtHolder(view);
    }

    @Override
    public void onBindViewHolder(DebtHolder holder, int position) {
        if (toOthers) {
            DebtOwedToOthers current = debtOwedToOthers.get(position);
            holder.name.setText(current.getName());
            holder.amount.setText(current.getAmountOwed() + " " + current.getCurrencyName());
        }
        else {
            DebtOwedToYou current = debtOwedToYous.get(position);
            holder.name.setText(current.getName());
            holder.amount.setText(current.getAmountOwed() + " " + current.getCurrencyName());
        }
        ArrayList<DebtOwedItem> debtOwedItems = new ArrayList<>();
        DebtOwedItemAdapter debtOwedItemAdapter = new DebtOwedItemAdapter();
        debtOwedItemAdapter.setDebtOwedItems(debtOwedItems);
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        holder.recyclerView.setAdapter(debtOwedItemAdapter);
        holder.recyclerView.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        if (toOthers) {
            return debtOwedToOthers.size();
        }
        else {
            return debtOwedToYous.size();
        }
    }

    void setDebtOwedToOthers(List<DebtOwedToOthers> debtOwedToOthers) {
        this.debtOwedToOthers = debtOwedToOthers;
        notifyDataSetChanged();
    }

    void setDebtOwedToYous(List<DebtOwedToYou> debtOwedToYous) {
        this.debtOwedToYous = debtOwedToYous;
        notifyDataSetChanged();
    }

    class DebtHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        TextView name;
        TextView amount;
        RecyclerView recyclerView;

        DebtHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.listPersonName);
            amount = itemView.findViewById(R.id.listPersonAmount);
            recyclerView = itemView.findViewById(R.id.list_sublist);
            ConstraintLayout constraintLayout = itemView.findViewById(R.id.list_constraintLayout);
            constraintLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recyclerView.getVisibility() == View.GONE) {
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                    else {
                        recyclerView.setVisibility(View.GONE);
                    }
                }
            });
            constraintLayout.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                v.setLongClickable(true);
                menu.add(this.getAdapterPosition(), v.getId(), 0, "Add item");
                menu.add(this.getAdapterPosition(), v.getId(), 0, "Rename");
                menu.add(this.getAdapterPosition(), v.getId(), 0, "Delete");
        }
    }
}
