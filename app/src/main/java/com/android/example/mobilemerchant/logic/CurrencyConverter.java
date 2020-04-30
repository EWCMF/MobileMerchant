package com.android.example.mobilemerchant.logic;

import com.android.example.mobilemerchant.logic.exceptions.CurrencyNotSupportedException;
import com.android.example.mobilemerchant.logic.exceptions.NegativeInputException;
import com.android.example.mobilemerchant.logic.exceptions.SameCurrencyException;
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

    public void convert(double input, String currencyFrom, String currencyTo) throws IOException, SameCurrencyException, CurrencyNotSupportedException, NegativeInputException {
        if (currencyFrom.equals(currencyTo)) {
            throw new SameCurrencyException("The two selected currencies cannot be the same");
        }
        if (!allowedCurrencies.contains(currencyFrom) || !allowedCurrencies.contains(currencyTo)) {
            throw new CurrencyNotSupportedException("One of the selected currencies is currently not allowed.");
        }
        if (input < 0) {
            throw new NegativeInputException("Input value cannot be below 0");
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
