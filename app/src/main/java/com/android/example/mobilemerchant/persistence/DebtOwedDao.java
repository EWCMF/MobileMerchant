package com.android.example.mobilemerchant.persistence;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.android.example.mobilemerchant.data.DebtOwed;

import java.util.List;

@Dao
public interface DebtOwedDao {
    @Query("SELECT * FROM DebtOwed")
    List<DebtOwed> getAll();

    @Query("SELECT * FROM DebtOwed WHERE owedID IN (:userIds)")
    List<DebtOwed> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM DebtOwed WHERE name LIKE :name LIMIT 1")
    DebtOwed findByName(String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(DebtOwed... debtOweds);

    @Delete
    void delete(DebtOwed debtOwed);

    @Update
    void update(DebtOwed debtOwed);

    @Query("DELETE FROM DebtOwed")
    void nukeTable();
}
