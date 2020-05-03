package com.android.example.mobilemerchant.persistence;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.android.example.mobilemerchant.data.DebtOwedItem;

import java.util.List;

@Dao
public interface DebtOwedItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(DebtOwedItem... debtOwedItems);

    @Update
    void update(DebtOwedItem debtOwedItem);

    @Delete
    void delete(DebtOwedItem debtOwedItem);

    @Query("SELECT * FROM DebtOwedItem")
    LiveData<List<DebtOwedItem>> getAll();

    @Query("SELECT * FROM DebtOwedItem WHERE debtOwnerID=:debtOwnerID")
    LiveData<List<DebtOwedItem>> getAllForName(final int debtOwnerID);

    @Query("DELETE FROM DebtOwedItem")
    void nukeTable();
}
