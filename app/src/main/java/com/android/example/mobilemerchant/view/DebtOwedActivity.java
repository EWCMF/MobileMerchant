package com.android.example.mobilemerchant.view;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
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
import com.android.example.mobilemerchant.data.DebtOwedItem;
import com.android.example.mobilemerchant.data.DebtOwedPerson;
import com.android.example.mobilemerchant.data.DebtOwedPersonWithItems;
import com.android.example.mobilemerchant.presentation.DebtOwedViewModel;
import com.android.example.mobilemerchant.presentation.DebtOwedViewModelFactory;

import java.util.List;
import java.util.Objects;

public class DebtOwedActivity extends ComponentActivity {
    RecyclerView recyclerView;
    DebtOwedViewModel debtOwedViewModel;
    DebtOwedAdapter debtOwedAdapter;
    EditText searchEditText;
    List<DebtOwedPersonWithItems> currentData;
    private int currentSelectedName;
    boolean toOthers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debt_owed);
        toOthers = Objects.requireNonNull(getIntent().getExtras()).getBoolean("toOthers");

        searchEditText = findViewById(R.id.debt_search_editText);
        searchEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                search();
                return true;
            }
            return false;
        });
        recyclerView = findViewById(R.id.debt_recyclerView);
        debtOwedAdapter = new DebtOwedAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(debtOwedAdapter);
        debtOwedViewModel = new ViewModelProvider(getViewModelStore(), new DebtOwedViewModelFactory(getApplication(), toOthers)).get(DebtOwedViewModel.class);
        if (toOthers) {
            debtOwedViewModel.getAllDebtOwedPersonWithItemsOthers().observe(this, debtOwedPersonWithItems -> {
                debtOwedAdapter.setDebtOwedList(debtOwedPersonWithItems);
                currentData = debtOwedPersonWithItems;
            });
        }
        else {
            debtOwedViewModel.getAllDebtOwedPersonWithItemsYou().observe(this, debtOwedPersonWithItems -> {
                debtOwedAdapter.setDebtOwedList(debtOwedPersonWithItems);
                currentData = debtOwedPersonWithItems;
            });
        }
    }

    public void openCreateNameDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        ViewGroup parent = findViewById(R.id.debt_owed_constraint);
        View dialogView = getLayoutInflater().inflate(R.layout.create_person_dialog, parent, false);
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
        ViewGroup parent = findViewById(R.id.debt_owed_constraint);
        View dialogView = getLayoutInflater().inflate(R.layout.create_item_dialog, parent, false);
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

    private void addToDB(String name) {
        if (toOthers) {
            debtOwedViewModel.insert(new DebtOwedPerson(name, true));
        }
        else {
            debtOwedViewModel.insert(new DebtOwedPerson(name, false));
        }
    }

    private void addItemToNameDB(int ownerID, String name, double amount, String currency) {
        if (toOthers) {
            debtOwedViewModel.insert(new DebtOwedItem(ownerID, name, amount, currency));
        }
        else {
            debtOwedViewModel.insert(new DebtOwedItem(ownerID, name, amount, currency));
        }
    }

    private void search() {
        String text = searchEditText.getText().toString();
        text = "%" + text + "%";
        if (toOthers) {
            debtOwedViewModel.searchOthers(text).observe(this, debtOwedPersonWithItems -> {
                debtOwedAdapter.setDebtOwedList(debtOwedPersonWithItems);
                currentData = debtOwedPersonWithItems;
            });
        }
        else {
            debtOwedViewModel.searchYou(text).observe(this, debtOwedPersonWithItems -> {
                debtOwedAdapter.setDebtOwedList(debtOwedPersonWithItems);
                currentData = debtOwedPersonWithItems;
            });
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.list_constraintLayout) {
            if (item.getTitle().equals("Add item")) {
                int ownerID = currentData.get(item.getGroupId()).debtOwedPerson.getOwedID();
                openCreateItemDialog(ownerID);
            }
            if (item.getTitle().equals("Delete")) {
                DebtOwedPerson debtOwedPerson = currentData.get(item.getGroupId()).debtOwedPerson;
                debtOwedViewModel.delete(debtOwedPerson);

            }
        } else if (item.getItemId() == R.id.sublist_constraintLayout) {
            if (item.getTitle().equals("Delete item")) {
                DebtOwedItem debtOwedItem = currentData.get(currentSelectedName).debtOwedItems.get(item.getGroupId());
                debtOwedViewModel.delete(debtOwedItem);
            }
        }
        return super.onContextItemSelected(item);
    }
}
