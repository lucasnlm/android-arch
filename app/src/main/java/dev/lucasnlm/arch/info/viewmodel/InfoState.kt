package dev.lucasnlm.arch.info.viewmodel

import dev.lucasnlm.arch.info.models.DeviceInfo
import dev.lucasnlm.arch.info.models.Localization

sealed class InfoState {
    data class LoadedState(
        val localization: Localization,
        val deviceInfo: DeviceInfo
    ) : InfoState()

    object LoadingState : InfoState()
}
