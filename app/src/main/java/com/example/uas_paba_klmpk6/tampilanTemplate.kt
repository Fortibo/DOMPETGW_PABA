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
import com.example.uas_paba_klmpk6.database.mainDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class tampilanTemplate : AppCompatActivity() {
    private lateinit var DB : mainDB
    //private lateinit var adapterTemplate : (....)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tampilan_template)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        DB = mainDB.getDatabase(this)


        super.onStart()
        CoroutineScope(Dispatchers.Main).async {
            //val daftarTemplate = DB.funmainDAO().(....)
            //ini fungsi querrynya eric yang select all template
            //Log.d("data ROOM", daftarTemplate.toString())
        }

        val _btnBackfromTemp = findViewById<ImageButton>(R.id.btnBackFromTemp)
        _btnBackfromTemp.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        val _btnAddTemplate = findViewById<Button>(R.id.btnAddTemplate)
        _btnAddTemplate.setOnClickListener {
            //startActivity(Intent(this, (....)::class.java))
        }
    }
}