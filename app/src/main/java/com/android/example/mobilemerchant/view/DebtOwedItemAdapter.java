package com.android.example.mobilemerchant.view;

import android.annotation.SuppressLint;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.example.mobilemerchant.R;
import com.android.example.mobilemerchant.data.DebtOwedItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DebtOwedItemAdapter extends RecyclerView.Adapter<DebtOwedItemAdapter.DebtItemHolder> {
    private List<DebtOwedItem> debtOwedItems = new ArrayList<>();
    private int parentIndex;
    private DebtOwedActivity reference;

    DebtOwedItemAdapter(int parentIndex, DebtOwedActivity reference) {
        this.parentIndex = parentIndex;
        this.reference = reference;
    }

    @NonNull
    @Override
    public DebtItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.debt_sublist_item, parent, false);
        return new DebtItemHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull DebtItemHolder holder, int position) {
        holder.name.setText(debtOwedItems.get(position).getItemName());
        String formatted = String.format(Locale.getDefault(),"%.2f", debtOwedItems.get(position).getValue());
        holder.amount.setText(formatted + " " + debtOwedItems.get(position).getCurrency());
    }

    @Override
    public int getItemCount() {
        return debtOwedItems.size();
    }

    void setDebtOwedItems(List<DebtOwedItem> debtOwedItems) {
        this.debtOwedItems = debtOwedItems;
        notifyDataSetChanged();
    }

    class DebtItemHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        TextView name;
        TextView amount;

        DebtItemHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.sublist_name_textView);
            amount = itemView.findViewById(R.id.sublist_value_textView);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            v.setLongClickable(true);
            reference.setCurrentSelectedName(parentIndex);
            menu.add(this.getAdapterPosition(), v.getId(), 0, "Update item");
            menu.add(this.getAdapterPosition(), v.getId(), 0, "Delete item");
        }
    }
}
