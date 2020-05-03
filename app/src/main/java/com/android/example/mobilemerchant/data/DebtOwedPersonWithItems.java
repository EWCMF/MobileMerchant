package com.android.example.mobilemerchant.data;
import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class DebtOwedPersonWithItems {
    @Embedded public DebtOwedPerson debtOwedPerson;
    @Relation(
            parentColumn = "owedID",
            entityColumn = "debtOwnerID"
    )
    public List<DebtOwedItem> debtOwedItems;
}
