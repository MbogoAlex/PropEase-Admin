package com.tms.propease_admin.ui.screen.property

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tms.propease_admin.appDataStore.DSRepository
import com.tms.propease_admin.model.CategoryItem
import com.tms.propease_admin.model.PropertyDetails
import com.tms.propease_admin.network.ApiRepository
import com.tms.propease_admin.utils.ExecutionStatus
import com.tms.propease_admin.utils.UserDetails
import com.tms.propease_admin.utils.toUserData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

val categoryItem = CategoryItem(
    id = 1,
    name = ""
)
data class VerifiedLivePropertiesScreenUiState(
    val userDetails: UserDetails = UserDetails(),
    val properties: List<PropertyDetails> = emptyList(),
    val categories: List<CategoryItem> = emptyList(),
    val locationSelected: String = "",
    val roomsSelected: String = "",
    val categorySelected: CategoryItem = categoryItem,
    val categoryIdSelected: String = "",
    val categoryNameSelected: String = "",
    val categoriesLoaded: Boolean = false,
    val filteringOn: Boolean = false,
    val executionStatus: ExecutionStatus = ExecutionStatus.INITIAL
)
class VerifiedLivePropertiesScreenViewModel(
    private val apiRepository: ApiRepository,
    private val dsRepository: DSRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(value = VerifiedLivePropertiesScreenUiState())
    val uiState: StateFlow<VerifiedLivePropertiesScreenUiState> = _uiState.asStateFlow()

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
        getCategories()
        getLiveProperties()
    }

    fun getCategories() {
        viewModelScope.launch {
            try {
               val response = apiRepository.getCategories()
               if(response.isSuccessful) {
                   _uiState.update {
                       it.copy(
                           categories = response.body()?.data?.categories!!,
                           categoriesLoaded = true
                       )
                   }
                   Log.i("FETCH_CATEGORIES", "SUCCESS")
               } else {
                   Log.e("FETCH_CATEGORIES_ERROR_RESPONSE", response.toString())
               }
            } catch (e: Exception) {
                Log.e("FETCH_CATEGORIES_ERROR_EXCEPTION", e.toString())
            }
        }
    }

    fun getLiveProperties() {
        _uiState.update {
            it.copy(
                executionStatus = ExecutionStatus.LOADING
            )
        }
        viewModelScope.launch {
            try {
              val response = apiRepository.getFilteredLiveProperties(
                  location = uiState.value.locationSelected,
                  rooms = uiState.value.roomsSelected,
                  categoryId = uiState.value.categoryIdSelected
              )
              if(response.isSuccessful) {
                  _uiState.update {
                      it.copy(
                          properties = response.body()?.data?.properties!!,
                          executionStatus = ExecutionStatus.SUCCESS
                      )
                  }
                  Log.i("FETCH_PROPERTIES", "SUCCESS")
              } else {
                  _uiState.update {
                      it.copy(
                          executionStatus = ExecutionStatus.FAIL
                      )
                  }
                  Log.e("FETCH_PROPERTIES_ERROR_RESPONSE", response.toString())
              }
            }catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        executionStatus = ExecutionStatus.FAIL
                    )
                }
                Log.e("FETCH_PROPERTIES_ERROR_EXCEPTION", e.toString())
            }
        }
    }

    fun filterByLocation(location: String) {
        _uiState.update {
            it.copy(
                filteringOn = true,
                locationSelected = location
            )
        }
        getLiveProperties()
    }

    fun filterByNumOfRooms(rooms: String) {
        _uiState.update {
            it.copy(
                filteringOn = true,
                roomsSelected = rooms
            )
        }
        getLiveProperties()
    }

    fun filterByCategory(categoryItem: CategoryItem) {
        _uiState.update {
            it.copy(
                filteringOn = true,
                categoryIdSelected = categoryItem.id.toString(),
                categoryNameSelected = categoryItem.name,
                categorySelected = categoryItem
            )
        }
        getLiveProperties()
    }

    fun undoFiltering() {
        _uiState.update {
            it.copy(
                locationSelected = "",
                roomsSelected = "",
                categoryNameSelected = "",
                categoryIdSelected = "",
                categorySelected = categoryItem
            )
        }
        getLiveProperties()
    }

    init {
        loadStartUpData()
    }
}