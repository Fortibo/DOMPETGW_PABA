package com.example.uas_paba_klmpk6.database

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class wallet(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id : Int = 0,

    @ColumnInfo(name = "jumlah_balance")
    var jumlah_balance : Int = 0

)
