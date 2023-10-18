package com.example.mindvocab.screens.learn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.mindvocab.R
import com.example.mindvocab.core.BaseFragment
import com.example.mindvocab.databinding.FragmentLearnWordBinding
import com.example.mindvocab.model.word.Word
import com.example.mindvocab.core.factory

class LearnWordFragment : BaseFragment() {

    override val viewModel: LearnWordViewModel by viewModels { factory() }

    private var _binding: FragmentLearnWordBinding? = null
    private val binding get() = _binding!!

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

        return binding.root
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