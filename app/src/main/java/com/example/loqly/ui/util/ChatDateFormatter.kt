package com.example.loqly.ui.util

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun ZonedDateTime.formatChatTimestamp(
    now: ZonedDateTime = ZonedDateTime.now(),
): String {
    val displayValue = this.withZoneSameInstant(now.zone)
    val valueDate = displayValue.toLocalDate()
    val currentDate = now.toLocalDate()
    val locale = Locale.getDefault()

    return when {
        valueDate == currentDate -> displayValue.format(DateTimeFormatter.ofPattern("HH:mm", locale))
        valueDate == currentDate.minusDays(1) -> "Yesterday"
        valueDate.year == currentDate.year -> displayValue.format(DateTimeFormatter.ofPattern("MMM d", locale))
        else -> displayValue.format(DateTimeFormatter.ofPattern("MMM d, yyyy", locale))
    }
}
