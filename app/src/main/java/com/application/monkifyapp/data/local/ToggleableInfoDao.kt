package com.application.monkifyapp.data.local

import androidx.room.*
import com.application.monkifyapp.domain.model.ToggleableInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface ToggleableInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(info:ToggleableInfo)

    @Delete
    suspend fun delete(info:ToggleableInfo)

    @Query("SELECT * FROM toggleable_info")
    fun getArticles(): Flow<List<ToggleableInfo>>

}