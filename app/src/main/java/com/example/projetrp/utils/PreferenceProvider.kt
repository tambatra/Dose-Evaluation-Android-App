package com.example.projetrp.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

class PreferenceProvider(context: Context) {
    private val appContex = context.applicationContext

    val preference: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContex)
}