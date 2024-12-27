package com.example.uas_paba_klmpk6

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uas_paba_klmpk6.database.expense
import com.example.uas_paba_klmpk6.database.history
import com.example.uas_paba_klmpk6.database.income

class DetailMutasiPage : AppCompatActivity() {
    private lateinit var adapterIncomeMutasi :adapterMutasiIncome
    private lateinit var adapterHistoryMutasi  :adapterMutasiAll
    private lateinit var adapterExpenseMutasi  :adapterMutasiExpense

    private var arIncome : MutableList<income> = mutableListOf()
    private var arExpense : MutableList<expense> = mutableListOf()
    private var arAll : MutableList<history> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail_mutasi_page)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val txtrangeTanggal = findViewById<TextView>(R.id.periodeDetailMutasi)
        val txtDetailTransaksi = findViewById<TextView>(R.id.jenisTransaksiDetailMutasi)

        val rvMutasi = findViewById<RecyclerView>(R.id.rvMutasi)
        val type = intent.getStringExtra("TYPE")
        val fromDate = intent.getStringExtra("datefrom")
        val toDate = intent.getStringExtra("dateto")

        txtrangeTanggal.setText("$fromDate - $toDate")
        txtDetailTransaksi.setText(type)

        adapterIncomeMutasi = adapterMutasiIncome(arIncome)
        adapterHistoryMutasi = adapterMutasiAll(arAll)
        adapterExpenseMutasi = adapterMutasiExpense(arExpense)

        rvMutasi.layoutManager = LinearLayoutManager(this)

        when (type) {
            "all" -> {
                val allData = intent.getParcelableArrayListExtra<history>("DATA")
                if (allData != null) {
                    adapterHistoryMutasi.isiData(allData)
                }
                rvMutasi.adapter = adapterHistoryMutasi
            }
            "income" -> {
                val incomeData = intent.getParcelableArrayListExtra<income>("DATA")
                if (incomeData != null) {
                    adapterIncomeMutasi.isiData(incomeData)
                }
                rvMutasi.adapter = adapterIncomeMutasi
            }
            "expense" -> {
                val expenseData = intent.getParcelableArrayListExtra<expense>("DATA")
                if (expenseData != null) {
                    adapterExpenseMutasi.isiData(expenseData)
                }
                rvMutasi.adapter = adapterExpenseMutasi
            }
        }
    }
}