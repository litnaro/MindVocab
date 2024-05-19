package com.example.mindvocab.screens.initial

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.mindvocab.core.BaseFragment
import com.example.mindvocab.screens.MainActivity
import com.example.mindvocab.screens.MainActivityArgs
import com.example.mindvocab.databinding.FragmentInitialBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InitialFragment : BaseFragment() {

    override val viewModel by viewModels<InitialViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentInitialBinding.inflate(inflater, container, false)

        viewModel.isSignedInLiveData.observe(viewLifecycleOwner) {
            launchMainScreen(it)
        }
        return binding.root
    }

    private fun launchMainScreen(isSignedIn: Boolean) {
        val intent = Intent(requireContext(), MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)

        intent.putExtras(MainActivityArgs(isSignedIn).toBundle())
        startActivity(intent)
    }

}