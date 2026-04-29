package com.example.loqly.ui.screens.onboarding

import java.time.LocalDate

sealed interface OnboardingActions {
    data class UpdateFirstName(val firstName: String) : OnboardingActions
    data class UpdateLastName(val lastName: String) : OnboardingActions
    data class UpdateBirthDate(val birthDate: LocalDate) : OnboardingActions
    object Submit : OnboardingActions
}