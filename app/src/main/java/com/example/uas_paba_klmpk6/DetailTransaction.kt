package com.example.uas_paba_klmpk6

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.uas_paba_klmpk6.database.expense
import com.example.uas_paba_klmpk6.database.history
import com.example.uas_paba_klmpk6.database.income
import com.example.uas_paba_klmpk6.database.mainDB
import com.example.uas_paba_klmpk6.helper.DateHelper.getCurrentDate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.math.tan

class DetailTransaction : AppCompatActivity() {
    private lateinit var DB : mainDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail_transaction)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        DB = mainDB.getDatabase(this)

        CoroutineScope(Dispatchers.Main).async {
            val income = DB.funmainDAO().selectAllIncome()
            Log.d("income ROOM", income.toString())
        }

        val _tvBack = findViewById<TextView>(R.id.tvBack)
        val _tvNamaTransactionDetail = findViewById<TextView>(R.id.tvNamaTransactionDetail)

        val _tvCategories = findViewById<TextView>(R.id.tvCategories)
        val _tvType = findViewById<TextView>(R.id.tvType)
        val _tvDate = findViewById<TextView>(R.id.tvDate)
        val _tvNote = findViewById<TextView>(R.id.tvNote)
        val _tvAmount = findViewById<TextView>(R.id.tvAmount)
        val _tvLocation = findViewById<TextView>(R.id.tvLocation)

        val _tvTypeColor = findViewById<TextView>(R.id.tvTypeColor)

        val type = intent.getStringExtra("type")
        _tvType.setText(type)

        val date = getCurrentDate()
        _tvDate.setText(date)

        val title = intent.getStringExtra("title")
        val amount = intent.getStringExtra("amount")
        val category = intent.getStringExtra("category")
        val note = intent.getStringExtra("note")
        val location = intent.getStringExtra("location")


        val _etTitle = findViewById<EditText>(R.id.etTitle)
        val _etAmount = findViewById<EditText>(R.id.etAmount)
        val spinnerCategories = findViewById<Spinner>(R.id.spinnerCategories)
        val rgType = findViewById<RadioGroup>(R.id.rgType)
        val radioIncome = findViewById<RadioButton>(R.id.radioCategory1)
        val radioExpense = findViewById<RadioButton>(R.id.radioCategory2)
        val _etTahun = findViewById<EditText>(R.id.etTahun)
        val _etTanggal = findViewById<EditText>(R.id.etTanggal)
        val _etBulan = findViewById<EditText>(R.id.etBulan)
        val _etJam = findViewById<EditText>(R.id.etJam)
        val _etMenit = findViewById<EditText>(R.id.etMenit)
        val _etLocation = findViewById<EditText>(R.id.etLocation)
        val _etNote = findViewById<EditText>(R.id.etNote)
        val _btnSimpanEdit = findViewById<Button>(R.id.btnSimpanEdit)
        val _btnHapus = findViewById<Button>(R.id.btnDelete)

        val categories = arrayOf("Food", "Vehicle", "Investment", "Others")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCategories.adapter = adapter

        rgType.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.radioCategory1 -> {
                    Log.d("RadioGroup", "Income selected")
                }
                R.id.radioCategory2 -> {
                    Log.d("RadioGroup", "Expense selected")
                }
            }
        }

        _etTitle.setText(title)
        _etAmount.setText(amount)

        val calendar = Calendar.getInstance()
        val tahun = calendar.get(Calendar.YEAR)
        val bulan = calendar.get(Calendar.MONTH)
        val tanggal = calendar.get(Calendar.DAY_OF_MONTH)
        val jam = calendar.get(Calendar.HOUR_OF_DAY)
        val menit = calendar.get(Calendar.MINUTE)
        _etTahun.setText(tahun.toString())
        _etBulan.setText(String.format("%02d", bulan))
        _etTanggal.setText(String.format("%02d", tanggal))
        _etJam.setText(String.format("%02d", jam))
        _etMenit.setText(String.format("%02d", menit))

        _etLocation.setText(location)
        _etNote.setText(note)

        if (category != null) {
            val categoryIndex = categories.indexOf(category)
            if (categoryIndex >= 0) {
                spinnerCategories.setSelection(categoryIndex)
            }
        }

        // BAGIAN EXPENSE
        if(_tvType.text == "Expense" || _tvType.text == "expense"){
            _tvCategories.setText(category)
            _tvNamaTransactionDetail.setText(" " + title)
            _tvAmount.setText("- " + amount)
            _tvNote.setText(note)
            _tvLocation.setText(location)

            _tvType.setText("Expense")
            _tvTypeColor.setText("Expense")
            _tvTypeColor.setBackgroundColor(Color.parseColor("#ff9c7e"))
            _tvAmount.setTextColor(Color.parseColor("#ff3728"))

            radioExpense.isChecked = true
        }

        // BAGIAN INCOME
        else if (_tvType.text == "Income" || _tvType.text == "income"){
            _tvCategories.setText(category)
            _tvNamaTransactionDetail.setText(" " + title)
            _tvAmount.setText("+ " + amount)
            _tvNote.setText(note)
            _tvLocation.setText(location)

            _tvType.setText("Income")
            _tvTypeColor.setText("Income")
            _tvTypeColor.setBackgroundColor(Color.parseColor("#C7F9BB"))
            _tvAmount.setTextColor(Color.parseColor("#4bc355"))

            radioIncome.isChecked = true
        }

        _btnSimpanEdit.setOnClickListener {
            val amountString = _etAmount.text.toString().replace("[^0-9]".toRegex(), "")
            val amount = amountString.toIntOrNull() ?: 0

            val category = spinnerCategories.selectedItem.toString()
            val title = _etTitle.text.toString()
            val note = _etNote.text.toString()
            val location = _etLocation.text.toString()
            val year = _etTahun.text.toString()
            val month = _etBulan.text.toString()
            val day = _etTanggal.text.toString()
            val hour = _etJam.text.toString()
            val minute = _etMenit.text.toString()
            val date = "$year-$month-$day-$hour-$minute"

            val id = intent.getIntExtra("id", -1)


            if (radioIncome.isChecked && _tvType.text != "Income") {
                DB.funmainDAO().insertIncome(
                    income(amount = amount, category = category, title = title, note = note, date = date, location = location)
                )
                DB.funmainDAO().deleteExpense(expense(id = id))
            } else if (radioExpense.isChecked && _tvType.text != "Expense") {
                DB.funmainDAO().insertExpense(
                    expense(amount = amount, category = category, title = title, note = note, date = date, location = location)
                )
                DB.funmainDAO().deleteIncome(income(id = id))
            } else {
                if (_tvType.text == "Income") {
                    DB.funmainDAO().updateIncome(
                        isi_amount = amount,
                        isi_category = category,
                        isi_title = title,
                        isi_note = note,
                        isi_date = date,
                        isi_location = location,
                        pilih_id = id
                    )
                } else if (_tvType.text == "Expense") {
                    DB.funmainDAO().updateExpense(
                        isi_amount = amount,
                        isi_category = category,
                        isi_title = title,
                        isi_note = note,
                        isi_date = date,
                        isi_location = location,
                        pilih_id = id
                    )
                }
            }

            Log.d("Update", "Data berhasil diperbarui untuk id $id")
            startActivity(Intent(this, HistoryPage::class.java))
        }




        _btnHapus.setOnClickListener {
            val id = intent.getIntExtra("id", -1)

            if (radioIncome.isChecked) {
                CoroutineScope(Dispatchers.IO).async {
                    val incomeToDelete = DB.funmainDAO().getItemIncome(id)
                    DB.funmainDAO().deleteIncome(incomeToDelete)
                }
            } else if (radioExpense.isChecked) {
                CoroutineScope(Dispatchers.IO).async {
                    val expenseToDelete = DB.funmainDAO().getItemExpense(id)
                    DB.funmainDAO().deleteExpense(expenseToDelete)
                }
            }

            // Feedback ke user
            Log.d("Delete", "Data berhasil dihapus untuk id $id")
            startActivity(Intent(this, HistoryPage::class.java))
        }


        _tvBack.setOnClickListener {
            startActivity(Intent(this, HistoryPage::class.java))
        }


        val _btnAddTransaction = findViewById<Button>(R.id.btnAddTransaction)
        _btnAddTransaction.setOnClickListener {
            startActivity(Intent(this, inputCategory::class.java))
        }
    }

}