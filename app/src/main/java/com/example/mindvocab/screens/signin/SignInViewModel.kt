package com.example.mindvocab.screens.signin

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
class SignInViewModel @Inject constructor(
    private val accountsRepository: AccountsRepository
) : BaseViewModel() {

    private val _signInResult = MutableLiveData<Result<Long>>(Result.Pending)
    val signInResult: LiveData<Result<Long>> = _signInResult

    fun signIn(login: String, password: CharArray) {
        viewModelScope.launch {
            try {
                _signInResult.value = Result.Success(accountsRepository.signIn(login, password))
            } catch (e: AppException) {
                _signInResult.value = Result.Error(e)
            }
        }
    }
}