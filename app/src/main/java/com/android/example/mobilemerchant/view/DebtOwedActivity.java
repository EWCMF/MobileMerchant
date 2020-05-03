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
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.Arrays;
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

        Button button = findViewById(R.id.debt_owed_button);
        button.setOnClickListener(v -> openCreateNameDialog(null));
    }

    public void openCreateNameDialog(DebtOwedPerson debtOwedPerson) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        ViewGroup parent = findViewById(R.id.debt_owed_constraint);
        View dialogView = getLayoutInflater().inflate(R.layout.create_person_dialog, parent, false);
        builder.setView(dialogView);
        EditText name = dialogView.findViewById(R.id.create_person_dialog_name_editText);
        Button button = dialogView.findViewById(R.id.create_person_dialog_button);
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);

        boolean isUpdate = false;
        if (debtOwedPerson != null) {
            name.setText(debtOwedPerson.getName());
            isUpdate = true;
        }

        boolean finalIsUpdate = isUpdate;
        button.setOnClickListener(v -> {
            if (!name.getText().toString().isEmpty()) {
                dialog.dismiss();
                if (!finalIsUpdate) {
                    addToDB(name.getText().toString());
                }
                else {
                    debtOwedPerson.setName(name.getText().toString());
                    updatePersonDB(debtOwedPerson);
                }
            }
            else {
                Toast.makeText(this, "Enter a name.", Toast.LENGTH_LONG).show();
            }
        });
        dialog.show();
    }


    public void openCreateItemDialog(int ownerID, DebtOwedItem debtOwedItem) {
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

        boolean isUpdate = false;
        if (debtOwedItem != null) {
            isUpdate = true;
            name.setText(debtOwedItem.getItemName());
            value.setText(String.valueOf(debtOwedItem.getValue()));
            String currentCurrency = debtOwedItem.getCurrency();
            String[] currencies = getResources().getStringArray(R.array.currenciesAvailable);
            List<String> searchable = Arrays.asList(currencies);
            int position = searchable.indexOf(currentCurrency);
            spinner.setSelection(position);
        }

        boolean finalIsUpdate = isUpdate;
        button.setOnClickListener(v -> {
            if (!name.getText().toString().isEmpty() && !value.getText().toString().isEmpty()) {
                dialog.dismiss();
                if (!finalIsUpdate) {
                    addItemToNameDB(ownerID, name.getText().toString(), Double.parseDouble(value.getText().toString()), spinner.getSelectedItem().toString());
                }
                else {
                    debtOwedItem.setCurrency(spinner.getSelectedItem().toString());
                    debtOwedItem.setItemName(name.getText().toString());
                    debtOwedItem.setValue(Double.parseDouble(value.getText().toString()));
                    updateItemDB(debtOwedItem);
                }
            }
            else {
                Toast.makeText(this, "Fill out all empty fields.", Toast.LENGTH_LONG).show();
            }
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

    private void updatePersonDB(DebtOwedPerson debtOwedPerson) {
        debtOwedViewModel.update(debtOwedPerson);
    }

    private void updateItemDB(DebtOwedItem debtOwedItem) {
        debtOwedViewModel.update(debtOwedItem);
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
                openCreateItemDialog(ownerID, null);
            }
            if (item.getTitle().equals("Rename")) {
                DebtOwedPerson update = currentData.get(item.getGroupId()).debtOwedPerson;
                openCreateNameDialog(update);
            }
            if (item.getTitle().equals("Delete")) {
                DebtOwedPerson debtOwedPerson = currentData.get(item.getGroupId()).debtOwedPerson;
                debtOwedViewModel.delete(debtOwedPerson);

            }
        } else if (item.getItemId() == R.id.sublist_constraintLayout) {
            if (item.getTitle().equals("Update item")) {
                DebtOwedItem debtOwedItem = currentData.get(currentSelectedName).debtOwedItems.get(item.getGroupId());
                int ownerID = currentData.get(currentSelectedName).debtOwedPerson.getOwedID();
                openCreateItemDialog(ownerID, debtOwedItem);
            }
            if (item.getTitle().equals("Delete item")) {
                DebtOwedItem debtOwedItem = currentData.get(currentSelectedName).debtOwedItems.get(item.getGroupId());
                debtOwedViewModel.delete(debtOwedItem);
            }
        }
        return super.onContextItemSelected(item);
    }
}
