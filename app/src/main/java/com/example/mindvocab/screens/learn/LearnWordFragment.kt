package com.example.mindvocab.screens.learn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.mindvocab.core.BaseFragment
import com.example.mindvocab.databinding.FragmentLearnWordBinding
import com.example.mindvocab.utils.factory

class LearnWordFragment : BaseFragment() {

    override val viewModel: LearnWordViewModel by viewModels { factory() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = FragmentLearnWordBinding.inflate(inflater, container, false)


        return binding.root
    }

}