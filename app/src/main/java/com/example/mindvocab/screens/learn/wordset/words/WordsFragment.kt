package com.example.mindvocab.screens.learn.wordset.words

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mindvocab.core.BaseFragment
import com.example.mindvocab.core.Result
import com.example.mindvocab.databinding.FragmentWordsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WordsFragment : BaseFragment() {

    override val viewModel by viewModels<WordsViewModel>()

    private val args: WordsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentWordsBinding.inflate(inflater, container, false)
        val wordAdapter = WordAdapter()
        binding.wordRv.apply {
            adapter = wordAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }

        viewModel.wordsList.observe(viewLifecycleOwner) {
            with(binding) {
                pendingShimmer.visibility = View.GONE
                pendingShimmer.stopShimmer()

                wordRv.visibility = View.GONE

                when(it) {
                    is Result.Pending -> {
                        pendingShimmer.visibility = View.VISIBLE
                        pendingShimmer.startShimmer()
                    }
                    is Result.Error -> {

                    }
                    is Result.Success -> {
                        wordRv.visibility = View.VISIBLE
                        wordAdapter.submitList(it.data)
                    }
                }
            }
        }

        viewModel.getWordsByWordSetId(args.wordSetId)

        return binding.root
    }
}