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
import com.android.example.mobilemerchant.data.DebtOthersItem;
import com.android.example.mobilemerchant.data.DebtOthersNamesWithItems;
import com.android.example.mobilemerchant.data.DebtOwedToOthers;
import com.android.example.mobilemerchant.data.DebtOwedToYou;
import com.android.example.mobilemerchant.data.DebtYouItem;
import com.android.example.mobilemerchant.data.DebtYouNamesWithItems;

import java.util.ArrayList;
import java.util.List;

public class DebtOwedAdapter extends RecyclerView.Adapter<DebtOwedAdapter.DebtHolder> {
    private List<DebtOthersNamesWithItems> debtOthersNamesWithItems = new ArrayList<>();
    private List<DebtYouNamesWithItems> debtYouNamesWithItems = new ArrayList<>();
    private boolean toOthers;
    private DebtOwedActivity reference;

    DebtOwedAdapter(boolean toOthers, DebtOwedActivity reference) {
        this.toOthers = toOthers;
        this.reference = reference;
    }

    @Override
    public DebtHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.debt_list_item, parent, false);
        return new DebtHolder(view);
    }

    @Override
    public void onBindViewHolder(DebtHolder holder, int position) {
        DebtOwedItemAdapter debtOwedItemAdapter;
        if (toOthers) {
            DebtOwedToOthers current = debtOthersNamesWithItems.get(position).debtOwedToOthers;
            holder.name.setText(current.getName());
            holder.amount.setText("No items");
            debtOwedItemAdapter = new DebtOwedItemAdapter(true, position, reference);
            if (debtOthersNamesWithItems.get(position).debtOwedItems != null) {
                ArrayList<DebtOthersItem> debtOthersItems = (ArrayList<DebtOthersItem>) debtOthersNamesWithItems.get(position).debtOwedItems;
                if (debtOthersItems.size() > 0) {
                    double total = 0;
                    for (int i = 0; i < debtOthersItems.size(); i++) {
                        total += debtOthersItems.get(i).getValue();
                    }
                    holder.amount.setText(total + " " + debtOthersItems.get(0).getCurrency());
                }
                debtOwedItemAdapter.setDebtOthersItems(debtOthersItems);
            }
        }
        else {
            DebtOwedToYou current = debtYouNamesWithItems.get(position).debtOwedToYou;
            holder.name.setText(current.getName());
            holder.amount.setText("No items");
            debtOwedItemAdapter = new DebtOwedItemAdapter(false, position, reference);
            if (debtYouNamesWithItems.get(position).debtOwedItems != null) {
                ArrayList<DebtYouItem> debtYouItems = (ArrayList<DebtYouItem>) debtYouNamesWithItems.get(position).debtOwedItems;
                if (debtYouItems.size() > 0) {
                    double total = 0;
                    for (int i = 0; i < debtYouItems.size(); i++) {
                        total += debtYouItems.get(i).getValue();
                    }
                    holder.amount.setText(total + " " + debtYouItems.get(0).getCurrency());
                }
                debtOwedItemAdapter.setDebtYouItems(debtYouItems);
            }
        }
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(reference));
        holder.recyclerView.setAdapter(debtOwedItemAdapter);
        holder.recyclerView.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        if (toOthers) {
            return debtOthersNamesWithItems.size();
        }
        else {
            return debtYouNamesWithItems.size();
        }
    }

    void setDebtOwedToOthers(List<DebtOthersNamesWithItems> debtOthersNamesWithItems) {
        this.debtOthersNamesWithItems = debtOthersNamesWithItems;
        notifyDataSetChanged();
    }

    void setDebtOwedToYous(List<DebtYouNamesWithItems> debtYouNamesWithItems) {
        this.debtYouNamesWithItems = debtYouNamesWithItems;
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
