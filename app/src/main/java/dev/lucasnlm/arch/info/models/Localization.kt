package dev.lucasnlm.arch.info.models

data class Localization(
    val title: String,
    val processor: String,
    val vendor: String,
    val model: String,
    val details: String,
    val abi: String,
    val cores: String,
    val revision: String,
    val governor: String,
    val clock: String,
    val min: String,
    val max: String,
    val coreN: String,
    val graphics: String,
    val renderer: String,
    val version: String,
    val instructions: String
)
