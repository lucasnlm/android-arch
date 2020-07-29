package dev.lucasnlm.arch.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@FlowPreview
@ExperimentalCoroutinesApi
abstract class IntentViewModel<Event, State> : ViewModel() {
    private val mutableState: MutableStateFlow<State> by lazy { MutableStateFlow(initialState()) }

    private val eventBroadcast = ConflatedBroadcastChannel<Event>()

    protected val state: State
        get() = mutableState.value

    init {
        viewModelScope.launch {
            eventBroadcast
                    .asFlow()
                    .flatMapConcat(::mapEventToState)
                    .collect { mutableState.value = it }
        }
    }

    suspend fun sendEvent(event: Event) {
        eventBroadcast.send(event)
    }

    override fun onCleared() {
        super.onCleared()
        eventBroadcast.close()
    }

    abstract fun initialState(): State

    abstract suspend fun mapEventToState(event: Event): Flow<State>

    fun observeState(): StateFlow<State> = mutableState

    fun observeEvent(): Flow<Event> = eventBroadcast.asFlow()
}
