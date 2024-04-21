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
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RepeatingWordsFragment : BaseFragment() {

    override val viewModel by viewModels<RepeatingWordsViewModel>()

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
            with(binding) {
                repeatingWordsRv.visibility = View.GONE
                pendingShimmer.visibility = View.GONE
                pendingShimmer.stopShimmer()
                emptyListContainer.visibility = View.GONE

                when(it) {
                    is Result.Pending -> {
                        pendingShimmer.visibility = View.VISIBLE
                        pendingShimmer.startShimmer()
                    }
                    is Result.Error -> {}
                    is Result.Success -> {
                        if (it.data.isEmpty()) {
                            emptyListContainer.visibility = View.VISIBLE
                        } else {
                            wordAdapter.submitList(it.data)
                            repeatingWordsRv.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }

        return binding.root
    }
}