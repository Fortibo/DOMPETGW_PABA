package com.example.uas_paba_klmpk6

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.uas_paba_klmpk6.database.expense
import com.example.uas_paba_klmpk6.database.history
import com.example.uas_paba_klmpk6.database.income
import java.text.NumberFormat
import java.util.Locale

class adapterMutasiIncome(private val incomeData : MutableList<income>) : RecyclerView.Adapter<adapterMutasiIncome.ListViewHolder> () {
    class ListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var tvIncomeTglMutasi =itemView.findViewById<TextView>(R.id.rvTanggalMutasi)
        var tvIncomeTglHariMutasi =itemView.findViewById<TextView>(R.id.rvTanggalHariMutasi)
        var tvIncomeIdMutasi =itemView.findViewById<TextView>(R.id.rvIdMutasi)
        var tvIncomeTipeTransaksiMutasi =itemView.findViewById<TextView>(R.id.rvTipeTransaksiMutasi)
        var tvIncomesetDBCB =itemView.findViewById<TextView>(R.id.setDBCB)
        var tvIncomeMoneyMutasi =itemView.findViewById<TextView>(R.id.rvMoneyMutasi)
        var tvIncomeJudulMutasi =itemView.findViewById<TextView>(R.id.rvJudulMutasi)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(
            R.layout.item_list_mutation,parent,false
        )
        return adapterMutasiIncome.ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return incomeData.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        var income = incomeData[position]
        val rupiahFormat: NumberFormat = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
        rupiahFormat.setMaximumFractionDigits(0) // Menghilangkan desimal
        val formattedAmount: String = rupiahFormat.format(income.amount)
        holder.tvIncomeTglMutasi.setText(income.date)
        holder.tvIncomeTglHariMutasi.setText(income.date)
        holder.tvIncomeIdMutasi.setText(position.toString())

        holder.tvIncomeTipeTransaksiMutasi.setText("Income")

        holder.tvIncomesetDBCB.setText("CR")
        holder.tvIncomesetDBCB.setTextColor(Color.parseColor("#4BC355"))

        holder.tvIncomeMoneyMutasi.setText(formattedAmount)
        holder.tvIncomeMoneyMutasi.setTextColor(Color.parseColor("#4BC355"))

        holder.tvIncomeJudulMutasi.setText(income.title)
    }
    fun isiData(incomeNew : List<income>){
        incomeData.clear()
        incomeData.addAll(incomeNew)
        notifyDataSetChanged()
    }
}