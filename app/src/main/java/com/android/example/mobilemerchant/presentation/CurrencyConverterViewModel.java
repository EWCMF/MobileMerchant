package com.android.example.mobilemerchant.presentation;

import android.app.Application;

import com.android.example.mobilemerchant.logic.CurrencyConverterModel;
import com.android.example.mobilemerchant.logic.CurrencyConverterRepository;
import com.android.example.mobilemerchant.logic.exceptions.CurrencyNotSupportedException;
import com.android.example.mobilemerchant.logic.exceptions.NegativeInputException;
import com.android.example.mobilemerchant.logic.exceptions.SameCurrencyException;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Locale;


public class CurrencyConverterViewModel  {
    private CurrencyConverterModel currencyConverterModel;
    private CurrencyConverterRepository currencyConverterRepository;
    private String input;
    private String result;
    private String exchangeRate;
    private String currencyFrom;
    private String currencyTo;
    private PropertyChangeSupport support;
    private ArrayList<String> allowedCurrencies;

    public CurrencyConverterViewModel(Application application) {
        this.currencyConverterModel = new CurrencyConverterModel();
        currencyConverterRepository = new CurrencyConverterRepository(application, currencyConverterModel);
        support = new PropertyChangeSupport(this);
        currencyConverterModel.addObserver(evt -> {
            if (evt.getPropertyName().equals("input")) {
                double raw = (double) evt.getNewValue();
                input = String.format(Locale.getDefault(), "%.2f", raw);
            }
            if (evt.getPropertyName().equals("exchangeRate")) {
                double raw = (double) evt.getNewValue();
                String formatted = String.format(Locale.getDefault(), "%.5f", raw);
                support.firePropertyChange("newExchangeRate", exchangeRate, formatted);
                this.exchangeRate = formatted;
            }
            if (evt.getPropertyName().equals("result")) {
                double raw = (double) evt.getNewValue();
                String formatted = String.format(Locale.getDefault(), "%.2f", raw);
                support.firePropertyChange("newResult", result, formatted + " " + currencyTo);
                this.result = formatted;
                support.firePropertyChange("newInput", input, input + " " + currencyFrom);
            }
        });
        allowedCurrencies = new ArrayList<>();
        allowedCurrencies.add("DKK");
        allowedCurrencies.add("USD");
        allowedCurrencies.add("EUR");
        allowedCurrencies.add("GBP");
        allowedCurrencies.add("SEK");
        allowedCurrencies.add("NOK");
        allowedCurrencies.add("JPY");
    }

    public void sendRequest(double input, String currencyFrom, String currencyTo) throws SameCurrencyException, CurrencyNotSupportedException, NegativeInputException {
        if (currencyFrom.equals(currencyTo)) {
            throw new SameCurrencyException("The two selected currencies cannot be the same");
        }
        if (!allowedCurrencies.contains(currencyFrom) || !allowedCurrencies.contains(currencyTo)) {
            throw new CurrencyNotSupportedException("One of the selected currencies is currently not allowed.");
        }
        if (input < 0) {
            throw new NegativeInputException("Input value cannot be below 0");
        }
        if (input != currencyConverterModel.getInput() || !currencyFrom.equals(this.currencyFrom) || !currencyTo.equals(this.currencyTo)) {
            this.currencyFrom = currencyFrom;
            this.currencyTo = currencyTo;
            currencyConverterRepository.getDataForBase(input, currencyFrom, currencyTo);
        }
    }

    public void addObserver(PropertyChangeListener propertyChangeListener) {
        support.addPropertyChangeListener(propertyChangeListener);
    }
}
