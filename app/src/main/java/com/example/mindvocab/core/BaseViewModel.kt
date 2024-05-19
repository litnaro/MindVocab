package com.example.mindvocab.core

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mindvocab.model.AppException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {

    fun <T> MutableLiveData<Result<T>>.trackActionExecutionNoResult(
        action: suspend() -> Unit
    ) {
        viewModelScope.launch {
            value = Result.Pending
            try {
                action()
            } catch (e: AppException) {
                // TODO log exception
                //e.initCause(e)
                value = Result.Error(e)
            }
        }
    }

    fun <T> MutableLiveData<Result<T>>.trackActionExecutionWithFlowResult(
        action: suspend() -> Flow<T>
    ) {
        viewModelScope.launch {
            value = Result.Pending
            try {
                action().collect {
                    value = Result.Success(it)
                }
            } catch (e: AppException) {
                e.initCause(e)
                value = Result.Error(e)
            }
        }
    }
}