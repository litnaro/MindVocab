package com.example.mindvocab.screens.settings.account.username

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.mindvocab.R
import com.example.mindvocab.core.BaseFragment
import com.example.mindvocab.core.Result
import com.example.mindvocab.databinding.FragmentSettingsChangeUsernameBinding
import com.example.mindvocab.model.SameDataModificationException
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangeUsernameFragment : BaseFragment() {

    override val viewModel by viewModels<ChangeUsernameViewModel>()

    private var _binding: FragmentSettingsChangeUsernameBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsChangeUsernameBinding.inflate(inflater, container, false)

        viewModel.accountUsername.observe(viewLifecycleOwner) {
            binding.accountUsernameField.setText(it)
        }

        viewModel.changePasswordResult.observe(viewLifecycleOwner) {
            when(it) {
                is Result.PendingResult -> {}
                is Result.ErrorResult -> {
                    if (it.exception is SameDataModificationException) {
                        binding.accountUsernameFieldContainer.error = requireContext().getString(R.string.new_username_equals_old_one_error_text)
                    }
                }
                is Result.SuccessResult -> {
                    binding.accountUsernameFieldContainer.helperText = requireContext().getString(R.string.username_successfully_changed)
                }
            }
        }

        binding.changeUsernameButton.setOnClickListener {
            binding.accountUsernameFieldContainer.error = null
            viewModel.changeUsername(
                binding.accountUsernameField.text.toString()
            )
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}