package com.example.uas_paba_klmpk6

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.uas_paba_klmpk6.database.mainDB
import java.util.Calendar

class inputTemplate : AppCompatActivity() {
    private lateinit var datePickerDialog: DatePickerDialog
    private lateinit var DB:mainDB
    private var selectedDate:String ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_input_template)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        DB = mainDB.getDatabase(this)
    }


//    DATEPICKER
    private fun initDatePicker(){
        val dateListener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
            val date = makeDateString(day, month + 1, year)
            var _tvSelected = findViewById<TextView>(R.id.selectedDate)
            _tvSelected.setText(date)
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
