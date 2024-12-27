package com.example.uas_paba_klmpk6

import android.app.DatePickerDialog
import android.content.Intent
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
import com.example.uas_paba_klmpk6.database.history
import com.example.uas_paba_klmpk6.database.income
import com.example.uas_paba_klmpk6.database.mainDB
import com.example.uas_paba_klmpk6.helper.DateHelper.getCurrentFormatDate
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MutasiPage : AppCompatActivity() {

    private lateinit var DB : mainDB


    private lateinit var adapterIncome :adapterIncome
    private lateinit var adapterHistory :adapterAll
    private lateinit var adapterExpense :adapterExpense

    private var arIncome : MutableList<income> = mutableListOf()
    private var arExpense : MutableList<expense> = mutableListOf()
    private var arAll : MutableList<history> = mutableListOf()



    private var all :Boolean = true
    private var incomeB : Boolean = false
    private var expenseB : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_mutasi_page)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val _btIncome = findViewById<CardView>(R.id.incomeSelectBtn)
        val _btExpense= findViewById<CardView>(R.id.expenseSelectBtn)
        val _btAll = findViewById<CardView>(R.id.allSelectButton)

        val _btnGo = findViewById<Button>(R.id.btnGoMutasi)
        val _btnBack = findViewById<ImageButton>(R.id.backButton)

        val _tglFrom = findViewById<TextView>(R.id.selectedFromDate)
        val _tglTo = findViewById<TextView>(R.id.selectedToDate)

        val _selectTglFrom = findViewById<CardView>(R.id.chooseFromDateBtn)
        val _selectTglTo = findViewById<CardView>(R.id.chooseToDateBtn)

        val tgl = getCurrentFormatDate()

        DB = mainDB.getDatabase(this)

        _tglFrom.setText(tgl)
        _tglTo.setText(tgl)

        _btnBack.setOnClickListener {
            finish()
        }

        _btIncome.setOnClickListener {
            all = false
            incomeB = true
            expenseB = false
            _btIncome.setBackgroundResource(R.drawable.border_selected_income)
            _btAll.setBackgroundResource(R.drawable.border)
            _btExpense.setBackgroundResource(R.drawable.border)
        }

        _btExpense.setOnClickListener {
            all = false
            incomeB = false
            expenseB = true
            _btExpense.setBackgroundResource(R.drawable.border_selected_expense)
            _btIncome.setBackgroundResource(R.drawable.border)
            _btAll.setBackgroundResource(R.drawable.border)
        }

        _btAll.setOnClickListener {
            all = true
            incomeB = false
            expenseB = false
            _btAll.setBackgroundResource(R.drawable.border_selected)
            _btExpense.setBackgroundResource(R.drawable.border)
            _btIncome.setBackgroundResource(R.drawable.border)
        }
        _selectTglFrom.setOnClickListener {
            val calendar = Calendar.getInstance()
            DatePickerDialog(this, { _, year, month, dayOfMonth ->
                val selectedDate = String.format("%02d - %02d - %04d", dayOfMonth, month + 1, year)
                _tglFrom.text = selectedDate
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
        }
        _selectTglTo.setOnClickListener {
            val calendar = Calendar.getInstance()
            DatePickerDialog(this, { _, year, month, dayOfMonth ->
                val selectedDate = String.format("%02d - %02d - %04d", dayOfMonth, month + 1, year)
                _tglTo.text = selectedDate
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        _btnGo.setOnClickListener {
            val fromDate = _tglFrom.text.toString().replace(" - ", "/")
            val toDate = _tglTo.text.toString().replace(" - ", "/")

            // Format ulang ke format yang sesuai dengan database
            val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val dbFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val formattedFromDate = dbFormatter.format(formatter.parse(fromDate)!!)
            val formattedToDate = dbFormatter.format(formatter.parse(toDate)!!)
            val intent = Intent(this, DetailMutasiPage::class.java)

            when {
                all -> {
                    arAll = DB.funmainDAO().getAllHistoryByDateRange(formattedFromDate, formattedToDate)
                    intent.putParcelableArrayListExtra("DATA", ArrayList(arAll))
                    intent.putExtra("TYPE", "all")
                    intent.putExtra("datefrom",fromDate)
                    intent.putExtra("dateto",toDate)
                    startActivity(intent)
                }
                incomeB -> {
                    arIncome = DB.funmainDAO().getIncomesByDateRange(formattedFromDate, formattedToDate)
                    intent.putParcelableArrayListExtra("DATA", ArrayList(arIncome))
                    intent.putExtra("TYPE", "income")
                    intent.putExtra("datefrom",fromDate)
                    intent.putExtra("dateto",toDate)
                    startActivity(intent)
                }
                expenseB -> {
                    arExpense = DB.funmainDAO().getExpensesByDateRange(formattedFromDate, formattedToDate)
                    intent.putParcelableArrayListExtra("DATA", ArrayList(arExpense))
                    intent.putExtra("TYPE", "expense")
                    intent.putExtra("datefrom",fromDate)
                    intent.putExtra("dateto",toDate)
                    startActivity(intent)

                }
            }
        }

    }
}