package com.example.uas_paba_klmpk6

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
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
import com.example.uas_paba_klmpk6.database.expense
import com.example.uas_paba_klmpk6.database.income
import com.google.android.material.bottomsheet.BottomSheetDialog

class inputCategory : AppCompatActivity() {
    private lateinit var adapterCategory: adapterCategory
    private var arCategoryIncome: MutableList<category> = mutableListOf(
        category(1,"Income", "Salary"),
        category(2,"Income", "Investing")
    )

    private var arCategoryExpense: MutableList<category> = mutableListOf(
        category(1, "Expsense", "Food"),
        category(2, "Expense","Drinks")
    )

    private var arCategoryEmpty: MutableList<category> = mutableListOf(
        category(1,"Income", "Salary"),
        category(2,"Income", "Investing")
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_input_category)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        adapterCategory = adapterCategory(arCategoryEmpty)

        var _selectedType = findViewById<TextView>(R.id.selectedType)
        var _selectedTypeCard = findViewById<CardView>(R.id.selectedTypeCard)

        _selectedTypeCard.isVisible = false

        var _btnIncome = findViewById<CardView>(R.id.incomeSelectBtn)
        var _btnExpense = findViewById<CardView>(R.id.expenseSelectBtn)
        _btnIncome.isVisible = false
        _btnExpense.isVisible = false

        var _selectedCategory = findViewById<TextView>(R.id.selectedCategory)
        var _btnChoose = findViewById<CardView>(R.id.chooseBtn)

        _btnChoose.setOnClickListener {
            _btnIncome.isVisible = true
            _btnExpense.isVisible = true
            _selectedTypeCard.isVisible = true
            _btnChoose.isVisible = false
        }

        val bottomSheetDialog = BottomSheetDialog(this@inputCategory)
        val view = LayoutInflater.from(this@inputCategory).inflate(R.layout.bottom_sheet_category, null)
        bottomSheetDialog.setContentView(view)

        val _rvCategoryBottomSheet = view.findViewById<RecyclerView>(R.id.categoryRecycler)
        _rvCategoryBottomSheet.layoutManager = LinearLayoutManager(this@inputCategory)
        _rvCategoryBottomSheet.adapter = adapterCategory

        _btnExpense.setOnClickListener {
            Log.d("DEBUG", "Income Categories: $arCategoryExpense")
            adapterCategory.updateData(arCategoryExpense)
            val selectorTitleTv = bottomSheetDialog.findViewById<TextView>(R.id.selectorTitleTv)
            val selectorTitleCard = bottomSheetDialog.findViewById<CardView>(R.id.selectorTitleCard)
            val selectorSubTitle = bottomSheetDialog.findViewById<TextView>(R.id.selectorSubTitleTv)
            _selectedTypeCard.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FF6B6B"))
            _selectedType.text = "Expense"

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

            selectorSubTitle?.setText("Earned Income")
            selectorTitleCard?.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#4BC355"))
            selectorTitleTv?.setText("Income")
            bottomSheetDialog.show()

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

        var _btnNext = findViewById<Button>(R.id.nextBtn)
        _btnNext.setOnClickListener {
            val intents: Intent
            if (_selectedCategory.text != "Choose Category"){
                var name = ""
                if (_selectedType.text == "Income"){
                    val dataIncome = income(category = _selectedCategory.text.toString())
                    name = "incomeData"

                    intents = Intent(this@inputCategory, inputAmount::class.java).apply {
                        putExtra(name, dataIncome)
                    }
                    startActivity(intents)
                } else if(_selectedType.text == "Expense"){
                    val dataExpense = expense(category = _selectedCategory.text.toString())
                    name = "expenseData"
                    intents = Intent(this@inputCategory, inputAmount::class.java).apply {
                        putExtra(name, dataExpense)
                    }
                    startActivity(intents)
                }
            }
        }
    }
}