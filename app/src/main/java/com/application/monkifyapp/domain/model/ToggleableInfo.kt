package com.application.monkifyapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.application.monkifyapp.util.Constants.TABLE_DATABASE_NAME


@Entity(TABLE_DATABASE_NAME)
data class ToggleableInfo(
    @PrimaryKey
    var id:Int,
    var isChecked:Boolean,
    var text:String)