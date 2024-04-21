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
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.example.mindvocab.R
import com.example.mindvocab.core.BaseFragment
import com.example.mindvocab.core.Result
import com.example.mindvocab.databinding.FragmentRepeatWordBinding
import com.example.mindvocab.model.repeating.NoWordsToRepeatException
import com.example.mindvocab.model.repeating.WordsToRepeatCurrentlyInTimeout
import com.example.mindvocab.model.settings.repeat.options.AnsweringVariantSetting
import com.example.mindvocab.model.settings.repeat.options.QuestionVariantSetting
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
                repeatingContainer.visibility = View.GONE
                repeatingExceptionContainer.visibility = View.GONE
                pendingShimmer.visibility = View.GONE
                pendingShimmer.stopShimmer()

                when(it) {
                    is Result.Pending -> {
                        binding.pendingShimmer.visibility = View.VISIBLE
                        pendingShimmer.startShimmer()
                    }
                    is Result.Error -> {
                        val context = root.context
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
                    is Result.Success -> {
                        repeatingContainer.visibility = View.VISIBLE
                        setWordData(it.data)
                    }
                }

            }
        }

        binding.flipCardButton.setOnClickListener {
            binding.wordAnswer.visibility = View.VISIBLE
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getWordToRepeat()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
        viewModel.questionVariantSetting.observe(viewLifecycleOwner) {
            when(it) {
                QuestionVariantSetting.WORD -> {
                    binding.wordToRepeat.text = word.word

                    binding.wordAnswer.text = word.translation.ifBlank {
                        word.explanation
                    }
                }
                QuestionVariantSetting.TRANSLATION -> {
                    binding.wordToRepeat.text = word.translation.ifBlank {
                        word.explanation
                    }
                    binding.wordAnswer.text = word.word
                }
                else -> {
                    binding.wordToRepeat.text = word.word

                    binding.wordAnswer.text = word.translation.ifBlank {
                        word.explanation
                    }
                }
            }
        }

        viewModel.answeringVariantSetting.observe(viewLifecycleOwner) {
            when(it) {
                AnsweringVariantSetting.TRANSLATION -> {
                    if (binding.wordAnswer.visibility == View.GONE) {
                        binding.wordAnswer.visibility = View.GONE
                    } else {
                        binding.wordAnswer.visibility = View.VISIBLE
                    }
                }
                AnsweringVariantSetting.GRAMMAR -> {
                    binding.wordAnswer.visibility = View.GONE
                    binding.wordGrammarCheckFieldContainer.visibility = View.VISIBLE
                }
                else -> {
                    if (binding.wordAnswer.visibility == View.GONE) {
                        binding.wordAnswer.visibility = View.GONE
                    } else {
                        binding.wordAnswer.visibility = View.VISIBLE
                    }
                }
            }
        }

        binding.wordGrammarCheckField.doOnTextChanged { text, _, _, _ ->
            if (text.toString().lowercase() == word.word.lowercase()) {
                binding.wordGrammarCheckFieldContainer.helperText = binding.root.context.getString(R.string.correct_answer)
            } else {
                binding.wordGrammarCheckFieldContainer.helperText = null
            }
        }

        binding.leftActionButton.setOnClickListener {
            viewModel.onWordRemember(word)
            resetUiElements()
        }

        binding.rightActionButton.setOnClickListener {
            viewModel.onWordForgot(word)
            resetUiElements()
        }
    }

    private fun resetUiElements() {
        binding.wordAnswer.visibility = View.GONE

        binding.wordGrammarCheckField.setText("")
        binding.wordGrammarCheckField.clearFocus()
        binding.wordGrammarCheckFieldContainer.helperText = null
    }
}