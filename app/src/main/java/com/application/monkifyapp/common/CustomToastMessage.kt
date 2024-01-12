package com.application.monkifyapp.common

import android.content.Context
import android.widget.Toast

fun customToastMessage(context: Context, message:String) {
    Toast.makeText(context,message, Toast.LENGTH_LONG).show()
}