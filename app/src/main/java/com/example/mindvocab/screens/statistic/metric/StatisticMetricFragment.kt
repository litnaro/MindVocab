package com.example.mindvocab.screens.statistic.metric

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.mindvocab.R
import com.example.mindvocab.core.BaseFragment
import com.example.mindvocab.databinding.FragmentStatisticMetricBinding
import com.example.mindvocab.model.statistic.entities.AchievementsStatistic
import com.example.mindvocab.model.statistic.entities.WordsStatistic
import com.google.android.material.chip.Chip
import com.google.android.material.color.MaterialColors
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StatisticMetricFragment : BaseFragment() {

    override val viewModel by viewModels<StatisticMetricViewModel>()

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
            binding.wordSetsStatisticChipGroup.visibility = View.GONE
            binding.wordSetsStatisticEmptyList.visibility = View.GONE

            if (it.isNotEmpty()) {
                setWordSetsStatistic(it)
                binding.wordSetsStatisticChipGroup.visibility = View.VISIBLE
            } else {
                binding.wordSetsStatisticEmptyList.visibility = View.VISIBLE
            }
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
                    chipBackgroundColor = MaterialColors.getColorStateListOrNull(
                        context,
                        com.google.android.material.R.attr.colorPrimaryContainer
                    )
                    setTextColor(MaterialColors.getColor(context, com.google.android.material.R.attr.colorOnPrimaryContainer, Color.WHITE))
                }
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}