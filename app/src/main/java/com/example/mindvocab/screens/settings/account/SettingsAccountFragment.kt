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
import com.example.mindvocab.core.Result
import com.example.mindvocab.databinding.FragmentSettingsAccountPreviewBinding
import com.example.mindvocab.model.account.etities.Account
import com.example.mindvocab.screens.settings.SettingsFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsAccountFragment : BaseFragment() {

    override val viewModel by viewModels<SettingsAccountViewModel>()

    private var _binding: FragmentSettingsAccountPreviewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsAccountPreviewBinding.inflate(inflater, container, false)

        viewModel.account.observe(viewLifecycleOwner) {
            when(it) {
                is Result.PendingResult -> {

                }
                is Result.ErrorResult -> {

                }
                is Result.SuccessResult -> {
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