package com.example.mindvocab.screens.statistic.diagram

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.mindvocab.R
import com.example.mindvocab.core.BaseFragment
import com.example.mindvocab.core.Result
import com.example.mindvocab.databinding.FragmentStatisticDiagramBinding
import com.example.mindvocab.model.statistic.entities.WordsStatisticPercentage
import dagger.hilt.android.AndroidEntryPoint
import ir.mahozad.android.PieChart

@AndroidEntryPoint
class StatisticDiagramFragment : BaseFragment() {

    override val viewModel by viewModels<StatisticDiagramViewModel>()

    private var _binding: FragmentStatisticDiagramBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatisticDiagramBinding.inflate(inflater, container, false)

        viewModel.statistic.observe(viewLifecycleOwner) {
            binding.diagramContainer.visibility = View.GONE
            binding.pendingProgressBar.visibility = View.GONE

            when(it) {
                is Result.Pending -> {
                    binding.pendingProgressBar.visibility = View.VISIBLE
                }
                is Result.Error -> {}
                is Result.Success -> {
                    binding.diagramContainer.visibility = View.VISIBLE
                    updateDiagram(it.data)
                }
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateDiagram(statistic: WordsStatisticPercentage) {
        val context = binding.root.context

        binding.progressDiagram.apply {
            slices = listOf(
                PieChart.Slice(
                    statistic.learnedWords,
                    context.getColor(R.color.word_learned_status),
                    legend = context.getString(R.string.words_learned_statistic),
                    label = context.getString(R.string.percentage, (statistic.learnedWords * 100).toInt())),
                PieChart.Slice(
                    statistic.knownWords,
                    context.getColor(R.color.word_known_status),
                    legend = context.getString(R.string.words_known_statistic),
                    label = context.getString(R.string.percentage, (statistic.knownWords * 100).toInt())),
                PieChart.Slice(
                    statistic.repeatingWords,
                    context.getColor(R.color.word_in_progress_status),
                    legend = context.getString(R.string.words_in_progress_statistic),
                    label = context.getString(R.string.percentage, (statistic.repeatingWords * 100).toInt())),
                PieChart.Slice(
                    statistic.wordsLeft,
                    requireContext().getColor(R.color.word_new_status),
                    legend = context.getString(R.string.words_left_statistic),
                    label = context.getString(R.string.percentage, (statistic.wordsLeft * 100).toInt())),
            )
            isCenterLabelEnabled = true
            isLegendEnabled = true
        }

        binding.daysInRow.text = statistic.accountLearningStreak.toString()
    }

}