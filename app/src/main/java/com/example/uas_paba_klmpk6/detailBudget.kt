package com.example.uas_paba_klmpk6

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
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

        val _bckBtn = findViewById<ImageButton>(R.id.backButton)
        val bt_detail = findViewById<Button>(R.id.btDetailBudget)
        val bt_addFunds = findViewById<LinearLayout>(R.id.constraintAddFunds)
        val bt_withdraw = findViewById<LinearLayout>(R.id.constraintWithdraw)

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


        val bottomSheetDialogAddFunds = BottomSheetDialog(this@detailBudget)
        val viewAddFunds = LayoutInflater.from(this@detailBudget).inflate(R.layout.bottom_sheet_add_funds, null)
        bottomSheetDialogAddFunds.setContentView(viewAddFunds)

        val _afReqFunds = bottomSheetDialogAddFunds.findViewById<TextView>(R.id.addFunds_required_funds)
        val _afColFunds = bottomSheetDialogAddFunds.findViewById<TextView>(R.id.addFunds_myMoney)
        val _afEdtAddFunds = bottomSheetDialogAddFunds.findViewById<EditText>(R.id.addFunds_add_money)
        val _afBtAddFunds = bottomSheetDialogAddFunds.findViewById<Button>(R.id.addFunds_submitBtn)

        val bottomSheetDialogWithdraw = BottomSheetDialog(this@detailBudget)
        val viewWithdraw = LayoutInflater.from(this@detailBudget).inflate(R.layout.bottom_sheet_withdraw, null)
        bottomSheetDialogWithdraw.setContentView(viewWithdraw)

        val _wdReqFunds = bottomSheetDialogWithdraw.findViewById<TextView>(R.id.withdraw_required_funds)
        val _wdColFunds = bottomSheetDialogWithdraw.findViewById<TextView>(R.id.withdraw_myMoney)
        val _wdEdtAddFunds = bottomSheetDialogWithdraw.findViewById<TextView>(R.id.withdraw_add_money)
        val _wdbtWithdraw = bottomSheetDialogWithdraw.findViewById<Button>(R.id.withdraw_submitBtn)

        val rupiahFormat: NumberFormat = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
        rupiahFormat.setMaximumFractionDigits(0) // Menghilangkan desimal


        bt_detail.setOnClickListener {
            bottomSheetDialog.show()
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
        _bckBtn.setOnClickListener {
            finish()
        }

        bt_addFunds.setOnClickListener {
            val reqM : String = rupiahFormat.format(budget.target_amount)
            val myM : String = rupiahFormat.format(budget.saved_amount)

            _afReqFunds?.setText(reqM)
            _afColFunds?.setText(myM)

            bottomSheetDialogAddFunds.show()

        }
        _afBtAddFunds?.setOnClickListener {
            var addFunds = _afEdtAddFunds?.text.toString()
            var fundInt = addFunds.toInt()
            var fundFormat = rupiahFormat.format(fundInt)

            var sisaBudget = budget.target_amount - fundInt
            var uang_sekarang = budget.saved_amount + fundInt
            CoroutineScope(Dispatchers.IO).async {
                DB.funBudgetDAO().updateBudgetFixed(

                    isi_saved_amount = uang_sekarang,
                    pilih_id_budget = Iid
                )
                val item = DB.funBudgetDAO().getBudgetById(Iid)
                if (item != null) {
                    budget = item
                }
                if (item != null) {
                    _detCategory.setText(item.budget_category)
                    _detText.setText(item.budget_name)
                }
            }

        bottomSheetDialogAddFunds.dismiss()
        }
        bt_withdraw.setOnClickListener {
            val reqM : String = rupiahFormat.format(budget.target_amount)
            val myM : String = rupiahFormat.format(budget.saved_amount)

            _wdReqFunds?.setText(reqM)
            _wdColFunds?.setText(myM)

            bottomSheetDialogWithdraw.show()
        }

        _wdbtWithdraw?.setOnClickListener {
            var addFunds = _wdEdtAddFunds?.text.toString()
            var fundInt = addFunds.toInt()
            var fundFormat = rupiahFormat.format(fundInt)

            var sisaBudget = budget.target_amount + fundInt
            var uang_sekarang = budget.saved_amount - fundInt
            CoroutineScope(Dispatchers.IO).async {
                DB.funBudgetDAO().updateBudgetFixed(

                    isi_saved_amount = uang_sekarang,
                    pilih_id_budget = Iid
                )
                val item = DB.funBudgetDAO().getBudgetById(Iid)
                if (item != null) {
                    budget = item
                }
                if (item != null) {
                    _detCategory.setText(item.budget_category)
                    _detText.setText(item.budget_name)
                }
            }

            bottomSheetDialogWithdraw.dismiss()
        }
    }
}