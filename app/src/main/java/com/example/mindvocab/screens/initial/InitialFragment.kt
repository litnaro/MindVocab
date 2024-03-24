package com.example.mindvocab.screens.initial

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mindvocab.MainActivity
import com.example.mindvocab.MainActivityArgs
import com.example.mindvocab.R
import com.example.mindvocab.databinding.FragmentInitialBinding

class InitialFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentInitialBinding.inflate(inflater, container, false)
        launchMainScreen(false)
        return binding.root
    }

    private fun launchMainScreen(isSignedIn: Boolean) {
        val intent = Intent(requireContext(), MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)

        intent.putExtras(MainActivityArgs(isSignedIn).toBundle())
        startActivity(intent)
    }

}