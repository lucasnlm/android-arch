package dev.lucasnlm.arch.info

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.lifecycle.lifecycleScope
import androidx.ui.core.setContent
import androidx.ui.foundation.VerticalScroller
import androidx.ui.layout.Column
import androidx.ui.layout.FlowRow
import androidx.ui.material.Scaffold
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import dev.lucasnlm.arch.info.composables.AppTopBar
import dev.lucasnlm.arch.info.composables.CardInfoColumn
import dev.lucasnlm.arch.info.composables.InfoItem
import dev.lucasnlm.arch.info.composables.InstructionItem
import dev.lucasnlm.arch.info.models.ClockInfo
import dev.lucasnlm.arch.info.models.DeviceInfo
import dev.lucasnlm.arch.info.models.Localization
import dev.lucasnlm.arch.info.viewmodel.InfoEvent
import dev.lucasnlm.arch.info.viewmodel.InfoViewModel
import dev.lucasnlm.arch.ui.ArchTheme
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel

@FlowPreview
@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {
    private val infoViewModel: InfoViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launchWhenCreated {
            infoViewModel.sendEvent(InfoEvent.RefreshEvent)

            infoViewModel.observeState().collect {
                updateState(it.localization, it.deviceInfo)
            }
        }
    }

    private fun updateState(
        localization: Localization,
        deviceInfo: DeviceInfo
    ) {
        setContent {
            ArchTheme {
                DeviceInfoScreen(
                    localization = localization,
                    deviceInfo = deviceInfo
                )
            }
        }
    }
}

@Composable
fun DeviceInfoScreen(
        localization: Localization,
        deviceInfo: DeviceInfo
) = with(deviceInfo) {
    Scaffold(
        topAppBar = {
            AppTopBar(localization.title)
        },
        bodyContent = {
            VerticalScroller {
                Column {
                    CardInfoColumn(localization.processor) {
                        InfoItem(localization.vendor, vendor)
                        InfoItem(localization.model, model)
                    }
                    CardInfoColumn(localization.details) {
                        InfoItem(localization.abi, abi)
                        InfoItem(localization.model, modelName)
                        InfoItem(localization.cores, cpuCores.toString())
                        InfoItem(localization.revision, revision)
                        InfoItem(localization.governor, governor)
                    }
                    CardInfoColumn(localization.clock) {
                        clockInfo.minClock?.let {
                            InfoItem(localization.min, it.toString())
                        }
                        clockInfo.maxClock?.let {
                            InfoItem(localization.max, it.toString())
                        }
                        clockInfo.clocks.forEachIndexed { index, clock ->
                            InfoItem(
                                String.format(localization.coreN, index + 1),
                                clock.toString()
                            )
                        }
                    }
                    CardInfoColumn(localization.graphics) {
                        InfoItem(localization.vendor, "Vend")
                        InfoItem(localization.renderer, "Vend")
                        InfoItem(localization.version, "Vend")
                    }
                    CardInfoColumn(localization.instructions) {
                        FlowRow(
                                mainAxisSpacing = 2.dp,
                                crossAxisSpacing = 2.dp
                        ) {
                            flags.forEach {
                                InstructionItem(it)
                            }
                        }
                    }
                }
            }
        })
}

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
            )
        )
    }
}
