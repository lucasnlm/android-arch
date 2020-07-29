package dev.lucasnlm.arch.info.viewmodel

sealed class InfoEvent {
    object RefreshEvent : InfoEvent()
}
