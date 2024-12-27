package com.example.uas_paba_klmpk6

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.uas_paba_klmpk6.database.expense
import com.example.uas_paba_klmpk6.database.income
import com.example.uas_paba_klmpk6.database.mainDB
import com.example.uas_paba_klmpk6.database.templateInput
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import java.text.NumberFormat
import java.util.Locale

class detailTemplate : AppCompatActivity() {
    private lateinit var DB : mainDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail_template)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        DB = mainDB.getDatabase(this)

        var terimaData = intent.getParcelableExtra<templateInput>("dataTemplate")

        val _tvReviewTemplateName = findViewById<TextView>(R.id.tvNamaTemplateReview)
        val _selectedTypeTemplateReview = findViewById<TextView>(R.id.selectedTypeTemplateReview)
        val _tvTemplateAmountReview = findViewById<TextView>(R.id.tvTemplateAmountReview)
        val _tvTemplateCategoryReview = findViewById<TextView>(R.id.tvTemplateCategoryReview)
        val _tvDateTemplateReview = findViewById<TextView>(R.id.tvDateTemplateReview)
        val _tvReminderReview = findViewById<TextView>(R.id.tvReminderReview)
        val _tvNoteReview = findViewById<TextView>(R.id.tvNoteReview)

        //Assign to view

        val _cardSelectedType = findViewById<CardView>(R.id.cardSelectedType)
        _tvReviewTemplateName.setText(terimaData?.title)
        if (terimaData?.type == "Income"){
            _cardSelectedType.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#C7F9BB"))
            _selectedTypeTemplateReview.setText(terimaData.type)

            _tvTemplateAmountReview.setTextColor(Color.parseColor("#C7F9BB"))
            _tvTemplateAmountReview.text = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
                .apply { maximumFractionDigits = 0 }
                .format(terimaData?.amount)
        }else if (terimaData?.type == "Expense"){
            _cardSelectedType.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FF6B6B"))
            _selectedTypeTemplateReview.setText(terimaData.type)

            _tvTemplateAmountReview.setTextColor(Color.parseColor("#FF6B6B"))
            _tvTemplateAmountReview.text = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
                .apply { maximumFractionDigits = 0 }
                .format(terimaData?.amount)
        }
        _tvTemplateCategoryReview.setText(terimaData?.category)
        _tvDateTemplateReview.setText(terimaData?.date)
        var reminderVal = terimaData?.reminder.toString()
        _tvReminderReview.setText("$reminderVal days")
        _tvNoteReview.setText(terimaData?.note)


        val _btnSubmit = findViewById<Button>(R.id.nextBtnTemplate)

        _btnSubmit.setOnClickListener{
            if (terimaData?.type == "Expense"){
                val data = expense(
                    category = terimaData.category,
                    amount = terimaData.amount,
                    date = terimaData.date,
                    title = terimaData.title,
                    note = terimaData.note)

                CoroutineScope(Dispatchers.IO).async {
                    DB.funmainDAO().insertExpense(data)
                }
            } else if (terimaData?.type == "Income"){
                val data = income(
                    category = terimaData.category,
                    amount = terimaData.amount,
                    date = terimaData.date,
                    title = terimaData.title,
                    note = terimaData.note)

                CoroutineScope(Dispatchers.IO).async {
                    DB.funmainDAO().insertIncome(data)
                }
            }
            val intent = Intent(this@detailTemplate, MainActivity::class.java)
            startActivity(intent)
        }

        val _btnCancel = findViewById<Button>(R.id.cancelBtnTemplate)
        _btnCancel.setOnClickListener{
            onBackPressedDispatcher.onBackPressed()
        }
    }
}