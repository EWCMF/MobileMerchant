package com.android.example.mobilemerchant.view;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;

import com.android.example.mobilemerchant.R;
import com.android.example.mobilemerchant.data.DebtOwedToYou;
import com.android.example.mobilemerchant.persistence.AppDatabase;

public class DebtOwedToYouActivity extends Activity {
    AppDatabase db;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debtyou);
        db = AppDatabase.getDatabase(this);
        textView = findViewById(R.id.debtYouTestText);
    }

    public void buttonTest(View view) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                final DebtOwedToYou debtOwedToYou = db.debtOwedToYouDao().findByName("Unlucky");
                Handler refresh = new Handler(Looper.getMainLooper());
                refresh.post(new Runnable() {
                    public void run()
                    {
                        String text = debtOwedToYou.getName() + " " + debtOwedToYou.getAmountOwed() + " " + debtOwedToYou.getCurrencyName();
                        textView.setText(text);
                    }
                });

            }
        });
        thread.start();

    }
}
