package com.example.mindvocab.screens.learn.wordset

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mindvocab.core.BaseFragment
import com.example.mindvocab.databinding.FragmentWordSetsBinding
import com.example.mindvocab.model.ErrorResult
import com.example.mindvocab.model.PendingResult
import com.example.mindvocab.model.SuccessResult
import com.example.mindvocab.model.sets.WordSetFilter
import com.example.mindvocab.model.sets.entity.WordSet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WordSetsFragment : BaseFragment() {

    override val viewModel by viewModels<WordSetsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentWordSetsBinding.inflate(inflater, container, false)

        viewModel.wordSetFilter.observe(viewLifecycleOwner) {
            when(it) {
                WordSetFilter.ALL -> {
                    binding.chipAll.isChecked = true
                }
                WordSetFilter.SELECTED -> {
                    binding.chipOnlySelected.isChecked = true
                }
                WordSetFilter.UNSELECTED -> {
                    binding.chipOnlyUnelected.isChecked = true
                }
                else -> {
                    binding.chipAll.isChecked = true
                }
            }
        }

        binding.chipGroup.setOnCheckedStateChangeListener { _, checkedIds ->
            when(checkedIds[0]) {
                binding.chipAll.id -> viewModel.getWordSets(WordSetFilter.ALL)
                binding.chipOnlySelected.id -> viewModel.getWordSets(WordSetFilter.SELECTED)
                binding.chipOnlyUnelected.id -> viewModel.getWordSets(WordSetFilter.UNSELECTED)
            }
        }

        val wordSetAdapter = WordSetAdapter(object : WordSetAdapter.Listener {

            override fun onWordSetDetail(wordSet: WordSet) {
                findNavController().navigate(WordSetsFragmentDirections.actionWordSetsFragmentToWordsFragment(wordSet.id))
            }

            override fun selectWordSet(wordSet: WordSet) {
                viewModel.selectWordSet(wordSet)
            }

            override fun unselectWordSet(wordSet: WordSet) {
                viewModel.unselectWordSet(wordSet)
            }
        })

        binding.wordSetsRv.apply {
            adapter = wordSetAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }

        val itemAnimator = binding.wordSetsRv.itemAnimator
        if (itemAnimator is DefaultItemAnimator) {
            itemAnimator.supportsChangeAnimations = false
        }

        viewModel.wordSetList.observe(viewLifecycleOwner) {
            binding.pendingResultProgressBar.visibility = View.GONE
            binding.wordSetsRv.visibility = View.GONE

            when(it) {
                is PendingResult -> {
                    binding.pendingResultProgressBar.visibility = View.VISIBLE
                }
                is ErrorResult -> {
                    // TODO create view for error result
                }
                is SuccessResult -> {
                    wordSetAdapter.submitList(it.data)
                    binding.wordSetsRv.visibility = View.VISIBLE
                }
            }
        }

        return binding.root
    }
}