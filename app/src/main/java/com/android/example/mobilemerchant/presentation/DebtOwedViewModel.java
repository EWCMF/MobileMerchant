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
    private LiveData<List<DebtOwedPerson>> allDebtOthers;
    private LiveData<List<DebtOwedPersonWithItems>> allDebtOwedPersonWithItemsOther;
    private LiveData<List<DebtOwedPersonWithItems>> allDebtOwedPersonWithItemsYou;
    private LiveData<List<DebtOwedItem>> allDebtOthersItems;

    public DebtOwedViewModel(Application application, boolean toOthers) {
        super(application);
        repository = new DebtOwedRepository(application, true);
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

    public void delete(DebtOwedPerson debtOwedPerson) {
        repository.delete(debtOwedPerson);
    }

    public void delete(DebtOwedItem debtOwedItem) {
        repository.delete(debtOwedItem);
    }

    public LiveData<List<DebtOwedPerson>> getAllDebtOthers() {
        return allDebtOthers;
    }

    public LiveData<List<DebtOwedPersonWithItems>> getAllDebtOwedPersonWithItemsOthers() {
        return allDebtOwedPersonWithItemsOther;
    }

    public List<DebtOwedPersonWithItems> getAllDebtOwedPersonWithItemsOthersSimple() {
        return repository.getDebtOthersPersonWithItemsSimple();
    }

    public LiveData<List<DebtOwedPersonWithItems>> getAllDebtOwedPersonWithItemsYou() {
        return allDebtOwedPersonWithItemsYou;
    }

    public List<DebtOwedPersonWithItems> getAllDebtOwedPersonWithItemsYouSimple() {
        return repository.getDebtYouPersonWithItemsSimple();
    }
}
