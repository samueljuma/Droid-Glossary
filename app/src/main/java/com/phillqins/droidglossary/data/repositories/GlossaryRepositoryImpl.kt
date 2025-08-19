package com.phillqins.droidglossary.data.repositories

import com.phillqins.droidglossary.data.models.Glossary
import com.phillqins.droidglossary.data.network.APIService
import com.phillqins.droidglossary.data.network.NetworkResult
import com.phillqins.droidglossary.data.network.safeApiCall
import com.phillqins.droidglossary.domain.GlossaryRepository
import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.CoroutineDispatcher

class GlossaryRepositoryImpl(
    private val apiService: APIService,
    private val dispatcher: CoroutineDispatcher,
): GlossaryRepository {

    override suspend fun fetchGlossaryItems(): NetworkResult<Glossary>{
        return safeApiCall(dispatcher){
            val response = apiService.fetchGlossaryItems()
            when(response.status){
                HttpStatusCode.OK -> { NetworkResult.Success(response.body()) }
                HttpStatusCode.Unauthorized -> NetworkResult.Error("Unauthorized")
                else -> NetworkResult.Error("Bad Request")
            }
        }
    }

}