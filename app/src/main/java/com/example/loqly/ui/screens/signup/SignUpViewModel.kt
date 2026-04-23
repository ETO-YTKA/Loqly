package com.example.loqly.ui.screens.signup

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.loqly.R
import com.example.loqly.domain.result.AppResult
import com.example.loqly.domain.validator.CredentialValidator
import com.example.loqly.domain.validator.EmailError
import com.example.loqly.domain.validator.PasswordError
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class SignUpUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val isPasswordVisible: Boolean = false,
    
    val emailError: String? = null,
    val passwordError: String? = null,
    val registrationError: String? = null
)

@HiltViewModel
class SignUpViewModel @Inject constructor(
    val credentialValidator: CredentialValidator,
    @ApplicationContext val context: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()

    fun updateEmail(value: String) {
        _uiState.update { state ->
            state.copy(
                email = value,
                emailError = null
            )
        }
    }

    fun updatePassword(value: String) {
        _uiState.update { state ->
            state.copy(
                password = value,
                passwordError = null
            )
        }
    }

    fun togglePasswordVisibility() {
        _uiState.update { state ->
            state.copy(isPasswordVisible = !state.isPasswordVisible)
        }
    }

    fun signUp() {
        validateEmail()
        validatePassword()
    }

    fun validateEmail() {
        when (val result = credentialValidator.validateEmail(uiState.value.email)) {

            is AppResult.Failure -> {

                val errorMessage = when (result.error) {
                    EmailError.EMPTY_FIELD -> {
                        context.getString(R.string.email_empty_error)
                    }
                    EmailError.INVALID_EMAIL -> {
                        context.getString(R.string.email_invalid_error)
                    }
                }

                _uiState.update { state ->
                    state.copy(emailError = errorMessage)
                }
            }

            is AppResult.Success -> {
                _uiState.update { state ->
                    state.copy(emailError = null)
                }
            }
        }
    }

    fun validatePassword() {
        when (val result = credentialValidator.validatePassword(uiState.value.password)) {

            is AppResult.Failure -> {

                val errorMessage = when (result.error) {
                    PasswordError.EMPTY_FIELD -> {
                        context.getString(R.string.password_empty_error)
                    }
                    PasswordError.HAS_WHITESPACE -> {
                        context.getString(R.string.password_has_whitespaces_error)
                    }
                    PasswordError.TOO_SHORT -> {
                        context.getString(R.string.password_too_short_error)
                    }
                    PasswordError.NO_DIGIT -> {
                        context.getString(R.string.password_no_digit_error)
                    }
                    PasswordError.NO_UPPERCASE -> {
                        context.getString(R.string.password_no_uppercase_error)
                    }
                    PasswordError.NO_LOWERCASE -> {
                        context.getString(R.string.password_no_lowercase_error)
                    }
                }

                _uiState.update { state ->
                    state.copy(passwordError = errorMessage)
                }
            }

            is AppResult.Success -> {
                _uiState.update { state ->
                    state.copy(passwordError = null)
                }
            }
        }
    }
}
