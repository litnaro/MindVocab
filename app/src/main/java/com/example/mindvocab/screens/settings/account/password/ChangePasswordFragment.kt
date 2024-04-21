package com.example.mindvocab.screens.settings.account.password

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.mindvocab.R
import com.example.mindvocab.core.BaseFragment
import com.example.mindvocab.core.Result
import com.example.mindvocab.databinding.FragmentSettingsChangePasswordBinding
import com.example.mindvocab.model.AppException
import com.example.mindvocab.model.AuthException
import com.example.mindvocab.model.account.EmptyFieldException
import com.example.mindvocab.model.account.Field
import com.example.mindvocab.model.account.PasswordMismatchException
import com.example.mindvocab.model.account.SameDataModificationException
import com.example.mindvocab.model.account.entities.ChangePasswordData
import com.example.mindvocab.model.account.security.toCharArray
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangePasswordFragment : BaseFragment() {

    override val viewModel by viewModels<ChangePasswordViewModel>()

    private var _binding: FragmentSettingsChangePasswordBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsChangePasswordBinding.inflate(inflater, container, false)

        viewModel.changePasswordResult.observe(viewLifecycleOwner) {
            when(it) {
                is Result.Pending -> {}
                is Result.Error -> handleSignUpErrors(it.exception)
                is Result.Success -> {
                    clearInputFields()
                    binding.oldPasswordFieldContainer.helperText = requireContext().getString(R.string.password_successfully_changed)
                }
            }
        }

        binding.changePasswordButton.setOnClickListener {
            clearErrors()
            viewModel.changePassword(
                ChangePasswordData(
                    oldPassword = binding.oldPasswordField.text.toCharArray(),
                    newPassword = binding.newPasswordField.text.toCharArray(),
                    newPasswordRepeat = binding.newPasswordRepeatField.text.toCharArray()
                )
            )
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun handleSignUpErrors(exception: AppException) {
        with(binding) {
            when(exception) {
                is EmptyFieldException -> {
                    when(exception.field) {
                        Field.Email -> {}
                        Field.Username -> {}
                        Field.Password -> {
                            if (oldPasswordField.text.toString().isBlank()) {
                                oldPasswordFieldContainer.error = requireContext().getString(R.string.fill_all_fields_error_text)
                            }
                            if (newPasswordField.text.toString().isBlank()) {
                                newPasswordFieldContainer.error = requireContext().getString(R.string.fill_all_fields_error_text)
                            }
                            if (newPasswordRepeatField.text.toString().isBlank()) {
                                newPasswordRepeatFieldContainer.error = requireContext().getString(R.string.fill_all_fields_error_text)
                            }
                        }
                    }
                }
                is PasswordMismatchException -> {
                    newPasswordFieldContainer.error = requireContext().getString(R.string.new_passwords_mismatch_error_text)
                    newPasswordRepeatFieldContainer.error = requireContext().getString(R.string.new_passwords_mismatch_error_text)
                }
                is SameDataModificationException -> {
                    newPasswordFieldContainer.error = requireContext().getString(R.string.new_password_equals_old_one_error_text)
                    newPasswordFieldContainer.error = requireContext().getString(R.string.new_password_equals_old_one_error_text)
                }
                is AuthException -> {
                    oldPasswordFieldContainer.error = requireContext().getString(R.string.wrong_old_password_error_text)
                }
            }
        }
    }

    private fun clearInputFields() {
        binding.oldPasswordField.setText("")
        binding.newPasswordField.clearFocus()
        binding.newPasswordField.setText("")
        binding.newPasswordField.clearFocus()
        binding.newPasswordRepeatField.setText("")
        binding.newPasswordRepeatField.clearFocus()
    }

    private fun clearErrors() {
        binding.oldPasswordFieldContainer.error = null
        binding.newPasswordFieldContainer.error = null
        binding.newPasswordRepeatFieldContainer.error = null
    }

}