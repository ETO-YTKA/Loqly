package com.example.loqly.di

import android.app.Application
import com.example.loqly.BuildConfig
import com.google.android.recaptcha.Recaptcha
import com.google.android.recaptcha.RecaptchaClient
import com.google.android.recaptcha.RecaptchaException
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RecaptchaModule {

    @Provides
    @Singleton
    fun provideRecaptchaClient(
        application: Application
    ): RecaptchaClient {
        return try {
            runBlocking {
                Recaptcha.fetchClient(application, BuildConfig.RECAPTCHA_SITE_KEY)
            }
        } catch (e: RecaptchaException) {
            Timber.e("Failed to initialize reCAPTCHA Enterprise ${e.message}")
            throw e
        }
    }
}
