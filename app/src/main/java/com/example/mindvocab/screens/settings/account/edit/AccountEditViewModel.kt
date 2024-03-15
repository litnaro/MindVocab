package com.example.mindvocab.screens.settings.account.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mindvocab.core.BaseViewModel
import com.example.mindvocab.core.Result
import com.example.mindvocab.model.account.AccountsRepository
import com.example.mindvocab.model.account.etities.Account
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountEditViewModel @Inject constructor(
    accountsRepository: AccountsRepository
) : BaseViewModel() {

    private val _account = MutableLiveData<Result<Account>>(Result.PendingResult())
    val account: LiveData<Result<Account>> get() = _account

    init {
        viewModelScope.launch {
            accountsRepository.getAccount().collect {
                if (it != null) {
                    _account.value = Result.SuccessResult(it)
                }
            }
        }
    }
}