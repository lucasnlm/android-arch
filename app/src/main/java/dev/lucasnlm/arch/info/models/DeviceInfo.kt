package dev.lucasnlm.arch.info.models

data class DeviceInfo(
    val model: String,
    val modelName: String,
    val vendor: String,
    val revision: String,
    val architecture: String,
    val cpuCores: Int,
    val clockInfo: ClockInfo,
    val stepping: String,
    val bogoMips: String,
    val bigLittle: Int,
    val abi: String,
    val serial: String,
    val device: String,
    val governor: String,
    val flags: List<String>
)
