package com.ajailani.moodify.util

import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging

class FCMUtil {
    companion object {
        fun subscribeToTopic() {
            FirebaseMessaging.getInstance().subscribeToTopic("recommendedActivities")
                .addOnCompleteListener { task ->
                    var message = "Success"

                    if (!task.isSuccessful) {
                        message = "Failed"
                    }

                    Log.d("Topic subscribing", message)
                }
        }
    }
}