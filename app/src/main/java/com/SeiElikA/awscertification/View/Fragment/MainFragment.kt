package com.SeiElikA.awscertification.View.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.SeiElikA.awscertification.Adapter.QuestionAdapter
import com.SeiElikA.awscertification.R
import com.SeiElikA.awscertification.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    private lateinit var control: FragmentMainBinding
    private var type = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            this.type = it.getInt("type")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        control = FragmentMainBinding.inflate(layoutInflater, container, false)
        return control.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        context?.let {
            control.listView.adapter = QuestionAdapter(it,  type)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(type: Int) =
            MainFragment().apply {
                val bundle = Bundle().apply {
                    putInt("type", type)
                }

                this.arguments = bundle
            }
    }
}