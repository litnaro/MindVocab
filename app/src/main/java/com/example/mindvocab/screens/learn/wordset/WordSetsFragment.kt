package com.example.mindvocab.screens.learn.wordset

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mindvocab.core.BaseFragment
import com.example.mindvocab.core.factory
import com.example.mindvocab.databinding.FragmentWordSetsBinding
import com.example.mindvocab.model.ErrorResult
import com.example.mindvocab.model.PendingResult
import com.example.mindvocab.model.SuccessResult
import com.example.mindvocab.model.sets.WordSetFilter
import com.example.mindvocab.model.sets.entity.WordSet

class WordSetsFragment : BaseFragment() {

    override val viewModel: WordSetsViewModel by viewModels { factory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentWordSetsBinding.inflate(inflater, container, false)

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

        viewModel.wordSetList.observe(viewLifecycleOwner) {
            when(it) {
                is PendingResult -> {
                    // TODO create view for pending result
                }
                is ErrorResult -> {
                    // TODO create view for error result
                }
                is SuccessResult -> {
                    // TODO create view for empty result
                    wordSetAdapter.submitList(it.data)
                }
            }
        }

        return binding.root
    }
}