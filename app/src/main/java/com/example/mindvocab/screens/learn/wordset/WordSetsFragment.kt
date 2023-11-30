package com.example.mindvocab.screens.learn.wordset

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mindvocab.R
import com.example.mindvocab.databinding.FragmentWordSetsBinding
import com.example.mindvocab.model.sets.WordSet
import com.example.mindvocab.model.word.Word

class WordSetsFragment : Fragment() {

    private val list = listOf(
        WordSet(
            id = 0,
            name = "Law",
            photo = "",
            wordsList = emptyList()
        ),
        WordSet(
            id = 1,
            name = "Science",
            photo = "",
            wordsList = emptyList()
        ),
        WordSet(
            id = 2,
            name = "Bad",
            photo = "",
            wordsList = emptyList()
        ),
        WordSet(
            id = 3,
            name = "Layout inflater",
            photo = "",
            wordsList = emptyList()
        ),
        WordSet(
            id = 4,
            name = "Verbs",
            photo = "",
            wordsList = emptyList()
        ),
        WordSet(
            id = 5,
            name = "Personality",
            photo = "",
            wordsList = emptyList()
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentWordSetsBinding.inflate(inflater, container, false)

        val wordSetAdapter = WordSetAdapter(object : WordSetAdapter.Listener {
            override fun addWordSetToLearning(wordSet: WordSet) {
                Toast.makeText(requireContext(), "added ${wordSet.name}", Toast.LENGTH_SHORT).show()
            }

            override fun onWordSetDetail(wordSet: WordSet) {
                Toast.makeText(requireContext(), "detail ${wordSet.name}", Toast.LENGTH_SHORT).show()
                findNavController().navigate(WordSetsFragmentDirections.actionWordSetsFragmentToWordsFragment())
            }
        })

        binding.wordSetsRv.apply {
            adapter = wordSetAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }

        wordSetAdapter.submitList(list)

        return binding.root
    }
}