package com.example.mindvocab.screens.learn

import android.graphics.text.LineBreaker
import android.os.Build
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
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.mindvocab.R
import com.example.mindvocab.core.BaseFragment
import com.example.mindvocab.core.Result
import com.example.mindvocab.databinding.FragmentLearnWordBinding
import com.example.mindvocab.model.AppException
import com.example.mindvocab.model.learning.NoMoreWordsToLearnForTodayException
import com.example.mindvocab.model.learning.NoWordsToLearnException
import com.example.mindvocab.model.word.entities.Word
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class LearnWordFragment : BaseFragment() {

    override val viewModel by viewModels<LearnWordViewModel>()

    private var _binding: FragmentLearnWordBinding? = null
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentLearnWordBinding.inflate(inflater, container, false)

        setupMenu()
        initialBinding()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getWordToLearn()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        textToSpeech?.stop()
        textToSpeech?.shutdown()
        textToSpeech = null
    }

    private fun speak(text: String) {
        textToSpeech?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    private fun initialBinding() {
        textToSpeech = TextToSpeech(requireContext(), textToSpeechListener)

        viewModel.wordLiveDataResult.observe(viewLifecycleOwner) {
            observeSideEffects(
                result = it,
                onReset = ::wordResetResult,
                onPending = ::wordPendingResult,
                onError = ::wordErrorResult,
                onSuccess = ::wordSuccessResult
            )
        }

        viewModel.maxWordsForTodayLiveData.observe(viewLifecycleOwner) {
            binding.maxWordsForToday.text = binding.root.context.getString(R.string.max_amount_of_words_started, it.value)
            binding.accountLearningProgress.max = it.value
        }

        viewModel.startedTodayWordsCountLiveData.observe(viewLifecycleOwner) {
            binding.startedTodayWordsCount.text = it.toString()
            binding.accountLearningProgress.progress = it
        }

        viewModel.isPreviousWordAvailableLiveData.observe(viewLifecycleOwner) {
            if (it) {
                binding.returnPreviousWordButton.alpha = 1f
            } else {
                binding.returnPreviousWordButton.alpha = 0.5f
            }
            binding.returnPreviousWordButton.isEnabled = it
        }

        binding.returnPreviousWordButton.setOnClickListener {
            viewModel.onWordReturnPrevious()
        }
    }

    private fun setupMenu() {
        (requireActivity() as MenuHost).addMenuProvider( object : MenuProvider {

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.learning_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when(menuItem.itemId) {
                    R.id.toWordSetsSelection -> {
                        findNavController().navigate(LearnWordFragmentDirections.actionLearnWordFragmentToWordSetsFragment())
                        true
                    }
                    else -> false
                }
            }

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun <T> wordSuccessResult(result: Result.Success<T>) {
        with(binding) {
            val word = result.data as Word
            setWordData(word)

            learnWordContainer.visibility = View.VISIBLE

            learnWordBlock.visibility = View.VISIBLE
            accountLearningProgress.visibility = View.VISIBLE

            maxWordsForToday.visibility = View.VISIBLE
            startedTodayWordsCount.visibility = View.VISIBLE
        }
    }

    private fun wordResetResult() {
        with(binding) {
            learnWordContainer.visibility = View.GONE

            learnEmptyWordSetsScroll.visibility = View.GONE
            learnWordBlock.visibility = View.GONE
            pendingShimmer.visibility = View.GONE
            pendingShimmer.stopShimmer()

            accountLearningProgress.visibility = View.GONE
            accountLearningProgressCheckImage.visibility = View.GONE

            maxWordsForToday.visibility = View.GONE
            startedTodayWordsCount.visibility = View.GONE
        }
    }

    private fun wordErrorResult(exception: AppException) {
        with(binding) {
            learnWordContainer.visibility = View.VISIBLE
            learnEmptyWordSetsScroll.visibility = View.VISIBLE
            val context = root.context
            if (exception is NoWordsToLearnException) {
                emptyWordToLearnImage.setImageResource(R.drawable.ic_selection_list)
                emptyWordToLearnTitle.text = context.getString(R.string.no_learning_words_exception_text_title)
                emptyWordToLearnText.text = context.getString(R.string.no_learning_words_exception_text)
            } else if (exception is NoMoreWordsToLearnForTodayException) {
                maxWordsForToday.visibility = View.VISIBLE
                startedTodayWordsCount.visibility = View.VISIBLE

                accountLearningProgressCheckImage.visibility = View.VISIBLE
                accountLearningProgress.visibility = View.VISIBLE

                emptyWordToLearnImage.setImageResource(R.drawable.ic_timer)
                emptyWordToLearnTitle.text = context.getString(R.string.learning_words_timeout_exception_text_title)
                emptyWordToLearnText.text = context.getString(R.string.learning_words_timeout_exception_text)
            }
        }
    }

    private fun wordPendingResult() {
        with(binding) {
            pendingShimmer.visibility = View.VISIBLE
            pendingShimmer.startShimmer()
        }
    }

    private fun setWordData(word: Word) {
        with(binding) {
            binding.word.text = word.word
            wordTranscription.text = word.transcription

            Glide.with(wordImage.context)
                .load(word.image)
                .centerCrop()
                .placeholder(R.drawable.ic_image_simple_placeholder)
                .error(R.drawable.ic_image_simple_placeholder)
                .into(wordImage)

            if (word.translation.isNotEmpty()) {
                wordTranslationTitle.visibility = View.VISIBLE
                wordTranslation.visibility = View.VISIBLE
                wordTranslation.text = word.translation
            } else {
                wordTranslationTitle.visibility = View.GONE
                wordTranslation.visibility = View.GONE
            }

            wordTranslation.text = word.translation
            wordExplaining.text = word.explanation

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                wordExplaining.justificationMode = LineBreaker.JUSTIFICATION_MODE_INTER_WORD
            }

            examplesRv.apply {
                adapter = ExampleAdapter(word.exampleList, object : ExampleAdapter.Listener {
                    override fun onSentenceListen(sentence: String) {
                        speak(sentence)
                    }
                })
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }

            binding.wordKnownButton.setOnClickListener {
                viewModel.onWordKnown(word)
            }

            binding.wordToLearnButton.setOnClickListener {
                viewModel.onWordToLearn(word)
            }

            binding.listenWordButton.setOnClickListener {
                speak(word.word)
            }
        }
    }

}