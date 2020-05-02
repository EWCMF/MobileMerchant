package com.android.example.mobilemerchant.presentation;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.android.example.mobilemerchant.data.DebtOthersItem;
import com.android.example.mobilemerchant.data.DebtOthersNamesWithItems;
import com.android.example.mobilemerchant.data.DebtOwedToOthers;
import com.android.example.mobilemerchant.logic.DebtOwedRepository;

import java.util.List;

public class DebtOwedOthersViewModel extends AndroidViewModel {
    private DebtOwedRepository repository;
    private LiveData<List<DebtOwedToOthers>> allDebtOthers;
    private LiveData<List<DebtOthersItem>> allDebtOthersItems;

    public DebtOwedOthersViewModel(Application application) {
        super(application);
        repository = new DebtOwedRepository(application, true);
        allDebtOthers = repository.getAllDebtOthers();
    }

    public void insert(DebtOwedToOthers debtOwedToOthers) {
        repository.insert(debtOwedToOthers);
    }

    public void insert(DebtOthersItem debtOthersItem) {
        repository.insert(debtOthersItem);
    }

    public void delete(DebtOwedToOthers debtOwedToOthers) {
        repository.delete(debtOwedToOthers);
    }

    public void delete(DebtOthersItem debtOthersItem) {
        repository.delete(debtOthersItem);
    }

    public LiveData<List<DebtOwedToOthers>> getAllDebtOthers() {
        return allDebtOthers;
    }

    public LiveData<List<DebtOthersItem>> getAllDebtOthersItems() {
        return allDebtOthersItems;
    }

    public LiveData<List<DebtOthersNamesWithItems>> getAllDebtOthersLive() {
        return repository.getDebtOthersNamesWithItems();
    }

    public List<DebtOthersNamesWithItems> getAllDebtOthersSimple() {
        return repository.getDebtOthersNamesWithItemsSimple();
    }
}
