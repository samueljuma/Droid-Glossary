package com.phillqins.droidglossary.utils

import android.content.Context
import androidx.core.content.edit

class SessionManager(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("session", Context.MODE_PRIVATE)

    companion object {
        private const val USER_IS_LOGGED_IN = "user_is_logged_in"
    }

    fun isUserLoggedIn() = sharedPreferences.getBoolean(USER_IS_LOGGED_IN, false)

    fun setUserLoggedIn() = sharedPreferences.edit { putBoolean(USER_IS_LOGGED_IN, true) }

    fun clearSessionDetails() = sharedPreferences.edit { clear() }

}