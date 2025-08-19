package com.phillqins.droidglossary.utils

import android.content.Context
import androidx.core.content.edit

class AuthSessionManager(context: Context): SessionManager {
    private val sharedPreferences = context.getSharedPreferences("session", Context.MODE_PRIVATE)
    companion object {
        private const val USER_IS_LOGGED_IN = "user_is_logged_in"
    }
    override fun isUserLoggedIn() = sharedPreferences.getBoolean(USER_IS_LOGGED_IN, false)

    override suspend fun setUserLoggedIn() = sharedPreferences.edit { putBoolean(USER_IS_LOGGED_IN, true) }

    override suspend fun clearSessionDetails() = sharedPreferences.edit { clear() }
}

interface SessionManager{
    fun isUserLoggedIn(): Boolean
    suspend fun setUserLoggedIn()
    suspend fun clearSessionDetails()
}