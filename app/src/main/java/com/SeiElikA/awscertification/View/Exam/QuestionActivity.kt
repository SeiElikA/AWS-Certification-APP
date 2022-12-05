package com.SeiElikA.awscertification.View.Exam

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.SeiElikA.awscertification.Data.Question
import com.SeiElikA.awscertification.Data.SelectAns
import com.SeiElikA.awscertification.View.Fragment.QuestionFragment
import com.SeiElikA.awscertification.R
import com.SeiElikA.awscertification.databinding.ActivityQuestionBinding
import com.SeiElikA.awscertification.formatZero
import com.google.gson.Gson
import java.lang.Thread.sleep
import kotlin.concurrent.thread

class QuestionActivity : AppCompatActivity() {
    private lateinit var control: ActivityQuestionBinding
    private var time = 0
    private var questionIndex = 0;

    companion object {
        var ansList = mutableListOf<SelectAns>()
    }

    override fun onBackPressed() {
        val alertDialog = AlertDialog.Builder(this)
            .setTitle("離開考試")
            .setMessage("確認要離開考試嗎")
            .setNegativeButton("Cancel") {_,_ ->}
            .setPositiveButton("Confirm") { _, _ ->
                ansList.clear()
                super.onBackPressed()
            }
            .create()
        alertDialog.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        control = ActivityQuestionBinding.inflate(layoutInflater)
        setContentView(control.root)

        // get question list id
        val questionId = intent.getIntExtra("question", -1)
        if(questionId == -1) {
            this.finish()
            return
        }

        // start count down
        thread {
            while(true) {
                time += 1
                val hour = time / 60 / 60
                val minute = time / 60 - hour * 60
                val second = time - hour * 60 * 60 - minute * 60

                runOnUiThread {
                    if(hour == 0) {
                        control.txtCountDown.text = "經過時間: ${minute.formatZero()}:${second.formatZero()}"
                    } else {
                        control.txtCountDown.text = "經過時間: ${hour.formatZero()}:${minute.formatZero()}:${second.formatZero()}"
                    }
                }

                sleep(1000)
            }
        }

        // decode question json
        var questionList = if(questionId == 1) {
            val jsonFile = resources.openRawResource(R.raw.question1).bufferedReader().readText()
            Gson().fromJson(jsonFile, Array<Question>::class.java).toMutableList()
        } else {
            val jsonFile = resources.openRawResource(R.raw.question2).bufferedReader().readText()
            Gson().fromJson(jsonFile, Array<Question>::class.java).toMutableList()
        }

        // random question
        val tempList = mutableListOf<Question>()
        while(questionList.isNotEmpty()) {
            val question = questionList.random()
            tempList.add(question)
            questionList.remove(question)
        }
        questionList = tempList.subList(0, 65)
        for (question in questionList) {
            ansList.add(SelectAns(question, listOf()))
        }

        // setting question spinner
        control.spQuestion.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, List(questionList.size) { index ->
            "第${index + 1}題"
        }.toList())

        control.spQuestion.onItemSelectedListener = object: OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                questionIndex = position
                control.viewPager.currentItem = questionIndex
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        // setting page
        control.viewPager.adapter = object: FragmentStateAdapter(this) {
            override fun getItemCount(): Int {
                return questionList.size
            }

            override fun createFragment(position: Int): Fragment {
                return QuestionFragment.newInstance(questionList[position])
            }
        }

        control.viewPager.registerOnPageChangeCallback(object: OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                questionIndex = position
                control.spQuestion.setSelection(questionIndex)
            }
        })

        // next and before button
        control.btnBefore.setOnClickListener {
            if(questionIndex == 0) {
                return@setOnClickListener
            }
            questionIndex -= 1
            control.viewPager.currentItem = questionIndex
        }

        control.btnNext.setOnClickListener {
            if(questionIndex == questionList.size - 1) {
                return@setOnClickListener
            }
            questionIndex += 1
            control.viewPager.currentItem = questionIndex
        }

        // set end exam button
        control.btnEndExam.setOnClickListener {
            fun endExam() {
                startActivity(Intent(this, EndExamActivity::class.java).putExtra("dataList", ansList.toTypedArray()))
                ansList.clear()
                this.finish()
            }

            if(ansList.count { x->x.selectAns.isNotEmpty() } != questionList.size) {
                val alertDialog = AlertDialog.Builder(this)
                    .setTitle("結束考試")
                    .setMessage("你還有${questionList.size - ansList.count { x->x.selectAns.isNotEmpty() }}題沒有做完，確認要結束考試嗎")
                    .setNegativeButton("Cancel") {_,_ ->}
                    .setPositiveButton("Confirm") { _, _ ->
                        endExam()
                    }
                    .create()
                alertDialog.show()
                return@setOnClickListener
            }

            val alertDialog = AlertDialog.Builder(this)
                .setTitle("結束考試")
                .setMessage("恭喜全部做完了！ 確認結束考試嗎")
                .setNegativeButton("Cancel") {_,_ ->}
                .setPositiveButton("Confirm") { _, _ ->
                    endExam()
                }
                .create()
            alertDialog.show()
        }

        // check not select question
        control.btnLessQuestion.setOnClickListener {
            selectQuestionActivityForResult.launch(Intent(this, NotSelectQuestionActivity::class.java)
                .putExtra("dataList", ansList.toTypedArray())
            )
        }
    }

    private var selectQuestionActivityForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        val index = it.data?.getIntExtra("index", 0) ?: 0
        control.viewPager.currentItem = index
    }
}