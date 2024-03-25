package com.example.mindvocab.screens.repeat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.example.mindvocab.R
import com.example.mindvocab.core.BaseFragment
import com.example.mindvocab.core.Result
import com.example.mindvocab.databinding.FragmentRepeatWordBinding
import com.example.mindvocab.model.NoWordsToRepeatException
import com.example.mindvocab.model.WordsToRepeatCurrentlyInTimeout
import com.example.mindvocab.model.word.entities.WordToRepeat
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RepeatWordFragment : BaseFragment() {

    override val viewModel by viewModels<RepeatWordViewModel>()

    private var _binding: FragmentRepeatWordBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRepeatWordBinding.inflate(inflater, container, false)

        setupMenu()

        viewModel.wordToRepeat.observe(viewLifecycleOwner) {
            with(binding) {
                repeatingContainer.visibility = View.INVISIBLE
                repeatingExceptionContainer.visibility = View.GONE
                pendingShimmer.visibility = View.GONE
                pendingShimmer.stopShimmer()

                when(it) {
                    is Result.PendingResult -> {
                        binding.pendingShimmer.visibility = View.VISIBLE
                        pendingShimmer.startShimmer()
                    }
                    is Result.ErrorResult -> {
                        val context = root.context
                        repeatingContainer.visibility = View.INVISIBLE
                        repeatingExceptionContainer.visibility = View.VISIBLE

                        when(it.exception) {
                            is NoWordsToRepeatException -> {
                                repeatingExceptionImage.setImageResource(R.drawable.ic_remember)
                                repeatingExceptionText.text = context.getString(R.string.no_words_to_repeat_exception_title)
                                repeatingExceptionSubtext.text = context.getString(R.string.no_words_to_repeat_exception_subtitle)
                            }
                            is WordsToRepeatCurrentlyInTimeout -> {
                                repeatingExceptionImage.setImageResource(R.drawable.ic_timeout)
                                repeatingExceptionText.text = context.getString(R.string.words_to_repeat_in_timeout_exception_title)
                                repeatingExceptionSubtext.text = context.getString(R.string.words_to_repeat_in_timeout_exception_subtitle)
                            }
                        }
                    }
                    is Result.SuccessResult -> {
                        repeatingContainer.visibility = View.VISIBLE
                        setWordData(it.data)
                    }
                }

            }
        }

        binding.wordToRepeat.setOnClickListener {
            binding.wordAnswer.visibility = View.VISIBLE
        }

        return binding.root
    }

    private fun setupMenu() {
        (requireActivity() as MenuHost).addMenuProvider( object : MenuProvider {

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.repeating_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when(menuItem.itemId) {
                    R.id.toRepeatingWordsList -> {
                        findNavController().navigate(RepeatWordFragmentDirections.actionRepeatWordFragmentToRepeatingWordsFragment())
                        true
                    }
                    else -> false
                }
            }

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun setWordData(word: WordToRepeat) {
        binding.wordToRepeat.text = word.word
        binding.wordAnswer.text = word.translation

        binding.leftActionButton.setOnClickListener {
            viewModel.onWordRemember(word)
            binding.wordAnswer.visibility = View.GONE
        }

        binding.rightActionButton.setOnClickListener {
            viewModel.onWordForgot(word)
            binding.wordAnswer.visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getWordToRepeat()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}