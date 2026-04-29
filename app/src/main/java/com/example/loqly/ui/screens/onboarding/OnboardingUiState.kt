package com.example.loqly.ui.screens.onboarding

import java.time.LocalDate

data class OnboardingUiState(
    val firstName: String = "",
    val lastName: String = "",
    val birthDate: LocalDate? = null,

    val firstNameError: String? = null,
    val lastNameError: String? = null,
    val birthDateError: String? = null,
)
