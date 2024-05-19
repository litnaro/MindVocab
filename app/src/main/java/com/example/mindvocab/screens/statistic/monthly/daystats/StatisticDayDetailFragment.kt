package com.example.mindvocab.screens.statistic.monthly.daystats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mindvocab.core.BaseFragment
import com.example.mindvocab.databinding.FragmentStatisticDayDetailBinding
import com.example.mindvocab.screens.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class StatisticDayDetailFragment : BaseFragment() {

    override val viewModel by viewModels<StatisticDayDetailViewModel>()

    private var _binding: FragmentStatisticDayDetailBinding? = null
    private val binding get() = _binding!!

    private val args: StatisticDayDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatisticDayDetailBinding.inflate(inflater, container, false)

        (activity as MainActivity).setCustomTitle(
            title = SimpleDateFormat("d MMMM yyyy", Locale.getDefault()).format(Date(args.dayDetailTime))
        )

        initStartedWords()
        initRepeatedWords()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initStartedWords() {
        val startedWordsAdapter = WordDayAdapter()
        binding.startedWords.apply {
            adapter = startedWordsAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }

        viewModel.startedWordsLiveData.observe(viewLifecycleOwner) {
            binding.startedWordsTitle.visibility = View.GONE
            binding.startedWords.visibility = View.GONE
            if (it.isNotEmpty()) {
                startedWordsAdapter.submitList(it)
                binding.startedWordsTitle.visibility = View.VISIBLE
                binding.startedWords.visibility = View.VISIBLE
            }
        }

        viewModel.getStartedWords(args.dayDetailTime)
    }

    private fun initRepeatedWords() {
        val repeatedWordsAdapter = WordDayAdapter()
        binding.repeatedWords.apply {
            adapter = repeatedWordsAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }

        viewModel.repeatedWordsLiveData.observe(viewLifecycleOwner) {
            binding.repeatedWordsTitle.visibility = View.GONE
            binding.repeatedWords.visibility = View.GONE
            if (it.isNotEmpty()) {
                repeatedWordsAdapter.submitList(it)
                binding.repeatedWordsTitle.visibility = View.VISIBLE
                binding.repeatedWords.visibility = View.VISIBLE
            }
        }

        viewModel.getRepeatedWords(args.dayDetailTime)
    }

}