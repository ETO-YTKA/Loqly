package com.example.loqly.ui.screens.signup

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loqly.R
import com.example.loqly.domain.result.AppResult
import com.example.loqly.domain.validator.CredentialValidator
import com.example.loqly.domain.validator.EmailError
import com.example.loqly.domain.validator.PasswordError
import com.example.loqly.domain.validator.UsernameError
import com.example.loqly.ui.util.debounceValidation
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    val credentialValidator: CredentialValidator,
    @param:ApplicationContext val context: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()

    private var usernameValidationJob: Job? = null
    private var emailValidationJob: Job? = null
    private var passwordValidationJob: Job? = null
    private var confirmPasswordValidationJob: Job? = null

    fun onAction(action: SignUpAction) {
        when (action) {
            is SignUpAction.UpdateUsername -> updateUsername(action.username)
            is SignUpAction.UpdateEmail -> updateEmail(action.email)
            is SignUpAction.UpdatePassword -> updatePassword(action.password)
            is SignUpAction.UpdateConfirmPassword -> updateConfirmPassword(action.password)
            is SignUpAction.Submit -> signUp(action.onSuccess)
        }
    }

    private fun updateUsername(value: String) {
        _uiState.update { state ->
            state.copy(
                username = value,
                usernameError = null
            )
        }

        usernameValidationJob = debounceValidation(
            scope = viewModelScope,
            previousJob = usernameValidationJob,
        ) {
            validateUsername()
        }
    }

    private fun updateEmail(value: String) {
        _uiState.update { state ->
            state.copy(
                email = value,
                emailError = null
            )
        }

        emailValidationJob = debounceValidation(
            scope = viewModelScope,
            previousJob = emailValidationJob,
        ) {
            validateEmail()
        }
    }

    private fun updatePassword(value: String) {
        _uiState.update { state ->
            state.copy(
                password = value,
                passwordError = null,
                confirmPasswordError = null
            )
        }

        passwordValidationJob = debounceValidation(
            scope = viewModelScope,
            previousJob = passwordValidationJob,
        ) {
            validatePassword()
        }

        confirmPasswordValidationJob = debounceValidation(
            scope = viewModelScope,
            previousJob = confirmPasswordValidationJob,
        ) {
            validateConfirmPassword()
        }
    }

    private fun updateConfirmPassword(value: String) {
        _uiState.update { state ->
            state.copy(
                confirmPassword = value,
                confirmPasswordError = null
            )
        }

        confirmPasswordValidationJob = debounceValidation(
            scope = viewModelScope,
            previousJob = confirmPasswordValidationJob,
        ) {
            validateConfirmPassword()
        }
    }

    private fun signUp(onSuccess: () -> Unit) {
        usernameValidationJob?.cancel()
        emailValidationJob?.cancel()
        passwordValidationJob?.cancel()
        confirmPasswordValidationJob?.cancel()

        validateUsername()
        validateEmail()
        validatePassword()
        validateConfirmPassword()

        if (isFieldsValid()) onSuccess()
    }

    private fun isFieldsValid(): Boolean {
        return uiState.value.usernameError == null &&
            uiState.value.emailError == null &&
            uiState.value.passwordError == null &&
            uiState.value.confirmPasswordError == null
    }

    private fun validateUsername() {
        when (val result = credentialValidator.validateUsername(uiState.value.username)) {

            is AppResult.Failure -> {

                val errorMessage = when (result.error) {
                    UsernameError.EMPTY_FIELD -> context.getString(R.string.username_empty_error)
                    UsernameError.TOO_LONG -> context.getString(R.string.username_too_long_error)
                    UsernameError.TOO_SHORT -> context.getString(R.string.username_too_short_error)
                    UsernameError.INVALID_CHARACTERS -> context.getString(R.string.username_invalid_characters_error)
                }

                _uiState.update { state ->
                    state.copy(usernameError = errorMessage)
                }
            }
            is AppResult.Success -> {
                _uiState.update { state ->
                    state.copy(usernameError = null)
                }
            }
        }
    }

    private fun validateEmail() {
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

    private fun validatePassword() {
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

    private fun validateConfirmPassword() {
        val password = uiState.value.password
        val confirmPassword = uiState.value.confirmPassword

        _uiState.update { state ->
            state.copy(
                confirmPasswordError = if (password != confirmPassword) {
                    context.getString(R.string.passwords_do_not_match_error)
                } else {
                    null
                }
            )
        }
    }
}
