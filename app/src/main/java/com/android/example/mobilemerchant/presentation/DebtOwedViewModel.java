package com.android.example.mobilemerchant.presentation;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.android.example.mobilemerchant.data.DebtOwedItem;
import com.android.example.mobilemerchant.data.DebtOwedPersonWithItems;
import com.android.example.mobilemerchant.data.DebtOwedPerson;
import com.android.example.mobilemerchant.logic.DebtOwedRepository;

import java.util.List;

public class DebtOwedViewModel extends AndroidViewModel {
    private DebtOwedRepository repository;
    private LiveData<List<DebtOwedPersonWithItems>> allDebtOwedPersonWithItemsOther;
    private LiveData<List<DebtOwedPersonWithItems>> allDebtOwedPersonWithItemsYou;

    DebtOwedViewModel(Application application, boolean toOthers) {
        super(application);
        repository = new DebtOwedRepository(application);
        if (toOthers) {
            allDebtOwedPersonWithItemsOther = repository.getDebtOthersPersonWithItems();
        }
        else {
            allDebtOwedPersonWithItemsYou = repository.getDebtYouPersonWithItems();
        }
    }

    public void insert(DebtOwedPerson debtOwedPerson) {
        repository.insert(debtOwedPerson);
    }

    public void insert(DebtOwedItem debtOwedItem) {
        repository.insert(debtOwedItem);
    }

    public void update(DebtOwedPerson debtOwedPerson) {
        repository.update(debtOwedPerson);
    }

    public void update(DebtOwedItem debtOwedItem) {
        repository.update(debtOwedItem);
    }

    public void delete(DebtOwedPerson debtOwedPerson) {
        repository.delete(debtOwedPerson);
    }

    public void delete(DebtOwedItem debtOwedItem) {
        repository.delete(debtOwedItem);
    }

    public LiveData<List<DebtOwedPersonWithItems>> searchOthers(String text) {
        return repository.searchOthers(text);
    }

    public LiveData<List<DebtOwedPersonWithItems>> searchYou(String text) {
        return repository.searchYou(text);
    }

    public LiveData<List<DebtOwedPersonWithItems>> getAllDebtOwedPersonWithItemsOthers() {
        return allDebtOwedPersonWithItemsOther;
    }

    public LiveData<List<DebtOwedPersonWithItems>> getAllDebtOwedPersonWithItemsYou() {
        return allDebtOwedPersonWithItemsYou;
    }
}
