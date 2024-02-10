package com.example.mindvocab.screens.settings.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.mindvocab.R
import com.example.mindvocab.core.BaseFragment
import com.example.mindvocab.core.factory
import com.example.mindvocab.databinding.FragmentSettingsAccountBinding
import com.example.mindvocab.model.ErrorResult
import com.example.mindvocab.model.PendingResult
import com.example.mindvocab.model.SuccessResult
import com.example.mindvocab.model.account.etities.Account
import com.example.mindvocab.screens.settings.SettingsFragmentDirections

class SettingsAccountFragment : BaseFragment() {

    override val viewModel: SettingsAccountViewModel by viewModels { factory() }

    private var _binding: FragmentSettingsAccountBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsAccountBinding.inflate(inflater, container, false)

        viewModel.account.observe(viewLifecycleOwner) {
            when(it) {
                is PendingResult -> {

                }
                is ErrorResult -> {

                }
                is SuccessResult -> {
                    setAccountData(it.data)
                }
            }
        }

        binding.accountEditButton.setOnClickListener {
            findNavController().navigate(SettingsFragmentDirections.actionSettingsToAccountEditFragment())
        }

        return binding.root
    }

    private fun setAccountData(account: Account) {
        binding.accountFullName.text = requireContext().getString(R.string.account_full_name, account.name, account.surname)
        binding.accountEmail.text = account.email

        Glide.with(binding.accountPhoto.context)
            .load(account.photo)
            .circleCrop()
            .placeholder(R.drawable.ic_face)
            .error(R.drawable.ic_face)
            .into(binding.accountPhoto)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}