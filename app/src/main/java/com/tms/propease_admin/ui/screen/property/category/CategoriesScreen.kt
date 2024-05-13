package com.tms.propease_admin.ui.screen.property.category

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tms.propease_admin.AppViewModelFactory
import com.tms.propease_admin.R
import com.tms.propease_admin.model.CategoryItem
import com.tms.propease_admin.ui.theme.PropEaseAdminTheme
import com.tms.propease_admin.utils.EditStatus
import com.tms.propease_admin.utils.InputForm

val categories = listOf<CategoryItem>(
    CategoryItem(
        id = 1,
        name = "Rental"
    ),
    CategoryItem(
        id = 2,
        name = "Airbnb"
    ),
    CategoryItem(
        id = 3,
        name = "Shop"
    ),
    CategoryItem(
        id = 4,
        name = "On Sale"
    ),
)
@Composable
fun CategoriesScreenComposable(
    navigateToHomeScreenWithArgs: (childScreen: String) -> Unit,
    navigateToHomeScreenWithoutArgs: () -> Unit,
) {

    BackHandler(onBack = navigateToHomeScreenWithoutArgs)

    val context = LocalContext.current
    val viewModel: CategoriesScreenViewModel = viewModel(factory = AppViewModelFactory.Factory)
    val uiState by viewModel.uiState.collectAsState()

    var showAddCategoryPopup by remember {
        mutableStateOf(false)
    }

    if(uiState.editStatus == EditStatus.SUCCESS) {
        viewModel.deleteCategoryName()
        Toast.makeText(context, "Category added", Toast.LENGTH_SHORT).show()
        navigateToHomeScreenWithArgs("categories-screen")
        viewModel.resetEditingStatus()
    }

    if(showAddCategoryPopup) {
        CategoryAdditionPopup(
            categoryValue = uiState.newCategoryName,
            onValueChange = {
                viewModel.updateCategoryName(it)
                viewModel.categoryAddButtonEnabled()
            },
            onDismissRequest = {
                viewModel.deleteCategoryName()
                showAddCategoryPopup = false },
            onConfirm = {
                viewModel.createCategory()
            },
            enabled = uiState.categoryAddButtonEnabled && uiState.editStatus != EditStatus.LOADING,
            editStatus = uiState.editStatus
        )
    }

    Box {
        CategoriesScreen(
            categories = uiState.categories,
            onAddCategory = {
                showAddCategoryPopup = true
            }
        )
    }
}

@Composable
fun CategoriesScreen(
    categories: List<CategoryItem>,
    onAddCategory: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            text = "Categories",
            fontWeight = FontWeight.Bold
        )
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            TextButton(onClick = onAddCategory) {
                Text(text = "+ Category")
            }
        }

        LazyColumn {
            items(categories) {
                CategoryItemCell(
                    category = it,
                    navigateToCategoryPropertiesScreen = {},
                    modifier = Modifier
                        .padding(10.dp)
                )
            }
        }
    }
}

@Composable
fun CategoryItemCell(
    category: CategoryItem,
    navigateToCategoryPropertiesScreen: (categoryId: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                navigateToCategoryPropertiesScreen(category.id.toString())
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(10.dp)
        ) {
            Text(
                text = category.name.uppercase(),
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = { navigateToCategoryPropertiesScreen(category.id.toString()) }) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = "${category.name} properties"
                )
            }
        }
    }
}

@Composable
fun CategoryAdditionPopup(
    categoryValue: String,
    onValueChange: (newValue: String) -> Unit,
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
    enabled: Boolean,
    editStatus: EditStatus,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        title = {
            Text(text = "New category")
        },
        text = {
               Column {
                   Text(
                       text = "This action cannot be undone if properties are added under this category",
                       fontWeight = FontWeight.Bold
                   )
                   Spacer(modifier = Modifier.height(10.dp))
                   Text(text = "Type category name:")
                   Spacer(modifier = Modifier.height(10.dp))
                   InputForm(
                       value = categoryValue,
                       label = "Category name",
                       onValueChange = onValueChange,
                       keyboardOptions = KeyboardOptions.Default.copy(
                           imeAction = ImeAction.Done,
                           keyboardType = KeyboardType.Text
                       ),
                       leadingIcon = R.drawable.category,
                       isError = false
                   )
               }
        },
        confirmButton = {
            Button(
                enabled = enabled,
                onClick = onConfirm
            ) {
                if(editStatus == EditStatus.LOADING) {
                    CircularProgressIndicator()
                } else {
                    Text(text = "Add category")
                }
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text(text = "Cancel")
            }
        },
        onDismissRequest = onDismissRequest
    )
}
@Preview(showBackground = true)
@Composable
fun CategoriesScreenPreview() {
    PropEaseAdminTheme {
        CategoriesScreen(
            categories = categories,
            onAddCategory = {}
        )
    }
}