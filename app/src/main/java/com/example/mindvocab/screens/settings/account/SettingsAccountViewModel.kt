package com.example.mindvocab.screens.settings.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mindvocab.core.BaseViewModel
import com.example.mindvocab.model.SuccessResult
import com.example.mindvocab.model.Result
import com.example.mindvocab.model.account.AccountRepository
import com.example.mindvocab.model.account.etities.Account
import kotlinx.coroutines.launch

class SettingsAccountViewModel(
    private val accountRepository: AccountRepository
) : BaseViewModel() {

    private val _account = MutableLiveData<Result<Account>>()
    val account: LiveData<Result<Account>> = _account

    init {
        viewModelScope.launch {
            accountRepository.getAccount().collect {
                if (it != null) {
                    _account.value = SuccessResult(it)
                }
            }
        }
    }
}