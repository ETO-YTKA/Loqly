package com.example.loqly.domain.model

import java.time.ZonedDateTime

data class ChatPreview(
    val id: String,
    val title: String,
    val imageUrl: String?,
    val lastMessage: String?,
    val lastMessageTime: ZonedDateTime?,
    val unreadCount: Int,
    val isOnline: Boolean?
)
