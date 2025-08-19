package com.phillqins.droidglossary.utils

import android.content.Context
import io.ktor.client.plugins.cookies.CookiesStorage
import io.ktor.http.Cookie
import io.ktor.http.Url
import androidx.core.content.edit
import kotlin.collections.filterNot
import kotlin.collections.joinToString
import kotlin.collections.mapNotNull
import kotlin.collections.toMutableList
import kotlin.text.split

class SharedPrefsCookieStorage(
    context: Context
) : CookiesStorage {
    private val prefs = context.getSharedPreferences("app_cookies", Context.MODE_PRIVATE)

    override suspend fun addCookie(requestUrl: Url, cookie: Cookie) {
        val storedCookies = get(requestUrl).toMutableList()
            .filterNot { it.name == cookie.name }
            .toMutableList()
        storedCookies.add(cookie)
        saveCookiesForUrl(requestUrl, storedCookies)
    }

    override suspend fun get(requestUrl: Url): List<Cookie> {
        val raw = prefs.getString(requestUrl.host, null) ?: return emptyList()
        return raw.split(";").mapNotNull { token ->
            val parts = token.split("=")
            if (parts.size == 2) Cookie(name = parts[0], value = parts[1], domain = requestUrl.host)
            else null
        }
    }

    override fun close() {}

    private fun saveCookiesForUrl(url: Url, cookies: List<Cookie>) {
        val cookieString = cookies.joinToString(";") { "${it.name}=${it.value}" }
        prefs.edit { putString(url.host, cookieString) }
    }
}
