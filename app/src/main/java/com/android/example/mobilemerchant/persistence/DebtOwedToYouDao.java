package com.android.example.mobilemerchant.persistence;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.android.example.mobilemerchant.data.DebtOwedToYou;

import java.util.List;

@Dao
public interface DebtOwedToYouDao {
    @Query("SELECT * FROM DebtOwedToYou")
    List<DebtOwedToYou> getAll();

    @Query("SELECT * FROM DebtOwedToYou WHERE owedID IN (:userIds)")
    List<DebtOwedToYou> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM DebtOwedToYou WHERE name LIKE :name LIMIT 1")
    DebtOwedToYou findByName(String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(DebtOwedToYou... debtOwedToYous);

    @Delete
    void delete(DebtOwedToYou debtOwedToYou);

    @Update
    void update(DebtOwedToYou debtOwedToYou);

    @Query("DELETE FROM DebtOwedToYou")
    void nukeTable();
}
