package com.example.mindvocab.screens.settings.account.edit

import android.app.Dialog
import android.content.Intent
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
import com.example.mindvocab.databinding.DialogDangerousActionBinding
import com.example.mindvocab.databinding.FragmentSettingsAccountBinding
import com.example.mindvocab.model.account.entities.Account
import com.example.mindvocab.screens.initial.InitialActivity
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
                is Result.Pending -> {}
                is Result.Error -> {}
                is Result.Success -> {
                    setAccountData(it.data)
                }
            }
        }

        binding.changePasswordContainer.setOnClickListener {
            findNavController().navigate(AccountEditFragmentDirections.actionAccountEditFragmentToChangePasswordFragment())
        }

        binding.changeUsernameContainer.setOnClickListener {
            findNavController().navigate(AccountEditFragmentDirections.actionAccountEditFragmentToChangeUsernameFragment())
        }

        binding.changeFullNameContainer.setOnClickListener {
            findNavController().navigate(AccountEditFragmentDirections.actionAccountEditFragmentToChangeFullNameFragment())
        }

        binding.logout.setOnClickListener {
            openDangerousActionDialog(title = requireContext().getString(R.string.dialog_title_logout)) {
                viewModel.logout()
                val intent = Intent(requireContext(), InitialActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
            }
        }

        binding.resetAccountProgress.setOnClickListener {
            openDangerousActionDialog(title = requireContext().getString(R.string.dialog_title_reset_account_data)) {
                viewModel.resetAccountProgress()
            }
        }

        return binding.root
    }

    private fun openDangerousActionDialog(title: String, onConfirm: () -> Unit) {
        val dialogBinding = DialogDangerousActionBinding.inflate(layoutInflater)
        val dialog = Dialog(requireContext())

        dialog.setContentView(dialogBinding.root)

        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        dialogBinding.title.text = title

        dialogBinding.confirm.setOnClickListener {
            onConfirm()
            dialog.dismiss()
        }

        dialogBinding.dismiss.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
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