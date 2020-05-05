package com.android.example.mobilemerchant.logic;

import com.android.example.mobilemerchant.data.CurrencyCache;
import com.android.example.mobilemerchant.logic.exceptions.CurrencyNotSupportedException;
import com.android.example.mobilemerchant.logic.exceptions.NegativeInputException;
import com.android.example.mobilemerchant.logic.exceptions.SameCurrencyException;
import com.android.example.mobilemerchant.persistence.AppDatabase;
import com.android.example.mobilemerchant.persistence.CurrencyCacheDao;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

public class CurrencyConverter {
    private double exchangeRate;
    private double input;
    private double result;
    private ArrayList<String> allowedCurrencies;
    private CurrencyCacheDao currencyCacheDao;

    public CurrencyConverter() {
        allowedCurrencies = new ArrayList<>();
        allowedCurrencies.add("DKK");
        allowedCurrencies.add("USD");
        allowedCurrencies.add("EUR");
        allowedCurrencies.add("GBP");
        allowedCurrencies.add("SEK");
        allowedCurrencies.add("NOK");
        allowedCurrencies.add("JPY");
    }

    public void convert(double input, String currencyFrom, String currencyTo, AppDatabase appDatabase) throws SameCurrencyException, CurrencyNotSupportedException, NegativeInputException {
        if (currencyFrom.equals(currencyTo)) {
            throw new SameCurrencyException("The two selected currencies cannot be the same");
        }
        if (!allowedCurrencies.contains(currencyFrom) || !allowedCurrencies.contains(currencyTo)) {
            throw new CurrencyNotSupportedException("One of the selected currencies is currently not allowed.");
        }
        if (input < 0) {
            throw new NegativeInputException("Input value cannot be below 0");
        }

        currencyCacheDao = appDatabase.currencyCacheDao();
        try {
            CurrencyCache currencyCache = currencyCacheDao.getBase(currencyFrom);
            long dayLimit = 86400000 + currencyCache.getDate();
            long current = new Date().getTime();
            if (dayLimit > current) {
                useCache(input, currencyTo, currencyCache);
            } else {
                currencyCacheDao.deleteOld(currencyCache);
                fetchJson(input, currencyFrom, currencyTo);
            }
        } catch (NullPointerException npe) {
            fetchJson(input, currencyFrom, currencyTo);
        }
    }

    private void fetchJson(double input, String currencyFrom, String currencyTo) {
        JsonNode root;
        try {
            root = new ObjectMapper().readTree(new URL("https://api.exchangeratesapi.io/latest?base=" + currencyFrom));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        this.input = input;
        exchangeRate = root.path("rates").path(currencyTo).asDouble();
        result = input * exchangeRate;

        long date = new Date().getTime();
        JsonNode rates = root.path("rates");
        double dkk = rates.path("DKK").asDouble();
        double usd = rates.path("USD").asDouble();
        double eur = rates.path("EUR").asDouble();
        double gbp = rates.path("GBP").asDouble();
        double sek = rates.path("SEK").asDouble();
        double nok = rates.path("NOK").asDouble();
        double jpy = rates.path("JPY").asDouble();

        CurrencyCache newCache = new CurrencyCache(date, currencyFrom, dkk, usd, eur, gbp, sek, nok, jpy);
        currencyCacheDao.insertAll(newCache);
    }

    private void useCache(double input, String currencyTo, CurrencyCache currencyCache) {
        double valueTo = 0;

        switch (currencyTo) {
            case "DKK":
                valueTo = currencyCache.getDkk();
                break;
            case "USD":
                valueTo = currencyCache.getUsd();
                break;
            case "EUR":
                valueTo = currencyCache.getEur();
                break;
            case "GBP":
                valueTo = currencyCache.getGbp();
                break;
            case "SEK":
                valueTo = currencyCache.getSek();
                break;
            case "NOK":
                valueTo = currencyCache.getNok();
                break;
            case "JPY":
                valueTo = currencyCache.getJpy();
        }

        this.input = input;
        exchangeRate = valueTo;
        result = input * exchangeRate;
    }

    public double getExchangeRate() {
        return exchangeRate;
    }

    public double getInput() {
        return input;
    }

    public double getResult() {
        return result;
    }
}
