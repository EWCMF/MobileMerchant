package com.android.example.mobilemerchant.persistence;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.android.example.mobilemerchant.data.DebtOthersItem;
import java.util.List;

@Dao
public interface DebtOthersItemDao {
    @Insert
    void insertAll(DebtOthersItem... debtOthersItems);

    @Update
    void update(DebtOthersItem debtOthersItem);

    @Delete
    void delete(DebtOthersItem debtOthersItem);

    @Query("SELECT * FROM DebtOthersItem")
    LiveData<List<DebtOthersItem>> getAll();

    @Query("SELECT * FROM DebtOthersItem WHERE debtOwnerID=:debtOwnerID")
    LiveData<List<DebtOthersItem>> getAllForName(final int debtOwnerID);

    @Query("DELETE FROM DebtOthersItem")
    void nukeTable();
}
