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
import com.example.mindvocab.databinding.FragmentLearnWordBinding
import com.example.mindvocab.core.factory
import com.example.mindvocab.model.ErrorResult
import com.example.mindvocab.model.NoMoreWordsToLearnForTodayException
import com.example.mindvocab.model.NoWordsToLearnException
import com.example.mindvocab.model.PendingResult
import com.example.mindvocab.model.SuccessResult
import com.example.mindvocab.model.word.entities.Word

class LearnWordFragment : BaseFragment() {

    override val viewModel: LearnWordViewModel by viewModels { factory() }

    private var _binding: FragmentLearnWordBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentLearnWordBinding.inflate(inflater, container, false)

        viewModel.word.observe(viewLifecycleOwner) {
            with(binding) {
                learnEmptyWordSetsBlock.visibility = View.GONE
                learnWordBlock.visibility = View.GONE
                learnPendingProgressBar.visibility = View.GONE

                accountLearningProgress.visibility = View.GONE
                accountLearningProgressCheckImage.visibility = View.GONE

                maxWordsForToday.visibility = View.GONE
                startedTodayWordsCount.visibility = View.GONE

                when(it) {
                    is ErrorResult -> {
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
                    is PendingResult -> {
                        learnPendingProgressBar.visibility = View.VISIBLE
                    }
                    is SuccessResult -> {
                        setWordData(it.data)

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
            viewModel.onWordListen()
        }

        //binding.learnWordContainer.setOnTouchListener(onTouchListener)

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
                        viewModel.onSentenceListen(sentence)
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

    //    private val onTouchListener = View.OnTouchListener { view, event ->
//
//        val MIN_SWIPE_DISTANCE = -350
//
//        // variables to store current configuration of quote card.
//        val displayMetrics = resources.displayMetrics
//        val cardWidth = binding.learnWordContainer.width
//        val cardStart = (displayMetrics.widthPixels.toFloat() / 2) - (cardWidth / 2)
//
//        when(event.action) {
//            MotionEvent.ACTION_UP -> {
//                var currentX = binding.learnWordContainer.x
//                binding.learnWordContainer.animate()
//                    .x(cardStart)
//                    .setDuration(150)
//                    .setListener(
//                        object : AnimatorListenerAdapter() {
//                            override fun onAnimationEnd(animation: Animator) {
//                                viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Default) {
//                                    delay(100)
//
//                                    // check if the swipe distance was more than
//                                    // minimum swipe required to load a new quote
//                                    if (currentX < MIN_SWIPE_DISTANCE) {
//                                        // Add logic to load a new quote if swiped adequately
//                                        //Toast.makeText(requireContext(), "Swap action", Toast.LENGTH_SHORT).show()
//                                        //viewModel.onWordLearn()
//                                        currentX = 0f
//                                    }
//                                }
//                            }
//                        }
//                    )
//                    .start()
//                //textView.text = getString(R.string.infoText)
//            }
//
//            MotionEvent.ACTION_MOVE -> {
//                // get the new co-ordinate of X-axis
//                val newX = event.rawX
//
//                // carry out swipe only if newX < cardStart, that is,
//                // the card is swiped to the left side, not to the right
//                if (newX - cardWidth < cardStart) {
//                    binding.learnWordContainer.animate()
//                        .x(
//                            min(cardStart, newX - (cardWidth / 2))
//                        )
//                        .setDuration(0)
//                        .start()
//                    if (binding.learnWordContainer.x < MIN_SWIPE_DISTANCE) {
//                        //textView.text = getString(R.string.releaseCard)
//                    } else {
//                        //textView.text = getString(R.string.infoText
//                    }
//                }
//            }
//        }
//
//        // required to by-pass lint warning
//        view.performClick()
//        return@OnTouchListener true
//    }

}