package dev.lucasnlm.arch.info.viewmodel

import dev.lucasnlm.arch.core.IntentViewModel
import dev.lucasnlm.arch.info.models.ClockInfo
import dev.lucasnlm.arch.info.models.ProcessorInfo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.flow

@ExperimentalCoroutinesApi
@FlowPreview
class InfoViewModel : IntentViewModel<InfoEvent, ProcessorInfo>() {
    override fun initialState(): ProcessorInfo = ProcessorInfo(
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

    override suspend fun mapEventToState(event: InfoEvent) = flow<ProcessorInfo> {

    }
}
