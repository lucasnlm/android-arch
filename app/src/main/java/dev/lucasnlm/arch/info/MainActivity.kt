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
import dev.lucasnlm.arch.info.models.ProcessorInfo
import dev.lucasnlm.arch.info.viewmodel.InfoEvent
import dev.lucasnlm.arch.info.viewmodel.InfoViewModel
import dev.lucasnlm.arch.ui.ArchTheme
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach

@FlowPreview
@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<InfoViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launchWhenCreated {
            viewModel.sendEvent(InfoEvent.RefreshEvent)

            viewModel
                .observeState()
                .collect {
                    updateState(it)
                }


        }
    }

    private fun updateState(processorInfo: ProcessorInfo) {
        setContent {
            ArchTheme {
                DeviceInfoScreen(processorInfo)
            }
        }
    }
}

@Composable
fun DeviceInfoScreen(processorInfo: ProcessorInfo) = with(processorInfo) {
    Scaffold(
            topAppBar = {
                AppTopBar()
            },
            bodyContent = {
                VerticalScroller {
                    Column {
                        CardInfoColumn("Processor") {
                            InfoItem("Vendor", vendor)
                            InfoItem("Model", model)
                        }
                        CardInfoColumn("Details") {
                            InfoItem("ABI", abi)
                            InfoItem("Model", modelName)
                            InfoItem("Cores", cpuCores.toString())
                            InfoItem("Revision", revision)
                            InfoItem("Governor", governor)
                        }
                        CardInfoColumn("Clock") {
                            InfoItem("Min", clockInfo.minClock.toString())
                            InfoItem("Max", clockInfo.maxClock.toString())
                            clockInfo.clocks.forEachIndexed { index, clock ->
                                InfoItem("Core $index", clock.toString())
                            }
                        }
                        CardInfoColumn("Graphics") {
                            InfoItem("Vendor", "Vend")
                            InfoItem("Renderer", "Vend")
                            InfoItem("Version", "Vend")
                        }
                        CardInfoColumn("Instructions") {
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
        //DeviceInfoScreen()
    }
}