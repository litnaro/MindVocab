package com.example.mindvocab.screens.browser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mindvocab.databinding.FragmentBrowserBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BrowserFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentBrowserBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

}