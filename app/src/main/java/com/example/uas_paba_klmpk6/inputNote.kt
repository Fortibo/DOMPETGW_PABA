package com.example.uas_paba_klmpk6

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.uas_paba_klmpk6.database.expense
import com.example.uas_paba_klmpk6.database.income

class inputNote : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_input_note)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val _btnBack = findViewById<ImageButton>(R.id.backButton)
        _btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }


        //Inpututnsunfiaunaiwundoaw
        val incomeData = intent.getParcelableExtra<income>("incomeData")
        val expenseData = intent.getParcelableExtra<expense>("expenseData")
        val _etTitle = findViewById<EditText>(R.id.etName)
        val _etNote = findViewById<EditText>(R.id.etNote)
        val category: String?
        var data: Parcelable?
        var name = ""

        //

        var _btnNext = findViewById<Button>(R.id.nextBtn)
        _btnNext.setOnClickListener {
            if (!_etTitle.text.isNullOrEmpty() && !_etNote.text.isNullOrEmpty()){
                if (incomeData != null) {
                    data = income(category = incomeData.category, amount = incomeData.amount, date = incomeData.date, title = _etTitle.text.toString(), note = _etNote.text.toString())
                    name = "incomeData"
                } else if (expenseData != null) {
                    data = expense(category = expenseData.category, amount = expenseData.amount, date = expenseData.date, title = _etTitle.text.toString(), note = _etNote.text.toString())
                    name = "expenseData"
                } else {
                    data = income()
                }
                val intent = Intent(this@inputNote, inputReview::class.java).apply {
                    putExtra(name, data)
                }
                startActivity(intent)
            } else if (!_etTitle.text.isNullOrEmpty() ){
                if (incomeData != null) {
                    data = income(category = incomeData.category, amount = incomeData.amount, date = incomeData.date, title = _etTitle.text.toString(), note = "-")
                    name = "incomeData"
                } else if (expenseData != null) {
                    data = expense(category = expenseData.category, amount = expenseData.amount, date = expenseData.date, title = _etTitle.text.toString(), note = "-")
                    name = "expenseData"
                } else {
                    data = income()
                }
                val intent = Intent(this@inputNote, inputReview::class.java).apply {
                    putExtra(name, data)
                }
                startActivity(intent)
            }
        }
    }
}