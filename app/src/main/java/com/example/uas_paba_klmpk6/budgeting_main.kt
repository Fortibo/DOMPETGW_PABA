package com.example.uas_paba_klmpk6

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
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

        val _btnAddBudget = findViewById<ConstraintLayout>(R.id.clBanner)
        _btnAddBudget.setOnClickListener{
            intent = Intent(this@budgeting_main, inputBudget::class.java)
            startActivity(intent)
        }

        val _btnHistory = findViewById<ImageButton>(R.id.btnHistory)
        _btnHistory.setOnClickListener{
            intent = Intent(this@budgeting_main, HistoryPage::class.java)
            startActivity(intent)
        }

        val _btnHome = findViewById<ImageButton>(R.id.btnHome)
        _btnHome.setOnClickListener{
            intent = Intent(this@budgeting_main, MainActivity::class.java)
            startActivity(intent)
        }

        val _btnTemplate = findViewById<ImageButton>(R.id.btnSettings)
        _btnTemplate.setOnClickListener {
            intent = Intent(this@budgeting_main, tampilanTemplate::class.java)
            startActivity(intent)
        }
        val _btnAddCategories = findViewById<ImageButton>(R.id.btnAdd)
        _btnAddCategories.setOnClickListener {
            intent = Intent(this@budgeting_main, inputCategory::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        CoroutineScope(Dispatchers.IO).async {
            val daftarBudget = DB.funBudgetDAO().getAllBudgets()
            adapterBudget.isiBudgetData(daftarBudget)
            Log.d("data ROOM", daftarBudget.toString())
        }
    }
}