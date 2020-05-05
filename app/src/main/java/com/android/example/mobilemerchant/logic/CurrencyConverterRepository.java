package com.android.example.mobilemerchant.logic;

import android.app.Application;

import com.android.example.mobilemerchant.data.CurrencyCache;
import com.android.example.mobilemerchant.persistence.AppDatabase;
import com.android.example.mobilemerchant.persistence.CurrencyCacheDao;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;
import java.util.Date;

public class CurrencyConverterRepository {
    private CurrencyCacheDao currencyCacheDao;
    private CurrencyConverterModel currencyConverterModel;

    public CurrencyConverterRepository(Application application, CurrencyConverterModel currencyConverterModel) {
        AppDatabase appDatabase = AppDatabase.getDatabase(application.getApplicationContext());
        this.currencyConverterModel = currencyConverterModel;
        currencyCacheDao = appDatabase.currencyCacheDao();
    }

    public void insert(CurrencyCache currencyCache) {
        currencyCacheDao.insertAll(currencyCache);
    }

    public void getDataForBase(double input, String currencyFrom, String currencyTo) {
        Thread thread = new Thread(() -> {
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
            } catch (NullPointerException e) {
                fetchJson(input, currencyFrom, currencyTo);
            }

        });
        thread.start();
    }

    private void fetchJson(double input, String currencyFrom, String currencyTo) {
        JsonNode root;
        try {
            root = new ObjectMapper().readTree(new URL("https://api.exchangeratesapi.io/latest?base=" + currencyFrom));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        long date = new Date().getTime();
        JsonNode rates = root.path("rates");
        double dkk = rates.path("DKK").asDouble();
        double usd = rates.path("USD").asDouble();
        double eur = rates.path("EUR").asDouble();
        double gbp = rates.path("GBP").asDouble();
        double sek = rates.path("SEK").asDouble();
        double nok = rates.path("NOK").asDouble();
        double jpy = rates.path("JPY").asDouble();

        currencyConverterModel.setInput(input);
        double exchangeRate = root.path("rates").path(currencyTo).asDouble();
        currencyConverterModel.setExchangeRate(exchangeRate);
        currencyConverterModel.setResult(input * exchangeRate);

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

        currencyConverterModel.setInput(input);
        double exchangeRate = valueTo;
        currencyConverterModel.setExchangeRate(exchangeRate);
        currencyConverterModel.setResult(input * exchangeRate);
    }
}
