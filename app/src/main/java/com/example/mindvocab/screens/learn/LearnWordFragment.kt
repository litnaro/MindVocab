package com.example.mindvocab.screens.learn

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.mindvocab.R
import com.example.mindvocab.core.BaseFragment
import com.example.mindvocab.databinding.FragmentLearnWordBinding
import com.example.mindvocab.model.word.Word
import com.example.mindvocab.core.factory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Float.min

class LearnWordFragment : BaseFragment() {

    override val viewModel: LearnWordViewModel by viewModels { factory() }

    private var _binding: FragmentLearnWordBinding? = null
    private val binding get() = _binding!!

    private val onTouchListener = View.OnTouchListener { view, event ->

        val MIN_SWIPE_DISTANCE = -350

        // variables to store current configuration of quote card.
        val displayMetrics = resources.displayMetrics
        val cardWidth = binding.learnWordContainer.width
        val cardStart = (displayMetrics.widthPixels.toFloat() / 2) - (cardWidth / 2)

        when(event.action) {
            MotionEvent.ACTION_UP -> {
                var currentX = binding.learnWordContainer.x
                binding.learnWordContainer.animate()
                    .x(cardStart)
                    .setDuration(150)
                    .setListener(
                        object : AnimatorListenerAdapter() {
                            override fun onAnimationEnd(animation: Animator) {
                                viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Default) {
                                    delay(100)

                                    // check if the swipe distance was more than
                                    // minimum swipe required to load a new quote
                                    if (currentX < MIN_SWIPE_DISTANCE) {
                                        // Add logic to load a new quote if swiped adequately
                                        //Toast.makeText(requireContext(), "Swap action", Toast.LENGTH_SHORT).show()
                                        //viewModel.onWordLearn()
                                        currentX = 0f
                                    }
                                }
                            }
                        }
                    )
                    .start()
                //textView.text = getString(R.string.infoText)
            }

            MotionEvent.ACTION_MOVE -> {
                // get the new co-ordinate of X-axis
                val newX = event.rawX

                // carry out swipe only if newX < cardStart, that is,
                // the card is swiped to the left side, not to the right
                if (newX - cardWidth < cardStart) {
                    binding.learnWordContainer.animate()
                        .x(
                            min(cardStart, newX - (cardWidth / 2))
                        )
                        .setDuration(0)
                        .start()
                    if (binding.learnWordContainer.x < MIN_SWIPE_DISTANCE) {
                        //textView.text = getString(R.string.releaseCard)
                    } else {
                        //textView.text = getString(R.string.infoText
                    }
                }
            }
        }

        // required to by-pass lint warning
        view.performClick()
        return@OnTouchListener true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentLearnWordBinding.inflate(inflater, container, false)

        viewModel.word.observe(viewLifecycleOwner) {
            setWordData(it)
        }

        binding.wordKnownButton.setOnClickListener {
            viewModel.onWordKnown()
        }

        binding.wordToLearnButton.setOnClickListener {
            viewModel.onWordLearn()
        }

        binding.learnWordContainer.setOnTouchListener(onTouchListener)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setWordData(word: Word) {
        with(binding) {
            binding.word.text = word.word
            wordTranscription.text = word.transcription

            Glide.with(wordImage.context)
                .load(word.photo)
                .centerCrop()
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_placeholder)
                .into(wordImage)

            wordTranslation.text = word.translationList.toString()
            wordExplaining.text = word.explanation

            examplesRv.apply {
                adapter = ExampleAdapter(word.exampleList, object : ExampleAdapter.Listener {
                    override fun onSentenceListen(sentence: String) {
                        Toast.makeText(requireContext(), sentence, Toast.LENGTH_SHORT).show()
                    }
                })
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}