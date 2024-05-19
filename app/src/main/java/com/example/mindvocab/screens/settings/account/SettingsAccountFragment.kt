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
import com.example.mindvocab.model.account.entities.Account
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

        initialBinding()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initialBinding() {
        viewModel.accountLiveDataResult.observe(viewLifecycleOwner) {
            observeSideEffects(
                result = it,
                onSuccess = ::accountSuccessResult
            )
        }

        binding.accountEditButton.setOnClickListener {
            findNavController().navigate(SettingsFragmentDirections.actionSettingsFragmentToAccountEditFragment())
        }
    }

    private fun <T> accountSuccessResult(result: Result.Success<T>) {
        setAccountData(result.data as Account)
    }

    private fun setAccountData(account: Account) {
        if (account.name.isBlank() || account.email.isBlank()) {
            binding.accountFullName.text = account.username
        } else {
            binding.accountFullName.text = requireContext().getString(R.string.account_full_name, account.name, account.surname)
        }
        binding.accountEmail.text = account.email

        Glide.with(binding.accountPhoto.context)
            .load(account.photo)
            .circleCrop()
            .placeholder(R.drawable.ic_face)
            .error(R.drawable.ic_face)
            .into(binding.accountPhoto)
    }

}