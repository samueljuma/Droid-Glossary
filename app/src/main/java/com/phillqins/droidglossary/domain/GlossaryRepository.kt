package com.phillqins.droidglossary.domain

import com.phillqins.droidglossary.data.models.Glossary
import com.phillqins.droidglossary.data.network.NetworkResult

interface GlossaryRepository {
    suspend fun fetchGlossaryItems(): NetworkResult<Glossary>
}