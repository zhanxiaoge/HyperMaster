package com.zhanxiaoge.hypermaster.ui.first

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class UiState(
    val title: String = "Hello, Miuix!",
)

class FirstViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

}
