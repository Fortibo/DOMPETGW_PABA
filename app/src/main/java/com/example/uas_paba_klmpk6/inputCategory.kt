package com.example.uas_paba_klmpk6

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
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
import com.google.android.material.bottomsheet.BottomSheetDialog

class inputCategory : AppCompatActivity() {
    private lateinit var adapterCategory: adapterCategory
    private var arCategoryIncome: MutableList<category> = mutableListOf(
        category(1,"Salary"),
        category(2, "Investing")
    )

    private var arCategoryExpense: MutableList<category> = mutableListOf(
        category(1,"Food"),
        category(2, "Drinks")
    )

    private var arCategoryEmpty: MutableList<category> = mutableListOf(
        category(1,"Salary"),
        category(2, "Investing")
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
//        var _rvCategory = findViewById<RecyclerView>(R.id.categoryRecycler)
//        _rvCategory.layoutManager = LinearLayoutManager(this)
//        _rvCategory.adapter = adapterCategory

        var _btnIncome = findViewById<CardView>(R.id.incomeSelectBtn)
        var _btnExpense = findViewById<CardView>(R.id.expenseSelectBtn)
        _btnIncome.isVisible = false
        _btnExpense.isVisible = false

        var _selectedCategory = findViewById<TextView>(R.id.selectedCategory)
        var _btnChoose = findViewById<CardView>(R.id.chooseBtn)

        _btnChoose.setOnClickListener {
            _btnIncome.isVisible = true
            _btnExpense.isVisible = true
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
            val selectorTitleCard = bottomSheetDialog.findViewById<TextView>(R.id.selectorTitleTv)

            selectorTitleCard?.setText("Expense")
            bottomSheetDialog.show()

        }

        _btnIncome.setOnClickListener {
            Log.d("DEBUG", "Income Categories: $arCategoryIncome")
            adapterCategory.updateData(arCategoryIncome)
            val selectorTitleCard = bottomSheetDialog.findViewById<TextView>(R.id.selectorTitleTv)

            selectorTitleCard?.setText("Income")
            bottomSheetDialog.show()

        }

        adapterCategory.setOnItemClickCallback(
            object :  adapterCategory.OnItemClickCallback {
                override fun nextInput(dtCategory: category) {
                    _btnIncome.isVisible = false
                    _btnExpense.isVisible = false
                    _btnChoose.isVisible = true
                    _selectedCategory.setText(dtCategory.name)
                }
            })
    }
}