package com.application.monkifyapp.di

import android.app.Application
import androidx.room.Room
import com.application.monkifyapp.data.local.InfoDatabase
import com.application.monkifyapp.data.local.ToggleableInfoDao
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

    @Provides
    @Singleton
    fun provideNewsDatabase(
        application: Application
    ): InfoDatabase {
        return Room.databaseBuilder(
            context = application,
            klass = InfoDatabase::class.java,
            name = "info_db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideNewsDao(
        infoDatabase: InfoDatabase
    ): ToggleableInfoDao = infoDatabase.toggleableInfoDao

}