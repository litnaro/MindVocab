package com.example.mindvocab.screens.statistic

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mindvocab.R
import com.example.mindvocab.databinding.FragmentStatisticBinding
import com.example.mindvocab.model.achievement.Achievement

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

        achievementsAdapter.submitList(list)
        return binding.root
    }
}