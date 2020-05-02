package com.android.example.mobilemerchant.view;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.example.mobilemerchant.R;
import com.android.example.mobilemerchant.data.DebtOthersItem;
import com.android.example.mobilemerchant.data.DebtOwedToOthers;
import com.android.example.mobilemerchant.logic.DebtOwedRepository;

public class MainActivity extends AppCompatActivity {
    Spinner languageSpinner;
    DebtOwedRepository debtOwedRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);

        languageSpinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.languageChoices,
                R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        languageSpinner.setAdapter(adapter);

        debtOwedRepository = new DebtOwedRepository(getApplication(), true);
        debtOwedRepository.insert(new DebtOwedToOthers(0, "test1"));
        debtOwedRepository.insert(new DebtOwedToOthers(1, "test2"));
        debtOwedRepository.insert(new DebtOthersItem(1, "test", 500, "DKK"));

        TextView calculator = findViewById(R.id.calculatorText);
        calculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), CalculatorActivity.class);
                startActivity(i);
            }
        });

        TextView currencyConverter = findViewById(R.id.currencyText);
        currencyConverter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), CurrencyConverterActivity.class);
                startActivity(i);
            }
        });

        TextView debtToYou = findViewById(R.id.debtYouTestText);
        debtToYou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), DebtOwedActivity.class);
                i.putExtra("toOthers", false);
                startActivity(i);
            }
        });

        TextView debtToOthers = findViewById(R.id.debtOthersText);
        debtToOthers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), DebtOwedActivity.class);
                i.putExtra("toOthers", true);
                startActivity(i);
            }
        });

    }


//creating inflatable menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
