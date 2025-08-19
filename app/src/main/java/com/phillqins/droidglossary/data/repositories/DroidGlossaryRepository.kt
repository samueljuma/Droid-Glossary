package com.phillqins.droidglossary.data.repositories

import com.phillqins.droidglossary.data.models.Glossary
import com.phillqins.droidglossary.data.models.UserCredentials
import com.phillqins.droidglossary.data.network.APIService
import com.phillqins.droidglossary.data.network.NetworkResult
import com.phillqins.droidglossary.data.network.safeApiCall
import com.phillqins.droidglossary.utils.SessionManager
import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.CoroutineDispatcher

class DroidGlossaryRepository(
    private val apiService: APIService,
    private val dispatcher: CoroutineDispatcher,
    private val sessionManager: SessionManager
) {

    suspend fun signIn(userCredentials: UserCredentials): NetworkResult<Any>{
        return safeApiCall(dispatcher){
            val response = apiService.signIn(userCredentials)
            when(response.status){
                HttpStatusCode.OK -> {
                    sessionManager.setUserLoggedIn() // set flag for user logged in, in shared prefs
                    NetworkResult.Success(response.body())
                }
                else -> NetworkResult.Error("Invalid Credentials")
            }
        }
    }

    suspend fun fetchGlossaryItems(): NetworkResult<Glossary>{
        return safeApiCall(dispatcher){
            val response = apiService.fetchGlossaryItems()
            when(response.status){
                HttpStatusCode.OK -> { NetworkResult.Success(response.body()) }
                HttpStatusCode.Unauthorized -> NetworkResult.Error("Unauthorized")
                else -> NetworkResult.Error("Bad Request")
            }
        }
    }

    fun isUserLoggedIn() = sessionManager.isUserLoggedIn()

    fun signOutUser(){
        sessionManager.clearSessionDetails()
    }

}