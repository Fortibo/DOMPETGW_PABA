package com.example.uas_paba_klmpk6

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.uas_paba_klmpk6.database.expense
import com.example.uas_paba_klmpk6.database.history
import java.text.NumberFormat
import java.util.Locale


class adapterMutasiExpense (private val expenseData : MutableList<expense>) : RecyclerView.Adapter<adapterMutasiExpense.ListViewHolder> () {
    class ListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var tvExpenseTglMutasi =itemView.findViewById<TextView>(R.id.rvTanggalMutasi)
        var tvExpenseTglHariMutasi =itemView.findViewById<TextView>(R.id.rvTanggalHariMutasi)
        var tvExpenseIdMutasi =itemView.findViewById<TextView>(R.id.rvIdMutasi)
        var tvExpenseTipeTransaksiMutasi =itemView.findViewById<TextView>(R.id.rvTipeTransaksiMutasi)
        var tvExpensesetDBCB =itemView.findViewById<TextView>(R.id.setDBCB)
        var tvExpenseMoneyMutasi =itemView.findViewById<TextView>(R.id.rvMoneyMutasi)
        var tvExpenseJudulMutasi =itemView.findViewById<TextView>(R.id.rvJudulMutasi)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(
            R.layout.item_list_mutation,parent,false
        )
        return adapterMutasiExpense.ListViewHolder(view)
    }

    override fun getItemCount(): Int {
       return expenseData.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        var expense = expenseData[position]


        val rupiahFormat: NumberFormat = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
        rupiahFormat.setMaximumFractionDigits(0) // Menghilangkan desimal
        val formattedAmount: String = rupiahFormat.format(expense.amount)


        holder.tvExpenseIdMutasi.setText(position.toString())
        holder.tvExpenseTglMutasi.setText(expense.date)
        holder.tvExpenseTglHariMutasi.setText(expense.date)
        holder.tvExpenseTipeTransaksiMutasi.setText("Expense")

        holder.tvExpensesetDBCB.setText("DB")
        holder.tvExpensesetDBCB.setTextColor(Color.parseColor("#FF3728"))

        holder.tvExpenseMoneyMutasi.setText(formattedAmount)
        holder.tvExpenseMoneyMutasi.setTextColor(Color.parseColor("#FF3728"))

        holder.tvExpenseJudulMutasi.setText(position)


    }
    fun isiData(newExpense: List<expense>){
        expenseData.clear()
        expenseData.addAll(newExpense)
        notifyDataSetChanged()
    }

}