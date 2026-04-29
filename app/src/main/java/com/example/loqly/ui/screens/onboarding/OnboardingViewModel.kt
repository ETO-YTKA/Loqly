package com.example.loqly.ui.screens.onboarding

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate

@HiltViewModel
class OnboardingViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(OnboardingUiState())
    val uiState: StateFlow<OnboardingUiState> = _uiState.asStateFlow()

    fun onAction(action: OnboardingActions) {
        when (action) {
            is OnboardingActions.UpdateFirstName -> updateFirstName(action.firstName)
            is OnboardingActions.UpdateLastName -> updateLastName(action.lastName)
            is OnboardingActions.UpdateBirthDate -> updateBirthDate(action.birthDate)
            OnboardingActions.Submit -> Unit
        }
    }

    private fun updateFirstName(value: String) {
        _uiState.update { state ->
            state.copy(
                firstName = value,
                firstNameError = null,
            )
        }
    }

    private fun updateLastName(value: String) {
        _uiState.update { state ->
            state.copy(
                lastName = value,
                lastNameError = null,
            )
        }
    }

    private fun updateBirthDate(value: LocalDate) {
        _uiState.update { state ->
            state.copy(
                birthDate = value,
                birthDateError = null,
            )
        }
    }
}
