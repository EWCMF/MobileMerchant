package com.android.example.mobilemerchant.persistence;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.android.example.mobilemerchant.data.DebtOthersNamesWithItems;
import com.android.example.mobilemerchant.data.DebtOwedToOthers;

import java.util.ArrayList;
import java.util.List;

@Dao
public abstract class DebtOwedToOthersDao {
    @Query("SELECT * FROM DebtOwedToOthers")
    public abstract LiveData<List<DebtOwedToOthers>> getAll();

    @Query("SELECT * FROM DebtOwedToOthers WHERE owedID IN (:userIds)")
    public abstract List<DebtOwedToOthers> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM DebtOwedToOthers WHERE name LIKE :name LIMIT 1")
    public abstract DebtOwedToOthers findByName(String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertAll(DebtOwedToOthers... debtOwedToOthers);

    @Delete
    public abstract void delete(DebtOwedToOthers debtOwedToOthers);

    @Update
    public abstract void update(DebtOwedToOthers debtOwedToOthers);

    @Query("DELETE FROM DebtOwedToOthers")
    public abstract void nukeTable();

    @Transaction
    @Query("SELECT * FROM DebtOwedToOthers")
    public abstract LiveData<List<DebtOthersNamesWithItems>> getDebtOthersNamesWithItems();

    @Transaction
    @Query("SELECT * FROM DebtOwedToOthers")
    public abstract List<DebtOthersNamesWithItems> getDebtOthersNamesWithItemsSimple();

}
