package com.phillqins.droidglossary.data.models

typealias Glossary = List<GlossaryItem>

data class GlossaryItem(
    val id: String,
    val term: String,
    val category: String,
    val definition: String
)
