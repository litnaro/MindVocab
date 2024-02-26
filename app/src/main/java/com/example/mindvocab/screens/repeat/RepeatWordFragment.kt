package com.example.mindvocab.screens.repeat

import android.animation.AnimatorSet
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.mindvocab.core.BaseFragment
import com.example.mindvocab.core.factory
import com.example.mindvocab.databinding.FragmentRepeatWordBinding
import com.example.mindvocab.model.ErrorResult
import com.example.mindvocab.model.PendingResult
import com.example.mindvocab.model.SuccessResult
import com.example.mindvocab.model.word.entities.WordToRepeat

class RepeatWordFragment : BaseFragment() {

    // https://www.youtube.com/watch?v=XCvejwakoao

    override val viewModel: RepeatWordViewModel by viewModels { factory() }

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
            when(it) {
                is PendingResult -> {

                }
                is ErrorResult -> {

                }
                is SuccessResult -> {
                    setWordData(it.data)
                }
                else -> {

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