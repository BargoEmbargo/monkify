package com.application.monkifyapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity("toggleable_info")
data class ToggleableInfo(
    @PrimaryKey
    var id:Int,
    var isChecked:Boolean,
    var text:String)