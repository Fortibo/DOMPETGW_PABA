package com.example.uas_paba_klmpk6.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class income(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_income")
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
    var date : String? = null,

    @ColumnInfo(name = "location")
    var location : String? = null
)