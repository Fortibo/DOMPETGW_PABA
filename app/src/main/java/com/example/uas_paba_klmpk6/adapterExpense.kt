package com.example.uas_paba_klmpk6

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.uas_paba_klmpk6.database.expense
import com.example.uas_paba_klmpk6.database.income
import java.text.NumberFormat
import java.util.Locale

class adapterExpense (private val expenseData : MutableList<expense>)  : RecyclerView.Adapter<adapterExpense.ListViewHolder> () {
    private lateinit var onItemClickCallback : OnItemClickCallback

    class OnItemClickCallback {

    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): adapterExpense.ListViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(
            R.layout.expense_item_list,parent,false
        )
        return ListViewHolder(view)
    }

    class ListViewHolder (itemView: View): RecyclerView.ViewHolder(itemView)  {
        var tvExpenseType =itemView.findViewById<TextView>(R.id.expenseType)
        var tvExpenseTitle =itemView.findViewById<TextView>(R.id.expenseTittle)
        var tvExpenseDate =itemView.findViewById<TextView>(R.id.expenseDate)
        var tvExpenseMoney =itemView.findViewById<TextView>(R.id.expenseMoney)
        var tvExpenseItem =itemView.findViewById<ImageView>(R.id.iconType)
        var tvExpenseContainer = itemView.findViewById<ConstraintLayout>(R.id.expenseContainer)
    }

    override fun onBindViewHolder(holder: adapterExpense.ListViewHolder, position: Int) {
        var expense = expenseData[position]
        holder.tvExpenseType.setText(expense.category)
        holder.tvExpenseTitle.setText(expense.title)
        holder.tvExpenseDate.setText(expense.date)

        val rupiahFormat: NumberFormat = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
        rupiahFormat.setMaximumFractionDigits(0) // Menghilangkan desimal
        val formattedAmount: String = rupiahFormat.format(expense.amount)
        val moneyExpense = "-$formattedAmount"

        holder.tvExpenseMoney.setText(moneyExpense)


    }

    override fun getItemCount(): Int {
        return expenseData.size
    }
    fun isiData(expense : List<expense>){
        expenseData.clear()
        expenseData.addAll(expense)
        notifyDataSetChanged()
    }
}