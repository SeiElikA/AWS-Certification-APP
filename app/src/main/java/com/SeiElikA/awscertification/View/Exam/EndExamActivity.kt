package com.SeiElikA.awscertification.View.Exam

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.SeiElikA.awscertification.Adapter.AnsResultAdapter
import com.SeiElikA.awscertification.Data.SelectAns
import com.SeiElikA.awscertification.databinding.ActivityEndExamBinding

class EndExamActivity : AppCompatActivity() {
    private lateinit var control: ActivityEndExamBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        control = ActivityEndExamBinding.inflate(layoutInflater)
        setContentView(control.root)

        var ansList = (intent.getSerializableExtra("dataList") as Array<SelectAns>).toMutableList()
        val correctCount = ansList.count {x -> x.selectAns.joinToString(",") == x.question.correctAns}
        control.txtCorrect.text = "答對題數： ${correctCount}/${ansList.size}"
        if(correctCount < ansList.size / 2) {
            control.txtCorrect.setTextColor(Color.RED)
        }
        ansList = ansList.filter { x->x.selectAns.joinToString(",") != x.question.correctAns }.toMutableList()

        control.listView.adapter = AnsResultAdapter(this, ansList)
    }
}