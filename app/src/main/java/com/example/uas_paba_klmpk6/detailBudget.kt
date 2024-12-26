package com.example.uas_paba_klmpk6

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.uas_paba_klmpk6.database.budgeting
import com.example.uas_paba_klmpk6.database.mainDB
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale

class detailBudget : AppCompatActivity() {
    private lateinit var budget : budgeting
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail_budget)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var Iid : Int = 0
        var DB = mainDB.getDatabase(this)
        Iid = intent.getIntExtra("id",-1)

        var _detIcon = findViewById<ImageView>(R.id.budgetDetailIcon)
        val _detCategory = findViewById<TextView>(R.id.budgetDetailCategory)
        val _detText = findViewById<TextView>(R.id.budgetDetailText)

        val bt_detail = findViewById<Button>(R.id.btDetailBudget)

        if(Iid != -1) {
            CoroutineScope(Dispatchers.IO).async {
                val item = DB.funBudgetDAO().getBudgetById(Iid)
                if (item != null) {
                    budget = item
                }
                if (item != null) {
                    _detCategory.setText(item.budget_category)
                    _detText.setText(item.budget_name)
                }
            }
        }
        val bottomSheetDialog = BottomSheetDialog(this@detailBudget)
        val view = LayoutInflater.from(this@detailBudget).inflate(R.layout.bottom_sheet_budget_detail, null)
        bottomSheetDialog.setContentView(view)

        val _bsReqFunds = bottomSheetDialog.findViewById<TextView>(R.id.details_required_funds)
        val _bsCategory = bottomSheetDialog.findViewById<TextView>(R.id.details_category)
        val _bsCollectedFunds = bottomSheetDialog.findViewById<TextView>(R.id.details_collected_funds)
        val _bsAchievementDate = bottomSheetDialog.findViewById<TextView>(R.id.details_achievement_date)
        val _bsRemainingTime = bottomSheetDialog.findViewById<TextView>(R.id.details_remaining_time)

        val rupiahFormat: NumberFormat = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
        rupiahFormat.setMaximumFractionDigits(0) // Menghilangkan desimal


        bt_detail.setOnClickListener {
            val reqBudget: String = rupiahFormat.format(budget.target_amount)
            _bsReqFunds?.setText(reqBudget)
            _bsCategory?.setText(budget.budget_category)

            val colFunds: String = rupiahFormat.format(budget.saved_amount)
            _bsCollectedFunds?.setText(colFunds)

            _bsAchievementDate?.setText(budget.target_date)
            val targetDate = budget.target_date
            if (!targetDate.isNullOrEmpty()) {
                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()) // Adjust format as per your date format
                try {
                    val targetDateParsed = dateFormat.parse(targetDate)
                    val currentTime = System.currentTimeMillis()
                    if (targetDateParsed != null) {
                        val timeDifference = targetDateParsed.time - currentTime // Difference in milliseconds
                        val daysRemaining = timeDifference / (1000 * 60 * 60 * 24) // Convert milliseconds to days

                        // Set the time remaining on the TextView
                        _bsRemainingTime?.setText("$daysRemaining days remaining")
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}