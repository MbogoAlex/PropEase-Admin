package com.tms.propease_admin.ui.screen.property.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tms.propease_admin.appDataStore.DSRepository
import com.tms.propease_admin.model.CategoryItem
import com.tms.propease_admin.model.NewCategoryRequestBody
import com.tms.propease_admin.network.ApiRepository
import com.tms.propease_admin.utils.EditStatus
import com.tms.propease_admin.utils.ExecutionStatus
import com.tms.propease_admin.utils.UserDetails
import com.tms.propease_admin.utils.category
import com.tms.propease_admin.utils.toUserData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class CategoriesScreenUiState(
    val userDetails: UserDetails = UserDetails(),
    val newCategoryName: String = "",
    val categories: List<CategoryItem> = emptyList(),
    val categoryAddButtonEnabled: Boolean = false,
    val executionStatus: ExecutionStatus = ExecutionStatus.INITIAL,
    val editStatus: EditStatus = EditStatus.INITIAL
)
class CategoriesScreenViewModel(
    private val apiRepository: ApiRepository,
    private val dsRepository: DSRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(value = CategoriesScreenUiState())
    val uiState: StateFlow<CategoriesScreenUiState> = _uiState.asStateFlow()

    fun updateCategoryName(categoryName: String) {
        _uiState.update {
            it.copy(
                newCategoryName = categoryName
            )
        }
    }

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
        getCategories()
    }

    fun getCategories() {
        _uiState.update {
            it.copy(
                executionStatus = ExecutionStatus.LOADING
            )
        }
        viewModelScope.launch {
            try {
                val response = apiRepository.getCategories()
                if(response.isSuccessful) {
                    _uiState.update {
                        it.copy(
                            categories = response.body()?.data?.categories!!,
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
            }catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        executionStatus = ExecutionStatus.FAIL
                    )
                }
            }
        }
    }

    fun createCategory() {
        _uiState.update {
            it.copy(
                editStatus = EditStatus.LOADING
            )
        }
        val category = NewCategoryRequestBody(
            name = uiState.value.newCategoryName
        )
        viewModelScope.launch {
            try {
                val response = apiRepository.createCategory(
                    token = uiState.value.userDetails.token,
                    categoryRequestBody = category
                )
                if(response.isSuccessful) {
                    _uiState.update {
                        it.copy(
                            editStatus = EditStatus.SUCCESS
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            editStatus = EditStatus.FAIL
                        )
                    }
                }
            }catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        editStatus = EditStatus.FAIL
                    )
                }
            }
        }
    }

    fun deleteCategoryName() {
        _uiState.update {
            it.copy(
                newCategoryName = ""
            )
        }
    }

    fun resetEditingStatus() {
        _uiState.update {
            it.copy(
                editStatus = EditStatus.INITIAL
            )
        }
    }

    fun categoryAddButtonEnabled() {
        _uiState.update {
            it.copy(
                categoryAddButtonEnabled = uiState.value.newCategoryName.isNotEmpty()
            )
        }
    }

    init {
        loadStartupData()
    }
}