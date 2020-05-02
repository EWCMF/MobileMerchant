package com.android.example.mobilemerchant.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity
public class DebtOwedToOthers {
    @PrimaryKey
    private int owedID;

    @ColumnInfo(name = "name")
    private String name;

    @Ignore
    private double amountOwed;

    @Ignore
    private String currencyName;

    @Ignore
    private List<DebtOthersItem> debtOthersItems;

    public DebtOwedToOthers(int owedID, String name) {
        this.owedID = owedID;
        this.name = name;
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

    public List<DebtOthersItem> getDebtOthersItems() {
        return debtOthersItems;
    }

    public void setDebtOthersItems(List<DebtOthersItem> debtOthersItems) {
        this.debtOthersItems = debtOthersItems;
    }
}
