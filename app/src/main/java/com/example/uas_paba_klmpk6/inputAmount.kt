package com.example.uas_paba_klmpk6

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.uas_paba_klmpk6.database.category
import com.example.uas_paba_klmpk6.database.expense
import com.example.uas_paba_klmpk6.database.income

class inputAmount : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_input_amount)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val incomeData = intent.getParcelableExtra<income>("incomeData")
        val expenseData = intent.getParcelableExtra<expense>("expenseData")
        val _etAmount = findViewById<TextView>(R.id.etAmount)

        var category: String?
        var data: Parcelable?
        var name = ""


        var _btnNext = findViewById<Button>(R.id.nextBtn)
        _btnNext.setOnClickListener {
            if (!_etAmount.text.isNullOrEmpty()){
                if (incomeData != null) {
                    category = incomeData.category
                    data = income(category = category, amount = _etAmount.text.toString().toInt())
                    name = "incomeData"
                } else if (expenseData != null) {
                    category = expenseData.category
                    data = expense(category = category, amount = _etAmount.text.toString().toInt())
                    name = "expenseData"
                } else {
                    category = "undefined"
                    data = income()
                }

                val intent = Intent(this@inputAmount, inputDate::class.java).apply {
                    putExtra(name, data)
                }
                startActivity(intent)
            } else {
                category = "undefined"
                data = income()
            }
        }
    }
}