package com.android.example.mobilemerchant.presentation;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.android.example.mobilemerchant.data.DebtOwedToOthers;
import com.android.example.mobilemerchant.logic.DebtOwedRepository;

import java.util.List;

public class DebtOwedViewModel extends AndroidViewModel {
    private DebtOwedRepository repository;
    private LiveData<List<DebtOwedToOthers>> allDebt;

    public DebtOwedViewModel(Application application) {
        super(application);
        repository = new DebtOwedRepository(application);
        allDebt = repository.getAllDebt();
    }

    public void insert(DebtOwedToOthers debtOwedToOthers) {
        repository.insert(debtOwedToOthers);
    }

    public void delete(DebtOwedToOthers debtOwedToOthers) {
        repository.delete(debtOwedToOthers);
    }

    public LiveData<List<DebtOwedToOthers>> getAllDebt() {
        return allDebt;
    }
}
