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

data class VerifiedNotLivePropertiesScreenUiState(
    val userDetails: UserDetails = UserDetails(),
    val properties: List<PropertyDetails> = emptyList(),
    val executionStatus: ExecutionStatus = ExecutionStatus.INITIAL
)
class VerifiedNotLivePropertiesScreenViewModel(
    private val apiRepository: ApiRepository,
    private val dsRepository: DSRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(value = VerifiedNotLivePropertiesScreenUiState())
    val uiState: StateFlow<VerifiedNotLivePropertiesScreenUiState> = _uiState.asStateFlow()

    fun loadStartUpDetails() {
        viewModelScope.launch {
            dsRepository.dsUserModel.collect() {dsUserModel->
                _uiState.update {
                    it.copy(
                        userDetails = dsUserModel.toUserData()
                    )
                }
            }
        }
        getUnpaidProperties()
    }

    fun getUnpaidProperties() {
        _uiState.update {
            it.copy(
                executionStatus = ExecutionStatus.LOADING
            )
        }
        viewModelScope.launch {
            try {
                val response = apiRepository.getUnpaidProperties(
                    token = uiState.value.userDetails.token
                )
                if(response.isSuccessful) {
                    _uiState.update {
                        it.copy(
                            properties = response.body()?.data?.property!!,
                            executionStatus = ExecutionStatus.SUCCESS
                        )
                    }
                    Log.i("UNPAID_PROPERTIES_FETCH", "SUCCESS")
                } else {
                    _uiState.update {
                        it.copy(
                            executionStatus = ExecutionStatus.FAIL
                        )
                    }
                    Log.e("UNPAID_PROPERTIES_FETCH_RESPONSE", response.toString())
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        executionStatus = ExecutionStatus.FAIL
                    )
                }
                Log.e("UNPAID_PROPERTIES_FETCH_EXCEPTION", e.toString())
            }
        }
    }

    init {
        loadStartUpDetails()
    }
}