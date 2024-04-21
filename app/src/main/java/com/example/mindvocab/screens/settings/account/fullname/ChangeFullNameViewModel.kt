package com.example.mindvocab.screens.settings.account.fullname

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mindvocab.core.BaseViewModel
import com.example.mindvocab.core.Result
import com.example.mindvocab.model.account.AccountsRepository
import com.example.mindvocab.model.account.entities.FullName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangeFullNameViewModel @Inject constructor(
    private val accountsRepository: AccountsRepository
) : BaseViewModel() {

    private val _changeFullNameResult = MutableLiveData<Result<Boolean>>()
    val changeFullNameResult: LiveData<Result<Boolean>> = _changeFullNameResult

    private val _accountFullName = MutableLiveData<FullName>()
    val accountFullName: LiveData<FullName> = _accountFullName

    init {
        getFullName()
    }

    fun changeFullName(fullName: FullName) {
        viewModelScope.launch {
            accountsRepository.updateFullName(fullName)
            _changeFullNameResult.value = Result.Success(true)
        }
    }

    private fun getFullName() {
        viewModelScope.launch {
            _accountFullName.value = accountsRepository.getAccountFullName()
        }
    }

}