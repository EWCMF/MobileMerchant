package com.android.example.mobilemerchant.logic;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class CurrencyConverterModel {
    private double input;
    private double exchangeRate;
    private double result;

    private PropertyChangeSupport support;

    public CurrencyConverterModel() {
        support = new PropertyChangeSupport(this);
    }

    public double getInput() {
        return input;
    }

    public void addObserver(PropertyChangeListener propertyChangeListener) {
        support.addPropertyChangeListener(propertyChangeListener);
    }

    void setInput(double input) {
        support.firePropertyChange("input", this.input, input);
        this.input = input;
    }

    void setExchangeRate(double exchangeRate) {
        support.firePropertyChange("exchangeRate", this.exchangeRate, exchangeRate);
        this.exchangeRate = exchangeRate;
    }

    void setResult(double result) {
        support.firePropertyChange("result", this.result, result);
        this.result = result;
    }
}
