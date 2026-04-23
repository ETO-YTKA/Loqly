package com.example.loqly.domain.validator

import com.example.loqly.domain.result.AppResult
import org.junit.Assert.assertEquals
import org.junit.Test

class CredentialValidatorTest {

    private val validator = CredentialValidator()

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
