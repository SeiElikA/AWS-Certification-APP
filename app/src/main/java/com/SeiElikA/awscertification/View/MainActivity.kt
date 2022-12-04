package com.SeiElikA.awscertification.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.SeiElikA.awscertification.View.Fragment.MainFragment
import com.SeiElikA.awscertification.R
import com.SeiElikA.awscertification.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var control: ActivityMainBinding
    private val idList = listOf(R.id.btnExam, R.id.btnAllQuestion)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        control = ActivityMainBinding.inflate(layoutInflater)
        setContentView(control.root)

        setViewPager()

        control.navigationBottom.setOnItemSelectedListener {
            val index = idList.indexOf(it.itemId)
            control.viewPager2.currentItem = index
            return@setOnItemSelectedListener true
        }
    }

    private fun setViewPager() {
        control.viewPager2.registerOnPageChangeCallback(object: OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                control.navigationBottom.selectedItemId = idList[position];
            }
        })

        control.viewPager2.adapter = object: FragmentStateAdapter(this) {
            override fun getItemCount(): Int {
                return 2
            }

            override fun createFragment(position: Int): Fragment {
                return MainFragment.newInstance(position)
            }
        }
    }
}