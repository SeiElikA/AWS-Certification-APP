package com.SeiElikA.awscertification.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.SeiElikA.awscertification.Data.SelectAns
import com.SeiElikA.awscertification.databinding.AdapterNotSelectQuestionBinding

class NotSelectQuestionAdapter(var context: Context, var ansList: Map<Int, String>): BaseAdapter() {
    override fun getCount(): Int {
        return ansList.count()
    }

    override fun getItem(position: Int): Any {
        return ansList.entries.toList()[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val control = AdapterNotSelectQuestionBinding.inflate(LayoutInflater.from(context), parent, false)
        val data = ansList.entries.toList()[position]
        control.txt.text = data.value
        return control.root
    }
}