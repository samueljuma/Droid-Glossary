package com.phillqins.droidglossary.data.network

import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.ResponseException
import java.nio.channels.UnresolvedAddressException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> NetworkResult<T>
): NetworkResult<T> {
    return withContext(dispatcher) {
        try {
            apiCall()
        } catch (e: HttpRequestTimeoutException) {
            NetworkResult.Error("Request Timeout Error")
        } catch (e: ResponseException) {
            val error = when (e.response.status.value) {
                503 -> "Service Temporarily Unavailable"
                500 -> "Internal Server Error"
                404 -> "Resource Not Found"
                400 -> "Bad Request"
                else -> "Unexpected error: ${e.response.status}"
            }
            NetworkResult.Error(error)
        } catch (e: UnresolvedAddressException) {
            NetworkResult.Error("Something is wrong, Check internet and try again")
        } catch (e: Exception) {
            NetworkResult.Error("An error occurred ${e.message}")
        }
    }
}