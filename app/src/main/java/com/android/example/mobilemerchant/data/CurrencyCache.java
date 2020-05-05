package com.android.example.mobilemerchant.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class CurrencyCache {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo
    private long date;

    @ColumnInfo
    private String base;

    @ColumnInfo
    private double dkk;

    @ColumnInfo
    private double usd;

    @ColumnInfo
    private double eur;

    @ColumnInfo
    private double gbp;

    @ColumnInfo
    private double sek;

    @ColumnInfo
    private double nok;

    @ColumnInfo
    private double jpy;


    public CurrencyCache(long date, String base, double dkk, double usd, double eur, double gbp, double sek, double nok, double jpy) {
        this.date = date;
        this.base = base;
        this.dkk = dkk;
        this.usd = usd;
        this.eur = eur;
        this.gbp = gbp;
        this.sek = sek;
        this.nok = nok;
        this.jpy = jpy;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public long getDate() {
        return date;
    }

    public String getBase() {
        return base;
    }

    public double getDkk() {
        return dkk;
    }

    public double getUsd() {
        return usd;
    }

    public double getEur() {
        return eur;
    }

    public double getGbp() {
        return gbp;
    }

    public double getSek() {
        return sek;
    }

    public double getNok() {
        return nok;
    }

    public double getJpy() {
        return jpy;
    }
}
