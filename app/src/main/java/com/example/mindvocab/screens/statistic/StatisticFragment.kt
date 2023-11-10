package com.example.mindvocab.screens.statistic

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mindvocab.databinding.FragmentStatisticBinding
import com.example.mindvocab.model.achievement.Achievement
import ir.mahozad.android.PieChart

class StatisticFragment : Fragment() {

    private val list = listOf(
        Achievement(
            id = 0,
            title = "Быстрее света",
            description = "Выучить как минимум 50 слов за 7 дней",
            icon = "",
            progress = 50
        ),
        Achievement(
            id = 1,
            title = "Серьезное заявление",
            description = "Выучить 100 слов за все время",
            icon = "",
            progress = 50
        ),
        Achievement(
            id = 2,
            title = "Фанат",
            description = "Учить слова 21 день подряд",
            icon = "",
            progress = 21
        ),
        Achievement(
            id = 2,
            title = "Приоритетное обучение",
            description = "Выучить все слова из одной категории",
            icon = "",
            progress = 1
        ),
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentStatisticBinding.inflate(layoutInflater, container, false)
        val achievementsAdapter = AchievementAdapter()
        binding.achievementsRv.apply {
            adapter = achievementsAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }

        binding.dailyProgressBar.apply {
            slices = listOf(
//                PieChart.Slice(0.4f, Color.BLUE),
//                PieChart.Slice(0.3f, Color.MAGENTA),
//                PieChart.Slice(0.3f, Color.YELLOW),
                    PieChart.Slice(0.4f, Color.rgb(120, 181, 0), legend = "Знаю", label = "40%"),
                    PieChart.Slice(0.2f, Color.rgb(0, 162, 216), legend = "Учу", label = "20%"),
                    PieChart.Slice(0.2f, Color.rgb(204, 168, 0), legend = "Выучено", label = "20%"),
                    PieChart.Slice(0.2f, Color.rgb(255, 4, 4), legend = "Осталось", label = "20%"),
                )
            isCenterLabelEnabled = true
            isLegendEnabled = true
        }


        achievementsAdapter.submitList(list)
        return binding.root
    }
}