package com.example.mindvocab.screens.repeat

import android.animation.AnimatorSet
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.mindvocab.R
import com.example.mindvocab.core.BaseFragment
import com.example.mindvocab.databinding.FragmentRepeatWordBinding
import com.example.mindvocab.model.ErrorResult
import com.example.mindvocab.model.NoWordsToRepeatException
import com.example.mindvocab.model.PendingResult
import com.example.mindvocab.model.SuccessResult
import com.example.mindvocab.model.WordsToRepeatCurrentlyInTimeout
import com.example.mindvocab.model.word.entities.WordToRepeat
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RepeatWordFragment : BaseFragment() {

    // https://www.youtube.com/watch?v=XCvejwakoao

    override val viewModel by viewModels<RepeatWordViewModel>()

    private var _binding: FragmentRepeatWordBinding? = null
    private val binding get() = _binding!!

    // Animation fields
    private lateinit var frontAnimator: AnimatorSet
    private lateinit var backAnimator: AnimatorSet
    private var isFront = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRepeatWordBinding.inflate(inflater, container, false)

        viewModel.wordToRepeat.observe(viewLifecycleOwner) {
            binding.repeatingContainer.visibility = View.INVISIBLE
            binding.repeatingExceptionContainer.visibility = View.GONE
            binding.repeatPendingProgressBar.visibility = View.GONE

            when(it) {
                is PendingResult -> {
                    binding.repeatPendingProgressBar.visibility = View.VISIBLE
                }
                is ErrorResult -> {
                    val context = binding.root.context
                    binding.repeatingContainer.visibility = View.INVISIBLE
                    binding.repeatingExceptionContainer.visibility = View.VISIBLE

                    when(it.exception) {
                        is NoWordsToRepeatException -> {
                            binding.repeatingExceptionImage.setImageResource(R.drawable.ic_remember)
                            binding.repeatingExceptionText.text = context.getString(R.string.no_words_to_repeat_exception_title)
                            binding.repeatingExceptionSubtext.text = context.getString(R.string.no_words_to_repeat_exception_subtitle)
                        }
                        is WordsToRepeatCurrentlyInTimeout -> {
                            binding.repeatingExceptionImage.setImageResource(R.drawable.ic_timeout)
                            binding.repeatingExceptionText.text = context.getString(R.string.words_to_repeat_in_timeout_exception_title)
                            binding.repeatingExceptionSubtext.text = context.getString(R.string.words_to_repeat_in_timeout_exception_subtitle)
                        }
                    }
                }
                is SuccessResult -> {
                    binding.repeatingContainer.visibility = View.VISIBLE
                    setWordData(it.data)
                }
            }
        }

        binding.wordToRepeat.setOnClickListener {
            binding.wordAnswer.visibility = View.VISIBLE
        }

        return binding.root
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

//    private fun setCardAnimation() {
//        val scale = requireActivity().applicationContext.resources.displayMetrics.density
//
//        binding.cardFrontContainer.cameraDistance = 8000 * scale
//        binding.cardBackContainer.cameraDistance = 8000 * scale
//
//        frontAnimator = AnimatorInflater.loadAnimator(requireActivity().applicationContext, R.animator.front_card_animator) as AnimatorSet
//        backAnimator = AnimatorInflater.loadAnimator(requireActivity().applicationContext, R.animator.back_card_animator) as AnimatorSet
//
//        binding.cardFrontContainer.setOnClickListener {
//            isFront = if (isFront) {
//                frontAnimator.setTarget(binding.cardFrontContainer)
//                backAnimator.setTarget(binding.cardBackContainer)
//
//                frontAnimator.start()
//                backAnimator.start()
//                false
//            } else {
//                frontAnimator.setTarget(binding.cardBackContainer)
//                backAnimator.setTarget(binding.cardFrontContainer)
//
//                frontAnimator.start()
//                backAnimator.start()
//                true
//            }
//        }
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}