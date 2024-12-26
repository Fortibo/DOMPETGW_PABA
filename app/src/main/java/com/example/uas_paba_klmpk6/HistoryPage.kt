package com.example.uas_paba_klmpk6

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uas_paba_klmpk6.database.expense
import com.example.uas_paba_klmpk6.database.history
import com.example.uas_paba_klmpk6.database.income
import com.example.uas_paba_klmpk6.database.mainDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import java.text.NumberFormat
import java.util.Locale

class HistoryPage : AppCompatActivity() {
    private lateinit var DB : mainDB


    private lateinit var adapterIncome :adapterIncome
    private lateinit var adapterHistory :adapterAll
    private lateinit var adapterExpense :adapterExpense

    private var arIncome : MutableList<income> = mutableListOf()
    private var arExpense : MutableList<expense> = mutableListOf()
    private var arAll : MutableList<history> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_history_page)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        adapterIncome = adapterIncome(arIncome)
        adapterExpense = adapterExpense(arExpense)
        adapterHistory = adapterAll(arAll)

        val _rvItem= findViewById<RecyclerView>(R.id.rv_item)

        val btIncome = findViewById<ConstraintLayout>(R.id.income)
        val btExpense = findViewById<ConstraintLayout>(R.id.expense)

        val txtNetMoney =  findViewById<TextView>(R.id.netbalanceMoney)
        val txtIncomeMoney =  findViewById<TextView>(R.id.incomeTextMoney)
        val txtExpenseMoney =  findViewById<TextView>(R.id.expenseTextMoney)



        DB = mainDB.getDatabase(this)

        _rvItem.layoutManager = LinearLayoutManager(this)
        _rvItem.adapter = adapterHistory

        btIncome.setOnClickListener {
            _rvItem.adapter = adapterIncome
        }

        btExpense.setOnClickListener {
            _rvItem.adapter = adapterExpense
        }
    }


    override fun onStart() {
        super.onStart()
        CoroutineScope(Dispatchers.IO).async {
            val totalIncome = DB.funmainDAO().getTotalIncome() ?: 0
            val totalExpense = DB.funmainDAO().getTotalExpense() ?: 0
            val rupiahFormat: NumberFormat = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
            rupiahFormat.setMaximumFractionDigits(0) // Menghilangkan desimal


            val formatIncome: String = rupiahFormat.format(totalIncome)
            val formatExpense: String = rupiahFormat.format(totalExpense)
            val net : String =  rupiahFormat.format(totalIncome - totalExpense)

            runOnUiThread {
                findViewById<TextView>(R.id.incomeTextMoney).text = formatIncome
                findViewById<TextView>(R.id.expenseTextMoney).text = formatExpense
                findViewById<TextView>(R.id.netbalanceMoney).text = net
            }
            val daftarIncome = DB.funmainDAO().selectAllIncome()
            val daftarExpense = DB.funmainDAO().selectAllExpense()
            val daftarAll = DB.funmainDAO().getAllHistory()
            adapterIncome.isiData(daftarIncome)
            adapterExpense.isiData(daftarExpense)
            adapterHistory.isiData(daftarAll)
            Log.d("data ROOM", daftarIncome.toString())
            Log.d("data ROOM", daftarExpense.toString())
            Log.d("data ROOM", daftarAll.toString())
        }
    }
}