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

    private val _changePasswordLiveDataResult = MutableLiveData<Result<Boolean>>()
    val changePasswordLiveDataResult: LiveData<Result<Boolean>> = _changePasswordLiveDataResult

    private val _accountUsernameLiveData = MutableLiveData<String>()
    val accountUsernameLiveData: LiveData<String> = _accountUsernameLiveData

    init {
        getCurrentUsername()
    }

    fun changeUsername(username: String) {
        viewModelScope.launch {
            try {
                accountsRepository.updateUsername(username)
                _changePasswordLiveDataResult.value = Result.Success(true)
            } catch (e: AppException) {
                _changePasswordLiveDataResult.value = Result.Error(e)
            }
        }
    }

    private fun getCurrentUsername() {
        viewModelScope.launch {
            _accountUsernameLiveData.value = accountsRepository.getAccountUsername()
        }
    }
}