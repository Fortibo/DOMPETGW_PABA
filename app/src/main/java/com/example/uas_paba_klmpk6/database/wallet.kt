package com.example.uas_paba_klmpk6.database

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class wallet(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Id")
    var id : Int = 0,

    @ColumnInfo(name = "tanggal")
    var jumlah_sbalance : Int = 0

)
