package com.example.loqly.ui.screens.login

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val isPasswordVisible: Boolean = false,

    val emailErrorMessage: String? = null,
    val passwordErrorMessage: String? = null,
    val loginErrorMessage: String? = null
)

@HiltViewModel
class LoginViewModel @Inject constructor(

) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun updateEmail(value: String) {
        _uiState.update { state ->
            state.copy(email = value)
        }
    }

    fun updatePassword(value: String) {
        _uiState.update { state ->
            state.copy(password = value)
        }
    }

    fun togglePasswordVisibility() {
        _uiState.update { state ->
            state.copy(isPasswordVisible = !state.isPasswordVisible)
        }
    }

    fun authenticate(onSuccess: () -> Unit) {
        onSuccess()
    }
}
