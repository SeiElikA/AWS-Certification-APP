package com.SeiElikA.awscertification.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.SeiElikA.awscertification.Data.Question
import com.SeiElikA.awscertification.View.Exam.QuestionActivity
import com.SeiElikA.awscertification.databinding.FragmentQuestionBinding
import com.SeiElikA.awscertification.removeHtml

class QuestionFragment : Fragment() {
    private lateinit var control: FragmentQuestionBinding
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
    ): View {
        control = FragmentQuestionBinding.inflate(layoutInflater, container, false)
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

            val questionList = listOf(control.rbA,control.rbB,control.rbC, control.rbD)

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
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(question: Question) =
            QuestionFragment().apply {
                arguments = Bundle().apply {
                    putSerializable("data", question)
                }
            }
    }
}