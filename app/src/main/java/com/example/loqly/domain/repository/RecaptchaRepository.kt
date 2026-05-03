package com.example.loqly.domain.repository

import com.example.loqly.domain.error.RecaptchaVerificationError
import com.example.loqly.domain.result.AppResult
import com.google.android.recaptcha.RecaptchaAction

interface RecaptchaRepository {
    suspend fun execute(recaptchaAction: RecaptchaAction): AppResult<RecaptchaVerificationError, String>
}