package com.android.example.mobilemerchant.view;

import android.annotation.SuppressLint;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.example.mobilemerchant.R;
import com.android.example.mobilemerchant.data.DebtOwedItem;
import com.android.example.mobilemerchant.data.DebtOwedPersonWithItems;
import com.android.example.mobilemerchant.data.DebtOwedPerson;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DebtOwedAdapter extends RecyclerView.Adapter<DebtOwedAdapter.DebtHolder> {
    private List<DebtOwedPersonWithItems> debtOwedPersonWithItems = new ArrayList<>();
    private DebtOwedActivity reference;

    DebtOwedAdapter(DebtOwedActivity reference) {
        this.reference = reference;
    }

    @NonNull
    @Override
    public DebtHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.debt_list_item, parent, false);
        return new DebtHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(DebtHolder holder, int position) {
        DebtOwedItemAdapter debtOwedItemAdapter;
        DebtOwedPerson current = debtOwedPersonWithItems.get(position).debtOwedPerson;
        holder.name.setText(current.getName());
        holder.amount.setText(R.string.debtOwedNoItems);
        holder.arrow.setVisibility(View.INVISIBLE);
        debtOwedItemAdapter = new DebtOwedItemAdapter(position, reference);
        if (debtOwedPersonWithItems.get(position).debtOwedItems != null) {
            ArrayList<DebtOwedItem> debtOwedItems = (ArrayList<DebtOwedItem>) debtOwedPersonWithItems.get(position).debtOwedItems;
            if (debtOwedItems.size() > 0) {
                holder.arrow.setVisibility(View.VISIBLE);
                String check = debtOwedItems.get(0).getCurrency();
                boolean same = true;
                for (int i = 0; i < debtOwedItems.size(); i++) {
                    if (!check.equals(debtOwedItems.get(i).getCurrency())) {
                        same = false;
                        break;
                    }
                }
                if (same) {
                    double total = 0;
                    for (int i = 0; i < debtOwedItems.size(); i++) {
                        total += debtOwedItems.get(i).getValue();
                    }
                    String formatted = String.format(Locale.getDefault(),"%.2f", total);
                    holder.amount.setText(formatted + " " + debtOwedItems.get(0).getCurrency());
                }
                else {
                    holder.amount.setText(R.string.debtOwedMixedCurrencies);
                }
            }
            debtOwedItemAdapter.setDebtOwedItems(debtOwedItems);
        }
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(reference));
        holder.recyclerView.setAdapter(debtOwedItemAdapter);
        holder.recyclerView.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return debtOwedPersonWithItems.size();
    }

    void setDebtOwedList(List<DebtOwedPersonWithItems> debtOwedPersonWithItems) {
        this.debtOwedPersonWithItems = debtOwedPersonWithItems;
        notifyDataSetChanged();
    }

    static class DebtHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        TextView name;
        TextView amount;
        ImageView arrow;
        RecyclerView recyclerView;

        DebtHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.listPersonName);
            amount = itemView.findViewById(R.id.listPersonAmount);
            arrow = itemView.findViewById(R.id.debt_list_arrow);
            recyclerView = itemView.findViewById(R.id.list_sublist);
            ConstraintLayout constraintLayout = itemView.findViewById(R.id.list_constraintLayout);
            constraintLayout.setOnClickListener(v -> {
                if (recyclerView.getVisibility() == View.GONE) {
                    recyclerView.setVisibility(View.VISIBLE);
                } else {
                    recyclerView.setVisibility(View.GONE);
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
