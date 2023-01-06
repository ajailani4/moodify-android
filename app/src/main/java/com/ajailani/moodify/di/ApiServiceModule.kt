package com.ajailani.moodify.di

import com.ajailani.moodify.data.remote.api_service.ActivityService
import com.ajailani.moodify.data.remote.api_service.AuthService
import com.ajailani.moodify.data.remote.api_service.MoodService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiServiceModule {
    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit): AuthService =
        retrofit.create(AuthService::class.java)

    @Provides
    @Singleton
    fun provideActivityService(retrofit: Retrofit): ActivityService =
        retrofit.create(ActivityService::class.java)

    @Provides
    @Singleton
    fun provideMoodService(retrofit: Retrofit): MoodService =
        retrofit.create(MoodService::class.java)
}