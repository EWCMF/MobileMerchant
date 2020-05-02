package com.android.example.mobilemerchant.data;
import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class DebtOthersNamesWithItems {
    @Embedded public DebtOwedToOthers debtOwedToOthers;
    @Relation(
            parentColumn = "owedID",
            entityColumn = "debtOwnerID"
    )
    public List<DebtOthersItem> debtOwedItems;
}
