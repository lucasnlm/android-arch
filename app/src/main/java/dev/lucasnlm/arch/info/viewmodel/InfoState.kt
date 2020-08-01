package dev.lucasnlm.arch.info.viewmodel

import dev.lucasnlm.arch.info.models.DeviceInfo
import dev.lucasnlm.arch.info.models.Localization

data class InfoState(
    val localization: Localization,
    val deviceInfo: DeviceInfo
)
