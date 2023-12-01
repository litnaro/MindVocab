package com.example.mindvocab.screens.statistic

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mindvocab.screens.statistic.diagram.StatisticDiagramFragment
import com.example.mindvocab.screens.statistic.metric.StatisticMetricFragment
import com.example.mindvocab.screens.statistic.monthly.StatisticMonthlyFragment
import java.lang.IllegalStateException

class StatisticPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    companion object {
        private const val PAGES_NUMBER = 3
    }

    override fun getItemCount(): Int = PAGES_NUMBER

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> {
                StatisticDiagramFragment()
            }
            1 -> {
                StatisticMonthlyFragment()
            }
            2 -> {
                StatisticMetricFragment()
            }
            else -> {
                throw IllegalStateException("Unknown fragment exception")
            }
        }
    }

}