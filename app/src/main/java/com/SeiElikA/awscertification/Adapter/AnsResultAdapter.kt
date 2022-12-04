package com.SeiElikA.awscertification.Adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.core.view.isGone
import com.SeiElikA.awscertification.Data.SelectAns
import com.SeiElikA.awscertification.databinding.AdapterAnsResultBinding
import com.SeiElikA.awscertification.removeHtml

class AnsResultAdapter(var context: Context, var list: List<SelectAns>): BaseAdapter() {
    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Any {
        return list.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val control = AdapterAnsResultBinding.inflate(LayoutInflater.from(context), parent, false)
        val data = list[position]
        data.question.let {
            var selectAns = data.selectAns
            val ans = it.correctAns?.split(",")?.toList() ?: listOf()

            control.txtTitle.text = it.body?.removeHtml()
            control.rbA.text = it.choices?.A?.removeHtml()
            control.rbB.text = it.choices?.B?.removeHtml()
            control.rbC.text = it.choices?.C?.removeHtml()
            control.rbD.text = it.choices?.D?.removeHtml()

            if(it.choices?.E != null) {
                control.rbE.isGone = false
                control.rbE.text = it.choices?.E?.removeHtml()
            }

            val questionList = listOf(control.rbA,control.rbB,control.rbC, control.rbD, control.rbE)

            control.txtMode.text = if(it.type == "1") "(單選題)" else "(多選題)"
            questionList.forEach { cb ->
                val correct = cb.tag.toString()
                if(selectAns.contains(correct)) {
                    cb.setBackgroundColor(Color.parseColor("#FF8A80")) // red
                    cb.isChecked = true
                }

                if(ans.contains(correct)) {
                    cb.setBackgroundColor(Color.parseColor("#69F0AE")) // green
                }

                cb.isClickable = false
            }
        }
        if(data.question.ansDesc?.replace(" ", "") != "") {
            data.question.ansDesc?.let {
                control.txtAnsDes.isGone =false
                control.txtAnsDes.text = "題目解析：\n$it"
            }

        }

        return control.root
    }
}