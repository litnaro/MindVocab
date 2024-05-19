package com.example.mindvocab.screens.repeat.words

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mindvocab.core.BaseFragment
import com.example.mindvocab.core.Result
import com.example.mindvocab.databinding.FragmentRepeatingWordsBinding
import com.example.mindvocab.model.word.entities.WordToRepeatDetail
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RepeatingWordsFragment : BaseFragment() {

    override val viewModel by viewModels<RepeatingWordsViewModel>()

    private var _binding: FragmentRepeatingWordsBinding? = null
    private val binding get() = _binding!!

    private var wordAdapter: RepeatingWordsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRepeatingWordsBinding.inflate(layoutInflater, container, false)

        initialBinding()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        wordAdapter = null
    }

    private fun createWordsAdapter() {
        wordAdapter = RepeatingWordsAdapter()

        binding.repeatingWordsRv.apply {
            adapter = wordAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun initialBinding() {
        createWordsAdapter()
        viewModel.repeatingWordsLiveDataResult.observe(viewLifecycleOwner) {
            observeSideEffects(
                result = it,
                onReset = ::wordsResetResult,
                onPending = ::wordsPendingResult,
                onSuccess = ::wordsSuccessResult
            )
        }
    }

    private fun wordsResetResult() {
        with(binding) {
            repeatingWordsRv.visibility = View.GONE
            pendingShimmer.visibility = View.GONE
            pendingShimmer.stopShimmer()
            emptyListContainer.visibility = View.GONE
        }
    }

    private fun wordsPendingResult() {
        binding.pendingShimmer.visibility = View.VISIBLE
        binding.pendingShimmer.startShimmer()
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> wordsSuccessResult(result: Result.Success<T>) {
        val wordsList = result.data as List<WordToRepeatDetail>
        if (wordsList.isEmpty()) {
            binding.emptyListContainer.visibility = View.VISIBLE
        } else {
            wordAdapter?.submitList(wordsList)
            binding.repeatingWordsRv.visibility = View.VISIBLE
        }
    }

}