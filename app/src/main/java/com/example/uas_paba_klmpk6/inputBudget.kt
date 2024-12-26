package com.example.uas_paba_klmpk6

import android.os.Bundle
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class inputBudget : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_input_budget)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val _btnCancel = findViewById<ConstraintLayout>(R.id.cvCancel)
        _btnCancel.setOnClickListener{
            onBackPressedDispatcher.onBackPressed()
        }

        val _etBudgetName = findViewById<EditText>(R.id.etBudgetName)
        val _etTargetAmount = findViewById<EditText>(R.id.etTargetAmount)
        val _btnSelectDate = findViewById<CardView>(R.id.cv3)
        val _etCategory = findViewById<EditText>(R.id.etCategory)
        val _etNote = findViewById<EditText>(R.id.etNote)

        val _btnNext = findViewById<ConstraintLayout>(R.id.cvNext)
    }
}