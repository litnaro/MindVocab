package com.example.mindvocab.core

import androidx.fragment.app.Fragment
import com.example.mindvocab.model.AppException

abstract class BaseFragment : Fragment() {

    abstract val viewModel: BaseViewModel

    fun <T> observeSideEffects(
        result: Result<T>,
        onReset: () -> Unit = {},
        onPending: () -> Unit = {},
        onError: (AppException) -> Unit = {},
        onSuccess: (Result.Success<T>) -> Unit
    ) {
        onReset()
        when(result) {
            is Result.Pending -> onPending()
            is Result.Error -> onError(result.exception)
            is Result.Success -> onSuccess(result)
        }
    }

}