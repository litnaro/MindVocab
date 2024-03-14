package com.example.mindvocab.screens.word

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mindvocab.core.BaseFragment
import com.example.mindvocab.databinding.FragmentWordsBinding
import com.example.mindvocab.model.ErrorResult
import com.example.mindvocab.model.PendingResult
import com.example.mindvocab.model.SuccessResult
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
            when(it) {
                is ErrorResult -> {

                }
                is PendingResult -> {

                }
                is SuccessResult -> {
                    wordAdapter.submitList(it.data)
                }
            }
        }
        viewModel.getWordsByWordSetId(args.wordSetId)

        return binding.root
    }
}