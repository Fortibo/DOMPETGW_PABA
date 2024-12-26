package com.example.uas_paba_klmpk6

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.uas_paba_klmpk6.database.budgeting
import com.example.uas_paba_klmpk6.database.mainDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.Calendar

class inputBudget : AppCompatActivity() {
    private lateinit var datePickerDialog: DatePickerDialog
    private lateinit var DB:mainDB
    var selectedDate: String? = null

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

        DB = mainDB.getDatabase(this)

        val _etBudgetName = findViewById<EditText>(R.id.etBudgetName)
        val _etTargetAmount = findViewById<EditText>(R.id.etTargetAmount)
        val _btnSelectDate = findViewById<CardView>(R.id.cv3)
        val _etCategory = findViewById<EditText>(R.id.etCategory)
        val _etNote = findViewById<EditText>(R.id.etNote)


        var budgetInput = budgeting()

        initDatePicker()
        _btnSelectDate.setOnClickListener{
            openDatePicker()
        }

        val _btnNext = findViewById<ConstraintLayout>(R.id.cvNext)
        _btnNext.setOnClickListener{
            val budgetName = _etBudgetName.text.toString()
            val targetAmount = _etTargetAmount.text.toString().toIntOrNull() ?: 0
            val category = _etCategory.text.toString()
            val note = _etNote.text.toString()

            // Populate the budgeting object
            budgetInput.budget_name = budgetName
            budgetInput.target_amount = targetAmount
            budgetInput.saved_amount = 0 // Default to 0 for new budgets
            budgetInput.target_date = selectedDate
            budgetInput.budget_category = category
            budgetInput.budget_note = note

            // Insert the budget into the database
            CoroutineScope(Dispatchers.IO).launch {
                DB.funBudgetDAO().insertBudget(budgetInput)
                Log.d("DB_INSERT", "Budget Added: $budgetInput")

                runOnUiThread {
                    finish() // Close the current activity
                }
            }
        }


    }

    private fun initDatePicker(){
        val dateListener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
            val date = makeDateString(day, month + 1, year)
            var _tvDateSelected = findViewById<TextView>(R.id.btnTargetDate)
            _tvDateSelected.setText(date)
            selectedDate = date
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

    private fun makeDateString(day: Int, month: Int, year: Int): String {
        val formattedDay = if (day < 10) "0$day" else "$day"
        val formattedMonth = if (month < 10) "0$month" else "$month"
        return "$year-$formattedMonth-$formattedDay"
    }

    private fun openDatePicker(){
        datePickerDialog.show()
    }
}