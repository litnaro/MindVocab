package com.example.mindvocab.screens.statistic.monthly

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.viewModels
import com.applandeo.materialcalendarview.CalendarDay
import com.applandeo.materialcalendarview.listeners.OnCalendarPageChangeListener
import com.example.mindvocab.R
import com.example.mindvocab.core.BaseFragment
import com.example.mindvocab.core.Result
import com.example.mindvocab.databinding.FragmentStatisticMonthlyBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class StatisticMonthlyFragment : BaseFragment() {

    override val viewModel by viewModels<StatisticMonthlyViewModel>()

    private var _binding: FragmentStatisticMonthlyBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatisticMonthlyBinding.inflate(inflater, container, false)

        viewModel.monthStatistic.observe(viewLifecycleOwner) { result ->
            when(result) {
                is Result.ErrorResult -> {}
                is Result.PendingResult -> {}
                is Result.SuccessResult -> {
                    binding.calendar.setCalendarDays(
                        result.data.map {
                            CalendarDay(it.day).apply {
                                if (it.isRepeatedOldWords && it.isStartedNewWords) {
                                    imageDrawable = AppCompatResources.getDrawable(requireContext(), R.drawable.ic_only_repeated_and_started_dot)
                                } else if (it.isRepeatedOldWords) {
                                    imageDrawable = AppCompatResources.getDrawable(requireContext(), R.drawable.ic_only_repeated_dot)
                                } else if (it.isStartedNewWords) {
                                    imageDrawable = AppCompatResources.getDrawable(requireContext(), R.drawable.ic_only_started_dot)
                                }
                            }
                        }
                    )
                }
            }
        }

        val monthChangeListener = object : OnCalendarPageChangeListener {
            override fun onChange() {
                viewModel.getMonthStatistic(
                    binding.calendar.currentPageDate.get(Calendar.MONTH)
                )
            }
        }

        binding.calendar.setOnPreviousPageChangeListener(monthChangeListener)
        binding.calendar.setOnForwardPageChangeListener(monthChangeListener)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getMonthStatistic(
            binding.calendar.currentPageDate.get(Calendar.MONTH)
        )
    }
}