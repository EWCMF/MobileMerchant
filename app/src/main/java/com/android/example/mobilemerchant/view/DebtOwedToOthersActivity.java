package com.android.example.mobilemerchant.view;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;

import com.android.example.mobilemerchant.R;
import com.android.example.mobilemerchant.persistence.AppDatabase;
import com.android.example.mobilemerchant.data.DebtOwedToOthers;

public class DebtOwedToOthersActivity extends Activity {
    AppDatabase db;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debtothers);
        db = AppDatabase.getDatabase(this);
        textView = findViewById(R.id.debtOthersTestText);
    }

    public void buttonTest(View view) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                final DebtOwedToOthers debtOwedToOthers = db.debtOwedToOthersDao().findByName("Tommy");
                Handler refresh = new Handler(Looper.getMainLooper());
                refresh.post(new Runnable() {
                    public void run()
                    {
                        String text = debtOwedToOthers.getName() + " " + debtOwedToOthers.getAmountOwed() + " " + debtOwedToOthers.getCurrencyName();
                        textView.setText(text);
                    }
                });

            }
        });
        thread.start();

    }
}
