package com.android.example.mobilemerchant.view;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
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
import com.android.example.mobilemerchant.data.DebtOthersItem;
import com.android.example.mobilemerchant.data.DebtOwedToOthers;
import com.android.example.mobilemerchant.data.DebtOwedToYou;
import com.android.example.mobilemerchant.data.DebtYouItem;
import com.android.example.mobilemerchant.presentation.DebtOwedOthersViewModel;
import com.android.example.mobilemerchant.presentation.DebtOwedYouViewModel;

import java.util.Objects;

public class DebtOwedActivity extends ComponentActivity implements ItemClickListener {
    RecyclerView recyclerView;
    DebtOwedOthersViewModel debtOwedOthersViewModel;
    DebtOwedYouViewModel debtOwedYouViewModel;
    DebtOwedAdapter debtOwedAdapter;
    private int nameSize;
    private int currentSelectedName;
    private int currentSelectedNameItemSize;
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
            debtOwedOthersViewModel.getAllDebtOthersLive().observe(this, debtOthersNamesWithItems -> {
                debtOwedAdapter.setDebtOwedToOthers(debtOthersNamesWithItems);
                nameSize = debtOthersNamesWithItems.size();
            });
        }
        else {
            debtOwedYouViewModel = new ViewModelProvider(getViewModelStore(), new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(DebtOwedYouViewModel.class);
            debtOwedYouViewModel.getAllDebtYouLive().observe(this, debtYouNamesWithItems -> {
                debtOwedAdapter.setDebtOwedToYous(debtYouNamesWithItems);
                nameSize = debtYouNamesWithItems.size();
            });
        }
    }

    public void openCreateNameDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.create_person_dialog, null);
        builder.setView(dialogView);
        EditText name = dialogView.findViewById(R.id.create_person_dialog_name_editText);
        Button button = dialogView.findViewById(R.id.create_person_dialog_button);
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);

        button.setOnClickListener(v -> {
            dialog.dismiss();
            addToDB(name.getText().toString());
        });
        dialog.show();
    }


    public void openCreateItemDialog(int ownerID) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.create_item_dialog, null);
        builder.setView(dialogView);
        EditText name = dialogView.findViewById(R.id.create_item_dialog_name_editText);
        EditText value = dialogView.findViewById(R.id.create_dialog_value_editText);
        Spinner spinner = dialogView.findViewById(R.id.create_dialog_spinner);
        Button button = dialogView.findViewById(R.id.create_dialog_button);
        ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.currenciesAvailable, R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);

        button.setOnClickListener(v -> {
            dialog.dismiss();
            addItemToNameDB(ownerID, name.getText().toString(), Double.parseDouble(value.getText().toString()), spinner.getSelectedItem().toString());
        });
        dialog.show();
    }

    public void setCurrentSelectedName(int currentSelectedName) {
        this.currentSelectedName = currentSelectedName;
    }

    public void setCurrentSelectedNameItemSize(int currentSelectedNameItemSize) {
        this.currentSelectedNameItemSize = currentSelectedNameItemSize;
    }

    private void addToDB(String name) {
        if (toOthers) {
            debtOwedOthersViewModel.insert(new DebtOwedToOthers(nameSize, name));
        }
        else {
            debtOwedYouViewModel.insert(new DebtOwedToYou(nameSize, name));
        }
    }

    private void addItemToNameDB(int ownerID, String name, double amount, String currency) {
        if (toOthers) {
            debtOwedOthersViewModel.insert(new DebtOthersItem(ownerID, name, amount, currency));
        }
        else {
            debtOwedYouViewModel.insert(new DebtYouItem(ownerID, name, amount, currency));
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
            if (item.getTitle().equals("Add item")) {
                openCreateItemDialog(item.getGroupId());
            }
            if (item.getTitle().equals("Delete")) {
                if (toOthers) {
                    DebtOwedToOthers debtOwedToOthers = debtOwedOthersViewModel.getAllDebtOthersSimple().get(item.getGroupId()).debtOwedToOthers;
                    debtOwedOthersViewModel.delete(debtOwedToOthers);
                }
                else {
                    DebtOwedToYou debtOwedToYou = debtOwedYouViewModel.getAllDebtYouSimple().get(item.getGroupId()).debtOwedToYou;
                    debtOwedYouViewModel.delete(debtOwedToYou);
                }
            }
        } else if (item.getItemId() == R.id.sublist_constraintLayout) {
            if (item.getTitle().equals("Delete item")) {
                if (toOthers) {
                    DebtOthersItem debtOwedToOthers = debtOwedOthersViewModel.getAllDebtOthersSimple().get(currentSelectedName).debtOwedItems.get(item.getGroupId());
                    debtOwedOthersViewModel.delete(debtOwedToOthers);
                }
                else {
                    DebtYouItem debtYouItem = debtOwedYouViewModel.getAllDebtYouSimple().get(currentSelectedName).debtOwedItems.get(item.getGroupId());
                    debtOwedYouViewModel.delete(debtYouItem);
                }
            }
        }
        return super.onContextItemSelected(item);
    }
}
