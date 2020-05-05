package com.android.example.mobilemerchant.persistence;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.android.example.mobilemerchant.data.CurrencyCache;
import com.android.example.mobilemerchant.data.DebtOwedItem;
import com.android.example.mobilemerchant.data.DebtOwedPerson;

@Database(entities = {DebtOwedPerson.class, DebtOwedItem.class, CurrencyCache.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DebtOwedPersonDao debtOwedPersonDao();
    public abstract DebtOwedItemDao debtOwedItemDao();
    public abstract CurrencyCacheDao currencyCacheDao();

    private static volatile AppDatabase appDatabaseInstance;

    public static AppDatabase getDatabase(final Context context) {
        if (appDatabaseInstance == null) {
            synchronized (AppDatabase.class) {
                if (appDatabaseInstance == null) {
                    appDatabaseInstance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "MerchantDB").fallbackToDestructiveMigration().allowMainThreadQueries().build();
                }
            }
        }
        return appDatabaseInstance;
    }
}
