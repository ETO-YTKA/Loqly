package com.example.loqly.domain.validator

import com.example.loqly.domain.result.AppError
import com.example.loqly.domain.result.AppResult
import jakarta.inject.Inject

enum class UsernameError: AppError {
    EMPTY_FIELD,
    TOO_LONG,
    TOO_SHORT,
    INVALID_CHARACTERS
}

enum class EmailError: AppError {
    EMPTY_FIELD,
    INVALID_EMAIL
}

enum class PasswordError: AppError {
    EMPTY_FIELD,
    HAS_WHITESPACE,
    TOO_SHORT,
    NO_DIGIT,
    NO_UPPERCASE,
    NO_LOWERCASE
}


class CredentialValidator @Inject constructor() {

    fun validateUsername(username: String): AppResult<UsernameError, Unit> {
        val trimmed = username.trim()

        if (trimmed.isEmpty()) {
            return AppResult.Failure(UsernameError.EMPTY_FIELD)
        }

        if (trimmed.length < 3) {
            return AppResult.Failure(UsernameError.TOO_SHORT)
        }

        if (trimmed.length > 20) {
            return AppResult.Failure(UsernameError.TOO_LONG)
        }

        val usernameRegex = Regex("^[A-Za-z0-9]+$")
        if (!usernameRegex.matches(trimmed)) {
            return AppResult.Failure(UsernameError.INVALID_CHARACTERS)
        }

        return AppResult.Success(Unit)
    }

    fun validateEmail(email: String): AppResult<EmailError, Unit> {
        val trimmed = email.trim()

        if (trimmed.isEmpty()) {
            return AppResult.Failure(EmailError.EMPTY_FIELD)
        }

        val emailRegex = Regex(
            "^[A-Za-z0-9+_.-]+@(?:[A-Za-z0-9](?:[A-Za-z0-9-]{0,61}[A-Za-z0-9])?\\.)+[A-Za-z]{2,}$"
        )
        if (!emailRegex.matches(trimmed)) {
            return AppResult.Failure(EmailError.INVALID_EMAIL)
        }

        return AppResult.Success(Unit)
    }

    fun validatePassword(password: String): AppResult<PasswordError, Unit> {

        if (password.isEmpty()) {
            return AppResult.Failure(PasswordError.EMPTY_FIELD)
        }

        if (password.any { it.isWhitespace() }) {
            return AppResult.Failure(PasswordError.HAS_WHITESPACE)
        }

        if (password.length < 8) {
            return AppResult.Failure(PasswordError.TOO_SHORT)
        }

        if (!password.any { it.isDigit() }) {
            return AppResult.Failure(PasswordError.NO_DIGIT)
        }

        if (!password.any { it.isUpperCase() }) {
            return AppResult.Failure(PasswordError.NO_UPPERCASE)
        }

        if (!password.any { it.isLowerCase() }) {
            return AppResult.Failure(PasswordError.NO_LOWERCASE)
        }

        return AppResult.Success(Unit)
    }
}
