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

    private val _changePasswordLiveDataResult = MutableLiveData<Result<Boolean>>()
    val changePasswordLiveDataResult: LiveData<Result<Boolean>> = _changePasswordLiveDataResult

    fun changePassword(changePasswordData: ChangePasswordData) {
        viewModelScope.launch {
            try {
                accountsRepository.changePassword(changePasswordData)
                _changePasswordLiveDataResult.value = Result.Success(true)
            } catch (e: AppException) {
                _changePasswordLiveDataResult.value = Result.Error(e)
            }
        }
    }
}