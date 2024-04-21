package com.example.mindvocab.screens.learn.wordset

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mindvocab.core.BaseFragment
import com.example.mindvocab.core.Result
import com.example.mindvocab.databinding.FragmentWordSetsBinding
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

        //For lower api
        binding.searchView.clearFocus()

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (query != null) {
                    viewModel.getWordSets(searchQuery = query)
                }
                return true
            }
        })

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
                binding.chipAll.id -> viewModel.getWordSets(filter = WordSetFilter.ALL)
                binding.chipOnlySelected.id -> viewModel.getWordSets(filter = WordSetFilter.SELECTED)
                binding.chipOnlyUnelected.id -> viewModel.getWordSets(filter = WordSetFilter.UNSELECTED)
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
            binding.pendingShimmer.visibility = View.GONE
            binding.pendingShimmer.stopShimmer()
            binding.wordSetsRv.visibility = View.GONE

            when(it) {
                is Result.Pending -> {
                    binding.pendingShimmer.visibility = View.VISIBLE
                    binding.pendingShimmer.startShimmer()
                }
                is Result.Error -> {
                    // TODO create view for error result
                }
                is Result.Success -> {
                    wordSetAdapter.submitList(it.data)
                    binding.wordSetsRv.visibility = View.VISIBLE
                }
            }
        }

        return binding.root
    }
}