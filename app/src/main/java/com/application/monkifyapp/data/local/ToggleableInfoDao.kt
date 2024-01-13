package com.application.monkifyapp.data.local

import androidx.room.*
import com.application.monkifyapp.domain.model.ToggleableInfo
import com.application.monkifyapp.util.Constants.TABLE_DATABASE_NAME
import kotlinx.coroutines.flow.Flow

@Dao
interface ToggleableInfoDao {

    @Upsert()
    suspend fun upsertInfo(info:ToggleableInfo)

    @Delete
    suspend fun deleteInfo(info:ToggleableInfo)

    @Query("SELECT * FROM $TABLE_DATABASE_NAME")
    fun getArticles(): Flow<List<ToggleableInfo>>

    @Query("DELETE FROM $TABLE_DATABASE_NAME")
    suspend fun deleteAllInfo()

    @Query("SELECT * FROM $TABLE_DATABASE_NAME WHERE id=:id")
    suspend fun getInfoById(id:Int):ToggleableInfo

}