package com.example.uas_paba_klmpk6

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uas_paba_klmpk6.database.budgeting
import com.example.uas_paba_klmpk6.database.mainDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class budgeting_main : AppCompatActivity() {
    private lateinit var DB : mainDB

    private lateinit var adapterBudget: adapterBudget
    private var BudgetList : MutableList<budgeting> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_budgeting_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        adapterBudget = adapterBudget(BudgetList)

        DB = mainDB.getDatabase(this)

        val _rvBudget = findViewById<RecyclerView>(R.id.rvBudget)

        _rvBudget.layoutManager = LinearLayoutManager(this)
        _rvBudget.adapter = adapterBudget
    }

    override fun onStart() {
        super.onStart()

//        CoroutineScope(Dispatchers.IO).launch {
//            // Create dummy budgeting data
//            val newBudget1 = budgeting(
//                budget_name = "Monthly Groceries",
//                target_amount = 1000000,
//                saved_amount = 500000,
//                target_date = "2024-12-31",
//                budget_category = "Food",
//                budget_note = "Budget for monthly groceries."
//            )
//
//            val newBudget2 = budgeting(
//                budget_name = "Vacation Fund",
//                target_amount = 5000000,
//                saved_amount = 2000000,
//                target_date = "2025-06-30",
//                budget_category = "Leisure",
//                budget_note = "Saving for a summer vacation."
//            )
//
//            val newBudget3 = budgeting(
//                budget_name = "Emergency Fund",
//                target_amount = 3000000,
//                saved_amount = 1000000,
//                target_date = "2025-12-31",
//                budget_category = "Savings",
//                budget_note = "For unexpected emergency expenses."
//            )
//
//            // Insert the dummy budgets into the database
//            DB.funBudgetDAO().insertBudget(newBudget1)
//            DB.funBudgetDAO().insertBudget(newBudget2)
//            DB.funBudgetDAO().insertBudget(newBudget3)
//
//            // Log the dummy data for confirmation
//            Log.d("DB_INSERT", "Budget Added: $newBudget1")
//            Log.d("DB_INSERT", "Budget Added: $newBudget2")
//            Log.d("DB_INSERT", "Budget Added: $newBudget3")
//        }
        CoroutineScope(Dispatchers.IO).async {
            val daftarBudget = DB.funBudgetDAO().getAllBudgets()
            adapterBudget.isiBudgetData(daftarBudget)
            Log.d("data ROOM", daftarBudget.toString())
        }
    }
}