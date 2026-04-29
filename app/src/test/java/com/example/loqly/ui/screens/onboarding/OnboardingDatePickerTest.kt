package com.example.loqly.ui.screens.onboarding

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.LocalDate

class OnboardingDatePickerTest {

    @Test
    fun birthDateToPickerMillis_roundTripsThroughPickerMillisToBirthDate() {
        val birthDate = LocalDate.of(2001, 4, 29)

        val result = pickerMillisToBirthDate(birthDateToPickerMillis(birthDate))

        assertEquals(birthDate, result)
    }

    @Test
    fun isSelectableBirthDate_acceptsDatesBeforeToday() {
        val today = LocalDate.of(2026, 4, 29)
        val yesterdayMillis = birthDateToPickerMillis(today.minusDays(1))

        val result = isSelectableBirthDate(
            utcTimeMillis = yesterdayMillis,
            today = today,
        )

        assertTrue(result)
    }

    @Test
    fun isSelectableBirthDate_rejectsToday() {
        val today = LocalDate.of(2026, 4, 29)

        val result = isSelectableBirthDate(
            utcTimeMillis = birthDateToPickerMillis(today),
            today = today,
        )

        assertFalse(result)
    }

    @Test
    fun isSelectableBirthDate_rejectsFutureDates() {
        val today = LocalDate.of(2026, 4, 29)
        val tomorrowMillis = birthDateToPickerMillis(today.plusDays(1))

        val result = isSelectableBirthDate(
            utcTimeMillis = tomorrowMillis,
            today = today,
        )

        assertFalse(result)
    }
}
