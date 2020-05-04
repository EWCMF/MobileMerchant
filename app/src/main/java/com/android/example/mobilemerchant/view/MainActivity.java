package com.android.example.mobilemerchant.view;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Locale;

import com.android.example.mobilemerchant.R;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);


        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.languageChoices, R.layout.custom_spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 1) {
                    setLocale("en");
                    Toast.makeText(parent.getContext(), "You have selected English", Toast.LENGTH_SHORT).show();
                } else if (position == 2) {
                    setLocale("da");
                    Toast.makeText(parent.getContext(), "Du har valgt dansk", Toast.LENGTH_SHORT).show();
                } else if (position == 3) {
                    setLocale("iw");
                    Toast.makeText(parent.getContext(), "בחרת עברית", Toast.LENGTH_SHORT).show();
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        TextView calculator = findViewById(R.id.calculatorText);
        calculator.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), CalculatorActivity.class);
            startActivity(i);
        });

        TextView currencyConverter = findViewById(R.id.currencyText);
        currencyConverter.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), CurrencyConverterActivity.class);
            startActivity(i);
        });

        TextView debtToYou = findViewById(R.id.debtYouTestText);
        debtToYou.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), DebtOwedActivity.class);
            i.putExtra("toOthers", false);
            startActivity(i);
        });

        TextView debtToOthers = findViewById(R.id.debtOthersText);
        debtToOthers.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), DebtOwedActivity.class);
            i.putExtra("toOthers", true);
            startActivity(i);
        });

    }



    public void setLocale(String lang){

        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.setLocale(myLocale);
        res.updateConfiguration(conf, dm);

        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

//creating inflatable menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
