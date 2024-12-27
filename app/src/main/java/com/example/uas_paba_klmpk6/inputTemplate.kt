package com.example.uas_paba_klmpk6

import android.app.DatePickerDialog
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uas_paba_klmpk6.database.category
import com.example.uas_paba_klmpk6.database.mainDB
import com.example.uas_paba_klmpk6.database.templateInput
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import java.util.Calendar

class inputTemplate : AppCompatActivity() {
    private lateinit var datePickerDialog: DatePickerDialog
    private lateinit var DB:mainDB
    private var selectedDate:String ?= null
    private lateinit var adapterCategory: adapterCategory

    private var arCategoryIncome: MutableList<category> = mutableListOf(
        category(1, "Income", "Salary"),
        category(2, "Income", "Freelance Work"),
        category(3, "Income", "Investments"),
        category(4, "Income", "Rental Income"),
        category(5, "Income", "Dividends"),
        category(6, "Income", "Interest"),
        category(7, "Income", "Business Profit"),
        category(8, "Income", "Bonuses"),
        category(9, "Income", "Gifts or Grants"),
        category(10, "Income", "Side Hustle"),
    )

    private var arCategoryExpense: MutableList<category> = mutableListOf(
        category(11, "Expense", "Rent"),
        category(12, "Expense", "Utilities"),
        category(13, "Expense", "Groceries"),
        category(14, "Expense", "Transportation"),
        category(15, "Expense", "Entertainment"),
        category(16, "Expense", "Dining Out"),
        category(17, "Expense", "Healthcare"),
        category(18, "Expense", "Education"),
        category(19, "Expense", "Debt Repayment"),
        category(20, "Expense", "Clothing")
    )

    private var arCategoryEmpty: MutableList<category> = mutableListOf(
        category(1,"Income", "Salary"),
        category(2,"Income", "Investing")
    )

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
        adapterCategory = adapterCategory(arCategoryEmpty)


        // Elements
        val _etTitle = findViewById<EditText>(R.id.etName)
        var _btnChoose = findViewById<CardView>(R.id.chooseCategoryBtn)
        var _btnIncome = findViewById<CardView>(R.id.incomeSelectBtn)
        var _btnExpense = findViewById<CardView>(R.id.expenseSelectBtn)
        var _selectedType = findViewById<TextView>(R.id.selectedType)
        var _selectedTypeCard = findViewById<CardView>(R.id.selectedTypeCard)
        var _selectedCategory = findViewById<TextView>(R.id.selectedCategory)
        var _btnDate = findViewById<CardView>(R.id.chooseDateBtn)
        val _etNote = findViewById<EditText>(R.id.etNoteCatatan)
        val _etAmount = findViewById<TextView>(R.id.etAmount)
        val _tvDate = findViewById<TextView>(R.id.selectedDate)
        val _submitBtn = findViewById<Button>(R.id.nextBtn)
        val _todayBtn = findViewById<CardView>(R.id.todayButton)
        val _etReminder = findViewById<EditText>(R.id.etReminder)


        val bottomSheetDialog = BottomSheetDialog(this@inputTemplate)
        val view = LayoutInflater.from(this@inputTemplate).inflate(R.layout.bottom_sheet_category, null)
        bottomSheetDialog.setContentView(view)

        val _rvCategoryBottomSheet = view.findViewById<RecyclerView>(R.id.categoryRecycler)
        _rvCategoryBottomSheet.layoutManager = LinearLayoutManager(this@inputTemplate)
        _rvCategoryBottomSheet.adapter = adapterCategory
        initDatePicker()

        _btnIncome.isVisible = false
        _btnExpense.isVisible = false


