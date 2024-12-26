package com.example.uas_paba_klmpk6

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.uas_paba_klmpk6.database.expense
import com.example.uas_paba_klmpk6.database.income
import com.example.uas_paba_klmpk6.database.mainDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class inputReview : AppCompatActivity() {
    private lateinit var DB : mainDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_input_review)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        DB = mainDB.getDatabase(this)
        val _submitButton = findViewById<Button>(R.id.nextBtn)
        val incomeData = intent.getParcelableExtra<income>("incomeData")
        val expenseData = intent.getParcelableExtra<expense>("expenseData")

        _submitButton.setOnClickListener {
            if (incomeData != null) {
                CoroutineScope(Dispatchers.IO).async {
                    DB.funmainDAO().insertIncome(incomeData)
                }
            } else if (expenseData != null) {
                CoroutineScope(Dispatchers.IO).async {
                    DB.funmainDAO().insertExpense(expenseData)
                }
            }
        }

    }
}