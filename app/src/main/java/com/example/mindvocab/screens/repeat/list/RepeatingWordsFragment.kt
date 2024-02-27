package com.example.mindvocab.screens.repeat.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mindvocab.core.BaseFragment
import com.example.mindvocab.core.factory
import com.example.mindvocab.databinding.FragmentRepeatingWordsBinding
import com.example.mindvocab.model.ErrorResult
import com.example.mindvocab.model.PendingResult
import com.example.mindvocab.model.SuccessResult

class RepeatingWordsFragment : BaseFragment() {

    override val viewModel: RepeatingWordsViewModel by viewModels { factory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentRepeatingWordsBinding.inflate(layoutInflater, container, false)

        val wordAdapter = RepeatingWordsAdapter()

        binding.repeatingWordsRv.apply {
            adapter = wordAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }

        viewModel.repeatingWords.observe(viewLifecycleOwner) {
            when(it) {
                is PendingResult -> {

                }
                is ErrorResult -> {

                }
                is SuccessResult -> {
                    wordAdapter.submitList(it.data)
                }
            }
        }

        return binding.root
    }
}