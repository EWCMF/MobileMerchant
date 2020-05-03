package com.android.example.mobilemerchant.logic;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.android.example.mobilemerchant.data.DebtOwedItem;
import com.android.example.mobilemerchant.data.DebtOwedPersonWithItems;
import com.android.example.mobilemerchant.data.DebtOwedPerson;
import com.android.example.mobilemerchant.persistence.AppDatabase;
import com.android.example.mobilemerchant.persistence.DebtOwedItemDao;
import com.android.example.mobilemerchant.persistence.DebtOwedPersonDao;

import java.util.List;

public class DebtOwedRepository {
    private DebtOwedPersonDao debtOwedPersonDao;
    private DebtOwedItemDao debtOwedItemDao;
    private LiveData<List<DebtOwedPerson>> allDebt;

    public DebtOwedRepository(Application application, boolean toOthers) {
        AppDatabase db = AppDatabase.getDatabase(application);
        debtOwedPersonDao = db.debtOwedPersonDao();
        debtOwedItemDao = db.debtOwedItemDao();
    }

    public void insert(DebtOwedPerson debtOwedPerson) {
        Thread thread = new Thread(() -> debtOwedPersonDao.insertAll(debtOwedPerson));
        thread.start();
    }

    public void insert(DebtOwedItem debtOwedItem) {
        Thread thread = new Thread(() -> debtOwedItemDao.insertAll(debtOwedItem));
        thread.start();
    }

    public void delete(DebtOwedPerson debtOwedPerson) {
        Thread thread = new Thread(() -> debtOwedPersonDao.delete(debtOwedPerson));
        thread.start();
    }

    public void delete(DebtOwedItem debtOwedItem) {
        Thread thread = new Thread(() -> debtOwedItemDao.delete(debtOwedItem));
        thread.start();
    }

    public LiveData<List<DebtOwedPersonWithItems>> searchOthers(String text) {
        return debtOwedPersonDao.searchNamesOthers(text);
    }

    public LiveData<List<DebtOwedPersonWithItems>> searchYou(String text) {
        return debtOwedPersonDao.searchNamesYou(text);
    }

    public LiveData<List<DebtOwedPersonWithItems>> getDebtOthersPersonWithItems() {
        return debtOwedPersonDao.getDebtOthersPersonWithItems();
    }

    public LiveData<List<DebtOwedPersonWithItems>> getDebtYouPersonWithItems() {
        return debtOwedPersonDao.getDebtYouPersonWithItems();
    }
}
