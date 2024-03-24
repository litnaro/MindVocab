package com.example.mindvocab

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.mindvocab.databinding.FragmentSignInBinding

class SignInFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSignInBinding.inflate(inflater, container, false)

        binding.signInButton.setOnClickListener {
            findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToTabsFragment())
        }

        return binding.root
    }

}