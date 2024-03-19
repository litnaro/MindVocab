package com.example.mindvocab.screens.learn

import android.graphics.text.LineBreaker
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.mindvocab.R
import com.example.mindvocab.core.BaseFragment
import com.example.mindvocab.core.Result
import com.example.mindvocab.databinding.FragmentLearnWordBinding
import com.example.mindvocab.model.NoMoreWordsToLearnForTodayException
import com.example.mindvocab.model.NoWordsToLearnException
import com.example.mindvocab.model.word.entities.Word
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LearnWordFragment : BaseFragment() {

    override val viewModel by viewModels<LearnWordViewModel>()

    private var _binding: FragmentLearnWordBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentLearnWordBinding.inflate(inflater, container, false)

        viewModel.word.observe(viewLifecycleOwner) {
            with(binding) {
                learnWordContainer.visibility = View.GONE

                learnEmptyWordSetsBlock.visibility = View.GONE
                learnWordBlock.visibility = View.GONE
                pendingShimmer.visibility = View.GONE
                pendingShimmer.stopShimmer()

                accountLearningProgress.visibility = View.GONE
                accountLearningProgressCheckImage.visibility = View.GONE

                maxWordsForToday.visibility = View.GONE
                startedTodayWordsCount.visibility = View.GONE

                when(it) {
                    is Result.ErrorResult -> {
                        learnWordContainer.visibility = View.VISIBLE
                        learnEmptyWordSetsBlock.visibility = View.VISIBLE
                        val context = root.context
                        if (it.exception is NoWordsToLearnException) {
                            emptyWordToLearnImage.setImageResource(R.drawable.ic_selection_list)
                            emptyWordToLearnTitle.text = context.getString(R.string.no_learning_words_exception_text_title)
                            emptyWordToLearnText.text = context.getString(R.string.no_learning_words_exception_text)
                        } else if (it.exception is NoMoreWordsToLearnForTodayException) {
                            maxWordsForToday.visibility = View.VISIBLE
                            startedTodayWordsCount.visibility = View.VISIBLE

                            accountLearningProgressCheckImage.visibility = View.VISIBLE
                            accountLearningProgress.visibility = View.VISIBLE

                            emptyWordToLearnImage.setImageResource(R.drawable.ic_timer)
                            emptyWordToLearnTitle.text = context.getString(R.string.learning_words_timeout_exception_text_title)
                            emptyWordToLearnText.text = context.getString(R.string.learning_words_timeout_exception_text)
                        }
                    }
                    is Result.PendingResult -> {
                        pendingShimmer.visibility = View.VISIBLE
                        pendingShimmer.startShimmer()
                    }
                    is Result.SuccessResult -> {
                        setWordData(it.data)

                        learnWordContainer.visibility = View.VISIBLE

                        learnWordBlock.visibility = View.VISIBLE
                        accountLearningProgress.visibility = View.VISIBLE

                        maxWordsForToday.visibility = View.VISIBLE
                        startedTodayWordsCount.visibility = View.VISIBLE
                    }
                }
            }
        }

        viewModel.maxWordsForToday.observe(viewLifecycleOwner) {
            binding.maxWordsForToday.text = binding.root.context.getString(R.string.max_amount_of_words_started, it.value)
            binding.accountLearningProgress.max = it.value
        }

        viewModel.startedTodayWordsCount.observe(viewLifecycleOwner) {
            binding.startedTodayWordsCount.text = it.toString()
            binding.accountLearningProgress.progress = it
        }

        binding.listenWordButton.setOnClickListener {
            //TODO listen word
        }

        viewModel.isPreviousWordAvailable.observe(viewLifecycleOwner) {
            //TODO replace with dimens
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

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getWordToLearn()
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

            wordTranslation.text = word.translation
            wordExplaining.text = word.explanation

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                wordExplaining.justificationMode = LineBreaker.JUSTIFICATION_MODE_INTER_WORD
            }

            examplesRv.apply {
                adapter = ExampleAdapter(word.exampleList, object : ExampleAdapter.Listener {
                    override fun onSentenceListen(sentence: String) {
                        //TODO listen sentence
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
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}