package com.application.monkifyapp.di

import android.app.Application
import androidx.room.Room
import com.application.monkifyapp.data.local.InfoDatabase
import com.application.monkifyapp.data.local.ToggleableInfoDao
import com.application.monkifyapp.data.manager.LocalUserManagerImpl
import com.application.monkifyapp.domain.manager.LocalUserManager
import com.application.monkifyapp.domain.useCases.*
import com.application.monkifyapp.util.Constants.DATABASE_NAME
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
    fun provideLocalUserManager(application: Application): LocalUserManager {
        return LocalUserManagerImpl(context = application)
    }

    @Provides
    @Singleton
    fun provideAppEntryUseCases(
        localUserManger: LocalUserManager
    ): AppEntryUseCases = AppEntryUseCases(
        readAppEntry = ReadAppEntry(localUserManger),
        saveAppEntry = SaveAppEntry(localUserManger),
        readDaysCompleted = ReadDaysCompleted(localUserManger),
        saveDaysCompleted = SaveDaysCompleted(localUserManger)
    )

    @Provides
    @Singleton
    fun provideNewsDatabase(
        application: Application
    ): InfoDatabase {
        return Room.databaseBuilder(
            context = application,
            klass = InfoDatabase::class.java,
            name = DATABASE_NAME
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideNewsDao(
        infoDatabase: InfoDatabase
    ): ToggleableInfoDao = infoDatabase.toggleableInfoDao

}