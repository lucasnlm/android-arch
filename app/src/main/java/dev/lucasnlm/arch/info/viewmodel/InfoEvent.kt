package dev.lucasnlm.arch.info.viewmodel

import dev.lucasnlm.arch.info.models.DeviceInfo

sealed class InfoEvent {
    object LoadEvent : InfoEvent()

    data class ShareDeviceInfo(
        val deviceInfo: DeviceInfo
    ) : InfoEvent()
}
