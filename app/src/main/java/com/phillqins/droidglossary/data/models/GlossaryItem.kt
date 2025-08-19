package com.phillqins.droidglossary.data.models

import kotlinx.serialization.Serializable

typealias Glossary = List<GlossaryItem>

@Serializable
data class GlossaryItem(
    val id: String,
    val term: String,
    val category: String,
    val definition: String
)
