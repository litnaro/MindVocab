package com.example.mindvocab.screens.initial

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mindvocab.core.BaseViewModel
import com.example.mindvocab.model.account.AccountsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InitialViewModel @Inject constructor(
    private val accountsRepository: AccountsRepository
) : BaseViewModel() {

    private val _isSignedInLiveData = MutableLiveData<Boolean>()
    val isSignedInLiveData: LiveData<Boolean> = _isSignedInLiveData

    init {
        viewModelScope.launch {
            _isSignedInLiveData.value = accountsRepository.isSignedIn()
        }
    }
}