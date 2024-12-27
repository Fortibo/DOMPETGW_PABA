package com.example.uas_paba_klmpk6

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.uas_paba_klmpk6.database.templateInput
import java.text.NumberFormat
import java.util.Locale

class adapterTemplate(private val templateData: MutableList<templateInput>) : RecyclerView.Adapter<adapterTemplate.ListViewHolder> () {

    class ListViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        var _tvTemplateName = itemView.findViewById<TextView>(R.id.tvTemplateName)
        var _tvTemplateType = itemView.findViewById<TextView>(R.id.tvTemplateType)
        var _tvTemplateAmount = itemView.findViewById<TextView>(R.id.tvTemplateAmount)
        var _tvTemplateNote = itemView.findViewById<TextView>(R.id.tvTemplateNote)
        val _tvTemplateDate = itemView.findViewById<TextView>(R.id.tvTemplateDate)
        var _tvTemplateCategory = itemView.findViewById<TextView>(R.id.tvTemplateCategory)

        var _clTemplateCard = itemView.findViewById<ConstraintLayout>(R.id.clTemplateCard)

        var _tvRemind = itemView.findViewById<TextView>(R.id.tvRemind)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(
            R.layout.item_template_recycler, parent, false
        )
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return templateData.size
    }
    fun isiTemplateData(newTemplateData: MutableList<templateInput>){
        templateData.clear()
        templateData.addAll(newTemplateData)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: adapterTemplate.ListViewHolder, position: Int) {
        var template = templateData[position]
        val rupiahFormat = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
        rupiahFormat.setMaximumFractionDigits(0)

        holder._tvTemplateName.setText(template.title)
        holder._tvTemplateType.setText(template.type)
        holder._tvTemplateAmount.text = rupiahFormat.format(template.amount)
        holder._tvTemplateNote.setText(template.note)
        holder._tvTemplateDate.setText(template.date)
        holder._tvTemplateCategory.setText(template.category)
        holder._tvRemind.setText("${template.reminder} days")


        holder._clTemplateCard.setOnClickListener{
            val intent = Intent(it.context, detailTemplate::class.java)
            intent.putExtra("dataTemplate", template)
            it.context.startActivity(intent)
            notifyDataSetChanged()
        }
    }


}