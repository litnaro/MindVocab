package com.example.mindvocab.screens.settings.account.edit

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
import com.example.mindvocab.databinding.FragmentSettingsAccountBinding
import com.example.mindvocab.model.account.etities.Account
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountEditFragment : BaseFragment() {

    override val viewModel by viewModels<AccountEditViewModel>()

    private var _binding: FragmentSettingsAccountBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsAccountBinding.inflate(inflater, container, false)

        viewModel.account.observe(viewLifecycleOwner) {
            when(it) {
                is Result.PendingResult -> {}
                is Result.ErrorResult -> {}
                is Result.SuccessResult -> {
                    setAccountData(it.data)
                }
            }
        }

        binding.changePasswordContainer.setOnClickListener {
            findNavController().navigate(AccountEditFragmentDirections.actionAccountEditFragmentToChangePasswordFragment())
        }

        binding.logout.setOnClickListener {
            viewModel.logout()
        }

        return binding.root
    }

    private fun setAccountData(account: Account) {
        with(binding) {
            accountUsernameSettingValue.text = account.username
            accountEmailSettingValue.text = account.email
            accountFullNameSettingValue.text = requireContext().getString(R.string.account_full_name, account.name, account.surname)

            Glide.with(accountPhoto.context)
                .load(account.photo)
                .circleCrop()
                .placeholder(R.drawable.ic_face)
                .error(R.drawable.ic_face)
                .into(accountPhoto)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}