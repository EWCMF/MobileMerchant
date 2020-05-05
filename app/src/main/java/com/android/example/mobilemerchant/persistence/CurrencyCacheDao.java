package com.android.example.mobilemerchant.persistence;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.android.example.mobilemerchant.data.CurrencyCache;

@Dao
public interface CurrencyCacheDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(CurrencyCache... currencyCaches);

    @Delete
    void deleteOld(CurrencyCache currencyCache);

    @Query("SELECT * FROM CurrencyCache WHERE base = :base")
    CurrencyCache getBase(String base);

}
