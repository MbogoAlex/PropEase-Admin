package com.tms.propease_admin.ui.screen.accountManagement

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tms.propease_admin.model.UserRegistrationRequestBody
import com.tms.propease_admin.network.ApiRepository
import com.tms.propease_admin.utils.ExecutionStatus
import com.tms.propease_admin.utils.checkIfEmailIsValid
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class RegistrationScreenUiState(
    val fname: String = "",
    val lname: String = "",
    val email: String = "",
    val phone: String = "",
    val password: String = "",
    val buttonEnabled: Boolean = false,
    val registrationFailureMessage: String = "",
    val executionStatus: ExecutionStatus = ExecutionStatus.INITIAL
)
class RegistrationScreenViewModel(
    private val apiRepository: ApiRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(value = RegistrationScreenUiState())
    val uiState: StateFlow<RegistrationScreenUiState> = _uiState.asStateFlow()

    fun updatefname(fname: String) {
        _uiState.update {
            it.copy(
                fname = fname
            )
        }
    }

    fun updatelname(lname: String) {
        _uiState.update {
            it.copy(
                lname = lname
            )
        }
    }

    fun updateEmail(email: String) {
        _uiState.update {
            it.copy(
                email = email
            )
        }
    }

    fun updatePhone(phone: String) {
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

    fun registerUser() {
        _uiState.update {
            it.copy(
                executionStatus = ExecutionStatus.LOADING
            )
        }
        val user = UserRegistrationRequestBody(
            fname = uiState.value.fname,
            lname = uiState.value.lname,
            email = uiState.value.email,
            phoneNumber = uiState.value.phone,
            password = uiState.value.password
        )
        viewModelScope.launch {
            try {
                val response = apiRepository.registerUser(user)
                if(response.isSuccessful) {
                    _uiState.update {
                        it.copy(
                            executionStatus = ExecutionStatus.SUCCESS
                        )
                    }
                    Log.i("REGISTRATION_SUCCESS", "ADMIN REGISTERED")
                } else {
                    _uiState.update {
                        it.copy(
                            registrationFailureMessage = "Account already exists",
                            executionStatus = ExecutionStatus.FAIL
                        )
                    }
                    Log.e("REGISTRATION_FAIL_RESPONSE", response.toString())
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        registrationFailureMessage = "Failed. Check your network and try again later",
                        executionStatus = ExecutionStatus.FAIL
                    )
                }
                Log.e("REGISTRATION_FAIL_EXCEPTION", e.toString())
            }
        }
    }

    fun resetRegistrationStatus() {
        _uiState.update {
            it.copy(
                executionStatus = ExecutionStatus.INITIAL
            )
        }
    }

    fun checkIfFieldValuesValid() {
        _uiState.update {
            it.copy(
                buttonEnabled = uiState.value.fname.isNotEmpty() &&
                        uiState.value.lname.isNotEmpty() &&
                        uiState.value.email.isNotEmpty() &&
                        uiState.value.phone.isNotEmpty() &&
                        uiState.value.password.isNotEmpty() &&
                        !checkIfEmailIsValid(uiState.value.email)
            )
        }
    }
}