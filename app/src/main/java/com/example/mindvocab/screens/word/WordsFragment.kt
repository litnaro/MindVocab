package com.example.mindvocab.screens.word

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mindvocab.databinding.FragmentWordsBinding
import com.example.mindvocab.model.word.learning.entities.WordToLearn
import com.github.javafaker.Faker
import kotlin.random.Random

class WordsFragment : Fragment() {

    private val random = Random(1)
    private val faker = Faker.instance()

    private val wordList = MutableList(7) {
        val id = (it + 1).toLong()
        WordToLearn(
            id = id,
            word = faker.cat().name(),
            audio = "",
            image = "https://source.unsplash.com/random?cat&iddqd=${random.nextInt()}",
            transcription = "[${faker.cat().name()}]",
            explanation = "${faker.cat().name()}; ${faker.cat().name()}",
            translationList = listOf(faker.cat().name(), faker.cat().name(), faker.cat().name()),
            exampleList = listOf(
                faker.lorem().sentence(5, 3),
                faker.lorem().sentence(5, 3),
                faker.lorem().sentence(5, 3),
                faker.lorem().sentence(5, 3),
                faker.lorem().sentence(5, 3)
            ),
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentWordsBinding.inflate(inflater, container, false)
        val wordAdapter = WordAdapter()
        binding.wordRv.apply {
            adapter = wordAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
        wordAdapter.submitList(wordList)
        return binding.root
    }
}