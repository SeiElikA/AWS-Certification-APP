package com.SeiElikA.awscertification.View.Questions

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.SeiElikA.awscertification.Data.Question
import com.SeiElikA.awscertification.Data.SelectAns
import com.SeiElikA.awscertification.Fragment.AllQuestionFragment
import com.SeiElikA.awscertification.Fragment.QuestionFragment
import com.SeiElikA.awscertification.Model.QuestionSaveModel
import com.SeiElikA.awscertification.R
import com.SeiElikA.awscertification.View.Exam.EndExamActivity
import com.SeiElikA.awscertification.View.Exam.NotSelectQuestionActivity
import com.SeiElikA.awscertification.View.Exam.QuestionActivity
import com.SeiElikA.awscertification.databinding.ActivityAllQuestionBinding
import com.SeiElikA.awscertification.databinding.ActivityQuestionBinding
import com.SeiElikA.awscertification.formatZero
import com.google.gson.Gson
import kotlin.concurrent.thread

class AllQuestionActivity : AppCompatActivity() {
    private lateinit var control: ActivityAllQuestionBinding
    private var questionIndex = 0;
    private lateinit var model: QuestionSaveModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        control = ActivityAllQuestionBinding.inflate(layoutInflater)
        setContentView(control.root)
        model = QuestionSaveModel(this)

        // get question list id
        val questionId = intent.getIntExtra("question", -1)
        if(questionId == -1) {
            this.finish()
            return
        }


        // decode question json
        val questionList = if(questionId == 1) {
            val jsonFile = resources.openRawResource(R.raw.question1).bufferedReader().readText()
            Gson().fromJson(jsonFile, Array<Question>::class.java).toMutableList()
        } else {
            val jsonFile = resources.openRawResource(R.raw.question2).bufferedReader().readText()
            Gson().fromJson(jsonFile, Array<Question>::class.java).toMutableList()
        }

        // setting question spinner
        control.spQuestion.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, List(questionList.size) { index ->
            "第${index + 1}題"
        }.toList())
        control.spQuestion.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
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
                return AllQuestionFragment.newInstance(questionList[position])
            }
        }

        control.viewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                questionIndex = position
                control.spQuestion.setSelection(questionIndex)

                if(questionId == 1) {
                    model.questionIndex = questionIndex
                } else {
                    model.questionIndex2 = questionIndex
                }
            }
        })

        if(questionId == 1) {
            control.viewPager.setCurrentItem(model.questionIndex, false)
        } else {
            control.viewPager.setCurrentItem(model.questionIndex2, false)
        }


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
    }
}