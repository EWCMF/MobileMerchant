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
import com.android.example.mobilemerchant.data.DebtOwedToYou;
import com.android.example.mobilemerchant.data.DebtYouNamesWithItems;

import java.util.ArrayList;
import java.util.List;

@Dao
public abstract class DebtOwedToYouDao {
    @Query("SELECT * FROM DebtOwedToYou")
    public abstract LiveData<List<DebtOwedToYou>> getAll();

    @Query("SELECT * FROM DebtOwedToYou")
    public List<DebtOwedToYou> getAllDebtOwed() {
        List<DebtYouNamesWithItems> debtYouNamesWithItems = getDebtYouNamesWithItems();
        List<DebtOwedToYou> debtOwedToYous = new ArrayList<>(debtYouNamesWithItems.size());
        for (DebtYouNamesWithItems current : debtYouNamesWithItems) {
            current.debtOwedToYou.setDebtYouItems(current.debtOwedItems);
            debtOwedToYous.add(current.debtOwedToYou);
        }
        return debtOwedToYous;
    }

    @Query("SELECT * FROM DebtOwedToYou WHERE owedID IN (:userIds)")
    public abstract List<DebtOwedToYou> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM DebtOwedToYou WHERE name LIKE :name LIMIT 1")
    public abstract DebtOwedToYou findByName(String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertAll(DebtOwedToYou... debtOwedToYous);

    @Delete
    public abstract void delete(DebtOwedToYou debtOwedToYou);

    @Update
    public abstract void update(DebtOwedToYou debtOwedToYou);

    @Query("DELETE FROM DebtOwedToYou")
    public abstract void nukeTable();

    @Transaction
    @Query("SELECT * FROM DebtOwedToYou")
    public abstract List<DebtYouNamesWithItems> getDebtYouNamesWithItems();
}
