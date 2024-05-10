package com.tms.propease_admin.ui.screen.accountManagement

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tms.propease_admin.appDataStore.DSRepository
import com.tms.propease_admin.appDataStore.DSUserModel
import com.tms.propease_admin.model.UserLoginRequestBody
import com.tms.propease_admin.network.ApiRepository
import com.tms.propease_admin.utils.ExecutionStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class LoginScreenUiState(
    val phone: String = "",
    val password: String = "",
    val buttonEnabled: Boolean = false,
    val loginFailureMessage: String = "",
    val executionStatus: ExecutionStatus = ExecutionStatus.INITIAL
)
class LoginScreenViewModel(
    private val apiRepository: ApiRepository,
    private val dsRepository: DSRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _uiState = MutableStateFlow(value = LoginScreenUiState())
    val uiState: StateFlow<LoginScreenUiState> = _uiState.asStateFlow()

    private val phone: String? = savedStateHandle[LoginScreenDestination.phoneNumber]
    private val password: String? = savedStateHandle[LoginScreenDestination.password]

    fun updatePhoneNumber(phone: String) {
        _uiState.update {
            it.copy(
                phone = phone
            )
        }
    }

    fun updatePassword(password: String) {
        _uiState.update {
            it.copy(
                password = password
            )
        }
    }

    fun loginUser() {
        _uiState.update {
            it.copy(
                executionStatus = ExecutionStatus.LOADING
            )
        }
        val user = UserLoginRequestBody(
            username = uiState.value.phone,
            password = uiState.value.password
        )
        viewModelScope.launch {
            try {
                val response = apiRepository.loginUser(user)
                if(response.isSuccessful) {
                    val userModel = DSUserModel(
                        userId = response.body()?.data?.user?.userInfo?.id!!,
                        userName = "${response.body()?.data?.user?.userInfo?.fname} ${response.body()?.data?.user?.userInfo?.lname}",
                        phoneNumber = response.body()?.data?.user?.userInfo?.phoneNumber!!,
                        email = response.body()?.data?.user?.userInfo?.email!!,
                        password = uiState.value.password,
                        token = response.body()?.data?.user?.token!!,
                        approvalStatus = response.body()?.data?.user?.userInfo?.approvalStatus!!
                    )
                    dsRepository.saveUserData(userModel)
                    _uiState.update {
                        it.copy(
                            executionStatus = ExecutionStatus.SUCCESS
                        )
                    }
                    Log.i("LOGIN_SUCCESS", "LOGIN SUCCESSFUL, TOKEN: ${response.body()?.data?.user?.token!!}")
                } else {
                    _uiState.update {
                        it.copy(
                            executionStatus = ExecutionStatus.FAIL,
                            loginFailureMessage = "incorrect email or password"
                        )
                    }
                    Log.e("LOGIN_FAIL_RESPONSE", response.toString())
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        executionStatus = ExecutionStatus.FAIL,
                        loginFailureMessage = "Failed. Check your connection and try again"
                    )
                }
                Log.e("LOGIN_FAIL_EXCEPTION", e.toString())
            }
        }
    }

    fun resetLoginStatus() {
        _uiState.update {
            it.copy(
                executionStatus = ExecutionStatus.INITIAL
            )
        }
    }

    fun checkIfAllFieldsAreFilled() {
        _uiState.update {
            it.copy(
                buttonEnabled = uiState.value.phone.isNotEmpty() &&
                                uiState.value.password.isNotEmpty()
            )
        }
    }

    init {
        if(phone != null && password != null) {
            _uiState.update {
                it.copy(
                    phone = phone,
                    password = password
                )
            }
        }

    }
}