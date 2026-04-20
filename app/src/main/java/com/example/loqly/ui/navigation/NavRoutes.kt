package com.example.loqly.ui.navigation

import kotlinx.serialization.Serializable

object NavRoutes {

    @Serializable
    object Splash

    @Serializable
    object Login

    @Serializable
    object SignUp

    @Serializable
    object ForgotPassword

    @Serializable
    data class Profile(val userId: String)

    @Serializable
    object Settings

    @Serializable
    object Chats

    @Serializable
    data class Chat(val chatId: String)
}
