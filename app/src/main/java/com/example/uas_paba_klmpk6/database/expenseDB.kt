package com.example.uas_paba_klmpk6.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [expense::class, income::class, wallet::class], version = 3)
abstract class expenseDB : RoomDatabase() {
    abstract fun funhistoryBelanjaDAO() : balanceDAO
    companion object{
        @Volatile
        private var INSTANCE : expenseDB? = null

        @JvmStatic
        fun getDatabase(context: Context) : expenseDB{
            if(INSTANCE == null){
                synchronized(expenseDB::class.java){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,expenseDB::class.java,"historyBarang_db"
                    ).allowMainThreadQueries().build()
                }
            }
            return INSTANCE as expenseDB
        }
    }
}