package com.android.example.mobilemerchant.logic;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.android.example.mobilemerchant.data.DebtOthersItem;
import com.android.example.mobilemerchant.data.DebtOthersNamesWithItems;
import com.android.example.mobilemerchant.data.DebtOwedToOthers;
import com.android.example.mobilemerchant.data.DebtOwedToYou;
import com.android.example.mobilemerchant.data.DebtYouItem;
import com.android.example.mobilemerchant.data.DebtYouNamesWithItems;
import com.android.example.mobilemerchant.persistence.AppDatabase;
import com.android.example.mobilemerchant.persistence.DebtOthersItemDao;
import com.android.example.mobilemerchant.persistence.DebtOwedToOthersDao;
import com.android.example.mobilemerchant.persistence.DebtOwedToYouDao;
import com.android.example.mobilemerchant.persistence.DebtYouItemDao;

import java.util.List;

public class DebtOwedRepository {
    private DebtOwedToOthersDao debtOwedToOthersDao;
    private DebtOwedToYouDao debtOwedToYouDao;
    private DebtYouItemDao debtYouItemDao;
    private DebtOthersItemDao debtOthersItemDao;
    private LiveData<List<DebtOwedToOthers>> allDebtOthers;
    private LiveData<List<DebtOwedToYou>> allDebtYou;

    public DebtOwedRepository(Application application, boolean toOthers) {
        AppDatabase db = AppDatabase.getDatabase(application);
        if (toOthers) {
            debtOwedToOthersDao = db.debtOwedToOthersDao();
            allDebtOthers = debtOwedToOthersDao.getAll();
            debtOthersItemDao = db.debtOthersItemDao();
        }
        else {
            debtOwedToYouDao = db.debtOwedToYouDao();
            allDebtYou = debtOwedToYouDao.getAll();
            debtYouItemDao = db.debtYouItemDao();
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

    public void insert(DebtOthersItem debtOthersItem) {
        Thread thread = new Thread(() -> debtOthersItemDao.insertAll(debtOthersItem));
        thread.start();
    }

    public void insert(DebtYouItem debtYouItem) {
        Thread thread = new Thread(() -> debtYouItemDao.insertAll(debtYouItem));
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

    public void delete(DebtOthersItem debtOthersItem) {
        Thread thread = new Thread(() -> debtOthersItemDao.delete(debtOthersItem));
        thread.start();
    }

    public void delete(DebtYouItem debtYouItem) {
        Thread thread = new Thread(() -> debtYouItemDao.delete(debtYouItem));
        thread.start();
    }

    public LiveData<List<DebtOwedToOthers>> getAllDebtOthers() {
        return allDebtOthers;
    }

    public LiveData<List<DebtOwedToYou>> getAllDebtYou() {
        return allDebtYou;
    }

    public LiveData<List<DebtOthersNamesWithItems>> getDebtOthersNamesWithItems() {
        return debtOwedToOthersDao.getDebtOthersNamesWithItems();
    }

    public List<DebtOthersNamesWithItems> getDebtOthersNamesWithItemsSimple() {
        return debtOwedToOthersDao.getDebtOthersNamesWithItemsSimple();
    }

    public LiveData<List<DebtYouNamesWithItems>> getDebtYouNamesWithItems() {
        return debtOwedToYouDao.getDebtYouNamesWithItems();
    }

    public List<DebtYouNamesWithItems> getDebtYouNamesWithItemsSimple() {
        return debtOwedToYouDao.getDebtYouNamesWithItemsSimple();
    }
}
