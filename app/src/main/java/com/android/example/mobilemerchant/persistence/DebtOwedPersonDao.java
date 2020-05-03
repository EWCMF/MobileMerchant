package com.android.example.mobilemerchant.persistence;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.android.example.mobilemerchant.data.DebtOwedPersonWithItems;
import com.android.example.mobilemerchant.data.DebtOwedPerson;

import java.util.List;

@Dao
public interface DebtOwedPersonDao {
    @Query("SELECT * FROM DebtOwedPerson")
    LiveData<List<DebtOwedPerson>> getAll();

    @Query("SELECT * FROM DebtOwedPerson WHERE owedID IN (:userIds)")
    List<DebtOwedPerson> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM DebtOwedPerson WHERE name LIKE :name LIMIT 1")
    DebtOwedPerson findByName(String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(DebtOwedPerson... debtOwedToOthers);

    @Delete
    void delete(DebtOwedPerson debtOwedPerson);

    @Update
    void update(DebtOwedPerson debtOwedPerson);

    @Query("DELETE FROM DebtOwedPerson")
    void nukeTable();

    @Transaction
    @Query("SELECT * FROM DebtOwedPerson WHERE to_others = 1")
    LiveData<List<DebtOwedPersonWithItems>> getDebtOthersPersonWithItems();

    @Transaction
    @Query("SELECT * FROM DebtOwedPerson WHERE to_others = 1")
    List<DebtOwedPersonWithItems> getDebtOthersPersonWithItemsSimple();

    @Transaction
    @Query("SELECT * FROM DebtOwedPerson WHERE to_others = 0")
    LiveData<List<DebtOwedPersonWithItems>> getDebtYouPersonWithItems();

    @Transaction
    @Query("SELECT * FROM DebtOwedPerson WHERE to_others = 0")
    List<DebtOwedPersonWithItems> getDebtYouPersonWithItemsSimple();

}
