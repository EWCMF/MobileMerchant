package com.android.example.mobilemerchant.persistence;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.android.example.mobilemerchant.data.DebtOwed;

@Database(entities = {DebtOwed.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DebtOwedDao debtOwedDao();

    private static volatile AppDatabase appDatabaseInstance;

    public static AppDatabase getDatabase(final Context context) {
        if (appDatabaseInstance == null) {
            synchronized (AppDatabase.class) {
                if (appDatabaseInstance == null) {
                    appDatabaseInstance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "MerchantDB").build();
                }
            }
        }
        return appDatabaseInstance;
    }
}
