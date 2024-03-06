package com.example.mindvocab.screens.statistic.metric

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.mindvocab.R
import com.example.mindvocab.core.BaseFragment
import com.example.mindvocab.core.factory
import com.example.mindvocab.databinding.FragmentStatisticMetricBinding
import com.example.mindvocab.model.statistic.entities.AchievementsStatistic
import com.example.mindvocab.model.statistic.entities.WordsStatistic
import com.google.android.material.chip.Chip

class StatisticMetricFragment : BaseFragment() {

    override val viewModel: StatisticMetricViewModel by viewModels { factory() }

    private var _binding: FragmentStatisticMetricBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatisticMetricBinding.inflate(inflater, container, false)

        viewModel.wordsStatistic.observe(viewLifecycleOwner) {
            setWordsStatistic(it)
        }

        viewModel.achievementsStatistic.observe(viewLifecycleOwner) {
            setAchievementsStatistic(it)
        }

        viewModel.wordSetsStatistic.observe(viewLifecycleOwner) {
            setWordSetsStatistic(it)
        }

        return binding.root
    }

    private fun setWordsStatistic(wordsStatistic: WordsStatistic) {
        with(binding) {
            allWordsCountValue.text = wordsStatistic.allWordsCount.toString()
            learnedWordsCountValue.text = wordsStatistic.learnedWords.toString()
            knownWordsCountValue.text = wordsStatistic.knownWordsCount.toString()
            leftWordsCountValue.text = wordsStatistic.wordsLeftCount.toString()
        }
    }

    private fun setAchievementsStatistic(achievementsStatistic: AchievementsStatistic) {
        binding.achievementsStatisticValue.text = binding.root.context.getString(
            R.string.amount_of,
            achievementsStatistic.achievementsCompleted,
            achievementsStatistic.achievementsCount
        )
    }

    private fun setWordSetsStatistic(list: List<String>) {
        list.forEach {
            binding.wordSetsStatisticChipGroup.addView(
                Chip(requireContext()).apply {
                    text = it
                    isCheckable = false
                    setEnsureMinTouchTargetSize(false)
                }
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}