package com.example.mindvocab.screens.settings.account.edit

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
class AccountEditViewModel @Inject constructor(
    private val accountsRepository: AccountsRepository
) : BaseViewModel() {

    private val _accountLiveDataResult = MutableLiveData<Result<Account>>(Result.Pending)
    val accountLiveDataResult: LiveData<Result<Account>> get() = _accountLiveDataResult

    init {
        viewModelScope.launch {
            accountsRepository.getAccount().collect {
                if (it != null) {
                    _accountLiveDataResult.value = Result.Success(it)
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            accountsRepository.logout()
        }
    }

    fun resetAccountProgress() {
        viewModelScope.launch {
            accountsRepository.resetAccountProgress()
        }
    }
}