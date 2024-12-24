package com.example.uas_paba_klmpk6.database

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class income(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Id")
    var id : Int = 0,

    @ColumnInfo(name = "amount")
    var amount : Int = 0,

    @ColumnInfo(name = "category")
    var category : String? = null,

    @ColumnInfo(name = "title")
    var title : String? = null,

    @ColumnInfo(name = "note")
    var note : String? = null,

    @ColumnInfo(name = "date")
    var date : String? = null
)
