package dev.lucasnlm.arch.info.models

data class GraphicsInfo(
    val renderer: String,
    val vendor: String,
    val version: String,
    val extensions: List<String>
)