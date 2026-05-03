package com.example.loqly.di

import com.example.loqly.data.repository.RecaptchaRepositoryImpl
import com.example.loqly.domain.repository.RecaptchaRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindRecaptchaRepository(impl: RecaptchaRepositoryImpl): RecaptchaRepository
}