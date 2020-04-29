package com.android.example.mobilemerchant.logic;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class CurrencyConverter {
    private double exchangeRate;
    private double input;
    private double result;
    private ArrayList<String> allowedCurrencies;

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

    public void convert(double input, String currencyFrom, String currencyTo) throws IOException {
        if (!allowedCurrencies.contains(currencyFrom) || !allowedCurrencies.contains(currencyTo)) {
            return;
        }
        if (input < 0) {
            return;
        }
        JsonNode root = new ObjectMapper().readTree(new URL("https://api.exchangeratesapi.io/latest?base=" + currencyFrom));
        this.input = input;
        exchangeRate = root.path("rates").path(currencyTo).asDouble();
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
