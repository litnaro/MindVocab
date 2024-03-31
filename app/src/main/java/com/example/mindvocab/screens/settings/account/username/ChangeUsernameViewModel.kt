package com.example.mindvocab.screens.settings.account.username

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mindvocab.core.BaseViewModel
import com.example.mindvocab.core.Result
import com.example.mindvocab.model.AppException
import com.example.mindvocab.model.account.AccountsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangeUsernameViewModel @Inject constructor(
    private val accountsRepository: AccountsRepository
) : BaseViewModel() {

    private val _changePasswordResult = MutableLiveData<Result<Boolean>>()
    val changePasswordResult: LiveData<Result<Boolean>> = _changePasswordResult

    private val _accountUsername = MutableLiveData<String>()
    val accountUsername: LiveData<String> = _accountUsername

    init {
        getCurrentUsername()
    }

    fun changeUsername(username: String) {
        viewModelScope.launch {
            try {
                accountsRepository.updateUsername(username)
                _changePasswordResult.value = Result.SuccessResult(true)
            } catch (e: AppException) {
                _changePasswordResult.value = Result.ErrorResult(e)
            }
        }
    }

    private fun getCurrentUsername() {
        viewModelScope.launch {
            _accountUsername.value = accountsRepository.getAccountUsername()
        }
    }
}