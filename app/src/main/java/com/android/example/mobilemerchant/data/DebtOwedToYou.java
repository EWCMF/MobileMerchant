package com.android.example.mobilemerchant.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity
public class DebtOwedToYou {
    @PrimaryKey
    private int owedID;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "amount_owed")
    private double amountOwed;

    @ColumnInfo(name = "currency")
    private String currencyName;

    @Ignore
    private List<DebtYouItem> debtYouItems;

    public DebtOwedToYou(int owedID, String name, double amountOwed, String currencyName) {
        this.owedID = owedID;
        this.name = name;
        this.amountOwed = amountOwed;
        this.currencyName = currencyName;
    }

    public int getOwedID() {
        return owedID;
    }

    public void setOwedID(int owedID) {
        this.owedID = owedID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmountOwed() {
        return amountOwed;
    }

    public void setAmountOwed(double amountOwed) {
        this.amountOwed = amountOwed;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public List<DebtYouItem> getDebtYouItems() {
        return debtYouItems;
    }

    public void setDebtYouItems(List<DebtYouItem> debtYouItems) {
        this.debtYouItems = debtYouItems;
    }
}
