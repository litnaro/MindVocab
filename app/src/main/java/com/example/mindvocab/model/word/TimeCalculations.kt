package com.example.mindvocab.model.word

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object TimeCalculations {

    fun convertMillisToDateString(millis: Long): String {
        if (millis == 0L) return "-"
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val date = Date(millis)
        return dateFormat.format(date)
    }
}