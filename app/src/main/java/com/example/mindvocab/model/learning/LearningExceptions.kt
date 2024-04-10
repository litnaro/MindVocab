package com.example.mindvocab.model.learning

import com.example.mindvocab.model.AppException

/**
 * Throws in case if all words already learned or none of the word set as selected.
 */
class NoWordsToLearnException : AppException()

/**
 * Throws when more then LearningSettings.WordsADay words were marked as started today.
 */
class NoMoreWordsToLearnForTodayException : AppException()