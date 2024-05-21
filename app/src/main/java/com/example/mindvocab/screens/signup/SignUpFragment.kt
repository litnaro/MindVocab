package com.example.mindvocab.screens.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mindvocab.R
import com.example.mindvocab.core.BaseFragment
import com.example.mindvocab.databinding.FragmentSignUpBinding
import com.example.mindvocab.model.account.AccountAlreadyExistsException
import com.example.mindvocab.model.AppException
import com.example.mindvocab.model.account.EmptyFieldException
import com.example.mindvocab.model.account.Field
import com.example.mindvocab.model.account.PasswordMismatchException
import com.example.mindvocab.model.account.entities.SignUpData
import com.example.mindvocab.model.account.security.toCharArray
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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

        viewModel.signUpLiveDataResult.observe(viewLifecycleOwner) {
            observeSideEffects(
                result = it,
                onError = ::signUpErrorResult
            )
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.navigateAfterSuccessFlow.collect {
                findNavController().popBackStack()
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun signUpErrorResult(exception: AppException) {
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