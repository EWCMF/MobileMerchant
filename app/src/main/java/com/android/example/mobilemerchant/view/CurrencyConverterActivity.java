package com.android.example.mobilemerchant.view;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.android.example.mobilemerchant.R;
import com.android.example.mobilemerchant.logic.exceptions.CurrencyNotSupportedException;
import com.android.example.mobilemerchant.logic.exceptions.NegativeInputException;
import com.android.example.mobilemerchant.logic.exceptions.SameCurrencyException;
import com.android.example.mobilemerchant.presentation.CurrencyConverterViewModel;

public class CurrencyConverterActivity extends Activity {
    CurrencyConverterViewModel currencyConverterViewModel;
    EditText input;
    Spinner currencyFromSpinner;
    Spinner currencyToSpinner;
    TextView inputNumber;
    TextView result;
    TextView exchangeRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency);

        currencyConverterViewModel = new CurrencyConverterViewModel(getApplication());

        currencyFromSpinner = findViewById(R.id.currencyFromSpinner);
        currencyToSpinner = findViewById(R.id.currencyToSpinner);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.currenciesAvailable,
                R.layout.custom_spinner);
        currencyFromSpinner.setAdapter(adapter1);
        currencyToSpinner.setAdapter(adapter1);

        input = findViewById(R.id.inputEditText);
        inputNumber = findViewById(R.id.inputResultTextView);
        inputNumber.setVisibility(View.INVISIBLE);
        result = findViewById(R.id.resultNumberTextView);
        result.setVisibility(View.INVISIBLE);
        exchangeRate = findViewById(R.id.exchangeRateNumberTextView);
        exchangeRate.setVisibility(View.INVISIBLE);

        if (savedInstanceState != null) {
            currencyFromSpinner.setSelection(savedInstanceState.getInt("spinner1", 0));
            currencyToSpinner.setSelection(savedInstanceState.getInt("spinner2", 0));
            inputNumber.setText(savedInstanceState.getString("inputText", ""));
            inputNumber.setVisibility(View.VISIBLE);
            result.setText(savedInstanceState.getString("resultText", ""));
            result.setVisibility(View.VISIBLE);
            exchangeRate.setText(savedInstanceState.getString("exchangeRateText", ""));
            exchangeRate.setVisibility(View.VISIBLE);
        }

        currencyConverterViewModel.addObserver(evt -> {
            if (evt.getPropertyName().equals("newInput")) {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(() -> {
                    inputNumber.setText((String) evt.getNewValue());
                    inputNumber.setVisibility(View.VISIBLE);
                });

            }
            if (evt.getPropertyName().equals("newExchangeRate")) {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(() -> {
                    exchangeRate.setText((String) evt.getNewValue());
                    exchangeRate.setVisibility(View.VISIBLE);
                });

            }
            if (evt.getPropertyName().equals("newResult")) {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(() -> {
                    result.setText((String) evt.getNewValue());
                    result.setVisibility(View.VISIBLE);
                });
            }
        });
    }

    public void convert(View view) {
        if (input.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), R.string.currencyInputEmpty, Toast.LENGTH_LONG).show();
            return;
        }

        double inputNum = Double.parseDouble(input.getText().toString());
        String currency1 = currencyFromSpinner.getSelectedItem().toString();
        String currency2 = currencyToSpinner.getSelectedItem().toString();

        try {
            currencyConverterViewModel.sendRequest(inputNum, currency1, currency2);
        } catch (SameCurrencyException s) {
            Handler newToast = new Handler(Looper.getMainLooper());
            newToast.post(() -> Toast.makeText(getApplicationContext(), R.string.currencySameCurrencyException, Toast.LENGTH_LONG).show());
        } catch (CurrencyNotSupportedException c) {
            Handler newToast = new Handler(Looper.getMainLooper());
            newToast.post(() -> Toast.makeText(getApplicationContext(), R.string.currencyCurrencyNotSupportedException, Toast.LENGTH_LONG).show());
        } catch (NegativeInputException n) {
            Handler newToast = new Handler(Looper.getMainLooper());
            newToast.post(() -> Toast.makeText(getApplicationContext(), R.string.currencyNegativeInputException, Toast.LENGTH_LONG).show());
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("spinner1", currencyFromSpinner.getSelectedItemPosition());
        outState.putInt("spinner2", currencyToSpinner.getSelectedItemPosition());
        outState.putString("inputText", inputNumber.getText().toString());
        outState.putString("resultText", result.getText().toString());
        outState.putString("exchangeRateText", exchangeRate.getText().toString());
    }
}
