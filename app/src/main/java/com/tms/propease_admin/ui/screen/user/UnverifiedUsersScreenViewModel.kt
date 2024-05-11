package com.tms.propease_admin.ui.screen.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tms.propease_admin.appDataStore.DSRepository
import com.tms.propease_admin.model.Document
import com.tms.propease_admin.model.UnverifiedUserProfile
import com.tms.propease_admin.model.UserProfile
import com.tms.propease_admin.network.ApiRepository
import com.tms.propease_admin.utils.ExecutionStatus
import com.tms.propease_admin.utils.UserDetails
import com.tms.propease_admin.utils.VerificationStatus
import com.tms.propease_admin.utils.toUserData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

val unverifiedUser = UserProfile(
    id = 1,
    email = "abc@gmail.com",
    phoneNumber = "12345678",
    imageUrl = "",
    approvalStatus = "UNAPPROVED",
    approved = false,
    roles = roles,
    fname = "James",
    lname = "Michael"
)

val unverifiedUserDt = UnverifiedUserProfile(
    documents = emptyList(),
    user = unverifiedUser
)
data class UnverifiedUsersScreenUiState(
    val users: List<UnverifiedUserProfile> = emptyList(),
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
                } else {
                    _uiState.update {
                        it.copy(
                            executionStatus = ExecutionStatus.FAIL
                        )
                    }
                }
            }catch (e:Exception) {
                _uiState.update {
                    it.copy(
                        executionStatus = ExecutionStatus.FAIL
                    )
                }
            }
        }
    }

    init {
        loadStartupData()
    }
}