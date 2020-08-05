package dev.lucasnlm.arch.info.domain

import dev.lucasnlm.arch.info.models.Localization
import dev.lucasnlm.arch.info.repository.DeviceInfoRepository
import dev.lucasnlm.arch.info.viewmodel.InfoEvent
import dev.lucasnlm.arch.info.viewmodel.InfoState
import dev.lucasnlm.arch.info.viewmodel.InfoUseCase
import kotlinx.coroutines.flow.single

class LoadDeviceInfoUseCase(
    private val localization: Localization,
    private val deviceInfoRepository: DeviceInfoRepository
) : InfoUseCase() {
    override suspend fun mapEventToState(currentState: InfoState, event: InfoEvent): InfoState {
        return when (event) {
            is InfoEvent.LoadEvent -> {
                InfoState.LoadedState(
                    localization = localization,
                    deviceInfo = deviceInfoRepository.readDeviceInfo().single()
                )
            }
            else -> currentState
        }
    }
}
