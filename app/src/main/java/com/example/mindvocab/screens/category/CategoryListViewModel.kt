package com.example.mindvocab.screens.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mindvocab.model.caregory.Category
import com.example.mindvocab.model.caregory.CategoryDataRepository
import com.example.mindvocab.model.caregory.CategoryListener
import com.example.mindvocab.model.utils.PendingResult
import com.example.mindvocab.model.utils.Result
import com.example.mindvocab.model.utils.SuccessResult
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CategoryListViewModel(
    private val repository: CategoryDataRepository
) : ViewModel() {

    private val _categoryList = MutableLiveData<Result<List<Category>>>(PendingResult())
    val categoryList: LiveData<Result<List<Category>>> = _categoryList

    private val listener: CategoryListener = {
        _categoryList.postValue(SuccessResult(it))
    }

    init {
        viewModelScope.launch {
            delay(1500)
            repository.addListener(listener)
        }
    }

    fun toggleSelectCategory(category: Category) {
        if (category.isSelected) {
            repository.removeCategoryFromSelected(category)
        } else {
            repository.addCategoryToSelected(category)
        }
    }

    override fun onCleared() {
        super.onCleared()
        repository.removeListener(listener)
    }

}