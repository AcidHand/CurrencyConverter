package com.acidhand.currencyconverter.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.acidhand.currencyconverter.data.database.dao.AppDao
import com.acidhand.currencyconverter.presentation.models.Currency

@Database(entities = [Currency::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getDao(): AppDao

    companion object {
        private var DB_INSTANCE: AppDatabase? = null

        @Synchronized
        fun getAppDb(context: Context): AppDatabase {
            if (DB_INSTANCE == null) {
                DB_INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "MYDB"
                )
                    .allowMainThreadQueries()
                    .build()
            }
            return DB_INSTANCE!!
        }
    }
}