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

import com.android.example.mobilemerchant.R;
import com.android.example.mobilemerchant.logic.CurrencyConverter;

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
        result = findViewById(R.id.resultNumberTextView);
        exchangeRate = findViewById(R.id.exchangeRateNumberTextView);

    }

    public void convert(View view) {
        final double inputNum = Double.parseDouble(input.getText().toString());
        final String currency1 = currencyFromSpinner.getSelectedItem().toString();
        final String currency2 = currencyToSpinner.getSelectedItem().toString();
        Log.v("Test", "" + inputNum + " " + currency1 + " " + currency2);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    currencyConverter.convert(inputNum, currency1, currency2);
                    String inputFormatted = String.format(Locale.getDefault(), "%.2f", currencyConverter.getInput());
                    final String inputText = inputFormatted + " " + currency1;
                    String resultFormatted = String.format(Locale.getDefault(), "%.2f", currencyConverter.getResult());
                    final String resultText = resultFormatted + " " + currency2;
                    final String exchangeRateFormatted = String.format(Locale.getDefault(), "%.5f", currencyConverter.getExchangeRate());
                    Handler refresh = new Handler(Looper.getMainLooper());
                    refresh.post(new Runnable() {
                        public void run()
                        {
                            inputNumber.setText(inputText);
                            result.setText(resultText);
                            exchangeRate.setText(exchangeRateFormatted);
                        }
                    });


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}
