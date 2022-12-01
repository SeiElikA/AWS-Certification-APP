package com.SeiElikA.awscertification.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.SeiElikA.awscertification.View.Exam.QuestionActivity
import com.SeiElikA.awscertification.View.Questions.AllQuestionActivity
import com.SeiElikA.awscertification.databinding.AdapterQuestionItemBinding

class QuestionAdapter(var context: Context, var type: Int): BaseAdapter() {
    override fun getCount(): Int {
        return 2
    }

    override fun getItem(p0: Int): Any {
        return p0
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val control = AdapterQuestionItemBinding.inflate(LayoutInflater.from(context), p2, false)
        var questionList = listOf("1. Cloud Practitioner", "2. Solutions Architect Associate")
        control.txtTitle.text = questionList[p0]

        if(type == 0) {
            control.root.setOnClickListener {
                context.startActivity(Intent(context, QuestionActivity::class.java).putExtra("question", (p0 + 1)))
            }
        } else {
            control.root.setOnClickListener {
                context.startActivity(Intent(context, AllQuestionActivity::class.java).putExtra("question", (p0 + 1)))
            }
        }

        return control.root
    }
}