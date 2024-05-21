package com.example.mindvocab.screens.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mindvocab.core.BaseViewModel
import com.example.mindvocab.core.Result
import com.example.mindvocab.model.AppException
import com.example.mindvocab.model.account.AccountsRepository
import com.example.mindvocab.model.account.entities.SignUpData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val accountsRepository: AccountsRepository
) : BaseViewModel() {

    private val _signUpLiveDataResult = MutableLiveData<Result<Boolean>>(Result.Pending)
    val signUpLiveDataResult: LiveData<Result<Boolean>> = _signUpLiveDataResult

    private val _navigateAfterSuccessChannel = Channel<Unit>(Channel.CONFLATED)
    val navigateAfterSuccessFlow = _navigateAfterSuccessChannel.receiveAsFlow()

    fun signUp(signUpData: SignUpData) {
        viewModelScope.launch {
            try {
                accountsRepository.signUp(signUpData)
                _signUpLiveDataResult.value = Result.Success(true)
                _navigateAfterSuccessChannel.send(Unit)
            } catch (e: AppException) {
                _signUpLiveDataResult.value = Result.Error(e)
            }
        }
    }
}