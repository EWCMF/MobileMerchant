package com.android.example.mobilemerchant.logic;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.android.example.mobilemerchant.data.DebtOwedToOthers;
import com.android.example.mobilemerchant.data.DebtOwedToYou;
import com.android.example.mobilemerchant.persistence.AppDatabase;
import com.android.example.mobilemerchant.persistence.DebtOwedToOthersDao;
import com.android.example.mobilemerchant.persistence.DebtOwedToYouDao;

import java.util.List;

public class DebtOwedRepository {
    private DebtOwedToOthersDao debtOwedToOthersDao;
    private DebtOwedToYouDao debtOwedToYouDao;
    private LiveData<List<DebtOwedToOthers>> allDebtOthers;
    private LiveData<List<DebtOwedToYou>> allDebtYou;

    public DebtOwedRepository(Application application, boolean toOthers) {
        AppDatabase db = AppDatabase.getDatabase(application);
        if (toOthers) {
            debtOwedToOthersDao = db.debtOwedToOthersDao();
            allDebtOthers = debtOwedToOthersDao.getAll();
        }
        else {
            debtOwedToYouDao = db.debtOwedToYouDao();
            allDebtYou = debtOwedToYouDao.getAll();
        }
    }

    public void insert(DebtOwedToOthers debtOwedToOthers) {
        Thread thread = new Thread(() -> debtOwedToOthersDao.insertAll(debtOwedToOthers));
        thread.start();
    }

    public void insert(DebtOwedToYou debtOwedToYou) {
        Thread thread = new Thread(() -> debtOwedToYouDao.insertAll(debtOwedToYou));
        thread.start();
    }


    public void delete(DebtOwedToOthers debtOwedToOthers) {
        Thread thread = new Thread(() -> debtOwedToOthersDao.delete(debtOwedToOthers));
        thread.start();
    }

    public void delete(DebtOwedToYou debtOwedToYou) {
        Thread thread = new Thread(() -> debtOwedToYouDao.delete(debtOwedToYou));
        thread.start();
    }

    public LiveData<List<DebtOwedToOthers>> getAllDebtOthers() {
        return allDebtOthers;
    }

    public LiveData<List<DebtOwedToYou>> getAllDebtYou() {
        return allDebtYou;
    }


}
