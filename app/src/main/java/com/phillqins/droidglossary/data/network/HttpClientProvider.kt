package com.phillqins.droidglossary.data.network

import android.content.Context
import android.util.Log
import com.phillqins.droidglossary.utils.BASE_URL
import com.phillqins.droidglossary.utils.SharedPrefsCookieStorage
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.cookies.HttpCookies
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object HttpClientProvider {
    fun create(context: Context): HttpClient{
        return HttpClient(CIO){
            defaultRequest {
                url(BASE_URL)
            }

            install(Logging){
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.d("KtorLogger", message)
                    }
                }
                level = LogLevel.ALL
            }

            install(HttpCookies){
                storage = SharedPrefsCookieStorage(context)
            }

            install(ContentNegotiation) {
                json(
                    Json {
                        encodeDefaults = true
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    }
                )
            }
        }
    }
}