package com.phillqins.droidglossary.data.network

import com.phillqins.droidglossary.data.models.UserCredentials
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.Serializable

class APIService(
    private val client: HttpClient
) {

    suspend fun signIn(userCredentials: UserCredentials): HttpResponse{
        return client.post("auth/login"){
            setBody(userCredentials)
            contentType(ContentType.Application.Json)
        }
    }

    suspend fun fetchGlossaryItems(): HttpResponse{
        return client.get("glossary")
    }
}
