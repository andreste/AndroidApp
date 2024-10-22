package com.andreste.whop.services

import com.andreste.whop.models.WhopNotification
import retrofit2.Response
import retrofit2.http.GET

interface NotificationsService {

    @GET("notifications/")
    suspend fun getNotifications(): Response<List<WhopNotification>>
}