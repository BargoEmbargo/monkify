package com.application.monkifyapp.data.local

import androidx.room.*
import com.application.monkifyapp.domain.model.ToggleableInfo
import com.application.monkifyapp.util.Constants.TABLE_DATABASE_NAME
import kotlinx.coroutines.flow.Flow

@Dao
interface ToggleableInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(info:ToggleableInfo)

    @Delete
    suspend fun delete(info:ToggleableInfo)

    @Query("SELECT * FROM $TABLE_DATABASE_NAME")
    fun getArticles(): Flow<List<ToggleableInfo>>

}