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
import com.example.mindvocab.model.account.entities.FullName
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangeFullNameFragment : BaseFragment() {

    override val viewModel by viewModels<ChangeFullNameViewModel>()

    private var _binding: FragmentSettingsChangeFullNameBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsChangeFullNameBinding.inflate(inflater, container, false)

        initialBinding()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initialBinding() {
        viewModel.accountFullNameLiveData.observe(viewLifecycleOwner) {
            binding.accountNameField.setText(it.name)
            binding.accountSurnameField.setText(it.surname)
        }

        viewModel.changeFullNameLiveDataResult.observe(viewLifecycleOwner) {
            observeSideEffects(
                result = it,
                onSuccess = ::accountNameSuccessResult
            )
        }

        binding.changeFullNameButton.setOnClickListener {
            viewModel.changeFullName(
                FullName(
                    name = binding.accountNameField.text.toString(),
                    surname = binding.accountSurnameField.text.toString()
                )
            )
        }
    }

    @Suppress("UNUSED_PARAMETER")
    private fun <T> accountNameSuccessResult(result: Result.Success<T>) {
        binding.accountNameFieldContainer.helperText = requireContext().getString(R.string.full_name_successfully_changed)
    }
}