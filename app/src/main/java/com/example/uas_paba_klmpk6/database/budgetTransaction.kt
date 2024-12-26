package com.example.uas_paba_klmpk6.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "budgetTransaction",
    foreignKeys = [
        ForeignKey(
            entity = budgeting::class,
            parentColumns = ["id_budget"],
            childColumns = ["id_budget"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["id_budget"])]
)
data class budgetTransaction(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_budget_transaction")
    var id : Int = 0,

    @ColumnInfo(name = "id_budget")
    var id_budget : Int,

    @ColumnInfo(name = "amount")
    var amount : Int = 0,

    @ColumnInfo(name =  "transaction_date")
    var transaction_date : String? = null,

    @ColumnInfo(name = "note")
    var note : String? = null
)
