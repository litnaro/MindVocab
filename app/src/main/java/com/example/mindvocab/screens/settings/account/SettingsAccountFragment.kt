package com.example.mindvocab.screens.settings.account

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.mindvocab.R
import com.example.mindvocab.databinding.FragmentSettingsAccountBinding
import com.example.mindvocab.screens.settings.SettingsFragmentDirections

class SettingsAccountFragment : Fragment() {

    private var _binding: FragmentSettingsAccountBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsAccountBinding.inflate(inflater, container, false)
        //prepareAccountSettings(true)

        return binding.root
    }

//    private fun prepareAccountSettings(isUserLogin: Boolean) {
//        with(binding) {
//            if (isUserLogin) {
//                userFullName.visibility = View.VISIBLE
//                signUpSuggestion.visibility = View.GONE
//
//                changePasswordButton.visibility = View.VISIBLE
//                changePasswordButton.setOnClickListener {
//                    findNavController().navigate(SettingsFragmentDirections.actionSettingsToChangePasswordFragment())
//                }
//
//                editProfileButton.visibility = View.VISIBLE
//
//                signUpButton.visibility = View.GONE
//                signInButton.visibility = View.GONE
//            } else {
//                userFullName.visibility = View.GONE
//                signUpSuggestion.visibility = View.VISIBLE
//
//                changePasswordButton.visibility = View.GONE
//                editProfileButton.visibility = View.GONE
//
//                signUpButton.visibility = View.VISIBLE
//                signInButton.visibility = View.VISIBLE
//            }
//        }
//    }

    private fun setUserData() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}