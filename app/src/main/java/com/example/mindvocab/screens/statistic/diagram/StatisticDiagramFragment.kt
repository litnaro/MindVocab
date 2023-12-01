package com.example.mindvocab.screens.statistic.diagram

import android.graphics.Color
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
                PieChart.Slice(0.4f, Color.rgb(120, 181, 0), legend = "Знаю", label = "40%"),
                PieChart.Slice(0.2f, Color.rgb(0, 162, 216), legend = "Учу", label = "20%"),
                PieChart.Slice(0.2f, Color.rgb(204, 168, 0), legend = "Выучено", label = "20%"),
                PieChart.Slice(0.2f, Color.rgb(255, 4, 4), legend = "Осталось", label = "20%"),
            )
            isCenterLabelEnabled = true
            isLegendEnabled = true
        }

        return binding.root
    }

}