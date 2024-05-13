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
    val rooms: List<String> = emptyList(),
    val locationSelected: String = "",
    val roomsSelected: String = "",
    val categorySelected: CategoryItem = categoryItem,
    val categoryIdSelected: String = "",
    val categoryNameSelected: String = "",
    val categoriesLoaded: Boolean = false,
    val filteringOn: Boolean = false,
    val loadPropertiesStatusCode: Int = 0,
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
                          executionStatus = ExecutionStatus.SUCCESS,
                          loadPropertiesStatusCode = response.code()
                      )
                  }
                  Log.i("FETCH_PROPERTIES", "SUCCESS")
              } else {
                  _uiState.update {
                      it.copy(
                          executionStatus = ExecutionStatus.FAIL,
                          loadPropertiesStatusCode = response.code()
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

        var noRooms = ""
        when(rooms.lowercase()) {
            "bedsitter" -> noRooms = "Bedsitter"
            "one bedroom" -> noRooms = "One bedroom"
            "two bedrooms" -> noRooms = "Two bedrooms"
            "three bedrooms" -> noRooms = "Three bedrooms"
            "four bedrooms" -> noRooms = "Four bedrooms"
            "five bedrooms" -> noRooms = "Five bedrooms"
            "bedsitter - rental, airbnb, on sale" -> noRooms = "Bedsitter"
            "one bedroom - rental, airbnb, on sale" -> noRooms = "One bedroom"
            "two bedrooms - rental, airbnb, on sale" -> noRooms = "Two bedrooms"
            "three bedrooms - rental, airbnb, on sale" -> noRooms = "Three bedrooms"
            "four bedrooms - rental, airbnb, on sale" -> noRooms = "Four bedrooms"
            "five bedrooms - rental, airbnb, on sale" -> noRooms = "Five bedrooms"
            "single room - shop, office, on sale" -> noRooms = "Single room"
            "two rooms - shop, office, on sale" -> noRooms = "Two rooms"
            "three rooms - shop, office, on sale" -> noRooms = "Three rooms"
            "single room" -> noRooms = "Single room"
            "two rooms" -> noRooms = "Two rooms"
            "three rooms" -> noRooms = "Three rooms"
        }
        _uiState.update {
            it.copy(
                filteringOn = true,
                roomsSelected = noRooms
            )
        }
        getLiveProperties()
    }

    fun filterByCategory(categoryItem: CategoryItem) {
        var selectableRooms = emptyList<String>()
        when(categoryItem.name.lowercase()) {
            "rental" -> {
                selectableRooms = listOf(
                    "Bedsitter",
                    "One bedroom",
                    "Two bedrooms",
                    "Three bedrooms",
                    "Four bedrooms",
                    "Five bedrooms"
                )
            }
            "arbnb" -> {
                selectableRooms = listOf(
                    "Bedsitter",
                    "One bedroom",
                    "Two bedrooms",
                    "Three bedrooms",
                    "Four bedrooms",
                    "Five bedrooms"
                )
            }
            "airbnb" -> {
                selectableRooms = listOf(
                    "Bedsitter",
                    "One bedroom",
                    "Two bedrooms",
                    "Three bedrooms",
                    "Four bedrooms",
                    "Five bedrooms"
                )
            }
            "on sale" -> {
                selectableRooms = listOf(
                    "Bedsitter - Rental, Airbnb, On sale",
                    "One bedroom - Rental, Airbnb, On sale",
                    "Two bedrooms - Rental, Airbnb, On sale",
                    "Three bedrooms - Rental, Airbnb, On sale",
                    "Four bedrooms - Rental, Airbnb, On sale",
                    "Five bedrooms - Rental, Airbnb, On sale",
                    "Single room - Shop, Office, On sale",
                    "Two rooms - Shop, Office, On sale",
                    "Three rooms - Shop, Office, On sale",
                )

            }
            "shop" -> {
                selectableRooms = listOf(
                    "Single room ",
                    "Two rooms",
                    "Three rooms",
                )

            }
            "office" -> {
                selectableRooms = listOf(
                    "Single room",
                    "Two rooms",
                    "Three rooms",
                )
            }
        }
        _uiState.update {
            it.copy(
                filteringOn = true,
                rooms = selectableRooms,
                roomsSelected = "",
                categoryIdSelected = categoryItem.id.toString(),
                categoryNameSelected = categoryItem.name,
                categorySelected = categoryItem
            )
        }
        getLiveProperties()
    }

    fun undoFiltering() {
        val rooms = listOf(
            "Bedsitter - Rental, Airbnb, On sale",
            "One bedroom - Rental, Airbnb, On sale",
            "Two bedrooms - Rental, Airbnb, On sale",
            "Three bedrooms - Rental, Airbnb, On sale",
            "Four bedrooms - Rental, Airbnb, On sale",
            "Five bedrooms - Rental, Airbnb, On sale",
            "Single room - Shop, Office, On sale",
            "Two rooms - Shop, Office, On sale",
            "Three rooms - Shop, Office, On sale",
        )
        _uiState.update {
            it.copy(
                rooms = rooms,
                locationSelected = "",
                roomsSelected = "",
                categoryNameSelected = "",
                categoryIdSelected = "",
                categorySelected = categoryItem
            )
        }
        getLiveProperties()
    }

    fun changeStatusCode() {
        _uiState.update {
            it.copy(
                loadPropertiesStatusCode = 0
            )
        }
    }

    init {
        loadStartUpData()
    }
}