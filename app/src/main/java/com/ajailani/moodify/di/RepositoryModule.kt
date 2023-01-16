package com.ajailani.moodify.di

import com.ajailani.moodify.data.repository.*
import com.ajailani.moodify.domain.repository.*
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

    @Binds
    abstract fun bindStatisticRepository(
        statisticRepositoryImpl: StatisticRepositoryImpl
    ): StatisticRepository

    @Binds
    abstract fun bindUserProfileRepository(
        userProfileRepositoryImpl: UserProfileRepositoryImpl
    ): UserProfileRepository
}