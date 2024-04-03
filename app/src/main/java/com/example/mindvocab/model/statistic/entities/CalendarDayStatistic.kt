package com.example.mindvocab.model.statistic.entities

import java.util.Calendar

data class CalendarDayStatistic (
    val day: Calendar,
    val isStartedNewWords: Boolean,
    val isRepeatedOldWords: Boolean
)
