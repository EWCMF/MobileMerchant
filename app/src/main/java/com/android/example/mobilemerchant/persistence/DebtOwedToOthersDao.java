package com.android.example.mobilemerchant.persistence;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.android.example.mobilemerchant.data.DebtOwedToOthers;

import java.util.List;

@Dao
public interface DebtOwedToOthersDao {
    @Query("SELECT * FROM DebtOwedToOthers")
    List<DebtOwedToOthers> getAll();

    @Query("SELECT * FROM DebtOwedToOthers WHERE owedID IN (:userIds)")
    List<DebtOwedToOthers> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM DebtOwedToOthers WHERE name LIKE :name LIMIT 1")
    DebtOwedToOthers findByName(String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(DebtOwedToOthers... debtOwedToOthers);

    @Delete
    void delete(DebtOwedToOthers debtOwedToOthers);

    @Update
    void update(DebtOwedToOthers debtOwedToOthers);

    @Query("DELETE FROM DebtOwedToOthers")
    void nukeTable();
}
