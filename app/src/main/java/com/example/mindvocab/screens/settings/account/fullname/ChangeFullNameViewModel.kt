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

    private val _changeFullNameLiveDataResult = MutableLiveData<Result<Boolean>>()
    val changeFullNameLiveDataResult: LiveData<Result<Boolean>> = _changeFullNameLiveDataResult

    private val _accountFullNameLiveData = MutableLiveData<FullName>()
    val accountFullNameLiveData: LiveData<FullName> = _accountFullNameLiveData

    init {
        getFullName()
    }

    fun changeFullName(fullName: FullName) {
        viewModelScope.launch {
            accountsRepository.updateFullName(fullName)
            _changeFullNameLiveDataResult.value = Result.Success(true)
        }
    }

    private fun getFullName() {
        viewModelScope.launch {
            _accountFullNameLiveData.value = accountsRepository.getAccountFullName()
        }
    }

}