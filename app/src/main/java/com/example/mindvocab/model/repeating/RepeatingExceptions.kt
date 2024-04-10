package com.example.mindvocab.model.repeating

import com.example.mindvocab.model.AppException

/**
 * If no words with were started or all previous words already learned.
 */
class NoWordsToRepeatException : AppException()

/**
 * If there are words to be repeated, but only a few time past since last repeat.
 */
class WordsToRepeatCurrentlyInTimeout : AppException()