package com.example.loqly.domain.error

import com.example.loqly.domain.result.AppError

enum class RecaptchaVerificationError : AppError {
    CONFIGURATION,
    NETWORK,
    UNKNOWN
}