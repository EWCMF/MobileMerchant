package com.android.example.mobilemerchant.view;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.android.example.mobilemerchant.R;

public class CurrencyConverterActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency);
        Spinner currencySpinner1 = findViewById(R.id.spinner2);
        Spinner currencySpinner2 = findViewById(R.id.spinner3);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.currenciesAvailable,
                R.layout.support_simple_spinner_dropdown_item);
        currencySpinner1.setAdapter(adapter1);
        currencySpinner2.setAdapter(adapter1);
    }
}
