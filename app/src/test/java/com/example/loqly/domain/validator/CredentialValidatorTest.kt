package com.example.loqly.domain.validator

import com.example.loqly.domain.result.AppResult
import org.junit.Assert.assertEquals
import org.junit.Test

class CredentialValidatorTest {

    private val validator = CredentialValidator()

    @Test
    fun validateUsername_rejectsEmptyValue() {
        val result = validator.validateUsername("")

        assertEquals(AppResult.Failure(UsernameError.EMPTY_FIELD), result)
    }

    @Test
    fun validateUsername_rejectsTooShortValue() {
        val result = validator.validateUsername("ab")

        assertEquals(AppResult.Failure(UsernameError.TOO_SHORT), result)
    }

    @Test
    fun validateUsername_rejectsTooLongValue() {
        val result = validator.validateUsername("abcdefghijklmnopqrstu")

        assertEquals(AppResult.Failure(UsernameError.TOO_LONG), result)
    }

    @Test
    fun validateUsername_rejectsInvalidCharacters() {
        val result = validator.validateUsername("user-name")

        assertEquals(AppResult.Failure(UsernameError.INVALID_CHARACTERS), result)
    }

    @Test
    fun validateUsername_rejectsUnderscore() {
        val result = validator.validateUsername("user_123")

        assertEquals(AppResult.Failure(UsernameError.INVALID_CHARACTERS), result)
    }

    @Test
    fun validateUsername_acceptsEnglishLettersAndNumbers() {
        val result = validator.validateUsername("user123")

        assertEquals(AppResult.Success(Unit), result)
    }

    @Test
    fun validateEmail_rejectsEmptyValue() {
        val result = validator.validateEmail("")

        assertEquals(AppResult.Failure(EmailError.EMPTY_FIELD), result)
    }

    @Test
    fun validateEmail_rejectsDomainLabelStartingWithHyphen() {
        val result = validator.validateEmail("user@-example.com")

        assertEquals(AppResult.Failure(EmailError.INVALID_EMAIL), result)
    }

    @Test
    fun validateEmail_rejectsRepeatedDotsInDomain() {
        val result = validator.validateEmail("user@company..com")

        assertEquals(AppResult.Failure(EmailError.INVALID_EMAIL), result)
    }

    @Test
    fun validateEmail_acceptsWellFormedAddress() {
        val result = validator.validateEmail("user.name+tag@example-domain.com")

        assertEquals(AppResult.Success(Unit), result)
    }

    @Test
    fun validatePassword_rejectsEmptyValue() {
        val result = validator.validatePassword("")

        assertEquals(AppResult.Failure(PasswordError.EMPTY_FIELD), result)
    }

    @Test
    fun validatePassword_rejectsTabWhitespace() {
        val result = validator.validatePassword("Passw0rd\t")

        assertEquals(AppResult.Failure(PasswordError.HAS_WHITESPACE), result)
    }

    @Test
    fun validatePassword_rejectsTooShortValue() {
        val result = validator.validatePassword("Pas1")

        assertEquals(AppResult.Failure(PasswordError.TOO_SHORT), result)
    }

    @Test
    fun validatePassword_rejectsMissingDigit() {
        val result = validator.validatePassword("Password")

        assertEquals(AppResult.Failure(PasswordError.NO_DIGIT), result)
    }

    @Test
    fun validatePassword_rejectsMissingUppercase() {
        val result = validator.validatePassword("password1")

        assertEquals(AppResult.Failure(PasswordError.NO_UPPERCASE), result)
    }

    @Test
    fun validatePassword_rejectsMissingLowercase() {
        val result = validator.validatePassword("PASSWORD1")

        assertEquals(AppResult.Failure(PasswordError.NO_LOWERCASE), result)
    }

    @Test
    fun validatePassword_acceptsStrongPassword() {
        val result = validator.validatePassword("Passw0rd")

        assertEquals(AppResult.Success(Unit), result)
    }
}
