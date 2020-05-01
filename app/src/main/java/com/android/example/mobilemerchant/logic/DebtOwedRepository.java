package com.android.example.mobilemerchant.logic;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.android.example.mobilemerchant.data.DebtOwedToOthers;
import com.android.example.mobilemerchant.persistence.AppDatabase;
import com.android.example.mobilemerchant.persistence.DebtOwedToOthersDao;

import java.util.List;

public class DebtOwedRepository {
    private DebtOwedToOthersDao debtOwedToOthersDao;
    private LiveData<List<DebtOwedToOthers>> allDebt;

    public DebtOwedRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        debtOwedToOthersDao = db.debtOwedToOthersDao();
        allDebt = debtOwedToOthersDao.getAll();
    }

    public void insert(DebtOwedToOthers debtOwedToOthers) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                debtOwedToOthersDao.insertAll(debtOwedToOthers);
            }
        });
        thread.start();
    }

    public void delete(DebtOwedToOthers debtOwedToOthers) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                debtOwedToOthersDao.delete(debtOwedToOthers);
            }
        });
        thread.start();
    }

    public LiveData<List<DebtOwedToOthers>> getAllDebt() {
        return allDebt;
    }


}
