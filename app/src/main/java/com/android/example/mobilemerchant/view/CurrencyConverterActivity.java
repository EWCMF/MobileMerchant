package com.android.example.mobilemerchant.view;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.example.mobilemerchant.R;
import com.android.example.mobilemerchant.logic.CurrencyConverter;
import com.android.example.mobilemerchant.logic.exceptions.CurrencyNotSupportedException;
import com.android.example.mobilemerchant.logic.exceptions.NegativeInputException;
import com.android.example.mobilemerchant.logic.exceptions.SameCurrencyException;

import java.io.IOException;
import java.util.Locale;

public class CurrencyConverterActivity extends Activity {
    CurrencyConverter currencyConverter;
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

        currencyConverter = new CurrencyConverter();
        currencyFromSpinner = findViewById(R.id.currencyFromSpinner);
        currencyToSpinner = findViewById(R.id.currencyToSpinner);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.currenciesAvailable,
                R.layout.support_simple_spinner_dropdown_item);
        currencyFromSpinner.setAdapter(adapter1);
        currencyToSpinner.setAdapter(adapter1);

        input = findViewById(R.id.inputEditText);
        inputNumber = findViewById(R.id.inputResultTextView);
        inputNumber.setVisibility(View.INVISIBLE);
        result = findViewById(R.id.resultNumberTextView);
        result.setVisibility(View.INVISIBLE);
        exchangeRate = findViewById(R.id.exchangeRateNumberTextView);
        exchangeRate.setVisibility(View.INVISIBLE);

    }

    public void convert(View view) {
        if (input.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), R.string.currencyInputEmpty, Toast.LENGTH_LONG).show();
            return;
        }
        final double inputNum = Double.parseDouble(input.getText().toString());
        final String currency1 = currencyFromSpinner.getSelectedItem().toString();
        final String currency2 = currencyToSpinner.getSelectedItem().toString();
        Log.v("Test", "" + inputNum + " " + currency1 + " " + currency2);
        Thread thread = new Thread(() -> {
            try {
                currencyConverter.convert(inputNum, currency1, currency2);
                String inputFormatted = String.format(Locale.getDefault(), "%.2f", currencyConverter.getInput());
                final String inputText = inputFormatted + " " + currency1;
                String resultFormatted = String.format(Locale.getDefault(), "%.2f", currencyConverter.getResult());
                final String resultText = resultFormatted + " " + currency2;
                final String exchangeRateFormatted = String.format(Locale.getDefault(), "%.5f", currencyConverter.getExchangeRate());
                Handler refresh = new Handler(Looper.getMainLooper());
                refresh.post(() -> {
                    inputNumber.setText(inputText);
                    inputNumber.setVisibility(View.VISIBLE);
                    result.setText(resultText);
                    result.setVisibility(View.VISIBLE);
                    exchangeRate.setText(exchangeRateFormatted);
                    exchangeRate.setVisibility(View.VISIBLE);
                });
            } catch (IOException e) {
                e.printStackTrace();
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
        });
        thread.start();
    }
}
