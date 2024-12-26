package com.example.uas_paba_klmpk6

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.uas_paba_klmpk6.database.expense
import com.example.uas_paba_klmpk6.database.income
import com.example.uas_paba_klmpk6.database.mainDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import java.text.NumberFormat
import java.util.Locale

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

        val _btnBack = findViewById<ImageButton>(R.id.backButton)
        _btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        DB = mainDB.getDatabase(this)
        val _submitButton = findViewById<Button>(R.id.nextBtn)
        val incomeData = intent.getParcelableExtra<income>("incomeData")
        val expenseData = intent.getParcelableExtra<expense>("expenseData")

        //Viewsss
        val _btnCancel = findViewById<Button>(R.id.cancelBtn)
        val _tvnamaTransaksi = findViewById<TextView>(R.id.tvNamaTransaksi)
        val _tvAmount = findViewById<TextView>(R.id.tvAmount)
        val _tvCategory = findViewById<TextView>(R.id.tvCategory)
        val _tvDate = findViewById<TextView>(R.id.tvDate)
        val _tvNote = findViewById<TextView>(R.id.tvNote)
        var _selectedType = findViewById<TextView>(R.id.selectedType)
        var _selectedTypeCard = findViewById<CardView>(R.id.selectedTypeCard)
        val rupiahFormat: NumberFormat = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
        rupiahFormat.setMaximumFractionDigits(0)
        //

        if (incomeData != null) {
            _tvnamaTransaksi.setText(incomeData.title)
            _tvAmount.setText(rupiahFormat.format(incomeData.amount))
            _tvCategory.setText(incomeData.category)
            _tvDate.setText(incomeData.date)
            _tvNote.setText(incomeData.note)
        } else if (expenseData != null) {
            _tvnamaTransaksi.setText(expenseData.title)
            _tvAmount.setText(rupiahFormat.format(expenseData.amount))
            _tvCategory.setText(expenseData.category)
            _tvDate.setText(expenseData.date)
            _tvNote.setText(expenseData.note)
            _tvAmount.setTextColor(ColorStateList.valueOf(Color.parseColor("#FF6B6B")))
            _selectedTypeCard.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FF6B6B"))
            _selectedType.text = "Expense"
        }

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
            val intent = Intent(this@inputReview, MainActivity::class.java)
            startActivity(intent)
        }

        _btnCancel.setOnClickListener {
            val intent = Intent(this@inputReview, MainActivity::class.java)
            startActivity(intent)
        }
    }
}