package com.example.uas_paba_klmpk6

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.uas_paba_klmpk6.database.expense
import com.example.uas_paba_klmpk6.database.income
import java.util.Calendar
import java.util.Date

class inputDate : AppCompatActivity() {
    private lateinit var datePickerDialog: DatePickerDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_input_date)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val _btnBack = findViewById<ImageButton>(R.id.backButton)
        _btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        initDatePicker()
        var _btnDate = findViewById<CardView>(R.id.chooseDateBtn)
        _btnDate.setOnClickListener {
            openDatePicker()
        }

        //INSERTS////////
        val incomeData = intent.getParcelableExtra<income>("incomeData")
        val expenseData = intent.getParcelableExtra<expense>("expenseData")
        val _tvDate = findViewById<TextView>(R.id.selectedDate)
        val category: String?
        var data: Parcelable?
        var name = ""
        //////////

        var _btnNext = findViewById<Button>(R.id.nextBtn)
        _btnNext.setOnClickListener {

            if (_tvDate.text != "Select Date"){
                if (incomeData != null) {
                    data = income(category = incomeData.category, amount = incomeData.amount, date = _tvDate.text.toString())
                    name = "incomeData"
                } else if (expenseData != null) {
                    data = expense(category = expenseData.category, amount = expenseData.amount, date = _tvDate.text.toString())
                    name = "expenseData"
                } else {
                    data = income()
                }
                Log.d("DEBUG", "Categories: ${data}")
                val intent = Intent(this@inputDate, inputNote::class.java).apply {
                    putExtra(name, data)
                }
                startActivity(intent)
            } else {
                data = income()
            }
        }
    }

    private fun initDatePicker(){
        val dateListener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
            val date = makeDateString(day, month + 1, year)
            var _tvSelected = findViewById<TextView>(R.id.selectedDate)
            _tvSelected.setText(date)
        }

        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)

        datePickerDialog = DatePickerDialog(
            this,
            dateListener,
            year,
            month,
            day
        )
    }

    private fun makeDateString(day: Int, month: Int, year: Int): String{
        return getMonthFormat(month) + " " + day + " " + year
    }

    private fun getMonthFormat(month: Int): String {
        return when (month) {
            1 -> "JAN"
            2 -> "FEB"
            3 -> "MAR"
            4 -> "APR"
            5 -> "MAY"
            6 -> "JUN"
            7 -> "JUL"
            8 -> "AUG"
            9 -> "SEP"
            10 -> "OCT"
            11 -> "NOV"
            12 -> "DEC"
            else -> "INVALID"
        }
    }

    private fun openDatePicker(){
        datePickerDialog.show()
    }
}