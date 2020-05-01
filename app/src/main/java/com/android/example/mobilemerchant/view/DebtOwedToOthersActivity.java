package com.android.example.mobilemerchant.view;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ListView;

import com.android.example.mobilemerchant.R;
import com.android.example.mobilemerchant.persistence.AppDatabase;
import com.android.example.mobilemerchant.data.DebtOwedToOthers;

import java.util.ArrayList;
import java.util.List;

public class DebtOwedToOthersActivity extends Activity {
    AppDatabase db;
    ListView listView;
    ArrayList<DebtOwedToOthers> debtOwedToOthers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debtothers);
        db = AppDatabase.getDatabase(this);
        listView = findViewById(R.id.listView);
        debtOwedToOthers = new ArrayList<>();
        populateList();

    }

    private void populateList() {
        Thread thread = new Thread(() -> {
            List<DebtOwedToOthers> list = db.debtOwedToOthersDao().getAll();
            debtOwedToOthers.addAll(list);
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(() -> listView.setAdapter(new DebtOwedAdapter(this, debtOwedToOthers)));

        });
        thread.start();

    }
}
