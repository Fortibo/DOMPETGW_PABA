package com.example.uas_paba_klmpk6

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.uas_paba_klmpk6.database.history
import com.example.uas_paba_klmpk6.helper.DateHelper.getCurrentDate
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DetailTransaction : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail_transaction)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val _tvBack = findViewById<TextView>(R.id.tvBack)
        val _tvNamaTransactionDetail = findViewById<TextView>(R.id.tvNamaTransactionDetail)
        val _tvCategories = findViewById<TextView>(R.id.tvCategories)
        val _tvType = findViewById<TextView>(R.id.tvType)
        val _tvDate = findViewById<TextView>(R.id.tvDate)
        val _tvTime = findViewById<TextView>(R.id.tvTime)
        val _tvNote = findViewById<TextView>(R.id.tvNote)
        val _tvAmount = findViewById<TextView>(R.id.tvAmount)
        val _tvTypeColor = findViewById<TextView>(R.id.tvTypeColor)
        val _ivIconType = findViewById<ImageView>(R.id.ivIconType)


        val title = intent.getStringExtra("title") ?: "Default Title"
        val amount = intent.getStringExtra("amount")
        val category = intent.getStringExtra("category") ?: "Default Category"
        val note = intent.getStringExtra("note") ?: "No Note"
        val type = intent.getStringExtra("type") ?: "Neutral"
        val date = getDate()
        val time = getTime()


        _tvNamaTransactionDetail.text = "  " + title
        _tvCategories.setText(category)
        _tvType.setText(type)
        _tvDate.setText(date)
        _tvTime.setText(time)
        _tvNote.setText(note)
        _tvTypeColor.text = type
        if(_tvType.text == "Expense" || _tvType.text == "expense"){
            _tvType.setText("Expense")
            _tvTypeColor.setText("Expense")
            _tvTypeColor.setBackgroundColor(Color.parseColor("#ff9c7e"))
            _tvAmount.text = "- " + amount
            _tvAmount.setTextColor(Color.parseColor("#ff3728"))
        }else if (_tvType.text == "Income" || _tvType.text == "income"){
            _tvType.setText("Income")
            _tvTypeColor.setText("Income")
            _tvTypeColor.setBackgroundColor(Color.parseColor("#C7F9BB"))
            _tvAmount.text = "+ " + amount
            _tvAmount.setTextColor(Color.parseColor("#4bc355"))
        }

        _tvBack.setOnClickListener {
            startActivity(Intent(this, HistoryPage::class.java))
        }
    }
    fun getDate(): String {
        val currentDate = Date()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(currentDate)
    }

    fun getTime(): String {
        val currentDate = Date()
        val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        return timeFormat.format(currentDate)
    }


}