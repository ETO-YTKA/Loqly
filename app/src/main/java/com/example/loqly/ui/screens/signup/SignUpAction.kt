package com.example.loqly.ui.screens.signup

sealed interface SignUpAction {
    data class UpdateUsername(val username: String) : SignUpAction
    data class UpdateEmail(val email: String) : SignUpAction
    data class UpdatePassword(val password: String) : SignUpAction
    data class UpdateConfirmPassword(val password: String) : SignUpAction
    object Submit : SignUpAction
}
