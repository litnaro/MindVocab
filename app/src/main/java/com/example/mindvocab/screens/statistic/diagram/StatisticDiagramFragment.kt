package com.example.mindvocab.screens.statistic.diagram

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mindvocab.R
import com.example.mindvocab.databinding.FragmentStatisticDiagramBinding
import ir.mahozad.android.PieChart

class StatisticDiagramFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentStatisticDiagramBinding.inflate(inflater, container, false)

        binding.progressDiagram.apply {
            slices = listOf(
                PieChart.Slice(0.4f, requireContext().getColor(R.color.word_known_status), legend = "Знаю", label = "40%"),
                PieChart.Slice(0.2f, requireContext().getColor(R.color.word_learned_status), legend = "Выучено", label = "20%"),
                PieChart.Slice(0.2f, requireContext().getColor(R.color.word_in_progress_status), legend = "Учу", label = "20%"),
                PieChart.Slice(0.2f, requireContext().getColor(R.color.word_new_status), legend = "Осталось", label = "20%"),
            )
            isCenterLabelEnabled = true
            isLegendEnabled = true
        }

        return binding.root
    }

}