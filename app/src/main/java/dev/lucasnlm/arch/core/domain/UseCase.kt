package dev.lucasnlm.arch.core.domain

abstract class UseCase<Event, State> {
    open suspend fun handleEvent(event: Event) { }

    open suspend fun mapEventToState(currentState: State, event: Event): State {
        return currentState
    }
}
