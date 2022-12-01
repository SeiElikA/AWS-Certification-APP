package com.SeiElikA.awscertification.View.Exam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.SeiElikA.awscertification.Adapter.NotSelectQuestionAdapter
import com.SeiElikA.awscertification.Data.SelectAns
import com.SeiElikA.awscertification.databinding.ActivityNotSelectQuestionBinding
import java.util.Map

class NotSelectQuestionActivity : AppCompatActivity() {
    private lateinit var control: ActivityNotSelectQuestionBinding
    private var questionMap = mutableMapOf<Int, String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        control = ActivityNotSelectQuestionBinding.inflate(layoutInflater)
        setContentView(control.root)

        val tempList = intent.getSerializableExtra("dataList") as Array<SelectAns>
        val ansList = tempList.toList()

        ansList.filter { x->x.selectAns.isEmpty() }.forEach { x->
            val index = ansList.indexOf(x)
            val title = "第${index + 1}題"
            questionMap[index] = title
        }

        val adapter = NotSelectQuestionAdapter(this, questionMap)
        control.listView.adapter = adapter
        control.listView.setOnItemClickListener { parent, view, position, id ->
            val data = adapter.getItem(position) as Map.Entry<Int, String>
            setResult(RESULT_OK, Intent().putExtra("index", data.key.toInt()))
            this.finishAfterTransition()
        }

        control.btnClose.setOnClickListener {
            this.finishAfterTransition()
        }
    }
}