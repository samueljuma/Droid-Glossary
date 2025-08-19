package com.phillqins.droidglossary.ui.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignInViewModel(): ViewModel(){
    private val _uiState = MutableStateFlow(SignInUiState())
    val uiState: StateFlow<SignInUiState> = _uiState.asStateFlow()

    private val _event = Channel<SignInUiEvent>()
    val event = _event.receiveAsFlow()

    fun onAction(event: SignInUiAction) {
        when (event) {
            is SignInUiAction.UsernameChanged -> _uiState.update { it.copy(username = event.value) }
            is SignInUiAction.PasswordChanged -> _uiState.update { it.copy(password = event.value) }
            SignInUiAction.TogglePasswordVisibility -> _uiState.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
            SignInUiAction.Submit -> signIn()
        }
    }


    private fun signIn() {
        showToast("Sign In Clicked")
    }

    fun showToast(message: String) {
        viewModelScope.launch {
            _event.send(SignInUiEvent.ShowToast(message))
        }
    }
}

data class SignInUiState(
    val username: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed interface SignInUiAction {
    data class UsernameChanged(val value: String) : SignInUiAction
    data class PasswordChanged(val value: String) : SignInUiAction
    object TogglePasswordVisibility : SignInUiAction
    object Submit : SignInUiAction
}

sealed class SignInUiEvent{
    data class ShowToast(val message: String): SignInUiEvent()
    object NavigateToHome: SignInUiEvent()
}
