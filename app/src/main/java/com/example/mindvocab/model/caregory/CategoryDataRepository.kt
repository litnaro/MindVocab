package com.example.mindvocab.model.caregory

import com.example.mindvocab.model.word.Word
import com.github.javafaker.Faker
import kotlin.random.Random

typealias CategoryListener = (categories: List<Category>) -> Unit

class CategoryDataRepository : CategoryRepository {

    private val random = Random(1)
    private val faker = Faker.instance()

    private val listeners = mutableSetOf<CategoryListener>()

    private var categoryList = MutableList(20) {
        val id = it + 1
        Category(
            id = id,
            name = faker.cat().name(),
            icon = "https://source.unsplash.com/random?cat&iddqd=${random.nextInt()}",
            words = MutableList(random.nextInt(4, 10)) {
                val id = it + 1
                Word(
                    id = id,
                    word = faker.company().name(),
                    transcription = faker.company().profession(),
                    photo = "https://source.unsplash.com/random?cat&iddqd=${random.nextInt()}",
                    audio = "",
                    usage = listOf(
                        faker.lorem().paragraph(1),
                        faker.lorem().paragraph(1),
                        faker.lorem().paragraph(1),
                    )
                )
            }.toList(),
            wordsKnown = MutableList(random.nextInt(4, 10)) {
                val id = it + 1
                Word(
                    id = id,
                    word = faker.company().name(),
                    transcription = faker.company().profession(),
                    photo = "https://source.unsplash.com/random?cat&iddqd=${random.nextInt()}",
                    audio = "",
                    usage = listOf(
                        faker.lorem().paragraph(1),
                        faker.lorem().paragraph(1),
                        faker.lorem().paragraph(1),
                    )
                )
            }.toList(),
            isSelected = random.nextBoolean()
        )
    }

    override fun getSelectedCategories(): List<Category> {
        return categoryList
    }

    override fun removeCategoryFromSelected(category: Category): Boolean {
        val indexToRemove = categoryList.indexOf(category)
        if (indexToRemove != -1 && category.isSelected) {
            categoryList = ArrayList(categoryList)
            categoryList[indexToRemove] = categoryList[indexToRemove].copy(isSelected = false)
            notifyUpdates()
            return true
        }
        return false
    }

    override fun addCategoryToSelected(category: Category): Boolean {
        val indexToRemove = categoryList.indexOf(category)
        if (indexToRemove != -1 && !category.isSelected) {
            categoryList = ArrayList(categoryList)
            categoryList[indexToRemove] = categoryList[indexToRemove].copy(isSelected = true)
            notifyUpdates()
            return true
        }
        return false
    }

    override fun addListener(listener: CategoryListener) {
        listeners.add(listener)
        listener.invoke(categoryList)
    }

    override fun removeListener(listener: CategoryListener) {
        listeners.remove(listener)
    }

    override fun notifyUpdates() {
        listeners.forEach { it.invoke(categoryList) }
    }
}