package com.example.mindvocab.screens.settings.account.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.mindvocab.R
import com.example.mindvocab.core.BaseFragment
import com.example.mindvocab.core.factory
import com.example.mindvocab.databinding.FragmentAccountEditBinding
import com.example.mindvocab.model.ErrorResult
import com.example.mindvocab.model.PendingResult
import com.example.mindvocab.model.SuccessResult
import com.example.mindvocab.model.account.etities.Account

class AccountEditFragment : BaseFragment() {

    override val viewModel: AccountEditViewModel by viewModels { factory() }

    private var _binding: FragmentAccountEditBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountEditBinding.inflate(inflater, container, false)

        viewModel.account.observe(viewLifecycleOwner) {
            when(it) {
                is PendingResult -> {}
                is ErrorResult -> {}
                is SuccessResult -> {
                    setAccountData(it.data)
                }
            }
        }

        return binding.root
    }

    private fun setAccountData(account: Account) {
        with(binding) {
            accountEmail.setText(account.email)
            accountUsername.setText(account.username)
            accountName.setText(account.name)
            accountSurname.setText(account.surname)

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