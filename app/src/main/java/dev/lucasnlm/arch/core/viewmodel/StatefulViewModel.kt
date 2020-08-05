package dev.lucasnlm.arch.core.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@FlowPreview
@ExperimentalCoroutinesApi
abstract class StatefulViewModel<Event, State> : StatelessViewModel<Event>() {
    private val mutableState: MutableStateFlow<State> by lazy { MutableStateFlow(initialState()) }

    protected val state: State
        get() = mutableState.value

    init {
        viewModelScope.launch {
            observeEvent()
                .onEach { handleEvent(it) }
                .flatMapConcat(::mapEventToState)
                .distinctUntilChanged()
                .collect { mutableState.value = it }
        }
    }

    protected abstract fun initialState(): State

    protected abstract suspend fun mapEventToState(event: Event): Flow<State>

    protected open suspend fun handleEvent(event: Event) {}

    fun observeState(): StateFlow<State> = mutableState
}
