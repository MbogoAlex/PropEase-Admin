package com.tms.propease_admin.ui.screen.user

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tms.propease_admin.appDataStore.DSRepository
import com.tms.propease_admin.model.UserVerificationProfile
import com.tms.propease_admin.network.ApiRepository
import com.tms.propease_admin.utils.ExecutionStatus
import com.tms.propease_admin.utils.UserDetails
import com.tms.propease_admin.utils.VerificationStatus
import com.tms.propease_admin.utils.toUserData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

val unverifiedUserDt = UserVerificationProfile(
    documents = emptyList(),
    user = unverifiedUser
)
data class UserDetailsScreenUiState(
    val unverifiedUser: UserVerificationProfile = unverifiedUserDt,
    val verificationStatus: VerificationStatus = VerificationStatus.INITIAL,
    val userDetails: UserDetails = UserDetails(),
    val idFront: String = "",
    val idBack: String = "",
    val userId: Int = 0,
    val executionStatus: ExecutionStatus = ExecutionStatus.INITIAL
)
class UserDetailsScreenViewModel(
    private val apiRepository: ApiRepository,
    private val dsRepository: DSRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _uiState = MutableStateFlow(value = UserDetailsScreenUiState())
    val uiState: StateFlow<UserDetailsScreenUiState> = _uiState.asStateFlow()

    private val index: String? = savedStateHandle[UserDetailsScreenDestination.userId]

    fun loadStartUpData() {
        viewModelScope.launch {
            dsRepository.dsUserModel.collect() {dsUserModel->
                _uiState.update {
                    it.copy(
                        userDetails = dsUserModel.toUserData()
                    )
                }
            }
        }
        getUnverifiedUser()
    }

    fun getUnverifiedUser() {
        _uiState.update {
            it.copy(
                executionStatus = ExecutionStatus.LOADING
            )
        }
        viewModelScope.launch {
            try {
                val response = apiRepository.getUnverifiedUsers(
                    token = uiState.value.userDetails.token
                )
//                val response = apiRepository.getUnverifiedUser(
//                    token = uiState.value.userDetails.token,
//                    userId = userId!!.toInt()
//                )
                if(response.isSuccessful) {
                    Log.i("UNVERIFIED_USER", response.body()?.data?.profiles!!.toString())
                    _uiState.update {
                        it.copy(
                            unverifiedUser = response.body()?.data?.profiles!![index!!.toInt()],
                            userId = response.body()?.data?.profiles!![index!!.toInt()].user.id,
                            idFront = response.body()?.data?.profiles!![index!!.toInt()].documents[0].name,
                            idBack = if(response.body()?.data?.profiles!![index!!.toInt()].documents.size == 1) "" else  response.body()?.data?.profiles!![index!!.toInt()].documents[1].name,
                            executionStatus = ExecutionStatus.SUCCESS
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            executionStatus = ExecutionStatus.FAIL
                        )
                    }
                    Log.e("FETCH_UNVERIFIED_USER_FAILURE_RESPONSE", response.toString())
                }
            }catch (e:Exception) {
                _uiState.update {
                    it.copy(
                        executionStatus = ExecutionStatus.FAIL
                    )
                }
                Log.e("FETCH_UNVERIFIED_USER_FAILURE_EXCEPTION", "$e")
            }
        }
    }

    fun verifyUser() {
        _uiState.update {
            it.copy(
                verificationStatus = VerificationStatus.LOADING
            )
        }
        viewModelScope.launch {
            try {
               var response = apiRepository.verifyUser(
                   token = uiState.value.userDetails.token,
                   userId = uiState.value.userId
               )
                if(response.isSuccessful) {
                    _uiState.update {
                        it.copy(
                            verificationStatus = VerificationStatus.SUCCESS
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            verificationStatus = VerificationStatus.FAIL
                        )
                    }
                    Log.e("USER_VERIFICATION_ERROR_RESPONSE", response.toString())
                }
            }catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        verificationStatus = VerificationStatus.FAIL
                    )
                }
                Log.e("USER_VERIFICATION_ERROR_EXCEPTION", e.toString())
            }
        }
    }

    fun resetVerificationStatus() {
        _uiState.update {
            it.copy(
                verificationStatus = VerificationStatus.INITIAL
            )
        }
    }

    init {
        loadStartUpData()
    }
}