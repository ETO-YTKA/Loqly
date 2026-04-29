package com.example.loqly.ui.screens.onboarding

import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate

class OnboardingViewModelTest {

    private val viewModel = OnboardingViewModel()

    @Test
    fun onAction_updatesBirthDate() {
        val birthDate = LocalDate.of(2001, 4, 29)

        viewModel.onAction(OnboardingActions.UpdateBirthDate(birthDate))

        assertEquals(birthDate, viewModel.uiState.value.birthDate)
    }
}
