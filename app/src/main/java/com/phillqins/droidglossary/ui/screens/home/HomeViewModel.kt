package com.phillqins.droidglossary.ui.screens.home

import androidx.lifecycle.ViewModel
import com.phillqins.droidglossary.data.models.GlossaryItem
import com.phillqins.droidglossary.data.repositories.DroidGlossaryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


data class HomeUIState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val glossaryItems: List<GlossaryItem> = emptyList()
)

sealed interface HomeUIAction{
    object RefreshGlossary: HomeUIAction
}
class HomeViewModel(
    private val repository: DroidGlossaryRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(HomeUIState())
    val uiState = _uiState.asStateFlow()


    fun onAction(action: HomeUIAction){
        when(action){
            HomeUIAction.RefreshGlossary -> refreshGlossary()
        }
    }

    fun refreshGlossary(){}

}