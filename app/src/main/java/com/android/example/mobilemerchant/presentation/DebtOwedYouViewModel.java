package com.android.example.mobilemerchant.presentation;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.android.example.mobilemerchant.data.DebtOwedToYou;
import com.android.example.mobilemerchant.logic.DebtOwedRepository;

import java.util.List;

public class DebtOwedYouViewModel extends AndroidViewModel {
    private DebtOwedRepository repository;
    private LiveData<List<DebtOwedToYou>> allDebtYou;

    public DebtOwedYouViewModel(Application application) {
        super(application);
        repository = new DebtOwedRepository(application, false);
        allDebtYou = repository.getAllDebtYou();
    }

    public void insert(DebtOwedToYou debtOwedToYou) {
        repository.insert(debtOwedToYou);
    }


    public void delete(DebtOwedToYou debtOwedToYou) {
        repository.delete(debtOwedToYou);
    }

    public LiveData<List<DebtOwedToYou>> getAllDebtYou() {
        return allDebtYou;
    }
}
