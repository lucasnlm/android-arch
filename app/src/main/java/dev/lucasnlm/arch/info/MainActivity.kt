package dev.lucasnlm.arch.info

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.ui.core.setContent
import dev.lucasnlm.arch.info.composables.DeviceInfoScreen
import dev.lucasnlm.arch.info.composables.LoadingScreen
import dev.lucasnlm.arch.info.viewmodel.InfoEvent
import dev.lucasnlm.arch.info.viewmodel.InfoState
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
            infoViewModel.run {
                sendEvent(InfoEvent.LoadEvent)
                observeState().collect { updateState(it) }
            }
        }
    }

    private fun updateState(infoState: InfoState) {
        setContent {
            ArchTheme {
                if (infoState is InfoState.LoadedState) {
                    DeviceInfoScreen(
                        localization = infoState.localization,
                        deviceInfo = infoState.deviceInfo,
                        viewModel = infoViewModel
                    )
                } else {
                    LoadingScreen()
                }
            }
        }
    }
}
