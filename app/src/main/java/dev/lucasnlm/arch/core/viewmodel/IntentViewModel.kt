package dev.lucasnlm.arch.core.viewmodel

import dev.lucasnlm.arch.core.domain.UseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.flow

@FlowPreview
@ExperimentalCoroutinesApi
abstract class IntentViewModel<Event, State> : StatefulViewModel<Event, State>() {
    abstract val useCases: List<UseCase<Event, State>>

    override suspend fun handleEvent(event: Event) {
        useCases.forEach { it.handleEvent(event) }
    }

    override suspend fun mapEventToState(event: Event) = flow {
        val resultState = useCases.fold(state) { currentState, useCase ->
            useCase.mapEventToState(currentState, event)
        }
        emit(resultState)
    }
}
