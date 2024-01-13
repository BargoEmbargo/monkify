package com.application.monkifyapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.application.monkifyapp.domain.model.ToggleableInfo


@Database(entities = [ToggleableInfo::class],version = 5,)
//@TypeConverters(NewsTypeConvertor::class)
abstract class InfoDatabase : RoomDatabase() {

    abstract val toggleableInfoDao:ToggleableInfoDao

}