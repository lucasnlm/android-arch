package dev.lucasnlm.arch.info.composables

import androidx.compose.Composable
import androidx.ui.foundation.VerticalScroller
import androidx.ui.layout.Column
import androidx.ui.layout.FlowRow
import androidx.ui.material.Scaffold
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import dev.lucasnlm.arch.core.viewmodel.StatelessViewModel
import dev.lucasnlm.arch.info.composables.widgets.AppTopBar
import dev.lucasnlm.arch.info.composables.widgets.CardInfoColumn
import dev.lucasnlm.arch.info.composables.widgets.InfoItem
import dev.lucasnlm.arch.info.composables.widgets.InstructionItem
import dev.lucasnlm.arch.info.models.ClockInfo
import dev.lucasnlm.arch.info.models.DeviceInfo
import dev.lucasnlm.arch.info.models.Localization
import dev.lucasnlm.arch.info.viewmodel.InfoEvent
import dev.lucasnlm.arch.ui.ArchTheme
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
@Composable
fun DeviceInfoScreen(
    localization: Localization,
    deviceInfo: DeviceInfo,
    viewModel: StatelessViewModel<InfoEvent>
) = with(deviceInfo) {
    Scaffold(
        topAppBar = {
            AppTopBar(
                title = localization.title,
                onShare = {
                    viewModel.sendEvent(InfoEvent.ShareDeviceInfo(deviceInfo))
                }
            )
        },
        bodyContent = {
            VerticalScroller {
                Column {
                    CardInfoColumn(
                        localization.processor
                    ) {
                        InfoItem(
                            localization.vendor,
                            vendor
                        )
                        InfoItem(
                            localization.model,
                            model
                        )
                    }
                    CardInfoColumn(
                        localization.details
                    ) {
                        InfoItem(
                            localization.abi,
                            abi
                        )
                        InfoItem(
                            localization.model,
                            modelName
                        )
                        InfoItem(
                            localization.cores,
                            cpuCores.toString()
                        )
                        InfoItem(
                            localization.revision,
                            revision
                        )
                        InfoItem(
                            localization.governor,
                            governor
                        )
                    }
                    CardInfoColumn(
                        localization.clock
                    ) {
                        clockInfo.minClock?.let {
                            if (it > 0) {
                                InfoItem(
                                    localization.min,
                                    it.toString()
                                )
                            }
                        }
                        clockInfo.maxClock?.let {
                            if (it > 0) {
                                InfoItem(
                                    localization.max,
                                    it.toString()
                                )
                            }
                        }
                        clockInfo.clocks.forEachIndexed { index, clock ->
                            if (clock > 0) {
                                InfoItem(
                                    String.format(localization.coreN, index + 1),
                                    clock.toString()
                                )
                            }
                        }
                    }
                    CardInfoColumn(
                        localization.instructions
                    ) {
                        FlowRow(
                            mainAxisSpacing = 2.dp,
                            crossAxisSpacing = 2.dp
                        ) {
                            flags.forEach {
                                InstructionItem(
                                    it
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}

@FlowPreview
@ExperimentalCoroutinesApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ArchTheme {
        val localization = Localization(
            title = "Device Info",
            processor = "Processor",
            vendor = "Vendor",
            model = "Model",
            details = "Details",
            abi = "ABI",
            cores = "Cores",
            revision = "Revision",
            governor = "Governor",
            clock = "Clock",
            min = "Min",
            max = "Max",
            coreN = "Core %d",
            graphics = "Graphics",
            renderer = "Renderer",
            version = "Version",
            instructions = "Instructions"
        )

        DeviceInfoScreen(
            localization = localization,
            deviceInfo = DeviceInfo(
                model = "model",
                modelName = "modelName",
                vendor = "vendor",
                revision = "revision",
                architecture = "architecture",
                cpuCores = 8,
                clockInfo = ClockInfo(
                    clocks = listOf(100, 200, 300, 400, 500, 600, 700, 800),
                    maxClock = 800,
                    minClock = 100
                ),
                stepping = "stepping",
                bogoMips = "bogoMips",
                bigLittle = 1,
                abi = "abi",
                serial = "serial",
                device = "device",
                governor = "governor",
                flags = listOf("A", "BC", "DEF")
            ),
            viewModel = StatelessViewModel()
        )
    }
}
