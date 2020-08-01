package dev.lucasnlm.arch.info.viewmodel

import dev.lucasnlm.arch.core.IntentViewModel
import dev.lucasnlm.arch.info.models.ClockInfo
import dev.lucasnlm.arch.info.models.DeviceInfo
import dev.lucasnlm.arch.info.models.Localization
import dev.lucasnlm.arch.info.repository.SocRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single

@ExperimentalCoroutinesApi
@FlowPreview
class InfoViewModel(
    private val localization: Localization,
    private val socRepository: SocRepository
) : IntentViewModel<InfoEvent, InfoState>() {

    override fun initialState(): InfoState =
        InfoState(
            localization,
            DeviceInfo(
                model = "model",
                modelName = "model name",
                vendor = "vendor",
                revision = "1",
                architecture = "arc",
                cpuCores = 8,
                clockInfo = ClockInfo(
                    clocks = listOf(),
                    minClock = 0,
                    maxClock = 1000
                ),
                stepping = "",
                bogoMips = "",
                bigLittle = 0,
                abi = "dd",
                serial = "sss",
                device = "ddd",
                governor = "dsdsd",
                flags = listOf("A", "B", "C")
            )
        )

    override suspend fun mapEventToState(event: InfoEvent) = flow {
        when (event) {
            is InfoEvent.RefreshEvent -> {
                emit(state.copy(deviceInfo = socRepository.readDeviceInfo().single()))
            }
        }
    }
}
