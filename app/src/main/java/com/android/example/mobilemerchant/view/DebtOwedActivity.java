package com.android.example.mobilemerchant.view;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.ComponentActivity;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.example.mobilemerchant.R;
import com.android.example.mobilemerchant.data.DebtOwedToOthers;
import com.android.example.mobilemerchant.data.DebtOwedToYou;
import com.android.example.mobilemerchant.presentation.DebtOwedOthersViewModel;
import com.android.example.mobilemerchant.presentation.DebtOwedYouViewModel;

import java.util.Objects;

public class DebtOwedActivity extends ComponentActivity implements ItemClickListener {
    RecyclerView recyclerView;
    DebtOwedOthersViewModel debtOwedOthersViewModel;
    DebtOwedYouViewModel debtOwedYouViewModel;
    DebtOwedAdapter debtOwedAdapter;
    boolean toOthers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debtothers);
        toOthers = Objects.requireNonNull(getIntent().getExtras()).getBoolean("toOthers");
        recyclerView = findViewById(R.id.debt_recyclerView);
        debtOwedAdapter = new DebtOwedAdapter(toOthers, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(debtOwedAdapter);

        if (toOthers) {
            debtOwedOthersViewModel = new ViewModelProvider(getViewModelStore(), new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(DebtOwedOthersViewModel.class);
            debtOwedOthersViewModel.getAllDebtOthers().observe(this, debtOwedToOthers -> debtOwedAdapter.setDebtOwedToOthers(debtOwedToOthers));
        }
        else {
            debtOwedYouViewModel = new ViewModelProvider(getViewModelStore(), new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(DebtOwedYouViewModel.class);
            debtOwedYouViewModel.getAllDebtYou().observe(this, debtOwedToYous -> debtOwedAdapter.setDebtOwedToYous(debtOwedToYous));
        }
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

        button.setOnClickListener(v -> {
            dialog.dismiss();
            addToDB(name.getText().toString(), Double.parseDouble(value.getText().toString()), spinner.getSelectedItem().toString());
        });
        dialog.show();
    }

    private void addToDB(String name, double value, String currency) {
        if (toOthers) {
            debtOwedOthersViewModel.insert(new DebtOwedToOthers(name, value, currency));
        }
        else {
            debtOwedYouViewModel.insert(new DebtOwedToYou(name, value, currency));
        }
    }

    @Override
    public void onClick(View view, int position) {
        if (toOthers) {
            final DebtOwedToOthers debtOwedToOthers = Objects.requireNonNull(debtOwedOthersViewModel.getAllDebtOthers().getValue()).get(position);
            debtOwedOthersViewModel.delete(debtOwedToOthers);
        }
        else {
            final DebtOwedToYou debtOwedToYou = Objects.requireNonNull(debtOwedYouViewModel.getAllDebtYou().getValue()).get(position);
            debtOwedYouViewModel.delete(debtOwedToYou);
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.list_constraintLayout) {
            Log.d("test1", item.getTitle().toString());
        } else if (item.getItemId() == R.id.sublist_constraintLayout) {
            Log.d("test2", item.getGroupId() + "");
            Log.d("test3", item.getTitle().toString());
        }
        return super.onContextItemSelected(item);
    }
}
