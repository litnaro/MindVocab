package com.example.mindvocab.screens.signin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mindvocab.R
import com.example.mindvocab.core.BaseFragment
import com.example.mindvocab.core.Result
import com.example.mindvocab.databinding.FragmentSignInBinding
import com.example.mindvocab.model.AppException
import com.example.mindvocab.model.AuthException
import com.example.mindvocab.model.account.EmptyFieldException
import com.example.mindvocab.model.account.Field
import com.example.mindvocab.model.account.security.toCharArray
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : BaseFragment() {

    override val viewModel by viewModels<SignInViewModel>()

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)

        binding.createAccountButton.setOnClickListener {
            findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToSignUpFragment())
        }

        binding.signInButton.setOnClickListener {
            clearErrors()
            viewModel.signIn(
                login = binding.loginField.text.toString(),
                password = binding.passwordField.text.toCharArray()
            )
        }

        viewModel.signInResult.observe(viewLifecycleOwner) {
            when(it) {
                is Result.Pending -> {}
                is Result.Error -> handleSignInErrors(it.exception)
                is Result.Success -> {
                    findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToTabsFragment())
                }
            }
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun handleSignInErrors(exception: AppException) {
        when(exception) {
            is EmptyFieldException -> {
                when(exception.field) {
                    Field.Email, Field.Username -> binding.loginFieldContainer.error = requireContext().getString(R.string.empty_login_error_text)
                    Field.Password -> binding.passwordFieldContainer.error = requireContext().getString(R.string.empty_password_error_text)
                }
            }
            is AuthException -> {
                binding.loginFieldContainer.error = requireContext().getString(R.string.no_such_account_error_text)
            }
        }
    }

    private fun clearErrors() {
        binding.loginFieldContainer.error = null
        binding.passwordFieldContainer.error = null
    }

}