        _btnExpense.setOnClickListener {
            Log.d("DEBUG", "Income Categories: $arCategoryExpense")
            adapterCategory.updateData(arCategoryExpense)
            val selectorTitleTv = bottomSheetDialog.findViewById<TextView>(R.id.selectorTitleTv)
            val selectorTitleCard = bottomSheetDialog.findViewById<CardView>(R.id.selectorTitleCard)
            val selectorSubTitle = bottomSheetDialog.findViewById<TextView>(R.id.selectorSubTitleTv)
            _selectedTypeCard.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FF6B6B"))
            _selectedType.text = "Expense"

            _btnExpense.setBackgroundResource(R.drawable.border_selected_expense)
            _btnIncome.setBackgroundResource(R.drawable.border)

            selectorSubTitle?.setText("Expenses Spent")
            selectorTitleCard?.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FF3728"))
            selectorTitleTv?.setText("Expense")
            bottomSheetDialog.show()

        }

        _btnIncome.setOnClickListener {
            Log.d("DEBUG", "Income Categories: $arCategoryIncome")
            adapterCategory.updateData(arCategoryIncome)
            val selectorTitleTv = bottomSheetDialog.findViewById<TextView>(R.id.selectorTitleTv)
            val selectorTitleCard = bottomSheetDialog.findViewById<CardView>(R.id.selectorTitleCard)
            val selectorSubTitle = bottomSheetDialog.findViewById<TextView>(R.id.selectorSubTitleTv)
            _selectedTypeCard.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#C7F9BB"))
            _selectedType.text = "Income"

            _btnExpense.setBackgroundResource(R.drawable.border)
            _btnIncome.setBackgroundResource(R.drawable.border_selected_income)

            selectorSubTitle?.setText("Earned Income")
            selectorTitleCard?.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#4BC355"))
            selectorTitleTv?.setText("Income")
            bottomSheetDialog.show()

        }

        _btnDate.setOnClickListener {
            openDatePicker()
        }

        _btnChoose.setOnClickListener {
            _btnIncome.isVisible = true
            _btnExpense.isVisible = true
            _selectedTypeCard.isVisible = true
            _btnChoose.isVisible = false
        }

        _todayBtn.setOnClickListener {
            val calendar = Calendar.getInstance()
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val month = calendar.get(Calendar.MONTH)
            val year = calendar.get(Calendar.YEAR)

            val date = makeDateString(day, month + 1, year)
            _tvDate.text = date
        }

        adapterCategory.setOnItemClickCallback(
            object :  adapterCategory.OnItemClickCallback {
                override fun pickedInput(dtCategory: category) {
                    _btnIncome.isVisible = false
                    _btnExpense.isVisible = false
                    _btnChoose.isVisible = true
                    _selectedCategory.setText(dtCategory.name)
                }
            })

        var dataTemplate: templateInput
        _submitBtn.setOnClickListener {
            if (_selectedCategory.text != "Choose Category"
                && _tvDate.text != "Select Date"
                && !_etAmount.text.isNullOrEmpty()
                && !_etTitle.text.isNullOrEmpty()
                && !_etNote.text.isNullOrEmpty()){

                dataTemplate = templateInput(type = _selectedType.text.toString(),
                    category = _selectedCategory.text.toString(),
                    title = _etTitle.text.toString(),
                    date =  _tvDate.text.toString(),
                    amount = _etAmount.text.toString().toInt(),
                    note = _etNote.text.toString(),
                    reminder = _etReminder.text.toString().toInt()
                )

                CoroutineScope(Dispatchers.IO).async {
                    DB.funmainDAO().insertTemplate(dataTemplate)
                }

                val intent = Intent(this@inputTemplate, tampilanTemplate::class.java)
                startActivity(intent)
            } else {
                dataTemplate = templateInput(type = _selectedType.text.toString(),
                    category = _selectedCategory.text.toString(),
                    title = _etTitle.text.toString(),
                    date =  _tvDate.text.toString(),
                    amount = _etAmount.text.toString().toInt(),
                    note = _etNote.text.toString(),
                    reminder = _etReminder.text.toString().toInt()
                )
            }

            Log.d("DEBUG", "Income Categories: $dataTemplate")
        }
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
