package com.android.example.mobilemerchant.view;

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
import com.android.example.mobilemerchant.data.DebtOwedPersonWithItems;
import com.android.example.mobilemerchant.data.DebtOwedPerson;

import java.util.ArrayList;
import java.util.List;

public class DebtOwedAdapter extends RecyclerView.Adapter<DebtOwedAdapter.DebtHolder> {
    private List<DebtOwedPersonWithItems> debtOwedPersonWithItems = new ArrayList<>();
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
        DebtOwedPerson current = debtOwedPersonWithItems.get(position).debtOwedPerson;
        holder.name.setText(current.getName());
        holder.amount.setText("No items");
        debtOwedItemAdapter = new DebtOwedItemAdapter(true, position, reference);
        if (debtOwedPersonWithItems.get(position).debtOwedItems != null) {
            ArrayList<DebtOwedItem> debtOwedItems = (ArrayList<DebtOwedItem>) debtOwedPersonWithItems.get(position).debtOwedItems;
            if (debtOwedItems.size() > 0) {
                double total = 0;
                for (int i = 0; i < debtOwedItems.size(); i++) {
                    total += debtOwedItems.get(i).getValue();
                }
                holder.amount.setText(total + " " + debtOwedItems.get(0).getCurrency());
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
                    } else {
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
