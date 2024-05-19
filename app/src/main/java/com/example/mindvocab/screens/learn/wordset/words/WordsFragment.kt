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
import com.example.mindvocab.model.word.entities.WordStatistic
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WordsFragment : BaseFragment() {

    override val viewModel by viewModels<WordsViewModel>()

    private var _binding: FragmentWordsBinding? = null
    private val binding get() = _binding!!

    private var wordAdapter: WordAdapter? = null

    private val args: WordsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWordsBinding.inflate(inflater, container, false)

        initialBinding()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        wordAdapter = null
    }

    private fun initialBinding() {
        createWordsAdapter()
        viewModel.wordsListLiveData.observe(viewLifecycleOwner) {
            observeSideEffects(
                result = it,
                onReset = ::wordListResetResult,
                onPending = ::wordListPendingResult,
                onSuccess = ::wordListSuccessResult
            )
        }
        viewModel.getWordsByWordSetId(args.wordSetId)
    }

    private fun createWordsAdapter() {
        wordAdapter = WordAdapter()
        binding.wordRv.apply {
            adapter = wordAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun wordListResetResult() {
        with(binding) {
            pendingShimmer.visibility = View.GONE
            pendingShimmer.stopShimmer()

            wordRv.visibility = View.GONE
        }
    }

    private fun wordListPendingResult() {
        with(binding) {
            pendingShimmer.visibility = View.VISIBLE
            pendingShimmer.startShimmer()
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> wordListSuccessResult(result: Result.Success<T>) {
        binding.wordRv.visibility = View.VISIBLE
        wordAdapter?.submitList(result.data as List<WordStatistic>)
    }

}