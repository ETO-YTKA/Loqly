package com.example.loqly.ui.util

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DebouncedValidationTest {

    @Test
    fun debounceValidation_waitsForDelayBeforeRunningValidation() = runTest {
        var validationCount = 0

        debounceValidation(
            scope = this,
            previousJob = null,
            delayMillis = 400L,
        ) {
            validationCount++
        }

        advanceTimeBy(399L)
        assertEquals(0, validationCount)

        advanceTimeBy(1L)
        advanceUntilIdle()
        assertEquals(1, validationCount)
    }

    @Test
    fun debounceValidation_cancelsPreviousJob() = runTest {
        var validationCount = 0
        var validationJob: Job? = null

        validationJob = debounceValidation(
            scope = this,
            previousJob = validationJob,
            delayMillis = 400L,
        ) {
            validationCount++
        }

        advanceTimeBy(200L)

        debounceValidation(
            scope = this,
            previousJob = validationJob,
            delayMillis = 400L,
        ) {
            validationCount++
        }

        advanceTimeBy(399L)
        assertEquals(0, validationCount)

        advanceTimeBy(1L)
        advanceUntilIdle()
        assertEquals(1, validationCount)
    }
}
