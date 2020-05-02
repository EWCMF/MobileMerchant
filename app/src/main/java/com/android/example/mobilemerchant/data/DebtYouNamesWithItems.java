package com.android.example.mobilemerchant.data;

import androidx.lifecycle.LiveData;
import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class DebtYouNamesWithItems {
    @Embedded public DebtOwedToYou debtOwedToYou;
    @Relation(
            parentColumn = "owedID",
            entityColumn = "itemID"
    )
    public LiveData<List<DebtOthersItem>> debtOwedItems;
}
