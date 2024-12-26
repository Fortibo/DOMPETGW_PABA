package com.example.uas_paba_klmpk6.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [expense::class,income::class,wallet::class], version = 2)
abstract class mainDB : RoomDatabase() {
    abstract fun funmainDAO() : balanceDAO
    companion object{
        @Volatile
        private var INSTANCE : mainDB? = null

        @JvmStatic
        fun getDatabase(context: Context) : mainDB{
            if(INSTANCE == null){
                synchronized(mainDB::class.java){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,mainDB::class.java,"main_db"
                    ).allowMainThreadQueries().build()
                }
            }
            return INSTANCE as mainDB
        }
    }
}