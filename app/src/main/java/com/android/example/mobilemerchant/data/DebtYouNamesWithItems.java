package com.android.example.mobilemerchant.data;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class DebtYouNamesWithItems {
    @Embedded public DebtOwedToYou debtOwedToYou;
    @Relation(
            parentColumn = "owedID",
            entityColumn = "debtOwnerID"
    )
    public List<DebtYouItem> debtOwedItems;
}
