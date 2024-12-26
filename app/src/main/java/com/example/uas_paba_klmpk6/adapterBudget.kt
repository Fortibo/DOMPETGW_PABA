package com.example.uas_paba_klmpk6

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.uas_paba_klmpk6.database.budgeting
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale

class adapterBudget (private val budgetData : MutableList<budgeting>) :RecyclerView.Adapter<adapterBudget.ListViewHolder>() {

    class ListViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        var _tvBudgetName = itemView.findViewById<TextView>(R.id.tvBudgetName)
        var _tvCurrentAmount = itemView.findViewById<TextView>(R.id.tvCurrentAmount)
        var _tvTargetAmount = itemView.findViewById<TextView>(R.id.tvTargetAmount)
        var _tvTimeRemaining = itemView.findViewById<TextView>(R.id.tvTimeRemaining)
        var _tvTargetDate = itemView.findViewById<TextView>(R.id.tvTargetDate)
        var _tvBudgetNote = itemView.findViewById<TextView>(R.id.tvBudgetNote)
        val _tvBudgetCategory = itemView.findViewById<TextView>(R.id.tvBudgetCategory)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(
            R.layout.budget_recycler, parent, false
        )
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: adapterBudget.ListViewHolder, position: Int) {
        var budget = budgetData[position]
        val rupiahFormat = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
        rupiahFormat.setMaximumFractionDigits(0)
        holder._tvBudgetName.setText(budget.budget_name)
        holder._tvCurrentAmount.text = rupiahFormat.format(budget.saved_amount)
        holder._tvTargetAmount.text = rupiahFormat.format(budget.target_amount)
        holder._tvTargetDate.setText(budget.target_date)
        holder._tvBudgetNote.setText(budget.budget_note)
        holder._tvBudgetCategory.setText(budget.budget_category)

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
                    holder._tvTimeRemaining.text = "$daysRemaining days remaining"
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun getItemCount(): Int {
        return budgetData.size
    }
    fun isiBudgetData(newBudgetData: MutableList<budgeting>) {
        budgetData.clear()
        budgetData.addAll(newBudgetData)
        notifyDataSetChanged()
    }

}