package com.android.example.mobilemerchant.persistence;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.android.example.mobilemerchant.data.DebtOwedToOthers;
import com.android.example.mobilemerchant.data.DebtOwedToYou;

@Database(entities = {DebtOwedToOthers.class, DebtOwedToYou.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DebtOwedToOthersDao debtOwedToOthersDao();
    public abstract DebtOwedToYouDao debtOwedToYouDao();

    private static volatile AppDatabase appDatabaseInstance;

    public static AppDatabase getDatabase(final Context context) {
        if (appDatabaseInstance == null) {
            synchronized (AppDatabase.class) {
                if (appDatabaseInstance == null) {
                    appDatabaseInstance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "MerchantDB").fallbackToDestructiveMigration().build();
                }
            }
        }
        return appDatabaseInstance;
    }
}
