package com.example.uas_paba_klmpk6

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uas_paba_klmpk6.database.history
import com.example.uas_paba_klmpk6.database.mainDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import java.text.NumberFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var DB: mainDB

    private lateinit var adapterTransactionList: adapterAll
    private var TransactionList: MutableList<history> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        adapterTransactionList = adapterAll(TransactionList)
        DB = mainDB.getDatabase(this)

        val _tvBalance = findViewById<TextView>(R.id.tvBalance)
        val _tvIncome = findViewById<TextView>(R.id.tvIncome)
        val _tvExpense = findViewById<TextView>(R.id.tvExpense)

        val income = DB.funmainDAO().getTotalIncome()
        val expense = DB.funmainDAO().getTotalExpense()
        val rupiahFormat: NumberFormat = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
        rupiahFormat.setMaximumFractionDigits(0)

        val formatIncome = rupiahFormat.format(income)
        val formatExpense = rupiahFormat.format(expense)
        val netBalance: String = rupiahFormat.format(income - expense)

        _tvBalance.setText(netBalance)
        _tvIncome.setText(formatIncome)
        _tvExpense.setText(formatExpense)

        val _rvTransactionListHome = findViewById<RecyclerView>(R.id.rvTransactionListHome)

        _rvTransactionListHome.layoutManager = LinearLayoutManager(this)
        _rvTransactionListHome.adapter = adapterTransactionList


        val _btHistory = findViewById<ImageButton>(R.id.btnHistory)
        val _btAdd = findViewById<ImageButton>(R.id.btnAdd)
        val _btSetting = findViewById<ImageButton>(R.id.btnSettings)

        _btAdd.setOnClickListener {
            val intent = Intent(this@MainActivity, inputCategory::class.java)
            startActivity(intent)
        }

        _btHistory.setOnClickListener {
            val intent = Intent(this@MainActivity, HistoryPage::class.java)
            startActivity(intent)
        }

        _btSetting.setOnClickListener {
            val intent = Intent(this@MainActivity, tampilanTemplate::class.java)
            startActivity(intent)
        }

        val _btnBudget = findViewById<ImageButton>(R.id.btnBudget)
        _btnBudget.setOnClickListener{
            val intent = Intent(this@MainActivity, budgeting_main::class.java)
            startActivity(intent)
        }

    }

    override fun onStart() {
        super.onStart()
        CoroutineScope(Dispatchers.IO).async {
            val daftarTransaction = DB.funmainDAO().getAllHistory()
            adapterTransactionList.isiData(daftarTransaction)
            Log.d("data ROOM", daftarTransaction.toString())
        }
    }
}