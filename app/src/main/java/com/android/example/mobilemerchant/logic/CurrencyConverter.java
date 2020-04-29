package com.android.example.mobilemerchant.logic;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;

public class CurrencyConverter {
    private double exchangeRate;
    private double input;
    private double result;

    public void convert(double input, String currencyFrom, String currencyTo) throws IOException {

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
