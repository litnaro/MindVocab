package com.example.mindvocab.screens.settings.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mindvocab.core.BaseViewModel
import com.example.mindvocab.model.SuccessResult
import com.example.mindvocab.model.Result
import com.example.mindvocab.model.account.AccountsRepository
import com.example.mindvocab.model.account.etities.Account
import kotlinx.coroutines.launch

class SettingsAccountViewModel(
    private val accountsRepository: AccountsRepository
) : BaseViewModel() {

    private val _account = MutableLiveData<Result<Account>>()
    val account: LiveData<Result<Account>> = _account

    init {
        viewModelScope.launch {
            accountsRepository.getAccount().collect {
                if (it != null) {
                    _account.value = SuccessResult(it)
                }
            }
        }
    }
}