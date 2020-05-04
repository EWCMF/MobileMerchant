package com.android.example.mobilemerchant.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(
        entity = DebtOwedPerson.class,
        parentColumns = "owedID",
        childColumns = "debtOwnerID",
        onDelete = ForeignKey.CASCADE
), indices = @Index(value = "debtOwnerID"))
public class DebtOwedItem {
    @PrimaryKey(autoGenerate = true)
    private int itemID;

    @ColumnInfo(name = "debtOwnerID")
    private int debtOwnerID;

    @ColumnInfo(name = "item_name")
    private String itemName;

    @ColumnInfo(name = "value")
    private double value;

    @ColumnInfo(name = "currency")
    private String currency;

    public DebtOwedItem(int debtOwnerID, String itemName, double value, String currency) {
        this.debtOwnerID = debtOwnerID;
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

    public int getDebtOwnerID() {
        return debtOwnerID;
    }
}
