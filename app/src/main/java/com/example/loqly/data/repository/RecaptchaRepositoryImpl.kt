package com.example.loqly.data.repository

import com.example.loqly.BuildConfig
import com.example.loqly.domain.error.RecaptchaVerificationError
import com.example.loqly.domain.repository.RecaptchaRepository
import com.example.loqly.domain.result.AppResult
import com.google.android.recaptcha.RecaptchaAction
import com.google.android.recaptcha.RecaptchaClient
import com.google.android.recaptcha.RecaptchaException
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecaptchaRepositoryImpl @Inject constructor(
    private val recaptchaClient: RecaptchaClient
) : RecaptchaRepository {

    override suspend fun execute(
        recaptchaAction: RecaptchaAction
    ): AppResult<RecaptchaVerificationError, String> {
        if (BuildConfig.RECAPTCHA_SITE_KEY.isBlank()) {
            Timber.e("Recaptcha site key is blank")
            return AppResult.Failure(RecaptchaVerificationError.CONFIGURATION)
        }

        return try {
            AppResult.Success(
                recaptchaClient.execute(
                    recaptchaAction,
                    timeout = RECAPTCHA_TIMEOUT_MILLIS
                ).getOrThrow()
            )
        } catch (e: RecaptchaException) {
            Timber.e("Recaptcha exception message: ${e.message}")
            AppResult.Failure(RecaptchaVerificationError.NETWORK)
        } catch (e: Exception) {
            Timber.e("Recaptcha exception message: ${e.message}")
            AppResult.Failure(RecaptchaVerificationError.UNKNOWN)
        }
    }

    private companion object {
        const val RECAPTCHA_TIMEOUT_MILLIS = 10_000L
    }
}