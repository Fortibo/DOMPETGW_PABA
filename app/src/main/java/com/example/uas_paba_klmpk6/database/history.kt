package com.example.uas_paba_klmpk6.database

data class history(
    val type: String, // "income" atau "expense"
    val amount: Int,
    val category: String,
    val title: String,
    val note: String,
    val date: String
)