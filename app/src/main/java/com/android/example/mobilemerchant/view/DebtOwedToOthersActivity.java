package com.android.example.mobilemerchant.view;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.ComponentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.example.mobilemerchant.R;
import com.android.example.mobilemerchant.data.DebtOwedToOthers;
import com.android.example.mobilemerchant.presentation.DebtOwedViewModel;
import java.util.List;
import java.util.Objects;

public class DebtOwedToOthersActivity extends ComponentActivity implements ItemClickListener {
    RecyclerView recyclerView;
    DebtOwedViewModel debtOwedViewModel;
    DebtOwedAdapter debtOwedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debtothers);
        recyclerView = findViewById(R.id.debt_recyclerView);
        debtOwedAdapter = new DebtOwedAdapter();
        debtOwedAdapter.setClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(debtOwedAdapter);
        debtOwedViewModel = new ViewModelProvider(getViewModelStore(), new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(DebtOwedViewModel.class);
        debtOwedViewModel.getAllDebt().observe(this, new Observer<List<DebtOwedToOthers>>() {
            @Override
            public void onChanged(List<DebtOwedToOthers> debtOwedToOthers) {
                debtOwedAdapter.setDebtOwedToOthers(debtOwedToOthers);
            }
        });
    }


    public void openCreateDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.create_dialog, null);
        builder.setView(dialogView);
        EditText name = dialogView.findViewById(R.id.create_dialog_name_editText);
        EditText value = dialogView.findViewById(R.id.create_dialog_value_editText);
        Spinner spinner = dialogView.findViewById(R.id.create_dialog_spinner);
        Button button = dialogView.findViewById(R.id.create_dialog_button);
        ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.currenciesAvailable, R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                addToDB(name.getText().toString(), Double.parseDouble(value.getText().toString()), spinner.getSelectedItem().toString());
            }
        });
        dialog.show();
    }

    private void addToDB(String name, double value, String currency) {
        debtOwedViewModel.insert(new DebtOwedToOthers(name, value, currency));
    }

    @Override
    public void onClick(View view, int position) {
        final DebtOwedToOthers debtOwedToOthers = Objects.requireNonNull(debtOwedViewModel.getAllDebt().getValue()).get(position);
        debtOwedViewModel.delete(debtOwedToOthers);
    }
}
