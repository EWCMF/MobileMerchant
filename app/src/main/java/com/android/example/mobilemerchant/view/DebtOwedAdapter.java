package com.android.example.mobilemerchant.view;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.example.mobilemerchant.R;
import com.android.example.mobilemerchant.data.DebtOwedToOthers;

import java.util.ArrayList;

public class DebtOwedAdapter extends ArrayAdapter<DebtOwedToOthers> {
    public DebtOwedAdapter(Activity context, ArrayList<DebtOwedToOthers> arrayList) {
        super(context, 0, arrayList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.debt_list_item, parent, false);
        }

        DebtOwedToOthers currentDebt = getItem(position);
        TextView nameTextView = listItemView.findViewById(R.id.listPersonName);
        assert currentDebt != null;
        nameTextView.setText(currentDebt.getName());

        TextView amountTextView = listItemView.findViewById(R.id.listPersonAmount);
        String amountConcat = currentDebt.getAmountOwed() + " " + currentDebt.getCurrencyName();
        amountTextView.setText(amountConcat);

        return listItemView;

    }
}
