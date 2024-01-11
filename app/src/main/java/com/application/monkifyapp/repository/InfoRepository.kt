package com.application.monkifyapp.repository

import com.application.monkifyapp.data.local.InfoDatabase
import com.application.monkifyapp.data.local.ToggleableInfoDao
import com.application.monkifyapp.domain.model.ToggleableInfo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InfoRepository @Inject constructor(private val infoDao: ToggleableInfoDao) {
    suspend fun upsertInfo(info:ToggleableInfo){
        infoDao.upsertInfo(info)
    }
    suspend fun deleteInfo(info: ToggleableInfo){
        infoDao.deleteInfo(info)
    }
    fun getArticles(): Flow<List<ToggleableInfo>> {
        return  infoDao.getArticles()
    }
}