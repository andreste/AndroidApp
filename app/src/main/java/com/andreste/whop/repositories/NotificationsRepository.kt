package com.andreste.whop.repositories

import com.andreste.whop.services.NotificationsService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationsRepository @Inject constructor(
    private val notificationsService: NotificationsService
) {

    suspend fun getNotifications() = notificationsService.getNotifications()

}