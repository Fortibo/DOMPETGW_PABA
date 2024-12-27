package com.example.uas_paba_klmpk6

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uas_paba_klmpk6.database.mainDB
import com.example.uas_paba_klmpk6.database.templateInput
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class tampilanTemplate : AppCompatActivity() {
    private lateinit var DB : mainDB
    private lateinit var adapterTemplate: adapterTemplate
    private var TemplateList : MutableList<templateInput> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tampilan_template)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        adapterTemplate = adapterTemplate(TemplateList)

        DB = mainDB.getDatabase(this)

        val _rvTemplate = findViewById<RecyclerView>(R.id.rvTemplate)

        _rvTemplate.layoutManager = LinearLayoutManager(this)
        _rvTemplate.adapter = adapterTemplate

        val _btnBackfromTemp = findViewById<ImageButton>(R.id.btnBackFromTemp)
        _btnBackfromTemp.setOnClickListener {
           onBackPressedDispatcher.onBackPressed()
        }

        val _btnAddTemplate = findViewById<Button>(R.id.btnAddTemplate)
        _btnAddTemplate.setOnClickListener {
            intent = Intent(this@tampilanTemplate, inputTemplate::class.java)
            startActivity(intent)
        }

        //Pindah Pindah NAV
        val _btnBudget = findViewById<ImageButton>(R.id.btnBudget)
        _btnBudget.setOnClickListener{
            intent = Intent(this@tampilanTemplate, budgeting_main::class.java)
            startActivity(intent)
        }

        val _btnHome = findViewById<ImageButton>(R.id.btnHome)
        _btnHome.setOnClickListener{
            intent = Intent(this@tampilanTemplate, MainActivity::class.java)
            startActivity(intent)
        }

        val _btnHistory = findViewById<ImageButton>(R.id.btnHistory)
        _btnHistory.setOnClickListener{
            intent = Intent(this@tampilanTemplate, HistoryPage::class.java)
            startActivity(intent)
        }

        val _btnAdd = findViewById<ImageButton>(R.id.btnAdd)
        _btnAdd.setOnClickListener{
            intent = Intent(this@tampilanTemplate, inputCategory::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        CoroutineScope(Dispatchers.IO).async {
            val daftarTemplate = DB.funmainDAO().selectAllTemplate()
            adapterTemplate.isiTemplateData(daftarTemplate)
            Log.d("data ROOM", daftarTemplate.toString())
        }
    }
}