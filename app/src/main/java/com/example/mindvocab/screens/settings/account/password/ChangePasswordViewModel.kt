package com.example.mindvocab.screens.settings.account.password

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mindvocab.core.BaseViewModel
import com.example.mindvocab.core.Result
import com.example.mindvocab.model.AppException
import com.example.mindvocab.model.account.AccountsRepository
import com.example.mindvocab.model.account.entities.ChangePasswordData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val accountsRepository: AccountsRepository
) : BaseViewModel() {

    private val _changePasswordResult = MutableLiveData<Result<Boolean>>()
    val changePasswordResult: LiveData<Result<Boolean>> = _changePasswordResult

    fun changePassword(changePasswordData: ChangePasswordData) {
        viewModelScope.launch {
            try {
                accountsRepository.changePassword(changePasswordData)
                _changePasswordResult.value = Result.Success(true)
            } catch (e: AppException) {
                _changePasswordResult.value = Result.Error(e)
            }
        }
    }
}