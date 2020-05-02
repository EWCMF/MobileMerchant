package com.android.example.mobilemerchant.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class DebtOwedItem {
    @PrimaryKey(autoGenerate = true)
    private int itemID;

    @ColumnInfo(name = "owner_id")
    private int ownerID;

    @ColumnInfo(name = "item_name")
    private String itemName;

    @ColumnInfo(name = "value")
    private double value;

    @ColumnInfo(name = "currency")
    private String currency;

    public DebtOwedItem(String itemName, double value, String currency) {
        this.itemName = itemName;
        this.value = value;
        this.currency = currency;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
