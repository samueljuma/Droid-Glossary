package com.phillqins.droidglossary.domain

import com.phillqins.droidglossary.data.models.UserCredentials
import com.phillqins.droidglossary.data.network.NetworkResult

interface AuthRepository {
    suspend fun signIn(userCredentials: UserCredentials): NetworkResult<Any>
    fun isUserLoggedIn(): Boolean
    suspend fun signOutUser()
}