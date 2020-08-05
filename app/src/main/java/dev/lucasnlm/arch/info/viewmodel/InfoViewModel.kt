package dev.lucasnlm.arch.info.viewmodel

import dev.lucasnlm.arch.core.viewmodel.IntentViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@FlowPreview
class InfoViewModel(
    override val useCases: List<InfoUseCase>
) : IntentViewModel<InfoEvent, InfoState>() {

    override fun initialState(): InfoState = InfoState.LoadingState
}
