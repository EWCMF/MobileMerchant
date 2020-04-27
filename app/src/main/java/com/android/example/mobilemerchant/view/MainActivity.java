package com.android.example.mobilemerchant.view;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.example.mobilemerchant.R;
import com.android.example.mobilemerchant.data.DebtOwedToYou;
import com.android.example.mobilemerchant.persistence.AppDatabase;
import com.android.example.mobilemerchant.data.DebtOwedToOthers;

public class MainActivity extends AppCompatActivity {
    Spinner languageSpinner;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = AppDatabase.getDatabase(this);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                db.debtOwedToOthersDao().nukeTable();
                db.debtOwedToYouDao().nukeTable();
                DebtOwedToOthers testDebtOwedToOthers = new DebtOwedToOthers("Tommy", 500, "DKK");
                DebtOwedToYou test2 = new DebtOwedToYou("Unlucky", 200, "DKK");
                db.debtOwedToOthersDao().insertAll(testDebtOwedToOthers);
                db.debtOwedToYouDao().insertAll(test2);
            }
        });
        thread.start();

        languageSpinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.languageChoices,
                R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        languageSpinner.setAdapter(adapter);

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
                Intent i = new Intent(getApplicationContext(), DebtOwedToYouActivity.class);
                startActivity(i);
            }
        });

        TextView debtToOthers = findViewById(R.id.debtOthersText);
        debtToOthers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), DebtOwedToOthersActivity.class);
                startActivity(i);
            }
        });
    }
}
