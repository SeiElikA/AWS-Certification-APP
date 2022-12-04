package com.SeiElikA.awscertification.View.Fragment

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import com.SeiElikA.awscertification.Data.Question
import com.SeiElikA.awscertification.R
import com.SeiElikA.awscertification.View.Exam.QuestionActivity
import com.SeiElikA.awscertification.databinding.FragmentAllQuestionBinding
import com.SeiElikA.awscertification.databinding.FragmentQuestionBinding
import com.SeiElikA.awscertification.removeHtml

class AllQuestionFragment : Fragment() {
    private lateinit var control: FragmentAllQuestionBinding
    private var question: Question? = null
    private var selectAns = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            question = it.getSerializable("data") as Question
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        control = FragmentAllQuestionBinding.inflate(layoutInflater, container, false)
        return control.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        question?.let {
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
                cb.setOnCheckedChangeListener { buttonView, isChecked ->
                    val select = buttonView.tag.toString()
                    if(isChecked) {
                        selectAns.add(select)
                    } else {
                        selectAns.remove(select)
                    }

                    selectAns.sort()

                    QuestionActivity.ansList.firstOrNull { x->x.question == it }?.selectAns = selectAns
                }
            }

            control.btnConfirmAns.setOnClickListener { view ->
                if(it.ansDesc?.isNotEmpty() == true) {
                    control.txtAnsDes.text = "題目解析： " + it.ansDesc
                    control.txtAnsDes.isGone = false
                }

                val ans = it.correctAns?.split(",")?.toList() ?: listOf()
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

            control.btnRestart.setOnClickListener {
                control.txtAnsDes.isGone = true

                questionList.forEach {
                    it.isClickable = true
                    it.isChecked = false
                    it.setBackgroundColor(Color.TRANSPARENT)
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(question: Question) =
            AllQuestionFragment().apply {
                arguments = Bundle().apply {
                    putSerializable("data", question)
                }
            }
    }
}