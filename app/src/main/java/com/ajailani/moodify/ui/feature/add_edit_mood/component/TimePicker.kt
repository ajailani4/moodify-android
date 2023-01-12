package com.ajailani.moodify.ui.feature.add_edit_mood.component

import android.app.TimePickerDialog
import android.content.Context
import java.util.*

fun Context.showTimePicker(onTimeChanged: (String) -> Unit) {
    val calendar = Calendar.getInstance()
    val calHour = calendar.get(Calendar.HOUR_OF_DAY)
    val calMinute = calendar.get(Calendar.MINUTE)

    TimePickerDialog(
        this,
        { _, hour, minute ->
            onTimeChanged(String.format("%02d:%02d", hour, minute))
        },
        calHour,
        calMinute,
        true
    ).show()
}