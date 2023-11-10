package com.example.mindvocab.screens.repeat

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.mindvocab.R
import com.example.mindvocab.core.BaseFragment
import com.example.mindvocab.core.BaseViewModel
import com.example.mindvocab.core.factory
import com.example.mindvocab.databinding.FragmentRepeatWordBinding

class RepeatWordFragment : BaseFragment() {

    // https://www.youtube.com/watch?v=XCvejwakoao

    override val viewModel: RepeatWordViewModel by viewModels { factory() }

    private var _binding: FragmentRepeatWordBinding? = null
    private val binding get() = _binding!!

    private lateinit var frontAnimator: AnimatorSet
    private lateinit var backAnimator: AnimatorSet
    private var isFront = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRepeatWordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val scale = requireActivity().applicationContext.resources.displayMetrics.density

        binding.cardFrontContainer.cameraDistance = 8000 * scale
        binding.cardBackContainer.cameraDistance = 8000 * scale

        frontAnimator = AnimatorInflater.loadAnimator(requireActivity().applicationContext, R.animator.front_card_animator) as AnimatorSet
        backAnimator = AnimatorInflater.loadAnimator(requireActivity().applicationContext, R.animator.back_card_animator) as AnimatorSet

        binding.cardFrontContainer.setOnClickListener {
            isFront = if (isFront) {
                frontAnimator.setTarget(binding.cardFrontContainer)
                backAnimator.setTarget(binding.cardBackContainer)

                frontAnimator.start()
                backAnimator.start()
                false
            } else {
                frontAnimator.setTarget(binding.cardBackContainer)
                backAnimator.setTarget(binding.cardFrontContainer)

                frontAnimator.start()
                backAnimator.start()
                true
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}