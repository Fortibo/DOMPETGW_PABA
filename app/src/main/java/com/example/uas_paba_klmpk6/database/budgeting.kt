package com.example.uas_paba_klmpk6.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class budgeting(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id_budget")
    var id_budget : Int = 0,

    @ColumnInfo(name="budget_name")
    var budget_name : String? = null,

    @ColumnInfo(name="target_amount")
    var target_amount  : Int = 0,

    @ColumnInfo(name = "saved_amount")
    var saved_amount : Int = 0,

    @ColumnInfo(name="target_date")
    var target_date : String? = null,

    @ColumnInfo(name = "category")
    var budget_category : String? = null,

    @ColumnInfo(name = "note")
    var budget_note : String? = null,
)
