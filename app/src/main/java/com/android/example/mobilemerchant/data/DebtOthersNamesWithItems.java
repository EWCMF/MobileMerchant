package com.android.example.mobilemerchant.data;

import androidx.lifecycle.LiveData;
import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class DebtOthersNamesWithItems {
    @Embedded public DebtOwedToOthers debtOwedToOthers;
    @Relation(
            parentColumn = "owedID",
            entityColumn = "itemID"
    )
    public LiveData<List<DebtOthersItem>> debtOwedItems;
}
