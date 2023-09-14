package com.example.mindvocab.model.caregory

import com.example.mindvocab.model.Repository

interface CategoryRepository : Repository {

    fun getSelectedCategories() : List<Category>

    fun removeCategoryFromSelected(category: Category) : Boolean

    fun addCategoryToSelected(category: Category) : Boolean

    fun addListener(listener: CategoryListener)

    fun removeListener(listener: CategoryListener)

    fun notifyUpdates()
}