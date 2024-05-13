package com.tms.propease_admin.ui.screen.user

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tms.propease_admin.appDataStore.DSRepository
import com.tms.propease_admin.model.UserProfile
import com.tms.propease_admin.model.UserVerificationProfile
import com.tms.propease_admin.network.ApiRepository
import com.tms.propease_admin.utils.ExecutionStatus
import com.tms.propease_admin.utils.UserDetails
import com.tms.propease_admin.utils.toUserData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

val unverifiedUser = UserProfile(
    id = 1,
    email = "abc@gmail.com",
    phoneNumber = "12345678",
    imageUrl = null,
    approvalStatus = "UNAPPROVED",
    approved = false,
    roles = roles,
    fname = "James",
    lname = "Michael"
)

data class UnverifiedUsersScreenUiState(
    val users: List<UserVerificationProfile> = emptyList(),
    val unverifiedUsers: List<UserProfile> = emptyList(),
//    val verificationStatus: VerificationStatus = VerificationStatus.INITIAL,
    val userDetails: UserDetails = UserDetails(),
    val executionStatus: ExecutionStatus = ExecutionStatus.INITIAL
)
class UnverifiedUsersScreenViewModel(
    private val apiRepository: ApiRepository,
    private val dsRepository: DSRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(value = UnverifiedUsersScreenUiState())
    val uiState: StateFlow<UnverifiedUsersScreenUiState> = _uiState.asStateFlow()

    private val unverifiedUsers = mutableListOf<UserProfile>()
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
        getUnverifiedUsers()
    }

    fun getUnverifiedUsers() {
        _uiState.update {
            it.copy(
                executionStatus = ExecutionStatus.LOADING
            )
        }
        viewModelScope.launch {
            Log.i("YOUR_TOKEN", uiState.value.userDetails.token)
            try {
                val response = apiRepository.getUnverifiedUsers(
                    token = uiState.value.userDetails.token
                )
                if(response.isSuccessful) {
                    for(user in response.body()?.data?.profiles!!) {
                        unverifiedUsers.add(user.user)
                    }
                    _uiState.update {
                        it.copy(
                            users = response.body()?.data?.profiles!!,
                            unverifiedUsers = unverifiedUsers,
                            executionStatus = ExecutionStatus.SUCCESS
                        )
                    }
                    Log.i("USERS_FETCH", "SUCCESS")
                } else {
                    _uiState.update {
                        it.copy(
                            executionStatus = ExecutionStatus.FAIL
                        )
                    }
                    Log.e("USERS_FETCH_ERROR_RESPONSE", response.toString())
                }
            }catch (e:Exception) {
                _uiState.update {
                    it.copy(
                        executionStatus = ExecutionStatus.FAIL
                    )
                }
                Log.e("USERS_FETCH_ERROR_EXCEPTION", e.toString())
            }
        }
    }

    init {
        loadStartupData()
    }
}