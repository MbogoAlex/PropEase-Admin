package com.tms.propease_admin.ui.screen.accountManagement

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tms.propease_admin.appDataStore.DSRepository
import com.tms.propease_admin.model.UserProfile
import com.tms.propease_admin.network.ApiRepository
import com.tms.propease_admin.utils.LoadingStatus
import com.tms.propease_admin.utils.UserDetails
import com.tms.propease_admin.utils.toUserData
import com.tms.propease_admin.utils.userProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ProfileScreenUiState(
    val profile: UserProfile = userProfile,
    val userDetails: UserDetails = UserDetails(),
    val loadingStatus: LoadingStatus = LoadingStatus.INITIAL
)
class ProfileScreenViewModel(
    private val apiRepository: ApiRepository,
    private val dsRepository: DSRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(value = ProfileScreenUiState())
    val uiState: StateFlow<ProfileScreenUiState> = _uiState.asStateFlow()
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
        loadUserData()
    }

    fun loadUserData() {
        viewModelScope.launch {
            try {
                val response = apiRepository.getUser(
                    token = uiState.value.userDetails.token,
                    userId = uiState.value.userDetails.userId!!
                )
                if(response.isSuccessful) {
                    _uiState.update {
                        it.copy(
                            profile = response.body()?.data?.profiles!!
                        )
                    }
                    Log.i("USER_FETCH", "SUCCESS. $response")
                } else {
                    Log.e("USER_FETCH_ERROR_RESPONSE", response.toString())
                }
            }catch (e: Exception) {
                Log.e("USER_FETCH_ERROR_EXCEPTION", e.toString())

            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            dsRepository.deletePreferences()
        }
    }

    init {
        loadStartupData()
    }

}