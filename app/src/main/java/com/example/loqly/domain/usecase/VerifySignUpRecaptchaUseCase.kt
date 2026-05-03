package com.example.loqly.domain.usecase

import com.example.loqly.domain.error.RecaptchaVerificationError
import com.example.loqly.domain.repository.RecaptchaRepository
import com.example.loqly.domain.result.AppResult
import com.google.android.recaptcha.RecaptchaAction
import jakarta.inject.Inject

class VerifySignUpRecaptchaUseCase @Inject constructor(
    private val repository: RecaptchaRepository
) {
    suspend operator fun invoke(): AppResult<RecaptchaVerificationError, String> {
        return repository.execute(RecaptchaAction.SIGNUP)
    }
}