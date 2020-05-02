package com.android.example.mobilemerchant.presentation;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.android.example.mobilemerchant.data.DebtOwedToOthers;
import com.android.example.mobilemerchant.logic.DebtOwedRepository;

import java.util.List;

public class DebtOwedOthersViewModel extends AndroidViewModel {
    private DebtOwedRepository repository;
    private LiveData<List<DebtOwedToOthers>> allDebtOthers;

    public DebtOwedOthersViewModel(Application application) {
        super(application);
        repository = new DebtOwedRepository(application, true);
        allDebtOthers = repository.getAllDebtOthers();
    }

    public void insert(DebtOwedToOthers debtOwedToOthers) {
        repository.insert(debtOwedToOthers);
    }

    public void delete(DebtOwedToOthers debtOwedToOthers) {
        repository.delete(debtOwedToOthers);
    }

    public LiveData<List<DebtOwedToOthers>> getAllDebtOthers() {
        return allDebtOthers;
    }
}
