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

    @Ignore
    private double amountOwed;

    @Ignore
    private String currencyName;

    @Ignore
    private List<DebtYouItem> debtYouItems;

    public DebtOwedToYou(int owedID, String name) {
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

    public List<DebtYouItem> getDebtYouItems() {
        return debtYouItems;
    }

    public void setDebtYouItems(List<DebtYouItem> debtYouItems) {
        if (debtYouItems.size() > 0) {
            String denominator = debtYouItems.get(0).getCurrency();
            boolean same = true;
            for (int i = 0; i < debtYouItems.size(); i++) {
                if (!debtYouItems.get(i).getCurrency().equals(denominator)) {
                    same = false;
                    break;
                }
            }
            if (same) {
                currencyName = denominator;
                for (int i = 0; i < debtYouItems.size(); i++) {
                    amountOwed += debtYouItems.get(i).getValue();
                }
            } else {
                currencyName = "Mixed";
                amountOwed = 0;
            }
        }
        this.debtYouItems = debtYouItems;
    }
}
