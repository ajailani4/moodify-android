package com.ajailani.moodify.ui.feature.add_edit_mood.component

import android.app.DatePickerDialog
import android.content.Context
import com.ajailani.moodify.util.currentDay
import com.ajailani.moodify.util.currentMonth
import com.ajailani.moodify.util.currentYear

fun Context.showDatePicker(onDateChanged: (String) -> Unit) {
    DatePickerDialog(
        this,
        { _, year, month, dayOfMonth ->
            onDateChanged(String.format("%02d-%02d-%02d", year, month + 1, dayOfMonth))
        },
        currentYear,
        currentMonth,
        currentDay
    ).show()
}