package com.tms.propease_admin.ui.screen.property

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tms.propease_admin.appDataStore.DSRepository
import com.tms.propease_admin.model.PropertyDetails
import com.tms.propease_admin.network.ApiRepository
import com.tms.propease_admin.utils.ExecutionStatus
import com.tms.propease_admin.utils.UserDetails
import com.tms.propease_admin.utils.toUserData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class UnverifiedPropertiesScreenUiState(
    val userDetails: UserDetails = UserDetails(),
    val properties: List<PropertyDetails> = emptyList(),
    val forceLogin: Boolean = false,
    val executionStatus: ExecutionStatus = ExecutionStatus.INITIAL
)
class UnverifiedPropertiesScreenViewModel(
    private val apiRepository: ApiRepository,
    private val dsRepository: DSRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(value = UnverifiedPropertiesScreenUiState())
    val uiState: StateFlow<UnverifiedPropertiesScreenUiState> = _uiState.asStateFlow()

    fun loadStartupData() {
        viewModelScope.launch {
            dsRepository.dsUserModel.collect() {dsUserModel->
                _uiState.update {
                    it.copy(
                        userDetails = dsUserModel.toUserData()
                    )
                }
            }
        }
        getUnverifiedProperties()
    }

    fun getUnverifiedProperties() {
        _uiState.update {
            it.copy(
                executionStatus = ExecutionStatus.LOADING
            )
        }
        viewModelScope.launch {
            try {
                val response = apiRepository.getUnverifiedProperties(
                    token = uiState.value.userDetails.token
                )
                if(response.isSuccessful) {
                    _uiState.update {
                        it.copy(
                            properties = response.body()?.data?.property!!,
                            executionStatus = ExecutionStatus.SUCCESS
                        )
                    }
                    Log.i("FETCH_UNVERIFIED_PROPERTIES", "FETCHED")
                } else {
                    _uiState.update {
                        it.copy(
                            executionStatus = ExecutionStatus.FAIL,
                            forceLogin = true
                        )
                    }
                    Log.e("FETCH_UNVERIFIED_PROPERTIES_ERROR_RESPONSE", response.toString())
                }
            }catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        executionStatus = ExecutionStatus.FAIL
                    )
                }
                Log.e("FETCH_UNVERIFIED_PROPERTIES_ERROR_EXCEPTION", e.toString())
            }
        }
    }

    fun resetExecutionStatus() {
        _uiState.update {
            it.copy(
                executionStatus = ExecutionStatus.INITIAL
            )
        }
    }

    init {
        loadStartupData()
    }
}