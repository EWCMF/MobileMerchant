package com.android.example.mobilemerchant.presentation;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.android.example.mobilemerchant.data.DebtOwedToYou;
import com.android.example.mobilemerchant.data.DebtYouItem;
import com.android.example.mobilemerchant.logic.DebtOwedRepository;

import java.util.List;

public class DebtOwedYouViewModel extends AndroidViewModel {
    private DebtOwedRepository repository;
    private LiveData<List<DebtOwedToYou>> allDebtYou;
    private LiveData<List<DebtYouItem>> allDebtYouItems;

    public DebtOwedYouViewModel(Application application) {
        super(application);
        repository = new DebtOwedRepository(application, false);
        allDebtYou = repository.getAllDebtYou();
    }

    public void insert(DebtOwedToYou debtOwedToYou) {
        repository.insert(debtOwedToYou);
    }

    public void insert(DebtYouItem debtYouItem) {
        repository.insert(debtYouItem);
    }

    public void delete(DebtOwedToYou debtOwedToYou) {
        repository.delete(debtOwedToYou);
    }

    public void delete(DebtYouItem debtYouItem) {
        repository.delete(debtYouItem);
    }

    public LiveData<List<DebtOwedToYou>> getAllDebtYou() {
        return allDebtYou;
    }

    public LiveData<List<DebtYouItem>> getAllDebtYouItems() {
        return allDebtYouItems;
    }

    public List<DebtOwedToYou> getAlldebtYouSimple() {
        return repository.getDebtYouNamesWithItems();
    }
}
