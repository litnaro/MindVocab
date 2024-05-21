package com.example.mindvocab.screens.repeat

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
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
import com.example.mindvocab.model.AppException
import com.example.mindvocab.model.repeating.NoWordsToRepeatException
import com.example.mindvocab.model.repeating.WordsToRepeatCurrentlyInTimeout
import com.example.mindvocab.model.settings.repeat.options.AnsweringVariantSetting
import com.example.mindvocab.model.settings.repeat.options.QuestionVariantSetting
import com.example.mindvocab.model.word.entities.WordToRepeat
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class RepeatWordFragment : BaseFragment() {

    override val viewModel by viewModels<RepeatWordViewModel>()

    private var _binding: FragmentRepeatWordBinding? = null
    private val binding get() = _binding!!

    private var textToSpeech: TextToSpeech? = null
    private val textToSpeechListener = TextToSpeech.OnInitListener { status ->
        if (status == TextToSpeech.SUCCESS) {
            val result = textToSpeech?.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "Language not supported")
            }
        } else {
            Log.e("TTS", "Initialization failed")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRepeatWordBinding.inflate(inflater, container, false)

        setupMenu()
        initialBinding()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getWordToRepeat()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        textToSpeech?.stop()
        textToSpeech?.shutdown()
        textToSpeech = null
    }

    private fun initialBinding() {
        textToSpeech = TextToSpeech(requireContext(), textToSpeechListener)

        viewModel.wordToRepeatLiveDataResult.observe(viewLifecycleOwner) {
            observeSideEffects(
                result = it,
                onReset = ::wordResetResult,
                onPending = ::wordPendingResult,
                onError = ::wordErrorResult,
                onSuccess = ::wordSuccessResult
            )
        }

        binding.flipCardButton.setOnClickListener {
            binding.wordAnswer.visibility = View.VISIBLE
        }
    }

    private fun speak(text: String) {
        textToSpeech?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    private fun wordResetResult() {
        with(binding) {
            repeatingContainer.visibility = View.GONE
            repeatingExceptionContainer.visibility = View.GONE
            pendingShimmer.visibility = View.GONE
            pendingShimmer.stopShimmer()
        }
    }

    private fun wordErrorResult(exception: AppException) {
        with(binding) {
            val context = root.context
            repeatingExceptionContainer.visibility = View.VISIBLE

            when(exception) {
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
    }

    private fun wordPendingResult() {
        with(binding) {
            pendingShimmer.visibility = View.VISIBLE
            pendingShimmer.startShimmer()
        }
    }

    private fun <T> wordSuccessResult(result: Result.Success<T>) {
        binding.repeatingContainer.visibility = View.VISIBLE
        setWordData(result.data as WordToRepeat)
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
        viewModel.questionVariantSettingLiveData.observe(viewLifecycleOwner) {
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

        viewModel.answeringVariantSettingLiveData.observe(viewLifecycleOwner) {
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
            clearFields()
        }

        binding.rightActionButton.setOnClickListener {
            viewModel.onWordForgot(word)
            clearFields()
        }

        binding.listenWordButton.setOnClickListener {
            speak(word.word)
        }
    }

    private fun clearFields() {
        binding.wordAnswer.visibility = View.GONE

        binding.wordGrammarCheckField.setText("")
        binding.wordGrammarCheckField.clearFocus()
        binding.wordGrammarCheckFieldContainer.helperText = null
    }
}