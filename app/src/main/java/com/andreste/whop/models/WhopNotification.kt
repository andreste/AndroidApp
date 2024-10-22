package com.andreste.whop.models

import kotlinx.serialization.Serializable

@Serializable
data class WhopNotification(
    val id: String,
    val title: String,
    val message: String,
    val read: Boolean,
    val timestamp: String
)