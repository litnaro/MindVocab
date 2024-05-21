package com.example.mindvocab.screens.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mindvocab.core.BaseViewModel
import com.example.mindvocab.core.Result
import com.example.mindvocab.model.AppException
import com.example.mindvocab.model.account.AccountsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val accountsRepository: AccountsRepository
) : BaseViewModel() {

    private val _signInLiveDataResult = MutableLiveData<Result<Long>>(Result.Pending)
    val signInLiveDataResult: LiveData<Result<Long>> = _signInLiveDataResult

    private val _navigateAfterSuccessChannel = Channel<Unit>(Channel.CONFLATED)
    val navigateAfterSuccessFlow = _navigateAfterSuccessChannel.receiveAsFlow()

    fun signIn(login: String, password: CharArray) {
        viewModelScope.launch {
            try {
                _signInLiveDataResult.value = Result.Success(accountsRepository.signIn(login, password))
                navigateAfterSuccess()
            } catch (e: AppException) {
                _signInLiveDataResult.value = Result.Error(e)
            }
        }
    }

    private fun navigateAfterSuccess() {
        viewModelScope.launch {
            _navigateAfterSuccessChannel.send(Unit)
        }
    }

}