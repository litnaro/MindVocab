package com.example.mindvocab.screens.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mindvocab.core.BaseViewModel
import com.example.mindvocab.core.Result
import com.example.mindvocab.model.AppException
import com.example.mindvocab.model.account.AccountsRepository
import com.example.mindvocab.model.account.etities.SignUpData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val accountsRepository: AccountsRepository
) : BaseViewModel() {

    private val _signUpResult = MutableLiveData<Result<Boolean>>(Result.PendingResult())
    val signUpResult: LiveData<Result<Boolean>> = _signUpResult

    fun signUp(signUpData: SignUpData) {
        viewModelScope.launch {
            try {
                accountsRepository.signUp(signUpData)
                _signUpResult.value = Result.SuccessResult(true)
            } catch (e: AppException) {
                _signUpResult.value = Result.ErrorResult(e)
            }
        }
    }
}