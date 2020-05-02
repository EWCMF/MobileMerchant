package com.android.example.mobilemerchant.persistence;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.android.example.mobilemerchant.data.DebtYouItem;
import java.util.List;

@Dao
public interface DebtYouItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(DebtYouItem... debtYouItems);

    @Update
    void update(DebtYouItem debtYouItem);

    @Delete
    void delete(DebtYouItem debtYouItem);

    @Query("SELECT * FROM debtyouitem")
    LiveData<List<DebtYouItem>> getAll();

    @Query("SELECT * FROM debtyouitem WHERE debtOwnerID=:debtOwnerID")
    LiveData<List<DebtYouItem>> getAllForName(final int debtOwnerID);
}
