package com.andreste.whop.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.andreste.whop.models.WhopNotification
import com.andreste.whop.repositories.NotificationsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

sealed class ViewState {
    data object Loading : ViewState()
    data class Content(val list: List<WhopNotification>) : ViewState()
    data class Error(val message: String) : ViewState()
}

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val notificationsRepository: NotificationsRepository
) : ViewModel() {
    private var navController: NavController? = null

    private val _state = MutableStateFlow<ViewState>(ViewState.Content(emptyList()))
    val state = _state.asStateFlow()

    fun start(navController: NavController) {
        this.navController = navController
    }

    fun getNotifications() {
        _state.value = ViewState.Loading

        viewModelScope.launch {
            val response = notificationsRepository.getNotifications()
            if (response.isSuccessful) {
                response.body()?.let {
                    val remainingNotifications = it.drop(40)
                    val initialNotifications = it.take(40)

                    _state.value = ViewState.Content(initialNotifications)
                    addNotificationsSporadically(remainingNotifications)
                }
            } else {
                _state.value = ViewState.Error("Could not get notifications")
            }
        }
    }

    fun markAsRead(notification: WhopNotification) {
        viewModelScope.launch {
            val currentState = _state.value
            if (currentState is ViewState.Content) {
                val updatedList = currentState.list.map {
                    if (it.id == notification.id) it.copy(read = true) else it
                }
                _state.value = ViewState.Content(updatedList)
            }
        }
    }

    private fun addNotificationsSporadically(remainingNotifications: List<WhopNotification>) {
        viewModelScope.launch {
            for (notification in remainingNotifications) {
                val randomDelay = Random.nextLong(5000, 15000)
                delay(randomDelay)
                val currentState = _state.value
                if (currentState is ViewState.Content) {
                    val updatedList = listOf(notification) + currentState.list
                    _state.value = ViewState.Content(updatedList)
                }
            }
        }
    }
}