package com.android.example.mobilemerchant.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity
public class DebtOwedPerson {
    @PrimaryKey(autoGenerate = true)
    private int owedID;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "to_others")
    private boolean toOthers;

    @Ignore
    private double amountOwed;

    @Ignore
    private String currencyName;

    @Ignore
    private List<DebtOwedItem> debtOwedItems;

    public DebtOwedPerson(String name, boolean toOthers) {
        this.name = name;
        this.toOthers = toOthers;
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

    public List<DebtOwedItem> getDebtOwedItems() {
        return debtOwedItems;
    }

    public void setDebtOwedItems(List<DebtOwedItem> debtOwedItems) {
        this.debtOwedItems = debtOwedItems;
    }

    public boolean isToOthers() {
        return toOthers;
    }

    public void setToOthers(boolean toOthers) {
        this.toOthers = toOthers;
    }
}
