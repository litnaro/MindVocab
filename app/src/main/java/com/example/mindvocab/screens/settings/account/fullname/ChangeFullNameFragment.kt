package com.example.mindvocab.screens.settings.account.fullname

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.mindvocab.R
import com.example.mindvocab.core.BaseFragment
import com.example.mindvocab.core.Result
import com.example.mindvocab.databinding.FragmentSettingsChangeFullNameBinding
import com.example.mindvocab.model.account.etities.FullName
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangeFullNameFragment : BaseFragment() {

    override val viewModel by viewModels<ChangeFullNameViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSettingsChangeFullNameBinding.inflate(inflater, container, false)

        viewModel.accountFullName.observe(viewLifecycleOwner) {
            binding.accountNameField.setText(it.name)
            binding.accountSurnameField.setText(it.surname)
        }

        viewModel.changeFullNameResult.observe(viewLifecycleOwner) {
            when(it) {
                is Result.PendingResult -> {}
                is Result.ErrorResult -> {}
                is Result.SuccessResult -> {
                    binding.accountNameFieldContainer.helperText = requireContext().getString(R.string.full_name_successfully_changed)
                }
            }
        }

        binding.changeFullNameButton.setOnClickListener {
            viewModel.changeFullName(
                FullName(
                    name = binding.accountNameField.text.toString(),
                    surname = binding.accountSurnameField.text.toString()
                )
            )
        }

        return binding.root
    }
}