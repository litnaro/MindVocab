package com.example.mindvocab.screens.signin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mindvocab.R
import com.example.mindvocab.core.BaseFragment
import com.example.mindvocab.databinding.FragmentSignInBinding
import com.example.mindvocab.model.AppException
import com.example.mindvocab.model.AuthException
import com.example.mindvocab.model.account.EmptyFieldException
import com.example.mindvocab.model.account.Field
import com.example.mindvocab.model.account.security.toCharArray
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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

        initialBinding()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initialBinding() {
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

        viewModel.signInLiveDataResult.observe(viewLifecycleOwner) {
            observeSideEffects(
                result = it,
                onError = ::signInErrorResult
            )
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.navigateAfterSuccessFlow.collect {
                findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToTabsFragment())
            }
         }
    }

    private fun signInErrorResult(exception: AppException) {
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