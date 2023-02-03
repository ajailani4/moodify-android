package com.ajailani.moodify.ui.feature.add_edit_mood.component

import android.app.TimePickerDialog
import android.content.Context
import com.ajailani.moodify.util.currentHour
import com.ajailani.moodify.util.currentMinute

fun Context.showTimePicker(onTimeChanged: (String) -> Unit) {
    TimePickerDialog(
        this,
        { _, hour, minute ->
            onTimeChanged(String.format("%02d:%02d", hour, minute))
        },
        currentHour,
        currentMinute,
        true
    ).show()
}