package com.android.example.mobilemerchant.persistence;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.android.example.mobilemerchant.data.DebtOwedItem;

@Dao
public interface DebtOwedItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(DebtOwedItem... debtOwedItems);

    @Update
    void update(DebtOwedItem debtOwedItem);

    @Delete
    void delete(DebtOwedItem debtOwedItem);

    @Query("DELETE FROM DebtOwedItem")
    void nukeTable();
}
