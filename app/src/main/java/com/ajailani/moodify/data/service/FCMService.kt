package com.ajailani.moodify.data.service

import android.app.NotificationManager
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.ajailani.moodify.R
import com.ajailani.moodify.util.Constants
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FCMService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        remoteMessage.notification?.let {
            sendNotification(
                title = it.title!!,
                description = it.body!!
            )
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    private fun sendNotification(title: String, description: String) {
        val notificationManager = ContextCompat.getSystemService(
            applicationContext,
            NotificationManager::class.java
        ) as NotificationManager

        val fcmNotification = NotificationCompat.Builder(
            applicationContext,
            Constants.NotificationChannel.FCM_ID
        )
            .setSmallIcon(R.drawable.ic_mood)
            .setContentTitle(title)
            .setContentText(description)
            .build()

        notificationManager.notify(1, fcmNotification)
    }
}