package com.android.example.mobilemerchant.view;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.example.mobilemerchant.R;
import com.android.example.mobilemerchant.data.DebtOthersItem;
import com.android.example.mobilemerchant.data.DebtYouItem;

import java.util.ArrayList;
import java.util.List;

public class DebtOwedItemAdapter extends RecyclerView.Adapter<DebtOwedItemAdapter.DebtItemHolder> {
    private List<DebtOthersItem> debtOthersItems = new ArrayList<>();
    private List<DebtYouItem> debtYouItems = new ArrayList<>();
    private boolean toOthers;
    private int parentIndex;
    private DebtOwedActivity reference;

    DebtOwedItemAdapter(boolean toOthers, int parentIndex, DebtOwedActivity reference) {
        this.toOthers = toOthers;
        this.parentIndex = parentIndex;
        this.reference = reference;
    }

    @NonNull
    @Override
    public DebtItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.debt_sublist_item, parent, false);
        return new DebtItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DebtItemHolder holder, int position) {
        if (toOthers) {
            holder.name.setText(debtOthersItems.get(position).getItemName());
            holder.amount.setText(debtOthersItems.get(position).getValue() + " " + debtOthersItems.get(position).getCurrency());
        }
        else {
            holder.name.setText(debtYouItems.get(position).getItemName());
            holder.amount.setText(debtYouItems.get(position).getValue() + " " + debtYouItems.get(position).getCurrency());
        }
    }

    @Override
    public int getItemCount() {
        if (toOthers) {
            return debtOthersItems.size();
        }
        else {
            return debtYouItems.size();
        }
    }

    public void setDebtOthersItems(List<DebtOthersItem> debtOthersItems) {
        this.debtOthersItems = debtOthersItems;
        notifyDataSetChanged();
    }

    public void setDebtYouItems(List<DebtYouItem> debtYouItems) {
        this.debtYouItems = debtYouItems;
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
            menu.add(this.getAdapterPosition(), v.getId(), 0, "Update amount");
            menu.add(this.getAdapterPosition(), v.getId(), 0, "Rename item");
            menu.add(this.getAdapterPosition(), v.getId(), 0, "Delete item");
        }
    }
}
