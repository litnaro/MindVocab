package com.example.mindvocab.screens.settings.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mindvocab.core.BaseViewModel
import com.example.mindvocab.core.Result
import com.example.mindvocab.model.account.AccountsRepository
import com.example.mindvocab.model.account.entities.Account
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsAccountViewModel @Inject constructor(
    private val accountsRepository: AccountsRepository
) : BaseViewModel() {

    private val _accountLiveDataResult = MutableLiveData<Result<Account>>()
    val accountLiveDataResult: LiveData<Result<Account>> = _accountLiveDataResult

    init {
        viewModelScope.launch {
            accountsRepository.getAccount().collect {
                if (it != null) {
                    _accountLiveDataResult.value = Result.Success(it)
                }
            }
        }
    }
}