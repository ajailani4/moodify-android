package com.ajailani.moodify

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.ajailani.moodify.util.Constants
import com.ajailani.moodify.util.FCMUtil
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        FCMUtil.subscribeToTopic()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                Constants.NotificationChannel.FCM_ID,
                Constants.NotificationChannel.FCM_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = Constants.NotificationChannel.FCM_DESCRIPTION
            }

            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }
}