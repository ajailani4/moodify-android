package com.ajailani.moodify.di

import com.ajailani.moodify.data.repository.ActivityRepositoryImpl
import com.ajailani.moodify.data.repository.AuthRepositoryImpl
import com.ajailani.moodify.data.repository.MoodRepositoryImpl
import com.ajailani.moodify.data.repository.UserCredentialRepositoryImpl
import com.ajailani.moodify.domain.repository.ActivityRepository
import com.ajailani.moodify.domain.repository.AuthRepository
import com.ajailani.moodify.domain.repository.MoodRepository
import com.ajailani.moodify.domain.repository.UserCredentialRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    abstract fun bindUserCredentialRepository(
        userCredentialRepositoryImpl: UserCredentialRepositoryImpl
    ): UserCredentialRepository

    @Binds
    abstract fun bindActivityRepository(
        activityRepositoryImpl: ActivityRepositoryImpl
    ): ActivityRepository

    @Binds
    abstract fun bindMoodRepository(
        moodRepositoryImpl: MoodRepositoryImpl
    ): MoodRepository
}