package dev.lucasnlm.arch.info.domain

import dev.lucasnlm.arch.info.logic.DeviceInfoSharer
import dev.lucasnlm.arch.info.viewmodel.InfoEvent
import dev.lucasnlm.arch.info.viewmodel.InfoUseCase

class ShareDeviceInfoUseCase(
    private val deviceInfoSharer: DeviceInfoSharer
) : InfoUseCase() {
    override suspend fun handleEvent(event: InfoEvent) {
        when (event) {
            is InfoEvent.ShareDeviceInfo -> {
                deviceInfoSharer.share(event.deviceInfo)
            }
            else -> { }
        }
    }
}
