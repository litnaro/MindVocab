package com.example.mindvocab.screens.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mindvocab.R
import com.example.mindvocab.core.BaseFragment
import com.example.mindvocab.core.Result
import com.example.mindvocab.databinding.FragmentSignUpBinding
import com.example.mindvocab.model.account.AccountAlreadyExistsException
import com.example.mindvocab.model.AppException
import com.example.mindvocab.model.account.EmptyFieldException
import com.example.mindvocab.model.account.Field
import com.example.mindvocab.model.account.PasswordMismatchException
import com.example.mindvocab.model.account.entities.SignUpData
import com.example.mindvocab.model.account.security.toCharArray
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : BaseFragment() {

    override val viewModel by viewModels<SignUpViewModel>()

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)

        binding.signUpButton.setOnClickListener {
            clearErrors()
            viewModel.signUp(
                SignUpData(
                    email = binding.emailField.text.toString(),
                    username = binding.usernameField.text.toString(),
                    password = binding.passwordField.text.toCharArray(),
                    repeatPassword = binding.passwordRepeatField.text.toCharArray()
                )
            )
        }

        binding.accountExistsButton.setOnClickListener {
            findNavController().popBackStack()
        }

        viewModel.signUpResult.observe(viewLifecycleOwner) {
            when(it) {
                is Result.Pending -> {}
                is Result.Error -> {
                    handleSignUpErrors(it.exception)
                }
                is Result.Success -> {
                    findNavController().popBackStack()
                }
            }
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun handleSignUpErrors(exception: AppException) {
        when(exception) {
            is EmptyFieldException -> {
                when(exception.field) {
                    Field.Email -> binding.emailFieldContainer.error = requireContext().getString(R.string.empty_email_error_text)
                    Field.Username -> binding.usernameFieldContainer.error = requireContext().getString(R.string.empty_username_error_text)
                    Field.Password -> {
                        binding.passwordFieldContainer.error = requireContext().getString(R.string.empty_password_error_text)
                        binding.passwordRepeatContainer.error = requireContext().getString(R.string.empty_password_error_text)
                    }
                }
            }
            is AccountAlreadyExistsException -> {
                binding.emailFieldContainer.error = requireContext().getString(R.string.account_already_exists_error_text)
            }
            is PasswordMismatchException -> {
                binding.passwordFieldContainer.error = requireContext().getString(R.string.password_mismatch_error_text)
                binding.passwordRepeatContainer.error = requireContext().getString(R.string.password_mismatch_error_text)
            }
        }
    }

    private fun clearErrors() {
        binding.emailFieldContainer.error = null
        binding.usernameFieldContainer.error = null
        binding.passwordFieldContainer.error = null
        binding.passwordRepeatContainer.error = null
    }
}