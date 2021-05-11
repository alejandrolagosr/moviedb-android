package com.flagos.common

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private const val NO_DATE = ""
const val NORMAL_DATE = "yyyy-MM-dd"
const val UI_DATE = "MMMM dd, yyyy"
const val PREFERENCE_FORCED_DATE_FORMAT = "dd/MM/yy"

fun SimpleDateFormat.safeParse(date: String): Date? {
    return try {
        this.parse(date)
    } catch (e: ParseException) {
        null
    }
}

fun Date?.asFormattedString(format: String = PREFERENCE_FORCED_DATE_FORMAT): String {
    return if (this == null) NO_DATE else SimpleDateFormat(format, Locale.US).format(this)
}

fun getFormattedDate(date: String, currentFormat: String, requiredFormat: String): String {
    return SimpleDateFormat(currentFormat, Locale.US).safeParse(date).asFormattedString(requiredFormat)
}
