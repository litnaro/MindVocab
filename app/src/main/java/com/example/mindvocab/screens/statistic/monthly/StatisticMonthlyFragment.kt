package com.example.mindvocab.screens.statistic.monthly

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.applandeo.materialcalendarview.CalendarDay
import com.applandeo.materialcalendarview.listeners.OnCalendarDayClickListener
import com.applandeo.materialcalendarview.listeners.OnCalendarPageChangeListener
import com.example.mindvocab.R
import com.example.mindvocab.core.BaseFragment
import com.example.mindvocab.core.Result
import com.example.mindvocab.databinding.FragmentStatisticMonthlyBinding
import com.example.mindvocab.screens.statistic.StatisticFragmentDirections
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
                is Result.Error -> {}
                is Result.Pending -> {}
                is Result.Success -> {
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

        binding.calendar.setOnCalendarDayClickListener(object : OnCalendarDayClickListener {
            override fun onClick(calendarDay: CalendarDay) {
                if (calendarDay.imageDrawable != null) {
                    findNavController().navigate(StatisticFragmentDirections.actionStatisticFragmentToStatisticDayDialogFragment(calendarDay.calendar.timeInMillis))
                }
            }
        })

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getMonthStatistic(binding.calendar.currentPageDate.get(Calendar.MONTH))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.calendar.setMaximumDate(Calendar.getInstance().apply {set(Calendar.DAY_OF_MONTH, this.getActualMaximum(Calendar.DAY_OF_MONTH))})

        val monthChangeListener = object : OnCalendarPageChangeListener {
            override fun onChange() {
                viewModel.getMonthStatistic(
                    binding.calendar.currentPageDate.get(Calendar.MONTH)
                )
            }
        }

        binding.calendar.setOnPreviousPageChangeListener(monthChangeListener)
        binding.calendar.setOnForwardPageChangeListener(monthChangeListener)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}