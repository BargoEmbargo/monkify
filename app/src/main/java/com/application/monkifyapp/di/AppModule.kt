package com.application.monkifyapp.di

import android.app.Application
import com.application.monkifyapp.data.manager.LocalUserManagerImpl
import com.application.monkifyapp.domain.manager.LocalUserManager
import com.application.monkifyapp.domain.useCases.AppEntryUseCases
import com.application.monkifyapp.domain.useCases.ReadAppEntry
import com.application.monkifyapp.domain.useCases.SaveAppEntry
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLocalUserManger(
        application: Application
    ): LocalUserManager = LocalUserManagerImpl(context = application)

    @Provides
    @Singleton
    fun provideAppEntryUseCases(
        localUserManger: LocalUserManager
    ): AppEntryUseCases = AppEntryUseCases(
        readAppEntry = ReadAppEntry(localUserManger),
        saveAppEntry = SaveAppEntry(localUserManger)
    )

}