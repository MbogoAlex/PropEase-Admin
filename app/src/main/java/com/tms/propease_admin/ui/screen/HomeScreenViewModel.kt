package com.tms.propease_admin.ui.screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tms.propease_admin.appDataStore.DSRepository
import com.tms.propease_admin.utils.UserDetails
import com.tms.propease_admin.utils.toUserData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class HomeScreenUiState(
    val userDetails: UserDetails = UserDetails(),
    val childScreen: String = "",
)
class HomeScreenViewModel(
    private val dsRepository: DSRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _uiState = MutableStateFlow(value = HomeScreenUiState())
    val uiState: StateFlow<HomeScreenUiState> = _uiState.asStateFlow()

    private val childScreen: String? = savedStateHandle[HomeScreenDestination.childScreen]
    fun loadStartupData() {
        viewModelScope.launch {
            dsRepository.dsUserModel.collect() {dsUserModel ->
                _uiState.update {
                    it.copy(
                        userDetails = dsUserModel.toUserData()
                    )
                }
            }
        }
        if(childScreen != null) {
            _uiState.update {
                it.copy(
                    childScreen = childScreen
                )
            }
        }
    }

    fun clearChildScreen() {
        _uiState.update {
            it.copy(
                childScreen = ""
            )
        }
    }

    init {
        loadStartupData()
    }
}