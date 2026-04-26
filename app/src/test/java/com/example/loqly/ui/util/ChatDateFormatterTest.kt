package com.example.loqly.ui.util

import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.Locale

class ChatDateFormatterTest {

    private val originalLocale = Locale.getDefault()

    @Before
    fun setLocale() {
        Locale.setDefault(Locale.US)
    }

    @After
    fun restoreLocale() {
        Locale.setDefault(originalLocale)
    }

    @Test
    fun formatChatTimestamp_returnsTimeForSameDay() {
        val now = zonedDateTime(year = 2026, month = 4, day = 25, hour = 20, minute = 0)
        val timestamp = zonedDateTime(year = 2026, month = 4, day = 25, hour = 14, minute = 35)

        val result = timestamp.formatChatTimestamp(now = now)

        assertEquals("14:35", result)
    }

    @Test
    fun formatChatTimestamp_returnsYesterdayForPreviousDay() {
        val now = zonedDateTime(year = 2026, month = 4, day = 25, hour = 9, minute = 0)
        val timestamp = zonedDateTime(year = 2026, month = 4, day = 24, hour = 23, minute = 15)

        val result = timestamp.formatChatTimestamp(now = now)

        assertEquals("Yesterday", result)
    }

    @Test
    fun formatChatTimestamp_returnsMonthAndDayForSameYear() {
        val now = zonedDateTime(year = 2026, month = 4, day = 25, hour = 9, minute = 0)
        val timestamp = zonedDateTime(year = 2026, month = 2, day = 3, hour = 18, minute = 45)

        val result = timestamp.formatChatTimestamp(now = now)

        assertEquals("Feb 3", result)
    }

    @Test
    fun formatChatTimestamp_returnsMonthDayAndYearForOlderYears() {
        val now = zonedDateTime(year = 2026, month = 4, day = 25, hour = 9, minute = 0)
        val timestamp = zonedDateTime(year = 2025, month = 11, day = 3, hour = 18, minute = 45)

        val result = timestamp.formatChatTimestamp(now = now)

        assertEquals("Nov 3, 2025", result)
    }

    private fun zonedDateTime(
        year: Int,
        month: Int,
        day: Int,
        hour: Int,
        minute: Int,
    ): ZonedDateTime = ZonedDateTime.of(
        year,
        month,
        day,
        hour,
        minute,
        0,
        0,
        ZoneId.of("UTC"),
    )
}
