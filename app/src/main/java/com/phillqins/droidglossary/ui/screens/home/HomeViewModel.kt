package com.phillqins.droidglossary.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phillqins.droidglossary.data.models.GlossaryItem
import com.phillqins.droidglossary.data.network.NetworkResult
import com.phillqins.droidglossary.domain.AuthRepository
import com.phillqins.droidglossary.domain.GlossaryRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


data class HomeUIState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val error: String? = null,
    val loadingMessage: String = "Loading ...",
    val glossaryItems: List<GlossaryItem> = emptyList()
)

sealed class HomeScreenEvent{
    data class ShowToast(val message: String): HomeScreenEvent()
    object NavigateToSignIn: HomeScreenEvent()
}

sealed interface HomeUIAction{
    object RefreshGlossary: HomeUIAction
    object SignOut: HomeUIAction
}
class HomeViewModel(
    private val glossaryRepository: GlossaryRepository,
    private val authRepository: AuthRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(HomeUIState())
    val uiState = _uiState.asStateFlow()

    private val _event = Channel<HomeScreenEvent>()
    val event = _event.receiveAsFlow()

    init {
        fetchGlossaryItems()
    }

    fun onAction(action: HomeUIAction){
        when(action){
            HomeUIAction.RefreshGlossary -> fetchGlossaryItems(true)
            HomeUIAction.SignOut -> signOut()
        }
    }

    fun signOut(){
        viewModelScope.launch {
            authRepository.signOutUser()
            _event.send(HomeScreenEvent.NavigateToSignIn)
        }
    }

    fun fetchGlossaryItems(isRefreshing: Boolean = false){
        _uiState.update {
            it.copy(
                isLoading = if (!isRefreshing) true else it.isLoading,
                isRefreshing = if (isRefreshing) true else it.isRefreshing,
                loadingMessage = "Fetching glossary...",
                error = null
            )
        }

        viewModelScope.launch {
            val result = glossaryRepository.fetchGlossaryItems()
            when(result){
                is NetworkResult.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isRefreshing = false,
                            error = result.message
                        )
                    }
                    showToast(result.message)
                }
                is NetworkResult.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isRefreshing = false,
                            glossaryItems = result.data
                        )
                    }
                    showToast("Glossary fetched successfully")
                }
            }
        }
    }

    fun showToast(message: String){
        viewModelScope.launch {
            _event.send(HomeScreenEvent.ShowToast(message))
        }
    }

}