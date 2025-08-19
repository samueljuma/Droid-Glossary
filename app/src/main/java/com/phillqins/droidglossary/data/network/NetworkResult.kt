package com.phillqins.droidglossary.data.network

sealed class NetworkResult<out T> {
    data class Success<out T>(val data: T) : NetworkResult<T>()
    data class Error(
        val message: String,
        val extra: Map<String, Any>? = null
    ) : NetworkResult<Nothing>()
}