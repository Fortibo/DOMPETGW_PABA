package com.example.uas_paba_klmpk6.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class templateInput(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_template")
    var id : Int = 0,

    @ColumnInfo(name = "type")
    var type: String,

    @ColumnInfo(name = "amount")
    var amount: Int = 0,

    @ColumnInfo(name = "category")
    var category: String,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "note")
    var note: String? = null,

    @ColumnInfo(name = "reminder")
    var reminder: Int,

    @ColumnInfo(name = "date")
    val date: String,
)
