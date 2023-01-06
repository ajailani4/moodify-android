package com.ajailani.moodify.util

import java.text.SimpleDateFormat
import java.util.*

class Formatter {
    companion object {
        fun formatDate(strDate: String): String {
            val locale = Locale(Locale.getDefault().displayLanguage, "ID")
            val date = SimpleDateFormat("yyyy-MM-dd", locale).parse(strDate)

            return SimpleDateFormat("dd MMM yyyy", locale).format(date!!)
        }
    }
}