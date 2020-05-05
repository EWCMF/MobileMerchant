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
    @Query("SELECT * FROM DebtOwedPerson WHERE to_others = 0")
    LiveData<List<DebtOwedPersonWithItems>> getDebtYouPersonWithItems();

    @Transaction
    @Query("SELECT * FROM DebtOwedPerson WHERE name LIKE :text AND to_others = 1")
    LiveData<List<DebtOwedPersonWithItems>> searchNamesOthers(String text);

    @Transaction
    @Query("SELECT * FROM DebtOwedPerson WHERE name LIKE :text AND to_others = 0")
    LiveData<List<DebtOwedPersonWithItems>> searchNamesYou(String text);
}
