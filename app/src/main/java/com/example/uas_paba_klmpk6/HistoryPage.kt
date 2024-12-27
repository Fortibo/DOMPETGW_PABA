package com.example.uas_paba_klmpk6

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
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
import com.example.uas_paba_klmpk6.helper.DateHelper.getCurrentDate
import com.google.android.material.floatingactionbutton.FloatingActionButton
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
        siapkanData()

        adapterIncome = adapterIncome(arIncome)
        adapterExpense = adapterExpense(arExpense)
        adapterHistory = adapterAll(arAll)

        DB = mainDB.getDatabase(this)

        val _rvItem= findViewById<RecyclerView>(R.id.rv_item)

        val btIncome = findViewById<ConstraintLayout>(R.id.income)
        val btExpense = findViewById<ConstraintLayout>(R.id.expense)
        val btNetBalance = findViewById<ConstraintLayout>(R.id.netBalance)
        val btHome = findViewById<ImageButton>(R.id.btnHome)
        val _btAdd = findViewById<ImageButton>(R.id.btnAdd)
        val _btnBudget = findViewById<ImageButton>(R.id.btnBudget)
        val _btnMutasi  = findViewById<ImageView>(R.id.mutasiBtn)

        val txtNetMoney =  findViewById<TextView>(R.id.netbalanceMoney)
        val txtIncomeMoney =  findViewById<TextView>(R.id.incomeTextMoney)
        val txtExpenseMoney =  findViewById<TextView>(R.id.expenseTextMoney)
        val txtQueryType = findViewById<TextView>(R.id.queryType)

        val totalIncome = DB.funmainDAO().getTotalIncome() ?: 0
        val totalExpense = DB.funmainDAO().getTotalExpense() ?: 0
        val rupiahFormat: NumberFormat = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
        rupiahFormat.setMaximumFractionDigits(0) // Menghilangkan desimal


        val formatIncome: String = rupiahFormat.format(totalIncome)
        val formatExpense: String = rupiahFormat.format(totalExpense)
        val net : String =  rupiahFormat.format(totalIncome - totalExpense)


        txtNetMoney.setText(net)
        txtIncomeMoney.setText(formatIncome)
        txtExpenseMoney.setText(formatExpense)



        _rvItem.layoutManager = LinearLayoutManager(this)
        _rvItem.adapter = adapterHistory

        btHome.setOnClickListener {
            val inten = Intent(this@HistoryPage,MainActivity::class.java)
            startActivity(inten)
        }

        btIncome.setOnClickListener {
            _rvItem.adapter = adapterIncome
            btIncome.setBackgroundResource(R.drawable.border_selected_income)
            btExpense.setBackgroundResource(R.drawable.border)
            btNetBalance.setBackgroundResource(R.drawable.border)

            txtQueryType.setText("Income")
            txtQueryType.setTextColor(Color.parseColor("#4BC355"))
        }

        btExpense.setOnClickListener {
            _rvItem.adapter = adapterExpense
            btExpense.setBackgroundResource(R.drawable.border_selected_expense)
            btIncome.setBackgroundResource(R.drawable.border)
            btNetBalance.setBackgroundResource(R.drawable.border)

            txtQueryType.setText("Expense(s)")
            txtQueryType.setTextColor(Color.parseColor("#FF3728"))
        }
        btNetBalance.setOnClickListener {
            _rvItem.adapter = adapterHistory
            btNetBalance.setBackgroundResource(R.drawable.border_selected)
            btIncome.setBackgroundResource(R.drawable.border)
            btExpense.setBackgroundResource(R.drawable.border)

            txtQueryType.setText("All")
            txtQueryType.setTextColor(Color.BLACK)
        }
        _btAdd.setOnClickListener {
            val intent = Intent(this@HistoryPage, inputCategory::class.java)
            startActivity(intent)
        }
        _btnBudget.setOnClickListener{
            val intent = Intent(this@HistoryPage, budgeting_main::class.java)
            startActivity(intent)
        }
        _btnMutasi.setOnClickListener {
            val intent = Intent(this@HistoryPage,MutasiPage::class.java)
            startActivity(intent)
        }
    }

    public fun siapkanData(){
        CoroutineScope(Dispatchers.IO).async {

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
    override fun onStart() {
        super.onStart()
        CoroutineScope(Dispatchers.IO).async {

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
//        CoroutineScope(Dispatchers.IO).async {
//            // Contoh Data Baru
//            val newIncome = income( amount = 500000, title = "Gaji Bulanan", category = "Salary", note = "TesIncome", date = getCurrentDate(), location = "TES LOKASI")
//            val newExpense = expense( amount = 200000, title = "Belanja Harian", category = "Food", note = "tesExpense", date = getCurrentDate(), location = "TES LOKASI")
//
//            // Masukkan Data ke Database
//            DB.funmainDAO().insertIncome(newIncome)
//            DB.funmainDAO().insertExpense(newExpense)
//
//            // Log untuk memastikan
//            Log.d("DB_INSERT", "Income Added: $newIncome")
//            Log.d("DB_INSERT", "Expense Added: $newExpense")
//        }
    }
}