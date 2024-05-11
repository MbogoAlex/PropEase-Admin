package com.tms.propease_admin.ui.screen.property

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tms.propease_admin.appDataStore.DSRepository
import com.tms.propease_admin.model.PropertyDetails
import com.tms.propease_admin.model.PropertyVerificationRequestBody
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

val propertyDt = PropertyDetails(
    user = propertyOwner,
    propertyId = 1,
    title = "A new property",
    description = "This is a new property",
    category = "Rental",
    rooms = 2,
    price = 10000.0,
    approved = false,
    paid = false,
    postedDate = "2024-03-03 12:44",
    deletionTime = "2024-03-03 12:44",
    features = listOf("Balcony", "Wi-fi", "Water"),
    location = propertyLocation,
    paymentDetails =propertyPayment,
    images = images
)
data class PropertyDetailsScreenUiState(
    val userDetails: UserDetails = UserDetails(),
    val property: PropertyDetails = propertyDt,
    val propertyVerified: Boolean = false,
    val propertyUnVerified: Boolean = false,
    val verificationMessage: String = "",
    val executionStatus: ExecutionStatus = ExecutionStatus.INITIAL
)
class PropertyDetailsScreenViewModel(
    private val apiRepository: ApiRepository,
    private val dsRepository: DSRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _uiState = MutableStateFlow(value = PropertyDetailsScreenUiState())
    val uiState: StateFlow<PropertyDetailsScreenUiState> = _uiState.asStateFlow()

    private val propertyId: String? = savedStateHandle[PropertyDetailsScreenDestination.propertyId]

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
        getPropertyDetails()
    }

    fun getPropertyDetails() {
        viewModelScope.launch {
            try {
                val response = apiRepository.getPropertyDetails(
                    token = uiState.value.userDetails.token,
                    propertyId = propertyId!!.toInt()
                )
                if(response.isSuccessful) {
                    _uiState.update {
                        it.copy(
                            property = response.body()?.data?.property!!,
                            executionStatus = ExecutionStatus.SUCCESS
                        )
                    }
                    Log.i("FETCH_PROPERTY_DETAILS", "FETCHED")
                } else {
                    _uiState.update {
                        it.copy(
                            executionStatus = ExecutionStatus.FAIL
                        )
                    }
                    Log.e("FETCH_PROPERTY_DETAILS_ERROR_RESPONSE", response.toString())
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        executionStatus = ExecutionStatus.FAIL
                    )
                }
                Log.e("FETCH_PROPERTY_DETAILS_ERROR_EXCEPTION", e.toString())
            }
        }
    }

    fun verifyProperty() {
        _uiState.update {
            it.copy(
                executionStatus = ExecutionStatus.LOADING
            )
        }
        viewModelScope.launch {
            var imagesId = mutableListOf<Int>()
            for(imageId in uiState.value.property.images) {
                imagesId.add(imageId.id)
            }
            val verificationRequestBody = PropertyVerificationRequestBody(
                approvedImagesId = imagesId
            )
            try {
                val response = apiRepository.verifyProperty(
                    token = uiState.value.userDetails.token,
                    propertyId = propertyId!!.toInt(),
                    propertyVerificationRequestBody = verificationRequestBody
                )
                if(response.isSuccessful) {
                    _uiState.update {
                        it.copy(
                            executionStatus = ExecutionStatus.SUCCESS,
                            propertyVerified = true
                        )
                    }
                    Log.i("PROPERTY_VERIFICATION", "SUCCESS")
                } else {
                    _uiState.update {
                        it.copy(
                            executionStatus = ExecutionStatus.FAIL,
                            verificationMessage = "Failed to approve property",
                            propertyVerified = false
                        )
                    }
                    Log.e("PROPERTY_VERIFICATION_FAILURE_RESPONSE", response.toString())
                }
            }catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        executionStatus = ExecutionStatus.FAIL,
                        verificationMessage = "Failed. Check your connection",
                        propertyVerified = false
                    )
                }
                Log.e("PROPERTY_VERIFICATION_FAILURE_EXCEPTION", e.toString())
            }
        }
    }

    fun rejectProperty() {
        _uiState.update {
            it.copy(
                executionStatus = ExecutionStatus.LOADING
            )
        }
        var imagesId = mutableListOf<Int>()
        for(imageId in uiState.value.property.images) {
            imagesId.add(imageId.id)
        }
        val verificationRequestBody = PropertyVerificationRequestBody(
            approvedImagesId = imagesId
        )
        viewModelScope.launch {
            try {
               val response = apiRepository.cancelPropertyVerification(
                   token = uiState.value.userDetails.token,
                   propertyId = propertyId!!.toInt(),
                   propertyVerificationRequestBody = verificationRequestBody
               )
                if(response.isSuccessful) {
                    _uiState.update {
                        it.copy(
                            propertyUnVerified = true,
                            executionStatus = ExecutionStatus.SUCCESS
                        )
                    }
                    Log.i("PROPERTY_UNVERIFICATION", "SUCCESS")
                } else {
                    _uiState.update {
                        it.copy(
                            propertyUnVerified = false,
                            verificationMessage = "Failed to disapprove property",
                            executionStatus = ExecutionStatus.FAIL
                        )
                    }
                    Log.e("PROPERTY_UNVERIFICATION_FAILURE_RESPONSE", response.toString())
                }
            }catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        propertyUnVerified = false,
                        verificationMessage = "Failed.Check your connection",
                        executionStatus = ExecutionStatus.FAIL
                    )
                }
                Log.e("PROPERTY_UNVERIFICATION_FAILURE_EXCEPTION", e.toString())
            }
        }
    }

    fun resetVerificationStatus() {
        _uiState.update {
            it.copy(
                executionStatus = ExecutionStatus.INITIAL,
                propertyVerified = false,
                propertyUnVerified = false
            )
        }
    }

    init {
        loadStartupData()
    }
}